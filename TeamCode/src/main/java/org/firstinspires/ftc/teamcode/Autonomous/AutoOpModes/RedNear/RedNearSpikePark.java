package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.RedNear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.RedNearAuto;

@Autonomous(name = "RedNearSpikePark")
public class RedNearSpikePark extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RedNearAuto auto = new RedNearAuto(hardwareMap, telemetry);
        waitForStart();
        auto.park(auto.spike2(RedNearAuto.startingPosition, auto.tfSpike(false), true));
    }
}
