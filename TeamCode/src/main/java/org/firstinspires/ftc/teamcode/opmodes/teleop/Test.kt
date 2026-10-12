package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.pedropathing.ivy.commands.Commands.infinite
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.teamcode.BiobuzzBot

@NextTeleop(name = "Test")
class Test(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    val gp1 = CommandGamepad(gamepad1)
    override fun periodic() {
    }

    override fun start() {
        val servo = NextServo("testServo")
        gp1.a.onTrue(infinite {servo.position = 0.0} )
        gp1.b.onTrue(infinite {servo.position = 1.0} )

    }
}