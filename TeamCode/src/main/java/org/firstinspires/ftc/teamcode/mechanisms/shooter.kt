package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Mechanism
import dev.nextftc.units.measuretypes.AngularVelocity

class Shooter : Mechanism {
    val shootMotor = NextMotor("shootMotor")
    val sizeAdjuster = NextServo("sizeAdjuster")

    constructor() {
        shootMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        shootMotor.velocityConstants.apply {kP = 1.0; kI = 0.0; kD = 0.0}
    }

    fun runShooter(velocity: AngularVelocity) = infinite {
        shootMotor.setVelocitySetpoint(velocity)
    }

    fun setServoPosition(position: Double) {
        sizeAdjuster.position = position
    }
}