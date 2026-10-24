package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.ivy.Command
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Telemetry

enum class SizeState(var rpm: Double, var position: Double) {
    POLLEN(Shooter.pollenShootRPM, 0.0), NECTAR(Shooter.nectarShootRPM, 1.0)
}
class Turret : Mechanism {
    val turretServo = NextServo("TurretServo", cacheTolerance = 0.0005)
    val sizeServo = NextServo("BackServo")

    var sizeState: SizeState = SizeState.POLLEN

    fun setSizeState(state: SizeState) = instant {
        sizeState = state
        sizeServo.position = state.position
    }

    fun turnTurret(turnSpeed: () -> Double) = infinite {
        turretServo.position =
            (turretServo.position +  (turnSpeed() * 0.01) ).coerceIn(0.2, 0.8)
    }

    fun turretGoto(position: Double) = instant {
        turretServo.position = position
    }
}