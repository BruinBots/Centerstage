package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.BlueNear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Autonomous(name = "BlueNearFull")
public class BlueNearFull extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        waitForStart();
        Backdrop.Side tfSpike = auto.tfSpike(true);
        auto.park(auto.placePixel(auto.spike(BlueNearAuto.startingPosition, tfSpike, true), tfSpike, true, true));
    }
}