package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.pedropathing.ivy.commands.Commands.infinite
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.groups.Groups.parallel
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.coroutine.command
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.units.RotationsPerMinute
import org.firstinspires.ftc.teamcode.BiobuzzBot
import org.firstinspires.ftc.teamcode.mechanisms.SizeState
import kotlin.math.abs

const val MIN_TRIGGER = 0.1
const val INTAKE_VOLTAGE = 6.0

val ANALOG_POS_RANGE = 0.1..1.0
val ANALOG_NEG_RANGE = -1.0..-0.1

const val DEFAULT_TURRET_POS = 0.5

const val SHOOTER_RPM_INCREMENT = 0.2

@NextTeleop(name = "New TeleOp For Competition")
class NewTele(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    override fun periodic() {
//        Telemetry.log("Currently Running!")
//        Telemetry.log("GP2 Left Trigger Value", gamepad2.left_trigger.toDouble())
//        Telemetry.log("Aux Left Stick X", gamepad2.left_stick_x.toDouble())
        Telemetry.log(
            "Current RPM: ",
            robot.shooter.shooterMotor.encoderVelocity
                .into(RotationsPerMinute)
                .toString(),
        )
        Telemetry.log(
            "Desired: ",
            robot.turret.sizeState.rpm
                .toString(),
        )

        @Suppress("MaxLineLength", "MagicNumber")
        Telemetry.log(
            (
                abs(
                    robot.shooter.shooterMotor.encoderVelocity
                        .into(RotationsPerMinute) - robot.turret.sizeState.rpm * 500,
                ) <= 50
            ).toString(),
            abs(
                robot.shooter.shooterMotor.encoderVelocity
                    .into(RotationsPerMinute) - robot.turret.sizeState.rpm * 500,
            ),
        )
    }

    override fun start() {
        robot.drivetrain.startDrivetrain(gamepad1)

        val driver = CommandGamepad(gamepad1)
        val aux = CommandGamepad(gamepad2)

        robot.turret.setSizeState(SizeState.POLLEN)

        driver.rightTrigger.isOver(MIN_TRIGGER).whileTrue(robot.intake.setVoltage(INTAKE_VOLTAGE))
        driver.leftTrigger.isOver(MIN_TRIGGER).whileTrue(robot.flipper.flip())

//        aux.rightTrigger.isOver(0.1).whileTrue(runShooterCycleNectar()).whileFalse(stopShooterCycle())
        aux.rightTrigger.isOver(MIN_TRIGGER).whileTrue(robot.shootWhenReady { robot.turret.sizeState })
        aux.leftTrigger.isOver(MIN_TRIGGER).whileTrue(robot.fullDump())

        aux.leftStickX.isBetween(ANALOG_POS_RANGE).whileTrue(
            robot.turret.turnTurret {
                -gamepad2.left_stick_x.toDouble()
            },
        )
        aux.leftStickX.isBetween(ANALOG_NEG_RANGE).whileTrue(
            robot.turret.turnTurret {
                -gamepad2.left_stick_x.toDouble()
            },
        )

        aux.a.onTrue(robot.turret.turretGoto(DEFAULT_TURRET_POS))
        aux.y.onTrue(robot.turret.setSizeState(SizeState.POLLEN))
        aux.b.onTrue(robot.turret.setSizeState(SizeState.NECTAR))

        aux.dpadUp.onTrue(robot.shooter.changeRPM(SHOOTER_RPM_INCREMENT))
        aux.dpadDown.onTrue(robot.shooter.changeRPM(-SHOOTER_RPM_INCREMENT))

        // TODO Auto
    }
}
