package org.firstinspires.ftc.teamcode.opmodes.auto

import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.BiobuzzBot

const val RIGHT_STRAFE_TIME = 0.75

@NextAutonomous(name = "Strafe Right Auto", preselectTeleop = "New TeleOp For Competition")
class StrafeRight(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    override fun start() {
        robot.strafeRightMoveForward(RIGHT_STRAFE_TIME).schedule()
    }
}
