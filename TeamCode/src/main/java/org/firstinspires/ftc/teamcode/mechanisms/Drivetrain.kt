package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.hardware.sensors.NextPinpoint
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.overrides.MecanumKinematics
import org.firstinspires.ftc.teamcode.overrides.mecanumDrive
import org.firstinspires.ftc.teamcode.overrides.mecanumDriveFieldCentric

const val STRAFE_COMPENSATION = 1.2

class Drivetrain : Mechanism {
    val frontLeft = NextMotor("frontLeft")
    val frontRight = NextMotor("frontRight")
    val backLeft = NextMotor("backLeft")
    val backRight = NextMotor("backRight")

    val pinpoint = NextPinpoint("pinpoint")


    constructor() {
        frontRight.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        backRight.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        frontLeft.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
        backLeft.zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE

        backLeft.direction = NextMotor.Direction.REVERSE
        frontLeft.direction = NextMotor.Direction.REVERSE

    }

    override fun periodic() {
//        Telemetry.log("frontLeft", frontLeft.encoderVelocity)
//        Telemetry.log("frontRight", frontRight.encoderVelocity)
//        Telemetry.log("backLeft", backLeft.encoderVelocity)
//        Telemetry.log("backRight", backRight.encoderVelocity)
        Telemetry.update()
        pinpoint.update()
    }

    fun moveForward(throttle: Double) = infinite {
        frontLeft.throttle = throttle
        frontRight.throttle = throttle
        backLeft.throttle = throttle
        backRight.throttle = throttle
    }.setEnd {
        frontLeft.throttle = 0.0
        frontRight.throttle = 0.0
        backLeft.throttle = 0.0
        backRight.throttle = 0.0
    }

    fun strafeLeft(throttle: Double) = infinite {
        frontLeft.throttle = -throttle
        frontRight.throttle = throttle
        backLeft.throttle = throttle
        backRight.throttle = -throttle
    }.setEnd {
        frontLeft.throttle = 0.0
        frontRight.throttle = 0.0
        backLeft.throttle = 0.0
        backRight.throttle = 0.0
    }
    fun strafeRight(throttle: Double) = infinite {
        frontLeft.throttle = throttle
        frontRight.throttle = -throttle
        backLeft.throttle = -throttle
        backRight.throttle = throttle
    }.setEnd {
        frontLeft.throttle = 0.0
        frontRight.throttle = 0.0
        backLeft.throttle = 0.0
        backRight.throttle = 0.0
    }


    fun startDrivetrain(gamepad: Gamepad) {
//        mecanumDriveFieldCentric(
//            frontLeft,
//            frontRight,
//            backLeft,
//            backRight,
//            gamepad,
//            { pinpoint.pose.heading.toDouble() },
//            MecanumKinematics(STRAFE_COMPENSATION),
//        ).schedule()
//    }
        mecanumDrive(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gamepad,
            MecanumKinematics(STRAFE_COMPENSATION)
        ).schedule()
    }
}
