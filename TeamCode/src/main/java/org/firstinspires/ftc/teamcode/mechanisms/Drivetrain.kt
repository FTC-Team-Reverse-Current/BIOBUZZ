package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.control.feedback.PIDCoefficients
import dev.nextftc.control.feedback.PIDController
import org.firstinspires.ftc.teamcode.overrides.MecanumKinematics
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.robot.Mechanism
import dev.nextftc.units.DegreesPerSecond
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.overrides.mecanumDrive

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
                    RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD,
                ),
            ),
        )
    }

    fun initDriveSettings() {
        frontLeft.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        frontRight.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        backLeft.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        backRight.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }

    fun startDrivetrain(gamepad: Gamepad) {

        val pid = PIDController(PIDCoefficients(kP = 0.01, kI = 0.0, kD = 0.001))

        mecanumDrive(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gamepad,
            MecanumKinematics(1.2)
        ).schedule()
    }
}