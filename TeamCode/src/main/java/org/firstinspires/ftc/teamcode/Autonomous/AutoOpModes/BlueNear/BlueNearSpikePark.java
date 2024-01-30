package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.BlueNear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Autonomous(name = "BlueNearSpikePark")
public class BlueNearSpikePark extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        Backdrop.Side tfSpike = auto.tfSpike();
        waitForStart();
        auto.park(auto.spike(BlueNearAuto.startingPosition, tfSpike, true)); // (60,61) Park
    }
}