package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.triggers.CommandGamepad

class Intake : Mechanism {
    val intakeMotor = NextMotor("IntakeMotor")

    constructor() {
        intakeMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }

    fun runIntake(throttle: Double) = infinite { intakeMotor.throttle = throttle }
}