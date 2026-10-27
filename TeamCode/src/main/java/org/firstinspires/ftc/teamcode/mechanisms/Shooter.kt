package org.firstinspires.ftc.teamcode.mechanisms
import android.util.Size
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.coroutine.command
import dev.nextftc.units.RotationsPerMinute
import dev.nextftc.units.measuretypes.AngularVelocity
import dev.nextftc.units.radians
import dev.nextftc.units.volts

class Shooter : Mechanism {
    val shooterMotor = NextMotor("ShooterMotor", anglePerCount = (2 * Math.PI/28.0).radians)

    constructor() {
        shooterMotor.direction = NextMotor.Direction.FORWARD

        // PID and FeedForward Coefficients
        shooterMotor.velocityConstants.apply {
            kP = 0.0005
            kI = 0.0
            kD = 0.0
            kS = 0.19
            kV = 1.0/5700.0
            kA = 0.0
        }


    }

    companion object {
        var nectarShootRPM = 12.0
        var pollenShootRPM = 12.0
    }

    fun automatedShoot(sizeState: SizeState) =
        infinite {
            setVoltage(sizeState.rpm)
        }.setEnd { shooterMotor.voltage = 0.0.volts }

    fun setVelocity(velocity: Double) =
        infinite {
            val angular = AngularVelocity(velocity, RotationsPerMinute)
            shooterMotor.setVelocitySetpoint(angular)
        }.setEnd { shooterMotor.voltage = 0.0.volts }

    fun changeRPM(amount: Double) =
        instant {
            nectarShootRPM += amount
            pollenShootRPM += amount
        }

//    fun setVoltage(voltage: Double) = infinite {
//        shooterMotor.voltage = voltage.volts
//    }.setEnd { shooterMotor.voltage = 0.0.volts }

    fun setVoltage(voltage: Double) {
        shooterMotor.voltage = voltage.volts
    }

    fun setThrottle(throttle: Double) =
        infinite {
            shooterMotor.throttle = throttle
        }
}
