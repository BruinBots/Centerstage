package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Karen;

@Config
@Autonomous(name="AutoTest", group="Autonomous: Testing")
public class AutoTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
//        Karen bot = new Karen(hardwareMap);
//        waitForStart();
//        aprilTags = new AprilTagsAutonomous();
//        aprilTags.initAprilTag(hardwareMap);

//        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        BlueFarAuto auto = new BlueFarAuto(hardwareMap, telemetry);
        waitForStart();


//        aprilTags.visionPortal.close();

        auto.placePixel(BlueFarAuto.startingPosition, "center", true);
//        auto.spike2(BlueFarAuto.startingPosition, auto.tfSpike(true),  false);

    }
}