package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.RedFar;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.RedFarAuto;

@Autonomous(name = "RedFarSpikeOnly")
public class RedFarSpikeOnly extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RedFarAuto auto = new RedFarAuto(hardwareMap, telemetry);
        waitForStart();
        auto.spike(RedFarAuto.startingPosition, auto.tfSpike(false), false);
    }
}
