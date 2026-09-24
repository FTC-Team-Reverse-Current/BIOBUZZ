package org.firstinspires.ftc.teamcode.overrides

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.control.drive.DriveInput
import org.firstinspires.ftc.teamcode.overrides.MecanumKinematics
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.drive.scalar
import dev.nextftc.units.DegreesPerSecond
import dev.nextftc.units.degreesPerSecond

fun mecanumDrive(
    frontLeft: NextMotor,
    frontRight: NextMotor,
    backLeft: NextMotor,
    backRight: NextMotor,
    gamepad: Gamepad,
    kinematics: MecanumKinematics = MecanumKinematics(),
): Command = Commands.infinite {
    val powers = kinematics.calculate(
        DriveInput(
            y = gamepad.right_stick_x.toDouble() * scalar,
            x = -gamepad.left_stick_x.toDouble() * scalar,
            rx = gamepad.right_stick_y.toDouble() * scalar,
        ),


    )

    frontLeft.setVelocitySetpoint(powers.frontLeft.degreesPerSecond)
    frontRight.setVelocitySetpoint(powers.frontRight.degreesPerSecond)
    backLeft.setVelocitySetpoint(powers.backLeft.degreesPerSecond)
    backRight.setVelocitySetpoint(powers.backRight.degreesPerSecond)
}