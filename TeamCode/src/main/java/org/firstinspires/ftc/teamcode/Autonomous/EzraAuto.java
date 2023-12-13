package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "BlueFarBackdrop", group = "Autonomous: Testing")
public class EzraAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Karen bot = new Karen(hardwareMap);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(12,-65, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        waitForStart();

        Trajectory traj1 = drive.trajectoryBuilder(startPose, true)
                .lineToConstantHeading(new Vector2d(11, -36))
                .lineToConstantHeading(new Vector2d(10, -11))
                .lineToConstantHeading(new Vector2d(-58, -11))
                .lineToConstantHeading(new Vector2d(10, -11))
                .lineToConstantHeading(new Vector2d(48, -35))
                .build();

        drive.followTrajectory(traj1);

        if(isStopRequested()) return;

    }
}
