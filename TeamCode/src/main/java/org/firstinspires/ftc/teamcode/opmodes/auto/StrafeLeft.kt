package org.firstinspires.ftc.teamcode.opmodes.auto

import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.BiobuzzBot

@NextAutonomous(name = "Strafe Left Auto", preselectTeleop = "New TeleOp For Competition")
class StrafeLeft(val robot: BiobuzzBot,
) : NextOpMode(robot) {
    val moveTime = 5.0
    override fun start() {
        robot.strafeLeftMoveForward(.75).schedule()
    }
}