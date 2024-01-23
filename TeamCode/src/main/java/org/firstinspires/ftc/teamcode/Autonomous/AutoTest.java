package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Config
@Autonomous(name = "AutoTest", group = "Autonomous: Testing")
public class AutoTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        BlueFarAuto auto = new BlueFarAuto(hardwareMap, telemetry);
        waitForStart();
        auto.placePixel(BlueFarAuto.startingPosition, Backdrop.Side.CENTER, true, false);
    }
}