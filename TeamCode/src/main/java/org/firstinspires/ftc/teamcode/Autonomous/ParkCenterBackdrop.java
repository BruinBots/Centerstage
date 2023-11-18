package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "ParkCenterBackdrop", group = "Autonomous: Testing")
public class ParkCenterBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

//        Trajectory backUpTrajectory = drive.trajectoryBuilder(new Pose2d())
//                .back(5) // move back 2 feet
//                .build();
//
//        Trajectory parkAtBackdropTrajectory = drive.trajectoryBuilder(backUpTrajectory.end().plus(new Pose2d(0, 0, Math.toRadians(90))), false)
//                .back(5 * 12) // forward 5 feet
//                .build();
//
//        SampleMecanumDrive drive2 = new SampleMecanumDrive(hardwareMap);
//
//        Trajectory parkAtBackdropTrajectory2 = drive2.trajectoryBuilder(new Pose2d())
//                .lineTo(new Vector2d(-(1.5*12+7.5), (2*12+1))) // 3 feet diagonal forward and right and -3in left and +7in right
//                .build();
//
//        Trajectory parkCenterBackdropTrajectory = drive2.trajectoryBuilder(parkAtBackdropTrajectory2.end())
//                .forward(4 * 12) // move 4 feet forwards
//                .build();
//
//        waitForStart();
//
//        if(isStopRequested()) return;
//
//        drive.followTrajectory(backUpTrajectory);
//        drive.turn(Math.toRadians(90));
//        drive.followTrajectory(parkAtBackdropTrajectory);
//        drive2.followTrajectory(parkAtBackdropTrajectory2);
////        drive2.followTrajectory(parkCenterBackdropTrajectory);

        // We want to start the bot at x: -36, y: 65, heading: 270 degrees
        Pose2d startPose = new Pose2d(-36,65, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose, true)
                .splineTo(new Vector2d(-36, 60), Math.toRadians(0))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end(), true)
                .splineTo(new Vector2d(18, 60), Math.toRadians(0))
                .build();

        Trajectory traj3 = drive.trajectoryBuilder(traj2.end(), true)
                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                        .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(traj1);
        drive.followTrajectory(traj2);
        drive.followTrajectory(traj3);
    }
}
