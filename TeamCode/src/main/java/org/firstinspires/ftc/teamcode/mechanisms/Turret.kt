package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextServo

class Turret : Mechanism {
    val turretServo = NextServo("TurretServo", cacheTolerance = 0.0005)

    fun turnTurret(turnSpeed: () -> Double) = infinite {
        turretServo.position =
            (turretServo.position +  (turnSpeed() * 0.1) ).coerceIn(0.2, 0.8)
    }

    fun turretGoto(position: Double) = instant {
        turretServo.position = position
    }
}