package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.ivy.Command
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.robot.Mechanism
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.drive.mecanumDriveFieldCentric
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

class TestingMechanisms : Mechanism {

    val TestingServo = NextServo("TestingServo")
    val TestingMotor = NextMotor("TestingMotor")
    fun motorOn() = infinite { TestingMotor.throttle = -1.0 }
    fun motorOff() = instant { TestingMotor.throttle = 0.0 }

    fun ServoOn() = infinite { TestingServo.position = TestingServo.position+0.1 }
    fun Servo1Off() = infinite { TestingServo.position = TestingServo.position}
    fun ServoOff() = infinite {TestingServo.position = TestingServo.position-0.1}

    fun InitTestingMechanisms(gamepad : Gamepad) {
        val gamepad = CommandGamepad(gamepad)

        gamepad.a.onTrue(motorOn())
        gamepad.a.onFalse(motorOff())

        gamepad.b.onTrue(ServoOn())
        gamepad.b.onFalse(Servo1Off())
        gamepad.x.onTrue(ServoOff())
        gamepad.x.onFalse(Servo1Off())
    }

}