package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Karen;

@Config
@Autonomous(name="AutoTest", group="Autonomous: Testing")
public class AutoTest extends LinearOpMode {

    public static int aprilId = 2;

    private AprilTagsAutonomous aprilTags;

    @Override
    public void runOpMode() throws InterruptedException {
//        Karen bot = new Karen(hardwareMap);
//        waitForStart();
//        aprilTags = new AprilTagsAutonomous();
//        aprilTags.initAprilTag(hardwareMap);

        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        waitForStart();

//        aprilTags.visionPortal.close();

//        auto.placePixel(BlueNearAuto.startingPosition, aprilId, true);
        auto.spike2(BlueNearAuto.startingPosition, "left", false);

    }

//    @Override
//    public void init() {
//        aprilTags = new AprilTagsAutonomous();
//    }
//
//    @Override
//    public void loop() {
//        Vector2d aprilVector = aprilTags.getOffset(hardwareMap, telemetry, aprilId);
//        telemetry.addData("x", aprilVector.getX());
//        telemetry.addData("y", aprilVector.getY());
//        telemetry.update();
//    }
}