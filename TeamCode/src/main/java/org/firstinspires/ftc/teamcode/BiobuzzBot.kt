package org.firstinspires.ftc.teamcode

import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain

class BiobuzzBot : NextRobot {
    @Suppress("EmptySecondaryConstructor") // Get Detekt off our assess
    constructor() {}

    val drivetrain = Drivetrain()

    override val mechanisms: Set<Mechanism> = setOf(drivetrain)
}
