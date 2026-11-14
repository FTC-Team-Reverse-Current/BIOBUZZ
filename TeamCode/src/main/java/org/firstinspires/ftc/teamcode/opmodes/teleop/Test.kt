package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.pedropathing.ivy.commands.Commands.infinite
import com.pedropathing.ivy.commands.Commands.waitMs
import dev.nextftc.hardware.actuators.NextServo
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.teamcode.BiobuzzBot
import org.firstinspires.ftc.teamcode.mechanisms.BallState

@NextTeleop(name = "Test")
class Test(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    val gp1 = CommandGamepad(gamepad1)

    override fun start() {
        val servo = NextServo("testServo")
        gp1.b.onTrue(robot.transfer.turnTransfer())
        gp1.x.whileTrue(robot.shoot()).onFalse(waitMs(800.0).then(robot.transfer.next90increment()))

//        gp1.x.onTrue(robot.transfer.transferShoot()).onFalse(waitMs(200.0).then(robot.transfer.next90increment()))
        gp1.y.onTrue(robot.transfer.dumpSingle())
        gp1.dpadUp.onTrue(robot.shooter.increaseRPM())
        gp1.dpadDown.onTrue(robot.shooter.decreaseRPM())

    }
}
