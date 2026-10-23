package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.pedropathing.ivy.commands.Commands.infinite
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.groups.Groups.parallel
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.coroutine.command
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.teamcode.BiobuzzBot

@NextTeleop(name = "New TeleOp For Competition")
class NewTele(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {

    override fun periodic() {
        Telemetry.log("Currently Running!")
        Telemetry.log("GP2 Left Trigger Value", gamepad2.left_trigger.toDouble())
        Telemetry.log("Aux Left Stick X", gamepad2.left_stick_x.toDouble())
    }

    override fun start() {
        robot.drivetrain.startDrivetrain(gamepad1)

        val driver = CommandGamepad(gamepad1)
        val aux = CommandGamepad(gamepad2)

        fun runShooterCycle() = parallel(
            robot.shooter.setVelocity(4500.0),
            robot.kicker.setVoltage(9.0)
        )

        fun stopShooterCycle() = parallel(
            robot.shooter.setVelocity(0.0),
            robot.kicker.setVoltage(0.0)
        )

        driver.rightTrigger.isOver(0.1).whileTrue(robot.intake.runIntake(0.7)).whileFalse(robot.intake.runIntake(0.0))

        aux.leftTrigger.isOver(0.1).whileTrue(runShooterCycle()).whileFalse(stopShooterCycle())

//        aux.leftStickX.isBetween(-1.0, 1.0).whileTrue(robot.turret.turnTurret(fun() = aux.leftStickX.value))
        robot.turret.defaultCommand

        aux.a.onTrue(robot.turret.turretGoto(0.5))
        aux.y.onTrue(robot.turret.turretGoto(0.75))

        aux.b.onTrue(robot.flipper.flip())
    }
}