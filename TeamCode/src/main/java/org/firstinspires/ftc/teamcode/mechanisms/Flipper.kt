package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextServo
import kotlin.math.floor

class Flipper : Mechanism {
    val flipperServo = NextServo("FlipperServo")


    fun flip() = instant {
        if (flipperServo.position in 0.0..0.05) {
            flipperServo.position = 0.7
        }
        else {
            flipperServo.position = 0.0
        }
    }
}