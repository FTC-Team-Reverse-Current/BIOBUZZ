package org.firstinspires.ftc.teamcode.mechanisms
import android.util.Size
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.coroutine.command
import dev.nextftc.units.RotationsPerMinute
import dev.nextftc.units.measuretypes.AngularVelocity
import dev.nextftc.units.volts
import com.aaravdhawan25.pidautotuner.PIDFController;
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.math.Pose
import dev.nextftc.control.feedback.PIDController
import dev.nextftc.units.Rotations
import dev.nextftc.units.measuretypes.Distance
import dev.nextftc.units.rotationsPerMinute
import kotlin.math.abs

enum class HivePositiones(
    var pos: Pose
) {
    RED_BOTTOM(Pose(58.0, 58.0)),
    RED_TOP(Pose(58.0, 83.0)),
    BLUE_BOTTOM(Pose(83.0, 58.0)),
    BLUE_TOP(Pose(83.0, 83.0)),
}
class Shooter : Mechanism {
    val shooterMotor = NextMotor("ShooterMotor", Rotations.of(1/28.0))
    val shooterController = PIDFController(0.02876, 0.0, 0.00077, 0.000642)
    var rpm : Double = 3500.0;

    lateinit var follower: () -> Pose

    constructor(poseFunc: () -> Pose) {
        this.follower = poseFunc
    }
    val getRPM = { rpm }

    constructor() {
        shooterMotor.direction = NextMotor.Direction.FORWARD

        shooterController.setOutputBounds(0.0, 1.0)
    }

    companion object {
        var nectarShootRPM = 12.0
        var pollenShootRPM = 12.0
    }

    override fun periodic() {
        val packet = TelemetryPacket()
        packet.put("RPM", rpm)
        packet.put("ShooterRPM", shooterMotor.encoderVelocity.into(RotationsPerMinute))
        packet.put("ShooterThrottle", shooterMotor.throttle)

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }

    fun pidShoot() = infinite {
        val velocity = abs(shooterMotor.encoderVelocity.into(RotationsPerMinute))

        val power = shooterController.calculate(velocity, getRPM().toDouble())
        shooterMotor.throttle = power
    }.setEnd { shooterMotor.throttle = 0.0 }

    fun setVoltage(voltage: Double) {
        shooterMotor.voltage = voltage.volts
    }

    fun setThrottle(throttle: Double) =
        infinite {
            shooterMotor.throttle = throttle
        }
    fun getDistance() = instant {
        val pose = follower()
        var closest = Pair(Pose(0.0, 0.0), Double.POSITIVE_INFINITY)
        var truedist = 0.0
        for (hive in HivePositions.entries) {
            val dist = pose.distance(hive.pos)
            if (dist <= truedist) {
                truedist = dist
            }
        }
    fun getRPM(distance: Double){
        rpm = distance*2
    }
    fun increaseRPM() = instant{
        rpm += 50
    }

    fun decreaseRPM() = instant{
        rpm -= 50
    }
}