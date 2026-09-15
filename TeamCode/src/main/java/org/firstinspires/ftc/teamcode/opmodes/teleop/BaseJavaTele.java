package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BiobuzzJavaBot;

import dev.nextftc.robot.Telemetry;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;

@NextTeleop(name = "Java TeleOP")
public class BaseJavaTele extends NextOpMode {
    private final BiobuzzJavaBot robot;
    private final Gamepad gamepad = new Gamepad();

    public BaseJavaTele(BiobuzzJavaBot robot) {
        super(robot);
        this.robot = robot;
    }

    @Override
    public void periodic() {
        Telemetry.log("Java functions are working!");
    }

    @Override
    public void start() {
        robot.drivetrain.initJavaIMU();
        robot.drivetrain.startJavaDrivetrain(gamepad);
    }
}
