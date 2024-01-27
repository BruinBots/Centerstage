package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(name="SecondaryTestMode")
public class SecondaryTestMode extends LinearOpMode {

    public static boolean doMove = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Backdrop.telemetry = telemetry;
        Backdrop.bot = new Karen(hardwareMap);
        Backdrop.initBot();

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(180));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        waitForStart();

        if (doMove) {
            Backdrop.alignBackdrop(drive, startPose, Backdrop.Side.CENTER);
        }
        else {
            while (opModeIsActive()) {
                telemetry.addData("distance", Backdrop.bot.distance.getDistance());
                telemetry.update();
                sleep(20);
            }
        }
    }
}
