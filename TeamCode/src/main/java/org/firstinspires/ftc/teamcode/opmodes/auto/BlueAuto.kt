package org.firstinspires.ftc.teamcode

import com.pedropathing.api.Paths.*
import com.pedropathing.api.Paths
import com.pedropathing.api.PoseFactory
import com.pedropathing.follower.Follower
import com.pedropathing.math.Pose
import com.pedropathing.paths.Path
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.Scheduler.schedule
import com.pedropathing.ivy.commands.Commands.*
import com.pedropathing.ivy.groups.Groups.sequential
import com.pedropathing.ivy.pedro.PedroCommands.follow
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.pedro.Constants

@NextAutonomous(name = "BlueAuto", preselectTeleop = "New TeleOp For Competition")
class BlueAuto (
    val robot: BiobuzzBot,
) : NextOpMode(robot) {

    private lateinit var follower: Follower

    private val poseFactory = PoseFactory.degrees()


    private val start = poseFactory.of(108.9217, 7.9743, 0.0)
    private val point1 = poseFactory.of(132.0444, 24.9139, 180.0)

    // Autonomous routine
    fun autoRoutine(): Command = sequential(
        follow(follower, path1()),
    )

    override fun start() {
        Scheduler.reset()
        follower = Constants.create(hardwareMap)
        follower.setPose(start)
        follower.update()

        schedule(autoRoutine())
    }

    override fun periodic() {
        follower.update()
        Scheduler.execute()

        telemetry.addData("x", follower.pose().x())
        telemetry.addData("y", follower.pose().y())
        telemetry.addData("heading", follower.pose().heading())

        if (follower.currentPath() != null) {
            telemetry.addData("Current path distance remaining", follower.distanceToEndpoint())
            telemetry.addData("Path number", follower.pathIndex())
        }

        telemetry.update()
    }

    fun path1(): Path = Paths.line(start, point1).linear(start, point1)
}
