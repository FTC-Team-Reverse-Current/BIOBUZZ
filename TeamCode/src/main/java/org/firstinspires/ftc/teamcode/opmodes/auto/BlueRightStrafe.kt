package org.firstinspires.ftc.teamcode.opmodes.auto

import com.pedropathing.api.Paths
import com.pedropathing.api.PoseFactory
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.groups.Groups.sequential
import com.pedropathing.ivy.pedro.PedroCommands.follow
import com.pedropathing.paths.Path
import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.BiobuzzBot
import org.firstinspires.ftc.teamcode.OpModeStorage

@NextAutonomous(name="Blue Right Flower to Park", group="Blue Autos")
class BlueRightStrafe(val robot: BiobuzzBot) : NextOpMode(robot) {
    private val poseFactory = PoseFactory.degrees()

    private val start = poseFactory.of(8.1902, 59.133, 90.0)
    private val point1 = poseFactory.of(11.3629, 97.1205, 0.0)
    private val point1Control1 = poseFactory.of(40.4792, 78.9064, 0.0)

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

    fun path1(): Path = Paths.curve(start, point1Control1, point1).constant(point1)
}