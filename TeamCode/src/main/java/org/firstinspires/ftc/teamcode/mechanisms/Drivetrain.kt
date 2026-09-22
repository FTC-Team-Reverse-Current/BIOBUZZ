package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.robot.Mechanism
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.overrides.MecanumKinematics
import org.firstinspires.ftc.teamcode.overrides.mecanumDrive

const val STRAFE_COMPENSATION = 1.2

class Drivetrain : Mechanism {
    val frontLeft = NextMotor("frontLeft")
    val frontRight = NextMotor("frontRight")
    val backLeft = NextMotor("backLeft")
    val backRight = NextMotor("backRight")

    val imu = NextIMU("imu")

    constructor() {
        imu.initialize(
            IMU.Parameters(
                RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD,
                ),
            ),
        )

        frontRight.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        backRight.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        frontLeft.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        backLeft.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }

    fun startDrivetrain(gamepad: Gamepad) {
        mecanumDrive(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gamepad,
            MecanumKinematics(STRAFE_COMPENSATION),
        ).schedule()
    }
}
