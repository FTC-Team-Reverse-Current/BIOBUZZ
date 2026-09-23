package org.firstinspires.ftc.teamcode.overrides

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.control.drive.DriveInput
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.drive.scalar

fun mecanumDrive(
    frontLeft: NextMotor,
    frontRight: NextMotor,
    backLeft: NextMotor,
    backRight: NextMotor,
    gamepad: Gamepad,
    kinematics: MecanumKinematics = MecanumKinematics(),
): Command =
    Commands.infinite {
        val powers =
            kinematics.calculate(
                DriveInput(
                    y = gamepad.right_stick_x.toDouble() * scalar,
                    x = -gamepad.left_stick_x.toDouble() * scalar,
                    rx = gamepad.right_stick_y.toDouble() * scalar,
                ),
            )
        frontLeft.throttle = powers.frontLeft
        frontRight.throttle = powers.frontRight
        backLeft.throttle = powers.backLeft
        backRight.throttle = powers.backRight
    }
