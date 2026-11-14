package org.firstinspires.ftc.teamcode.mechanisms

import com.aaravdhawan25.pidautotuner.PIDFController
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
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
import kotlin.math.abs
import kotlin.math.absoluteValue

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

enum class BallState(val color: ColorProfile) {
    RED(red),
    YELLOW(yellow)
}

class Transfer : Mechanism {
    val angleCount = (3.14159 * 2) / 560

    val transferMotor = NextMotor("TransferMotor", Rotations.of(1/530.0), cacheTolerance = 0.01);

    val sensor = NextColorDistanceSensor("ColorSensor")
    val positionController = PIDFController(0.01641,0.0,0.00058,0.0)
    var desiredPosition = 0


    constructor() {
        transferMotor.direction = NextMotor.Direction.REVERSE
        transferMotor.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        positionController.setOutputBounds(-0.3, 0.3);
    }

    var detecting = true;

    val balls = mutableListOf(BallState.YELLOW, BallState.YELLOW, BallState.YELLOW, BallState.YELLOW)

    override fun periodic() {
        val packet = TelemetryPacket()
        sensor.update()
        if (detecting) {
            if (sensor.isColor(yellow)) {
                turnTransfer(BallState.YELLOW).schedule();
            } else if (sensor.isColor(red)) {
                turnTransfer(BallState.RED).schedule();
            }
        }
        packet.put("Detecting", detecting)
        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }

    fun turnTransfer(state: BallState) = coroutine {
        wait(0.3)
        balls.add(state);
        detecting = false
        if (balls.size <= 3) {
            desiredPosition += (530/4)
        }


//        while ((transferMotor.encoderPosition.into(Degrees) - (balls.size * 90.0) - 10).absoluteValue <= 1) {
        while (abs((transferMotor.encoderPosition.magnitude * 530) - desiredPosition) >= 2) {
            val packet = TelemetryPacket()
            val output: Double = positionController.calculate(
                desiredPosition.toDouble(), transferMotor.encoderPosition.into(Rotations) * 530
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

        detecting = balls.size != 4
    }
}