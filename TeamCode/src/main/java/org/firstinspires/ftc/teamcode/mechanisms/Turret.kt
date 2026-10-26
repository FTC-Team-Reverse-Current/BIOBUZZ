package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.ivy.Command
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry

const val POLLEN_SERVO_POS = 0.0
const val NECTAR_SERVO_POS = 0.667
const val CACHE_TOLERANCE = 0.0005
const val TURN_INPUT_COEF = 0.01
val TURRET_ROTATION_RANGE = 0.2..0.8

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

    var sizeState: SizeState = SizeState.POLLEN

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
