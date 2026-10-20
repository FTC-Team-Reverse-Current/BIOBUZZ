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

class P2Mechanisms : Mechanism {
    val Shooter = NextMotor("ShooterMotor")
    val Flipper = NextServo("FlipperServo")

    constructor() {
        Shooter.direction = NextMotor.Direction.REVERSE
    }

//    fun StartP2Mechanisms(gamepad: Gamepad) {
//        val gp2 = CommandGamepad(gamepad)
//
//        fun flipdown() = instant {Flipper.position=0.5}
//        fun flipup() = instant {Flipper.position=0.0}
//
//        gp2.leftTrigger.isOver(0.5).whileTrue(runShooter(AngularVelocity(60.0, RotationsPerMinute)))
//        gp2.dpadUp.onTrue(flipup())
//        gp2.dpadDown.onFalse(flipdown())
//    }

    override fun periodic() {
        super.periodic()

    }

    fun runShooter(throttle: Double) = infinite {
        Shooter.throttle = throttle
    }
}