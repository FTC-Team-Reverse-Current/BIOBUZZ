package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.ivy.Command
import com.pedropathing.math.Vector2D
import dev.nextftc.control.geometry.Pose2d
import dev.nextftc.control.geometry.Vector2d
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.hardware.sensors.NextPinpoint
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry
import dev.nextftc.units.Unit
import dev.nextftc.units.degrees
import dev.nextftc.units.inches
import dev.nextftc.units.meters
import dev.nextftc.units.unittypes.DistanceUnit
import kotlin.math.PI
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
    var pos: Pose2d
) {
    RED_LEFT_POS(Pose2d(58.0.inches, 85.0.inches, 0.0.degrees)),
    RED_RIGHT_POS(Pose2d(84.0.inches, 85.0.inches, 0.0.degrees)),
    BLUE_LEFT_POS(Pose2d(58.0.inches, 58.0.inches, 0.0.degrees)),
    BLUE_RIGHT_POS(Pose2d(84.0.inches, 58.0.inches, 0.0.degrees))
}

enum class SizeState(
    var rpm: Double,
    var position: Double,
) {
    POLLEN(Shooter.pollenShootRPM, POLLEN_SERVO_POS),
    NECTAR(Shooter.nectarShootRPM, NECTAR_SERVO_POS),
}

class Turret : Mechanism {
    val turretServo = NextServo("TurretServo", cacheTolerance = CACHE_TOLERANCE)
    val sizeServo = NextServo("BackServo")

    var pinpoint: NextPinpoint

    constructor(pinpoint: NextPinpoint) {
        this.pinpoint = pinpoint
    }

    var sizeState: SizeState = SizeState.POLLEN

    override val defaultCommand: Command = infinite {
        val pose = pinpoint.pose
        var closest = Pair(Pose2d(0.0.inches, 0.0.inches, 0.0.degrees), Double.POSITIVE_INFINITY)
        for (hive in HivePositions.entries) {
            val dist = pose.distanceTo(hive.pos)
            if (dist < closest.second) {
                closest = Pair(hive.pos, dist)
            }
        }

        val dx = closest.first.position.x.magnitude - pose.position.x.magnitude
        val dy = closest.first.position.y.magnitude - pose.position.y.magnitude

        val targetAngle = atan2(dy, dx) * (180 / PI)
        val robotAngle = pose.position.angle().toDouble()

        val relativeAngle = targetAngle - robotAngle

        val normalizedAngle = ((relativeAngle % 360.0) + 360.0) % 360.0

        // TODO: Change based on totol range (Here it is 0.0 to 1.0)
        turretServo.position = normalizedAngle / 360
    }

    fun setSizeState(state: SizeState) =
        instant {
            sizeState = state
            sizeServo.position = state.position
        }

    fun turnTurret(turnSpeed: () -> Double) =
        infinite {
            turretServo.position =
                (turretServo.position + (turnSpeed() * TURN_INPUT_COEF)).coerceIn(TURRET_ROTATION_RANGE)
        }

    fun turretGoto(position: Double) =
        instant {
            turretServo.position = position
        }
}
