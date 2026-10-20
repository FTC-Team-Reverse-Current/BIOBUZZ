package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.ivy.Command
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.units.RotationsPerMinute
import dev.nextftc.units.measuretypes.AngularVelocity
import dev.nextftc.units.unittypes.AngularVelocityUnit

class TurretRotation : Mechanism {
    val Turret = NextServo("TurretServo")

    constructor() {
    }

    override fun periodic() {
        super.periodic()

    }

    fun turnTurret(turnspeed: Double) = infinite {
        Turret.position += turnspeed
    }
}