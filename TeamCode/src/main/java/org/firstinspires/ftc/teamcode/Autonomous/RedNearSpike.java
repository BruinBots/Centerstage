package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "RedNearSpike", group = "Autonomous: Testing")
public class RedNearSpike extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Karen bot = new Karen(hardwareMap);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(12, -60, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TensorFlowForAutonomousBlueRed tf = new TensorFlowForAutonomousBlueRed(hardwareMap, telemetry);
        tf.initTfod();
        sleep(500);

        waitForStart();

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

        tf.visionPortal.close();

        telemetry.addData("side", side);
        sleep(5000);
        stop();

//        Trajectory traj0a = drive.trajectoryBuilder(startPose, true)
//                .lineToConstantHeading(new Vector2d(20, -55))
//                .build();
//
//        Trajectory traj0b;
//
//        switch (side) {
//            case "Left":
//                telemetry.addData("side", "left");
//                traj0b = drive.trajectoryBuilder(traj0a.end())
//                        .lineToConstantHeading(new Vector2d(12 +12, -65+40))
//                        .build();
//                break;
//            case "Center":
//                telemetry.addData("side", "center");
//                traj0b = drive.trajectoryBuilder(traj0a.end())
//                        .lineToConstantHeading(new Vector2d(12, -65+45))
//                        .build();
//                break;
//            case "Right":
//                telemetry.addData("side", "right");
//                traj0b = drive.trajectoryBuilder(traj0a.end())
//                        .lineToConstantHeading(new Vector2d(12+10, -65+40))
//                        .build();
//                break;
//            default:
//                telemetry.addData("side", "default");
//                traj0b = drive.trajectoryBuilder(traj0a.end())
//                        .lineToConstantHeading(new Vector2d(12+10, -65+40))
//                        .build();
//                break;
//        }
//
////        Trajectory traj0c = drive.trajectoryBuilder(traj0b.end())
////                .lineTo(new Vector2d(12, -65))
////                .build();
//
////        Trajectory traj1 = drive.trajectoryBuilder(startPose, true)
////                .splineTo(new Vector2d(12, -60), Math.toRadians(90))
////                .splineTo(new Vector2d(48, -36), Math.toRadians(0))
////                .build();
//
////        Trajectory traj2 = drive.trajectoryBuilder(traj0c.end(), true)
////                .lineTo(new Vector2d(60, -60))
////                .build();
//
//        if(isStopRequested()) return;
//
////        bot.startAuto();
//        sleep(2000);
//
//        drive.followTrajectory(traj0a);
//        drive.followTrajectory(traj0b);
//
//
//        sleep(1000);
//
////        drive.followTrajectory(traj0c);
//
////        drive.followTrajectory(traj1); // navigate to backboard
//
////        bot.placePixel();
//
////        drive.followTrajectory(traj2);
    }
}
