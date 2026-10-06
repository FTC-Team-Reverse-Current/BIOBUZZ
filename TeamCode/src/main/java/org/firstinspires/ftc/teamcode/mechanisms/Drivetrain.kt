package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.drive.mecanumDriveFieldCentric
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

class Drivetrain : Mechanism {
    val frontLeft = NextMotor("frontLeft")
    val frontRight = NextMotor("frontRight")
    val backLeft = NextMotor("backLeft")
    val backRight = NextMotor("backRight")

    val imu = NextIMU("imu")

    fun imuInit() {
        imu.initialize(
            IMU.Parameters(
                RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD,
                ),
            ),
        )
    }

    fun AllMotorsOn() = infinite {
        frontLeft.throttle = 0.25
        frontRight.throttle = 0.25
        backLeft.throttle = 0.25
        backRight.throttle = 0.25
    }

    fun AllMotorsOff() = instant {
        frontLeft.throttle = 0.0
        frontRight.throttle = 0.0
        backLeft.throttle = 0.0
        backRight.throttle = 0.0
    }

    fun startDrivetrain(gamepad: Gamepad) {
        mecanumDriveFieldCentric(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gamepad,
            { imu.yawPitchRollAngles.getYaw(AngleUnit.RADIANS) },
        ).schedule()
        val gamepad = CommandGamepad(gamepad)
        gamepad.y.onTrue(AllMotorsOn())
        gamepad.y.onFalse(AllMotorsOff())

    }
}
