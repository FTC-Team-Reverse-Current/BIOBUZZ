package org.firstinspires.ftc.teamcode.opmodes.teleop

import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.units.RotationsPerMinute
import dev.nextftc.units.measuretypes.AngularVelocity
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
        robot.p1mechanisms.StartP1Mechanisms(gamepad1)

        val driver = CommandGamepad(gamepad1)
        val aux = CommandGamepad(gamepad2)

        aux.leftTrigger.isOver(0.1).whileTrue(robot.p2mechanisms.runShooter(aux.leftTrigger.value.dec())).whileFalse(robot.p2mechanisms.runShooter(0.0))
        aux.leftStickX.isOver(0.1).whileTrue(robot.turret.turnTurret(aux.leftStickX.value.dec()))
        aux.leftStickX.isUnder(-0.1).whileTrue(robot.turret.turnTurret(aux.leftStickX.value.dec()))
    }

}
