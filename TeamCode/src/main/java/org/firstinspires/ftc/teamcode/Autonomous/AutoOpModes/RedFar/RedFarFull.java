package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.RedFar;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.RedFarAuto;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Autonomous(name = "RedFarFull")
public class RedFarFull extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RedFarAuto auto = new RedFarAuto(hardwareMap, telemetry);
        waitForStart();
        Backdrop.Side tfSpike = auto.tfSpike(false);
        auto.park(auto.placePixel(auto.spike(RedFarAuto.startingPosition, tfSpike, true), tfSpike, false, true));
    }
}