package org.firstinspires.ftc.teamcode.opmodes.auto

import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.BiobuzzBot

const val LEFT_STRAFE_TIME = 0.75

@NextAutonomous(name = "Strafe Left Auto", preselectTeleop = "New TeleOp For Competition")
class StrafeLeft(
    val robot: BiobuzzBot,
) : NextOpMode(robot) {
    override fun start() {
        robot.strafeLeftMoveForward(LEFT_STRAFE_TIME).schedule()
    }
}
