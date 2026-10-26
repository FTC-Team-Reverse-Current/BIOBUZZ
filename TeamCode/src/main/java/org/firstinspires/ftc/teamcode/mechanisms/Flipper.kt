package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Mechanism
import kotlin.math.floor

val FLIPPER_MIN_RANGE = 0.0..0.05

const val FLIPPER_MIN_POS = 0.0
const val FLIPPER_MAX_POS = 0.7

class Flipper : Mechanism {
    val flipperServo = NextServo("FlipperServo")

    fun flip() =
        instant {
            if (flipperServo.position in FLIPPER_MIN_RANGE) {
                flipperServo.position = FLIPPER_MAX_POS
            } else {
                flipperServo.position = FLIPPER_MIN_POS
            }
        }
}
