package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BlueFarAuto extends BaseAuto {

    public static Pose2d startingPosition = new Pose2d(-36, 65, Math.toRadians(90));

    public BlueFarAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap, telemetry);
    }

    @Override
    public Trajectory spikeStart(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-44, 55))
                .build();
    }

    @Override
    public Trajectory spikeLeft(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-36, 34))
                .build();
    }

    @Override
    public Trajectory spikeCenter(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-42, 30))
                .build();
    }

    @Override
    public Trajectory spikeRight(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-57, 32))
                .build();
    }

    @Override
    public Trajectory spikeEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-44, 60))
                .build();
    }

    @Override
    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(60, 60))
                .build();
    }
}
