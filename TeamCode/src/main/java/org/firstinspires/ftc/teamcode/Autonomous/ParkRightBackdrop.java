package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "ParkRightBackdrop", group = "Autonomous: Testing")
public class ParkRightBackdrop extends LinearOpMode {
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
                .forward(3 * 12) // move forward 3 feet
                .build();

        Trajectory parkRightBackdropTrajectory = drive.trajectoryBuilder(parkCenterBackdropTrajectory.end())
                .lineTo(new Vector2d(1 * 12, 1 * 12)) // move diagonal right and forwards 1 foot
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(backUpTrajectory);
        drive.turn(Math.toRadians(-90));
        drive.followTrajectory(parkAtBackdropTrajectory);
        drive.followTrajectory(parkCenterBackdropTrajectory);
        drive.followTrajectory(parkRightBackdropTrajectory);
    }
}
