package org.firstinspires.ftc.teamcode

import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.groups.Groups.parallel
import dev.nextftc.robot.Mechanism
import dev.nextftc.v2.pedro.PedroMechanism
import dev.nextftc.robot.NextRobot
import dev.nextftc.robot.coroutine.command
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.Flipper
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Shooter
import org.firstinspires.ftc.teamcode.mechanisms.SizeState
import org.firstinspires.ftc.teamcode.mechanisms.Transfer
import org.firstinspires.ftc.teamcode.mechanisms.Turret
import org.firstinspires.ftc.teamcode.pedro.Constants

const val INTAKE_VOLTAGE = -6.0
const val AUTO_THROTTLE = 0.3
const val STRAFE_THROTTLE = 0.5
const val AUTO_STRAFE_MS = 350.0
const val SECONDS_TO_MS = 1000.0
const val DEFAULT_SHOOT_RPM = 4500.0

class BiobuzzBot : NextRobot {
    @Suppress("EmptySecondaryConstructor") // Get Detekt off our assess
    constructor() {}

    val drivetrain = Drivetrain()
    val intake = Intake()

    val shooter = Shooter()
    val flipper = Flipper()
    val pedro = PedroMechanism(Constants::create)
    val transfer = Transfer()

    val turret = Turret { pedro.follower.pose() }
//    val d = NextMecanum(hardwareMap,Constants.drivetrainConfig)

    override val mechanisms: Set<Mechanism> = setOf(drivetrain, intake, shooter, turret, flipper, pedro, transfer)



    fun moveForward(seconds: Double) =
        command {
            await(drivetrain.moveForward(AUTO_THROTTLE).raceWith(waitMs(seconds * SECONDS_TO_MS)))
        }

    fun strafeRightMoveForward(seconds: Double) =
        command {
            await(drivetrain.strafeRight(STRAFE_THROTTLE).raceWith(waitMs(AUTO_STRAFE_MS)))
            await(drivetrain.moveForward(AUTO_THROTTLE).raceWith(waitMs(seconds * SECONDS_TO_MS)))
        }

    fun strafeLeftMoveForward(seconds: Double) =
        command {
            await(drivetrain.strafeLeft(STRAFE_THROTTLE).raceWith(waitMs(AUTO_STRAFE_MS)))
            await(drivetrain.moveForward(AUTO_THROTTLE).raceWith(waitMs(seconds * SECONDS_TO_MS)))
        }

    fun strafeLeft(seconds: Double) =
        command {
            await(drivetrain.moveForward(AUTO_THROTTLE).raceWith(waitMs(seconds * SECONDS_TO_MS)))
        }
}
