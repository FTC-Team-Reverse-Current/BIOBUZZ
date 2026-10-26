package org.firstinspires.ftc.teamcode

import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.commands.Commands.waitUntil
import com.pedropathing.ivy.groups.Groups.parallel
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import dev.nextftc.robot.coroutine.command
import dev.nextftc.units.RotationsPerMinute
import dev.nextftc.units.measuretypes.AngularVelocity
import dev.nextftc.units.volts
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.Flipper
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Kicker
import org.firstinspires.ftc.teamcode.mechanisms.Shooter
import org.firstinspires.ftc.teamcode.mechanisms.SizeState
import org.firstinspires.ftc.teamcode.mechanisms.Turret
import kotlin.math.abs

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
    val turret = Turret()

    val kicker = Kicker()

    val flipper = Flipper()

    override val mechanisms: Set<Mechanism> = setOf(drivetrain, intake, shooter, turret, kicker, flipper)

    fun fullDump() =
        parallel(
            kicker.dump(),
            intake.setVoltage(-INTAKE_VOLTAGE),
        )

    fun shootWhenReady(sizeState: () -> SizeState) =
        parallel(
            shooter.automatedShoot(sizeState()),
            kicker.kick(),
        )

    fun moveForward(seconds: Double) =
        command {
            await(drivetrain.moveForward(AUTO_THROTTLE).raceWith(waitMs(seconds * SECONDS_TO_MS)))
        }

    fun runShootCycle() =
        parallel(
            shooter.setVelocity(DEFAULT_SHOOT_RPM),
            kicker.kick(),
        )

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
