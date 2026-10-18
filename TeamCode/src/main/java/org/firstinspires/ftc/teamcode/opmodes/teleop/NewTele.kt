package org.firstinspires.ftc.teamcode.opmodes.teleop

import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import org.firstinspires.ftc.teamcode.BiobuzzBot

@NextTeleop(name = "New TeleOp For Competition")
class NewTele(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    override fun periodic() {
        Telemetry.log("Currently Running!")
    }

    override fun start() {
        robot.drivetrain.startDrivetrain(gamepad1)
        robot.p1mechanisms.StartP1Mechanisms(gamepad1)
    }
}
