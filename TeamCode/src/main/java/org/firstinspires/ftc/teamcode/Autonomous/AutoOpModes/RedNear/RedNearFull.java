package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.RedNear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.RedNearAuto;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Autonomous(name = "RedNearFull")
public class RedNearFull extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RedNearAuto auto = new RedNearAuto(hardwareMap, telemetry);
        Backdrop.Side tfSpike = auto.tfSpike();
        waitForStart();
        auto.placePixel(auto.spike(RedNearAuto.startingPosition, tfSpike, true), tfSpike, false, true);
    }
}