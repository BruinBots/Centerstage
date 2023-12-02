package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "BlueNearBackdrop", group = "Autonomous: Testing")
public class BlueNearBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Karen bot = new Karen(hardwareMap);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(12,65, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        TensorFlowForAutonomous tf = new TensorFlowForAutonomous(hardwareMap, telemetry);
        tf.initTfod();
        sleep(500);

        waitForStart();

        bot.dropper.dropperDown();

        tf.visionPortal.resumeStreaming();

        int i = 0;
        String side = "none";
        while (side.equals("none") && i < 10) {
            side = tf.getSide();
            telemetry.addData("A-side", side);
            telemetry.update();
            i ++;
            sleep(20);
        }

        tf.visionPortal.stopStreaming();
//        tf.visionPortal.close();

        telemetry.addData("side", side);
//        sleep(5000);

        Trajectory traj0a = drive.trajectoryBuilder(startPose, true)
                .lineToConstantHeading(new Vector2d(12, 55))
                .build();

        Trajectory traj0b;

        switch (side) {
            case "left":
                telemetry.addData("side", "left");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(13, 34))
                        .build();
                break;
            case "center":
                telemetry.addData("side", "center");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(12, 37))
                        .build();
                break;
            case "right":
                telemetry.addData("side", "right");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(14, 36))
                        .build();
                break;
            default:
                telemetry.addData("side", "default");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .build();
                break;
        }


//        sleep(5000);

//        Trajectory traj1 = drive.trajectoryBuilder(startPose, true)
//                .splineTo(new Vector2d(12, 60), Math.toRadians(270))
//                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
//                .build();
//
        Trajectory traj2 = drive.trajectoryBuilder(traj0b.end().plus(new Pose2d(0, 0, Math.toRadians(180))), true)
//                .splineToConstantHeading(new Vector2d(40, 36), Math.toRadians(0))
                .splineTo(new Vector2d(60, 60), Math.toRadians(0))
                .build();


        // -36, 28 center
        // -34, 34 left
        // -42, 28 right

        // NEW
        // -42, 30 center
        // -35 34 left
        // -57, 32 right

        // y=36 center
        // y=28 right
        // y=42 left

//        if(isStopRequested()) return;

//        bot.startAuto();
        sleep(2000);

        drive.followTrajectory(traj0a);
        drive.followTrajectory(traj0b);
        if (side.equals("center")) {
            drive.turn(Math.toRadians(-90));
        }
        else if (side.equals("right")) {
            drive.turn(Math.toRadians(-170));
        }

        bot.dropper.dropperUp();

        if (side.equals("right")) {
            drive.turn(Math.toRadians(-10));
        }

//        drive.followTrajectory(traj1); // navigate to backboard

//        bot.placePixel();

        drive.followTrajectory(traj2);

        sleep(1000);
    }
}
