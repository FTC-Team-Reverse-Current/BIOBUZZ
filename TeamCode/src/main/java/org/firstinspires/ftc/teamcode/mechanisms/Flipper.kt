package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Mechanism

val FLIPPER_LEFT_MIN_RANGE = 0.20..0.30
val FLIPPER_RIGHT_MIN_RANGE = 0.0..0.05

const val FLIPPER_LEFT_MIN_POS = 0.25
const val FLIPPER_RIGHT_MIN_POS = 0.0
const val FLIPPER_LEFT_MAX_POS = 0.0
const val FLIPPER_RIGHT_MAX_POS = 0.25

class Flipper : Mechanism {
    val flipperServoLeft = NextServo("flowerLeft")
    val flipperServoRight = NextServo("flowerRight")

    fun flip() =
        instant {
            if (flipperServoLeft.position in FLIPPER_LEFT_MIN_RANGE && flipperServoRight.position in FLIPPER_RIGHT_MIN_RANGE) {
                flipperServoLeft.position = FLIPPER_LEFT_MAX_POS
                flipperServoRight.position = FLIPPER_RIGHT_MAX_POS
            } else {
                flipperServoLeft.position = FLIPPER_LEFT_MIN_POS
                flipperServoRight.position = FLIPPER_RIGHT_MIN_POS
            }
        }
}
