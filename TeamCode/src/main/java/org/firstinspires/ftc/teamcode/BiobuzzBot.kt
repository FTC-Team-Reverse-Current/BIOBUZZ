package org.firstinspires.ftc.teamcode

import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.Flipper
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Kicker
import org.firstinspires.ftc.teamcode.mechanisms.Shooter
import org.firstinspires.ftc.teamcode.mechanisms.Turret


class BiobuzzBot : NextRobot {

    @Suppress("EmptySecondaryConstructor") // Get Detekt off our assess
    constructor() {
    }

    val drivetrain = Drivetrain()
    val intake = Intake()

    val shooter = Shooter()
    val turret = Turret()

    val kicker = Kicker()

    val flipper = Flipper()

    override val mechanisms: Set<Mechanism> = setOf(drivetrain, intake, shooter, turret, kicker, flipper)
}
