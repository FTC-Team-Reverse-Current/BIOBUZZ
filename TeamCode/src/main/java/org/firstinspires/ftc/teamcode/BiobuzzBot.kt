package org.firstinspires.ftc.teamcode

import dev.nextftc.hardware.sensors.NextPinpoint
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.P1Mechanisms
import org.firstinspires.ftc.teamcode.mechanisms.P2Mechanisms
import org.firstinspires.ftc.teamcode.mechanisms.TurretRotation


class BiobuzzBot : NextRobot {

    @Suppress("EmptySecondaryConstructor") // Get Detekt off our assess
    constructor() {
    }

    val drivetrain = Drivetrain()
    val p1mechanisms = P1Mechanisms()

    val p2mechanisms = P2Mechanisms()
    val turret = TurretRotation()

    override val mechanisms: Set<Mechanism> = setOf(drivetrain, p1mechanisms, p2mechanisms, turret)
}
