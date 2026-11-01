package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("backLeft");
        c.backRightName.set("backRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(5.132563433309239);
        c.yPodOffset.set(4.461579510546106);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2652551272422448);
                Controller secondaryTranslationalForward = Controller.proportional(0.09800468176847263);
                Controller primaryTranslationalLateral = Controller.proportional(0.33647977750360514);
                Controller secondaryTranslationalLateral = Controller.proportional(0.12432028688233933);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016076643557984712));
                c.brake.set(Controller.proportionalFeedforward(0.013665147024287005));

                c.headingFeedback.set(Controller.proportional(3.8142075539808413));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04183130789724758, 0.010173625505845654));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07372081056734077, 0.03767490359791605));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0015359965324333261, 0.0025406087429723284));

                c.maxAchievableForwardVelocity.set(64.86863039958514);
                c.maxAchievableStrafeVelocity.set(53.66353934804537);
                c.naturalForwardDeceleration.set(43.67596356363515);
                c.naturalStrafeDeceleration.set(87.43563810367012);
            }
    );
}
