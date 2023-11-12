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

        Trajectory backUpTrajectory = drive.trajectoryBuilder(new Pose2d())
                .back(2 * 12) // move back 2 feet
                .build();

        Trajectory parkAtBackdropTrajectory = drive.trajectoryBuilder(backUpTrajectory.end())
                .forward(5 * 12) // forward 5 feet
                .lineTo(new Vector2d(3 * 12, 3 * 12)) // 3 feet diagonal forward and right
                .build();

        Trajectory parkCenterBackdropTrajectory = drive.trajectoryBuilder(parkAtBackdropTrajectory.end())
                .forward(4 * 12) // move 4 feet forwards
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(backUpTrajectory);
        drive.turn(Math.toRadians(-90));
        drive.followTrajectory(parkAtBackdropTrajectory);
        drive.followTrajectory(parkCenterBackdropTrajectory);
    }
}
