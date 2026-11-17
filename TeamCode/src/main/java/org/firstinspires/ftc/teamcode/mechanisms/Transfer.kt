package org.firstinspires.ftc.teamcode.mechanisms

import com.aaravdhawan25.pidautotuner.PIDFController
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.ivy.Command
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextColorDistanceSensor
import dev.nextftc.hardware.sensors.colors.ColorProfile
import dev.nextftc.hardware.sensors.colors.ColorSpace
import dev.nextftc.hardware.sensors.colors.NextColor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry
import dev.nextftc.units.Degrees
import dev.nextftc.units.Rotations
import dev.nextftc.units.radians
import dev.nextftc.units.volts
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor

val yellow = ColorProfile(
    space = ColorSpace.HSV,
    color = NextColor.hsv(90f, 0.667f, 0.0118f),
    tolerance = NextColor.hsv(20f, 0.3f, 1f),
)

val red = ColorProfile(
    space = ColorSpace.HSV,
    color = NextColor.hsv(15f, 0.6667f, 0.0236f),
    tolerance = NextColor.hsv(5f, 0.91f, 0.91f),
)

val blue = ColorProfile(
    space = ColorSpace.HSV,
    color = NextColor.hsv(230f, 0.885f, 0.908f),
    tolerance = NextColor.hsv(5f, 0.91f, 0.91f),
)

enum class BallState(val color: ColorProfile) {
    RED(red),
    YELLOW(yellow)
}

class Transfer : Mechanism {

    val transferMotor = NextMotor("TransferMotor", Rotations.of(1/530.0), cacheTolerance = 0.01);

    val sensor = NextColorDistanceSensor("ColorSensor")
    val positionController = PIDFController(0.01641,0.0,0.00058,0.0)
    var desiredPosition = 0.0


    constructor() {
        transferMotor.direction = NextMotor.Direction.REVERSE
        transferMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        positionController.setOutputBounds(-0.3, 0.3);
    }

    var detecting = true;

    val balls = mutableListOf(BallState.YELLOW, BallState.YELLOW, BallState.YELLOW, BallState.YELLOW)



    override val defaultCommand: Command = infinite {
        val packet = TelemetryPacket()
        sensor.update()
        packet.put("Sensor Hue", sensor.hue)
        packet.put("Sensor Saturation", sensor.saturation)
        packet.put("Sensor Value", sensor.value)
        packet.put("Color Detected", sensor.color)
        if (sensor.isColor(yellow)||sensor.isColor(red)||sensor.isColor(blue)) {
            packet.put("Is Turning?", "Yup!")
            turnTransfer()
        } else {
            packet.put("Is Turning?", "Nope!")
        }
    }


    fun turnTransfer() = coroutine {
        detecting = false
        desiredPosition += (530/4)


        while (abs((transferMotor.encoderPosition.magnitude * 530) - desiredPosition) >= 2) {
            val packet = TelemetryPacket()
            val output: Double = positionController.calculate(
                desiredPosition, transferMotor.encoderPosition.into(Rotations) * 530
            );

            transferMotor.throttle = -output

            packet.put("TransferPositionTick", transferMotor.encoderPosition.into(Rotations) * 530)
            packet.put("TransferThrottle", transferMotor.throttle)
            packet.put("TransferOutput", output)
            packet.put("TransferDistanceFromDesired", abs(transferMotor.encoderPosition.magnitude * 530) - desiredPosition)
            FtcDashboard.getInstance().sendTelemetryPacket(packet)

            yield()
        }
        transferMotor.throttle = 0.0;
    }

    fun transferShoot() = infinite {
        transferMotor.voltage = 11.0.volts;
    }.setEnd {
        transferMotor.throttle = 0.0;
    }

    fun dumpSingle() = coroutine {
        desiredPosition -= (530/2)
        await(turnTransfer())
    }

    fun next90increment() = coroutine {
        desiredPosition = floor(((transferMotor.encoderPosition.into(Rotations) * 530) / (530/4)) )* (530/4)
        await(turnTransfer())
    }
}