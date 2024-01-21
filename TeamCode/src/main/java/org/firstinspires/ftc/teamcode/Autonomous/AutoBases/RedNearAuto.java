package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RedNearAuto extends BaseAuto {

    public static Pose2d startingPosition = new Pose2d(12, -62, Math.toRadians(90));

    public RedNearAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap, telemetry, startingPosition);
    }

    @Override
    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(60, -61))
                .build();
    }

    @Override
    public Trajectory spikeEnter2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(15, -35))  //(13,-35)
                .build();
    }

    @Override
    public Trajectory spikeExit2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
//                .lineToConstantHeading(new Vector2d(startingPosition.getX(), startingPosition.getY()+2))
                .lineToConstantHeading(new Vector2d(30. -30))
                .build();
    }

    @Override
    public Vector2d relativeSpikeCenter2() {
        return new Vector2d(0, 14);
    }

    @Override
    public Vector2d relativeSpikeLeft2() {
        return new Vector2d(11, 0);
    }

    @Override
    public Vector2d relativeSpikeRight2() {
        return new Vector2d(-11, 0);
    }

    @Override
    public Trajectory backdropStart1(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(15, -60))
                .build();
    }

    @Override
    public Trajectory backdropStart2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(40, -35))
                .build();
    }

    @Override
    public Trajectory backdropEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(45, -60))
                .build();
    }
}
