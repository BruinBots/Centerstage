package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.BlueNear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Autonomous(name = "BlueNearSpikeOnly")
public class BlueNearSpikeOnly extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        Backdrop.Side tfSpike = auto.tfSpike();
        waitForStart();
        auto.spike(BlueNearAuto.startingPosition, tfSpike, false);
    }
}