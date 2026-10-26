package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.units.volts

class Intake : Mechanism {
    val intakeMotor = NextMotor("IntakeMotor")

    constructor() {
        intakeMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }

    fun setThrottle(throttle: Double) = infinite { intakeMotor.throttle = throttle }

    fun setVoltage(voltage: Double) =
        infinite {
            intakeMotor.voltage = voltage.volts
        }.setEnd { intakeMotor.voltage = 0.0.volts }
}
