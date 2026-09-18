package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.hardware.RobotController
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.drive.mecanumDriveFieldCentric
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

class Drivetrain : Mechanism {
    val frontLeft = NextMotor(RobotController.controlHub, 0)
    val frontRight = NextMotor(RobotController.controlHub, 1)
    val backLeft = NextMotor(RobotController.controlHub, 2)
    val backRight = NextMotor(RobotController.controlHub, 3)

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

    fun startDrivetrain(gamepad: Gamepad) {
        mecanumDriveFieldCentric(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gamepad,
            { imu.yawPitchRollAngles.getYaw(AngleUnit.RADIANS) },
        ).schedule()
    }
}
