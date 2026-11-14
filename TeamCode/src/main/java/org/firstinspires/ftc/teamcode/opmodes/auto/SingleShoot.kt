package org.firstinspires.ftc.teamcode.opmodes.auto

import com.pedropathing.api.Paths
import com.pedropathing.api.PoseFactory
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.groups.Groups.sequential
import com.pedropathing.ivy.pedro.PedroCommands.follow
import com.pedropathing.paths.Path
import com.pedropathing.paths.interpolator.Interpolator
import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.BiobuzzBot
import org.firstinspires.ftc.teamcode.OpModeStorage

@NextAutonomous(name="Shoot and Park", group="Complex Autos")
class SingleShoot(val robot: BiobuzzBot) : NextOpMode(robot) {
    private val poseFactory = PoseFactory.degrees()

    private val start = poseFactory.of(8.083, 35.0136, 90.0)
    private val shootPosition = poseFactory.of(57.9936, 16.5083, 90.0)
    private val shootPositionControl1 = poseFactory.of(24.2925, 11.8011, 0.0)
    private val shootPositionSegment1Start = poseFactory.of(57.9936, 16.5083, 0.0)
    private val shootPositionSegment1End = poseFactory.of(57.9936, 16.5083, 0.0)
    private val shootPositionSegment2Start = poseFactory.of(57.9936, 16.5083, 0.0)
    private val shootPositionSegment2End = poseFactory.of(57.9936, 16.5083, 90.0)
    private val endPositionStart = poseFactory.of(57.9936, 16.5083, 90.0)
    private val endPosition = poseFactory.of(10.2922, 95.3224, 0.0)
    private val endPositionControl1 = poseFactory.of(11.3203, 57.058, 0.0)

    fun shootPosition(): Path = Paths.curve(start, shootPositionControl1, shootPosition).heading(
        Interpolator.piecewise().until(0.25, Interpolator.linear(shootPositionSegment1Start, shootPositionSegment1End)).until(1.0, Interpolator.linear(shootPositionSegment2Start, shootPositionSegment2End)))

    fun endPosition(): Path = Paths.curve(endPositionStart, endPositionControl1, endPosition).linear(endPositionStart, endPosition)

    fun autoRoutine(): Command = sequential(
        follow(robot.pedro.follower, shootPosition()),
        robot.shoot().raceWith(waitMs(5000.0)),
        follow(robot.pedro.follower, endPosition())
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
}