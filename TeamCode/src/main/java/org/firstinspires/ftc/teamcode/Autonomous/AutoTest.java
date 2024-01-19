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

    public static int aprilId = 1;

    private AprilTagsAutonomous aprilTags;

    @Override
    public void runOpMode() throws InterruptedException {
        Karen bot = new Karen(hardwareMap);
        waitForStart();
        bot.inOutTake.scoopHalfDown();
        sleep(1000);
        bot.arm.moveArm(Arm.MAX_ARM_POSITION);
        sleep(2500);
        bot.claw.setClawWrist(0.4);
        sleep(1000);
        // TODO: open claw
        bot.claw.openBothClaw();
        sleep(1000);
        bot.arm.moveArm(0, 0.2);
        sleep(2500);
//        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
//        waitForStart();
//        auto.placePixel(BlueNearAuto.startingPosition, aprilId, true);

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
