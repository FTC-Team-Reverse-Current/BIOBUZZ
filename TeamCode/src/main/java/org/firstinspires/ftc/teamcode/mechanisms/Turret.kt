package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.ivy.Command
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextServo

class Turret(val joystick_value: () -> Double) : Mechanism {
    val turretServo = NextServo("TurretServo", cacheTolerance = 0.0005)

    fun turnTurret(turnSpeed: () -> Double) = infinite {

        turretServo.position =
            (turretServo.position +  (turnSpeed() * 0.1) ).coerceIn(0.2, 0.8)
    }

    override val defaultCommand: Command
        get() = turnTurret(fun() = joystick_value())

    fun turretGoto(position: Double) = instant {
        turretServo.position = position
    }
}