package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.sensors.NextIMU;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.drive.DriveCommands;

public class JavaDrivetrain implements Mechanism {
    NextMotor frontLeft = new NextMotor("frontLeft");
    NextMotor frontRight = new NextMotor("frontRight");
    NextMotor backLeft = new NextMotor("backLeft");
    NextMotor backRight = new NextMotor("backRight");

    NextIMU imu = new NextIMU();

    public void initJavaIMU() {
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
    }

    public void startJavaDrivetrain(Gamepad gamepad) {
        DriveCommands.mecanumDriveFieldCentric(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gamepad,
            () -> imu.getYawPitchRollAngles().getYaw()
        ).schedule();
    }
}