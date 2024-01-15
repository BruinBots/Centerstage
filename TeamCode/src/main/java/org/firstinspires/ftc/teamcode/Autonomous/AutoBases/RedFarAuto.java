package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RedFarAuto extends BaseAuto {

    public static Pose2d startingPosition = new Pose2d(-36, -60, Math.toRadians(270));

    public RedFarAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap, telemetry, startingPosition);
    }

    @Override
    public Trajectory spikeStart(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-44, -55))
                .build();
    }

    @Override
    public Trajectory spikeLeft(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-35, -34))
                .build();
    }

    @Override
    public Trajectory spikeCenter(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-36, -37))
                .build();
    }

    @Override
    public Trajectory spikeRight(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-34, -36))
                .build();
    }

    @Override
    public Trajectory spikeEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-55, -60))
                .build();
    }

    @Override
    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(60, -60))
                .build();
    }

    @Override
    public Trajectory spikeEnter2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-35, -35))
                .build();
    }

    @Override
    public Trajectory spikeExit2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(startingPosition.getX(), startingPosition.getY()))
                .build();
    }

    @Override
    public Vector2d relativeSpikeCenter2() {
        return new Vector2d(0, 10);
    }

    @Override
    public Vector2d relativeSpikeLeft2() {
        return new Vector2d(10, 0);
    }

    @Override
    public Vector2d relativeSpikeRight2() {
        return new Vector2d(-10, 0);
    }

    @Override
    public Trajectory backdropStart1(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(24, -60))
                .build();
    }

    @Override
    public Trajectory backdropStart2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(36, -35), Math.toRadians(270))
                .build();
    }

    @Override
    public Trajectory backdropEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(36, -35))
                .build();
    }
}
