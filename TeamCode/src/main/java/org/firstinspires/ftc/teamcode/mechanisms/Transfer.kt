package org.firstinspires.ftc.teamcode.mechanisms

import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextColorDistanceSensor
import dev.nextftc.hardware.sensors.colors.ColorProfile
import dev.nextftc.hardware.sensors.colors.ColorSpace
import dev.nextftc.hardware.sensors.colors.NextColor
import dev.nextftc.robot.Mechanism
import dev.nextftc.units.Degrees
import dev.nextftc.units.degrees
import kotlin.math.absoluteValue

val yellow = ColorProfile(
    space = ColorSpace.HSV,
    color = NextColor.hsv(47f, 1.0f, 0.94f),
    tolerance = NextColor.hsv(20f, 0.3f, 1f),
)

val red = ColorProfile(
    space = ColorSpace.HSV,
    color = NextColor.hsv(351f, 0.47f, 0.87f),
    tolerance = NextColor.hsv(5f, 0.91f, 0.91f),
)

enum class BallState(val color: ColorProfile) {
    RED(red),
    YELLOW(yellow)
}

class Transfer : Mechanism {
    val transferMotor = NextMotor("TransferMotor");
    val sensor = NextColorDistanceSensor("ColorSensor")

    var detecting = true;

    val balls = mutableListOf(BallState.YELLOW, BallState.YELLOW, BallState.YELLOW, BallState.YELLOW)

    override fun periodic() {
        sensor.update()
        if (detecting) {
            if (sensor.isColor(yellow)) {
                turnTransfer(BallState.YELLOW).schedule();
            } else if (sensor.isColor(red)) {
                turnTransfer(BallState.RED).schedule();
            }
        }

    }

    fun turnTransfer(state: BallState) = coroutine {
        balls.add(state);
        detecting = false
        transferMotor.setPositionSetpoint(((balls.size * 90.0) - 10).degrees);
        waitUntil { (transferMotor.encoderPosition.into(Degrees) - (balls.size * 90.0) - 10).absoluteValue <= 1 }
        if (balls.size < 4) {
            detecting = true
        }
    }
}

