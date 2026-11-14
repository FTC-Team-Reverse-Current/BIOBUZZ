package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Mechanism

val LEFT_POS = doubleArrayOf(0.0, 0.25)
val RIGHT_POS = doubleArrayOf(0.0, 0.25)

class Flower : Mechanism {

    val leftServo = NextServo("flowerLeft")
    val rightServo = NextServo("flowerRight")

    var down : Boolean = false

    fun flip() = instant {
        val set = if (down) 1 else 0
        leftServo.position = LEFT_POS[set]
        rightServo.position = RIGHT_POS[set]
    }

    fun turnFlower(turnSpeed: () -> Double) =
        infinite {
            leftServo.position =
                (leftServo.position + (turnSpeed() * TURN_INPUT_COEF))
            rightServo.position =
                (rightServo.position - (turnSpeed() * TURN_INPUT_COEF))
        }
}