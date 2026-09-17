package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.pedropathing.revhub.drivetrains.Mecanum
import com.pedropathing.revhub.localizers.PinpointLocalizer
import com.pedropathing.tuning.autotune.Procedure
import com.pedropathing.tuning.autotune.Tuner
import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner
import org.firstinspires.ftc.teamcode.pedro.Constants

class Tuning {
    @Tuner
    fun mecanumTuner(): Procedure {
        return MecanumTuner()
    }

    @Tuner
    fun pinpointTuner(): Procedure {
        return PinpointTuner()
    }

//    @Tuner
//    fun forsightTuner(): Procedure {
//        return ForesightTuner({hardwareMap -> PinpointLocalizer(hardwareMap, Constants.localizerConfig)}, { hardwareMap ->
//            Mecanum(
//                hardwareMap,
//                Constants.drivetrainConfig
//            )
//        })
//    }
}