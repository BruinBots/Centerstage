package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;

public class BlueFarParkOnly extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BlueFarAuto auto = new BlueFarAuto(hardwareMap, telemetry);
        waitForStart();
        auto.park(auto.spike(BlueFarAuto.startingPosition, auto.tfSpike()).end());
    }
}
