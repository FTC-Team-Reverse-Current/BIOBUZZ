package org.firstinspires.ftc.teamcode

import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.TestingMechanisms

class BiobuzzBot : NextRobot {
    val drivetrain = Drivetrain()
    val testingmechanisms = TestingMechanisms()

    override val mechanisms: Set<Mechanism> = setOf(drivetrain, testingmechanisms)
}
