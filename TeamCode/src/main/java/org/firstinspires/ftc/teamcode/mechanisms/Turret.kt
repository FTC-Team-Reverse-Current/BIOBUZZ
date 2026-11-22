package org.firstinspires.ftc.teamcode.mechanisms

import com.aaravdhawan25.pidautotuner.PIDFController
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.canvas.Rotation
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.follower.Follower
import com.pedropathing.follower.FollowerLog
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.behaviors.InterruptedBehavior
import com.pedropathing.math.Pose
import com.pedropathing.math.Vector2D
import dev.nextftc.control.geometry.Pose2d
import dev.nextftc.control.geometry.Vector2d
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.hardware.sensors.NextPinpoint
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry
import dev.nextftc.units.Degrees
import dev.nextftc.units.Rotations
import dev.nextftc.units.Unit
import dev.nextftc.units.degrees
import dev.nextftc.units.inches
import dev.nextftc.units.meters
import dev.nextftc.units.unittypes.DistanceUnit
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

const val POLLEN_SERVO_POS = 0.0
const val NECTAR_SERVO_POS = 0.667
const val CACHE_TOLERANCE = 0.0005
const val TURN_INPUT_COEF = 0.01
val TURRET_ROTATION_RANGE = 0.2..0.8


// TODO: Change these values

enum class HivePositions(
    var pos: Pose
) {
    RED_BOTTOM(Pose(58.0, 58.0)),
    RED_TOP(Pose(58.0, 83.0)),
    BLUE_BOTTOM(Pose(83.0, 58.0)),
    BLUE_TOP(Pose(83.0, 83.0)),
}

enum class SizeState(
    var rpm: Double,
    var position: Double,
) {
    POLLEN(Shooter.pollenShootRPM, POLLEN_SERVO_POS),
    NECTAR(Shooter.nectarShootRPM, NECTAR_SERVO_POS),
}

class Turret : Mechanism {
    val turretMotor = NextMotor("TurretMotor", (360.0/3180.0).degrees, cacheTolerance = 0.001)
    val sizeServo = NextServo("BackServo")

    val controller = PIDFController(0.01674, 0.0, 0.00059, 0.0)

    var poseFunction: () -> Pose

    constructor(poseFunction: () -> Pose) {
        this.poseFunction = poseFunction
        turretMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }

    var sizeState: SizeState = SizeState.POLLEN

    override var defaultCommand: Command = infinite {
        val packet = TelemetryPacket()
        val pose = poseFunction()

        val closest = HivePositions.entries.minByOrNull { pose.distance(it.pos) }

        if (closest == null) return@infinite

        val dx = closest.pos.x() - pose.x()

        // Make it negative because Pedro makes left positive and right negative
        val dy = -(closest.pos.y() - pose.y())

        // Remember X and Y are flipped, so you need to swap them
        val fieldRelativeAngle = atan2(dx, dy)
        packet.put("Field Relative Angle", fieldRelativeAngle)

        val heading = pose.heading()
        packet.put("Command Reported Heading", heading)

        val robotRelativeAngle = fieldRelativeAngle - heading
        packet.put("Robot Relative Angle", robotRelativeAngle)

        // In the case that robotRelative is negative you need to convert it
        val positiveRobotRadians = if (robotRelativeAngle < 0.0) (robotRelativeAngle + (2.0 * PI)) else robotRelativeAngle
        packet.put("Positive Robot Radians", positiveRobotRadians)

        // Then convert it to revolutions so we can convert it to ticks
        val positiveRobotTicks = (positiveRobotRadians / (2.0 * PI)) * 3180.0
        packet.put("Positive Robot Ticks", positiveRobotTicks)

        // Don't use built-in PID controller
        this.controller.setOutputBounds(-0.5, 0.5)

        var currentPos = turretMotor.encoderPosition.into(Rotations) * 3180.0

        var error = positiveRobotTicks - currentPos
        packet.put("Error", error)

        var output = this.controller.calculate(
            positiveRobotTicks,
            currentPos
        )

        turretMotor.throttle = -output
        packet.put("Output", output)

        packet.put("Closest Hive", closest.name)
        packet.put("Desired Ticks", positiveRobotTicks)
        packet.put("Current Pos", currentPos)

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }.setEnd { turretMotor.throttle = 0.0 }.setInterruptedBehavior(InterruptedBehavior.END)

    fun setSizeState(state: SizeState) =
        instant {
            sizeState = state
            sizeServo.position = state.position
        }

    fun turnTurret(turnSpeed: () -> Double) = infinite {
        this.turretMotor.throttle = turnSpeed() / 2.0
    }

    fun turretGoto(position: Double) = infinite {
        val packet = TelemetryPacket()
        val desiredTicks = (position / 360.0) * 560.0
        val currentTicks = turretMotor.encoderPosition.into(Rotations) * 560.0

        controller.setOutputBounds(-1.0, 1.0)

        packet.put("Desired Position", desiredTicks)
        packet.put("Current Position", currentTicks)

        if (abs(desiredTicks - currentTicks) > 10.0) {
            val output = controller.calculate(desiredTicks, currentTicks)
            packet.put("Output", output)
        }
    }.setEnd { turretMotor.throttle = 0.0 }.setInterruptedBehavior(InterruptedBehavior.END)
}