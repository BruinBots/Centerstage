package org.firstinspires.ftc.teamcode.Autonomous.AutoOpModes.RedNear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.RedNearAuto;

@Autonomous(name = "RedNearFull")
public class RedNearFull extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RedNearAuto auto = new RedNearAuto(hardwareMap, telemetry);
        waitForStart();
        String tfSpike = auto.tfSpike(false);
        auto.park(auto.placePixel(auto.spike2(RedNearAuto.startingPosition, tfSpike, true), tfSpike, false, true));
    }
}