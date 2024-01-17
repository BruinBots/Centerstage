package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(36, 36, Math.toRadians(180), Math.toRadians(180), 16.8)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36, 60, Math.toRadians(270)))
                                .lineToConstantHeading(new Vector2d(-44, 55))
                                .lineToConstantHeading(new Vector2d(-42, 30))
                                .lineToConstantHeading(new Vector2d(-44, 60))
                                .lineToConstantHeading(new Vector2d(15, 60))
                                .lineToConstantHeading(new Vector2d(15, 35))
                                .lineToConstantHeading(new Vector2d(48, 35))
                                .lineToConstantHeading(new Vector2d(24, 60))
                                .lineToConstantHeading(new Vector2d(60, 60))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.99f)
                .addEntity(myBot)
                .start();
    }
}