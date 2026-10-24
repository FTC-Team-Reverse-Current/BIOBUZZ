package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.units.volts

class Kicker : Mechanism {
    val kickerMotor = NextMotor("KickerMotor")

    constructor() {
        kickerMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.FLOAT
        kickerMotor.direction = NextMotor.Direction.FORWARD
    }

    val kickVoltage = (-9.0).volts

    fun dump() = infinite {
        kickerMotor.voltage = -kickVoltage
    }.setEnd { kickerMotor.voltage = 0.0.volts }

    fun kick() = infinite {
        kickerMotor.voltage = kickVoltage
    }.setEnd { kickerMotor.voltage = 0.0.volts }

    fun setVoltage(voltage: Double) = infinite { kickerMotor.voltage = voltage.volts}
}