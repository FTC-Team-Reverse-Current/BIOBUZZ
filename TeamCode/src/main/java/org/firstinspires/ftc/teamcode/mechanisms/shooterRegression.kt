package org.firstinspires.ftc.teamcode.mechanisms

import com.pedropathing.follower.Follower
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands.infinite
import com.pedropathing.math.Pose
import kotlin.math.hypot


class shooterRegression(val distanceFromHub: Int) {
    var rpmNeeded: Int = distanceFromHub * 2 //do the mathy maths here for the regression
}
enum class HivePositions(
    var pos: Pose
) {
    RED_LEFT_POS(Pose(58.0, 85.0)),
    RED_RIGHT_POS(Pose(84.0, 85.0)),
    BLUE_LEFT_POS(Pose(58.0, 58.0)),
    BLUE_RIGHT_POS(Pose(84.0, 58.0)),
}

class Shooterpos(private val follower: () -> Pose) {

    val defaultCommand: Command = infinite {
        val pose = follower()
        var closest = Pair(Pose(0.0, 0.0), Double.POSITIVE_INFINITY)
        for (hive in HivePositions.entries) {
            val dist = pose.distance(hive.pos)
            if (dist < closest.second) {
                closest = Pair(hive.pos, dist)
            }
        }

        val dx = closest.first.x() - pose.x()
        val dy = closest.first.y() - pose.y()


fun main() {
    HivePositions.RED_RIGHT_POS.pos
    val distance = hypot(dx, dy).toInt()
    val mathcalculate = shooterRegression(distance)
    val rpmneededval = mathcalculate.rpmNeeded
        }
    }
}