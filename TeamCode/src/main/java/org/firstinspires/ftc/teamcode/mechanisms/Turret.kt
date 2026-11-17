package org.firstinspires.ftc.teamcode.mechanisms

import com.aaravdhawan25.pidautotuner.PIDFController
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.follower.Follower
import com.pedropathing.ivy.Command
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
    val turretMotor = NextMotor("TurretMotor")
    val sizeServo = NextServo("BackServo")

    val controller = PIDFController(1.0, 0.0, 0.0, 0.0)

    var poseFunc: () -> Pose

    constructor(poseFunc: () -> Pose) {
        this.poseFunc = poseFunc
    }

    var sizeState: SizeState = SizeState.POLLEN

    fun autoAim() = infinite {
        val pose = poseFunc()
        var closest = Pair(Pose(0.0, 0.0), Double.POSITIVE_INFINITY)
        for (hive in HivePositions.entries) {
            val dist = pose.distance(hive.pos)
            if (dist < closest.second) {
                closest = Pair(hive.pos, dist)
            }
        }

        val dx = closest.first.x() - pose.x()

        // Make it negative because Pedro makes left positive and right negative
        val dy = -(closest.first.y() - pose.y())

        // Remember X and Y are flipped, so you need to swap them
        val fieldRelativeAngle = atan2(dx, dy)

        // Subtract PI from the robot heading to change its range from (0 - 2pi) => (-pi - pi)
        val robotRelativeAngle = fieldRelativeAngle - (pose.heading() - Math.PI)

        val robotRelativeDegrees = Math.toDegrees(robotRelativeAngle)

        // Don't use built-in PID controller
        this.controller.setOutputBounds(-1.0, 1.0)

        val desiredTicks = robotRelativeDegrees * (14/180)
        val output = this.controller.calculate(desiredTicks, turretMotor.absoluteEncoderPosition.into(Degrees) * (14/180))

        turretMotor.throttle = output
    }

//    fun autoAim() = infinite {
//        val pose = follower()
//        var closest = Pair(Pose(0.0, 0.0), Double.POSITIVE_INFINITY)
//        for (hive in HivePositions.entries) {
//            val dist = pose.distance(hive.pos)
//            if (dist < closest.second) {
//                closest = Pair(hive.pos, dist)
//            }
//        }
//
//        val dx = closest.first.x() - pose.x()
//        val dy = closest.first.y() - pose.y()
//
//        val targetAngle = atan2(dy, dx) * (180 / PI)
//        val robotAngle = pose.heading() * (180 / PI)
//
//        val relativeAngle = targetAngle - robotAngle
//
//        val normalizedAngle = ((relativeAngle % 360.0) + 360.0) % 360.0
//
//        // TODO: Change based on totol range (Here it is 0.0 to 1.0)
//        val desiredAngle = (normalizedAngle / 360)
//        turretServo.position = (desiredAngle).coerceIn(0.1, 0.9)
//
//        val packet = TelemetryPacket()
//        packet.put("ClosestHive", closest.first)
//        packet.put("targetAngle", targetAngle)
//        packet.put("RobotAngle", robotAngle)
//        packet.put("RelativeAngle", relativeAngle)
//        packet.put("NormalizedAngle", normalizedAngle)
//        packet.put("DesiredAngle", desiredAngle)
//        packet.put("FinalAngle", desiredAngle.coerceIn(0.1, 0.9))
//
//        FtcDashboard.getInstance().sendTelemetryPacket(packet)
//    }

    fun setSizeState(state: SizeState) =
        instant {
            sizeState = state
            sizeServo.position = state.position
        }

    fun turnTurret(turnSpeed: () -> Double) = infinite {
        this.turretMotor.throttle = turnSpeed() * 0.1
    }

    fun turretGoto(position: Double) = instant {
        // Half a rotation since the input will be from -180 to 180
        val desiredTicks = position * (14/180)
        while (abs(position - Math.toDegrees(turretMotor.absoluteEncoderPosition.magnitude)) > 10.0) {
            this.controller.setOutputBounds(-1.0, 1.0)
            val output = controller.calculate(desiredTicks, Math.toDegrees(turretMotor.absoluteEncoderPosition.magnitude) * (14/180))
            turretMotor.throttle = output
        }
    }
}