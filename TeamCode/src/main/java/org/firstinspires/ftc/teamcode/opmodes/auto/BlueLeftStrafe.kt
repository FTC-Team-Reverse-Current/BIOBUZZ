package org.firstinspires.ftc.teamcode

import com.pedropathing.api.Paths
import com.pedropathing.api.PoseFactory
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.groups.Groups.sequential
import com.pedropathing.ivy.pedro.PedroCommands.follow
import com.pedropathing.paths.Path
import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode

@NextAutonomous(name = "Blue Left Flower to Park", group="Blue Autos", preselectTeleop = "Competition TeleOp")
class BlueLeftStrafe (
    val robot: BiobuzzBot,
) : NextOpMode(robot) {

    private val poseFactory = PoseFactory.degrees()

    private val start = poseFactory.of(108.9217, 7.9743, 0.0)
    private val point1 = poseFactory.of(132.0444, 24.9139, 180.0)


    // Autonomous routine
    fun autoRoutine(): Command = sequential(
        follow(robot.pedro.follower, path1()),
    )

    override fun start() {
        Scheduler.reset()
        robot.pedro.follower.setPose(start)
        robot.pedro.follower.update()

        autoRoutine().schedule()

    }

    override fun periodic() {
        Scheduler.execute()

    }

    override fun end() {
        super.end()
        OpModeStorage.autonomousEndPose = robot.pedro.follower.pose()
    }

    fun path1(): Path = Paths.line(start, point1).linear(start, point1)

}