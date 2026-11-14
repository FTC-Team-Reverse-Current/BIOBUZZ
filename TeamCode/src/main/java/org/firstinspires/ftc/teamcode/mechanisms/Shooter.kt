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
import dev.nextftc.control.feedback.PIDController
import dev.nextftc.units.Rotations
import dev.nextftc.units.rotationsPerMinute
import kotlin.math.abs

class Shooter : Mechanism {
    val shooterMotor = NextMotor("ShooterMotor", Rotations.of(1/28.0))
    val shooterController = PIDFController(0.02876, 0.0, 0.00077, 0.000642)
    var rpm = 2500;
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

    fun increaseRPM() = instant{
        rpm += 50
    }

    fun decreaseRPM() = instant{
        rpm -= 50
    }
}