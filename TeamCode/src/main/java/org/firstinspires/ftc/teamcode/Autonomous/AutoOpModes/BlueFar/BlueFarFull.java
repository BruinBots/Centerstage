package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.BlueFar;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;

@Autonomous(name = "BlueFarFull")
public class BlueFarFull extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BlueFarAuto auto = new BlueFarAuto(hardwareMap, telemetry);
        waitForStart();
        String tfSpike = auto.tfSpike();
        auto.park(auto.placePixel(auto.spike2(BlueFarAuto.startingPosition, tfSpike, true), tfSpike));
    }
}