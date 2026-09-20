package org.firstinspires.ftc.teamcode.opmodes.teleop

import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import org.firstinspires.ftc.teamcode.BiobuzzBot

@NextTeleop(name = "Base TeleOp")
class BaseTele(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    override fun periodic() {
        Telemetry.log("Currently Running!")
    }

    override fun start() {
        robot.drivetrain.startDrivetrain(gamepad1)
    }
}