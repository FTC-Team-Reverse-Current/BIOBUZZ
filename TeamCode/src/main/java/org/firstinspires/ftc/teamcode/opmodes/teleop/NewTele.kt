package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.follower.ManualDrive
import com.pedropathing.ivy.commands.Commands.infinite
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.groups.Groups.parallel
import dev.nextftc.hardware.lynx.NextLynxModule
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.coroutine.command
import dev.nextftc.robot.opmode.BlazeBulkReadHook
import dev.nextftc.robot.opmode.BlazeOpMode
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.units.RotationsPerMinute
import dev.nextftc.units.inches
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
) : BlazeOpMode(robot, BlazeBulkReadHook(NextLynxModule.Type.CONTROL_HUB, false)) {
    override fun periodic() {

        Telemetry.log(
            "Current RPM: ",
            robot.shooter.shooterMotor.encoderVelocity
                .into(RotationsPerMinute)
                .toString(),
        )
        Telemetry.log(
            "Robot Pos X:",
            robot.pedro.follower.pose().y()
        )
        Telemetry.log(
            "Robot Pos Y:",
            robot.pedro.follower.pose().x()
        )
        Telemetry.log(
            "Robot Pos Heading:",
            robot.pedro.follower.pose().heading()
        )
        Telemetry.log(
            "Servo Pos:",
            robot.turret.turretServo.position
        )

        val packet = TelemetryPacket()
        val currentPose = robot.pedro.follower.pose()
        packet.put("Pose x", (currentPose.x() - 72))
        packet.put("Pose y", (72 - currentPose.y()))
        packet.put("Left Servo Pose", robot.flower.leftServo.position)
        packet.put("Right Servo Pose", robot.flower.rightServo.position)

        packet.put("Pose heading", (currentPose.heading() + ((Math.PI) / 2)))
        packet.fieldOverlay().setFill("blue").fillRect(-20.0, -20.0, 40.0, 40.0);

        FtcDashboard.getInstance().sendTelemetryPacket(packet)

//        @Suppress("MaxLineLength", "MagicNumber")
//        Telemetry.log(
//            (
//                abs(
//                    robot.shooter.shooterMotor.encoderVelocity
//                        .into(RotationsPerMinute) - robot.turret.sizeState.rpm * 500,
//                ) <= 50
//            ).toString(),
//            abs(
//                robot.shooter.shooterMotor.encoderVelocity
//                    .into(RotationsPerMinute) - robot.turret.sizeState.rpm * 500,
//            ),
//        )

        val powers = ManualDrive.fieldCentric(
            -gamepad1.left_stick_y.toDouble(),
            -gamepad1.left_stick_x.toDouble(),
            gamepad1.right_stick_x.toDouble(),
            robot.pedro.follower.pose().heading()
        )
        ManualDrive.driveOrHold(robot.pedro.follower, powers);
        robot.pedro.follower.update()
    }

    public override fun onControlHubBulkData() {
        robot.transfer.updatePID()
        robot.shooter.updatePID()
    }


    override fun start() {
//        robot.drivetrain.startDrivetrain(gamepad1)
        val driver = CommandGamepad(gamepad1)
        val aux = CommandGamepad(gamepad2)

        robot.turret.setSizeState(SizeState.POLLEN)

        driver.rightTrigger.isOver(MIN_TRIGGER).whileTrue(robot.intake.setVoltage(INTAKE_VOLTAGE))

        driver.a.onTrue(robot.flower.flip())

        driver.y.onTrue(robot.resetPos())

        aux.rightTrigger.isOver(MIN_TRIGGER).whileTrue(robot.shoot()).onFalse(waitMs(800.0).then(robot.transfer.next90increment()))
        aux.b.onTrue(robot.transfer.turnTransfer())
        aux.y.onTrue(robot.transfer.dumpSingle())
        aux.a.whileTrue(robot.turret.autoAim())

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

        aux.dpadUp.onTrue(robot.shooter.increaseRPM())
        aux.dpadDown.onTrue(robot.shooter.decreaseRPM())
        aux.rightStickX.isBetween(ANALOG_POS_RANGE).whileTrue(
            robot.flower.turnFlower (
                (-gamepad2.left_stick_x.toDouble() + 1) / 2
            ),
        )
        aux.rightStickX.isBetween(ANALOG_NEG_RANGE).whileTrue(
            robot.flower.turnFlower (
                (gamepad2.left_stick_x.toDouble() + 1) / 2
            ),
        )

        // TODO Auto
    }
}
