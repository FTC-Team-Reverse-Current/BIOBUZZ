package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextServo
import kotlin.math.floor

class Flipper : Mechanism {
    val flipperServo = NextServo("FlipperServo")

    var flipped: Boolean = false

    fun flip() = instant {
        if (flipped) {
            flipperServo.position = 0.7
        }
        else {
            flipperServo.position = 0.0
        }
    }
}