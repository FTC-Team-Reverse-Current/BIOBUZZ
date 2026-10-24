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

    fun fullDump() = parallel(
        kicker.dump(),
        intake.setVoltage(-6.0)
    )

    fun shootWhenReady(sizeState: () -> SizeState) = parallel(
            shooter.automatedShoot(sizeState()),
            kicker.kick())

    fun moveForward(seconds: Double) = command {
        try {
            fork(drivetrain.moveForward(0.3))
            wait(seconds)
        } finally {}
    }

    fun runShootCycle() = parallel(
        shooter.setVelocity(4500.0),
        kicker.kick()
    )

    fun strafeRightMoveForward(seconds: Double) = command {
        await(drivetrain.strafeRight(0.5).raceWith(waitMs(350.0)))
        await(drivetrain.moveForward(0.3).raceWith(waitMs(seconds*1000.0)))
    }

    fun strafeLeftMoveForward(seconds: Double) = command {
        await(drivetrain.strafeLeft(0.5).raceWith(waitMs(350.0)))
        await(drivetrain.moveForward(0.3).raceWith(waitMs(seconds*1000.0)))
    }

    fun strafeLeft(seconds: Double) = command {
        try {
            fork(drivetrain.moveForward(0.3))
            wait(seconds)
        } finally {}
    }
}
