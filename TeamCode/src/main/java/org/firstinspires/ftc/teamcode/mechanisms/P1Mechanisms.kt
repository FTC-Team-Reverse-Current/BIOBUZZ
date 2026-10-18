package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.triggers.CommandGamepad

class P1Mechanisms : Mechanism {
    val IntakeMotor = NextMotor("IntakeMotor")



    fun StartP1Mechanisms(gamepad: Gamepad) {
        val gp1 = CommandGamepad(gamepad)

        fun runintake() = infinite {IntakeMotor.throttle=0.7}
        fun stopintake() = instant {IntakeMotor.throttle=0.0}


        gp1.a.whileTrue(runintake())
        gp1.a.whileFalse(stopintake())
    }
}