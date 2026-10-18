package org.firstinspires.ftc.teamcode

import dev.nextftc.hardware.sensors.NextPinpoint
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.P1Mechanisms


class BiobuzzBot : NextRobot {
    @Suppress("EmptySecondaryConstructor") // Get Detekt off our assess
    constructor() {}

    val drivetrain = Drivetrain()
    val p1mechanisms = P1Mechanisms()

    override val mechanisms: Set<Mechanism> = setOf(drivetrain, p1mechanisms)
}
