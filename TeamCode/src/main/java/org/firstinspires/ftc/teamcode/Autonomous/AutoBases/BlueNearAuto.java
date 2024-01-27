package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import android.content.res.AssetManager;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Config
public class BlueNearAuto extends BaseAuto {

    public static Pose2d startingPosition = new Pose2d(12, 62, Math.toRadians(270));

    public static int PARK_X;
    public static int PARK_Y;
    public static int SPIKEENTER_X;
    public static int SPIKEENTER_Y;
    public static int SPIKEEXIT_X;
    public static int SPIKEEXIT_Y;
    public static int RELATIVESPIKECENTER_Y;
    public static int RELATIVESPIKELEFT_X;
    public static int RELATIVESPIKERIGHT_X;
    public static int BACKDROPSTART1_X;
    public static int BACKDROPSTART1_Y;
    public static int BACKDROPSTART2_X;
    public static int BACKDROPSTART2_Y;
    public static int BACKDROPALIGN_X;
    public static int BACKDROPALIGN_Y;
    public static int BACKDROPEND_X;
    public static int BACKDROPEND_Y;

    public BlueNearAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap, telemetry, startingPosition, true);
        AssetManager assetManager = AppUtil.getDefContext().getAssets();
        try (InputStream input = assetManager.open("coordinates.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PARK_X = Integer.parseInt(prop.getProperty("blue.park.x"));
            PARK_Y = Integer.parseInt(prop.getProperty("blue.park.y"));
            SPIKEENTER_X = Integer.parseInt(prop.getProperty("bluenear.spikeenter.x"));
            SPIKEENTER_Y = Integer.parseInt(prop.getProperty("bluenear.spikeenter.y"));
            SPIKEEXIT_X = Integer.parseInt(prop.getProperty("bluenear.spikeexit.x"));
            SPIKEEXIT_Y = Integer.parseInt(prop.getProperty("blue.spikeexit.y"));
            RELATIVESPIKECENTER_Y = Integer.parseInt(prop.getProperty("blue.relativespikecenter.y"));
            RELATIVESPIKELEFT_X = Integer.parseInt(prop.getProperty("blue.relativespikeleft.x"));
            RELATIVESPIKERIGHT_X = Integer.parseInt(prop.getProperty("blue.relativespikeright.x"));
            BACKDROPSTART1_X = Integer.parseInt(prop.getProperty("blue.backdropstart1.x"));
            BACKDROPSTART1_Y = Integer.parseInt(prop.getProperty("blue.backdropstart1.y"));
            BACKDROPSTART2_X = Integer.parseInt(prop.getProperty("blue.backdropstart2.x"));
            BACKDROPSTART2_Y = Integer.parseInt(prop.getProperty("blue.backdropstart2.y"));
            BACKDROPALIGN_X = Integer.parseInt(prop.getProperty("blue.backdropalign.x"));
            BACKDROPALIGN_Y = Integer.parseInt(prop.getProperty("blue.backdropalign.y"));
            BACKDROPEND_X = Integer.parseInt(prop.getProperty("blue.backdropend.x"));
            BACKDROPEND_Y = Integer.parseInt(prop.getProperty("blue.backdropend.y"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(PARK_X, PARK_Y))
                .build();
    }

    @Override
    public Trajectory spikeEnter(Pose2d startPose, boolean isCenter) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(SPIKEENTER_X, isCenter ? SPIKEENTER_Y + RELATIVESPIKECENTER_Y : SPIKEENTER_Y))
                .build();
    }

    @Override
    public Trajectory spikeExit(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(SPIKEEXIT_X, SPIKEEXIT_Y))
                .build();
    }

    @Override
    public Vector2d relativeSpikeCenter() {
        return new Vector2d(0, RELATIVESPIKECENTER_Y);
    }

    @Override
    public Vector2d relativeSpikeLeft() {
        return new Vector2d(RELATIVESPIKELEFT_X, 0);
    }

    @Override
    public Vector2d relativeSpikeRight() {
        return new Vector2d(RELATIVESPIKERIGHT_X, 0);
    }

    @Override
    public Trajectory backdropStart1(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(BACKDROPSTART1_X, BACKDROPSTART1_Y))
                .build();
    }

    @Override
    public Trajectory backdropStart2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(BACKDROPSTART2_X, BACKDROPSTART2_Y))
                .build();
    }

    @Override
    public Trajectory backdropAlign(Pose2d startPose, int offset) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(BACKDROPALIGN_X - BACKDROP_DISTANCE_FROM_WALL, BACKDROPALIGN_Y + offset))
                .build();
    }

    @Override
    public Trajectory backdropEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(BACKDROPEND_X, BACKDROPEND_Y))
                .build();
    }
}
