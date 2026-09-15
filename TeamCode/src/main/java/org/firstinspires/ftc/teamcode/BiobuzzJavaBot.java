package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.JavaDrivetrain;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class BiobuzzJavaBot implements NextRobot {
    public JavaDrivetrain drivetrain = new JavaDrivetrain();

    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(drivetrain);
    }
}