package org.firstinspires.ftc.teamcode.Utilities;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import static android.os.SystemClock.sleep;

public class Backdrop {

    public static double BACKDROP_OFFSET = 3;

    public static enum Side {
        LEFT,
        CENTER,
        RIGHT
    }
    public static Karen bot;

    public static void initBot() {
        if (bot == null) {
            Backdrop.bot = new Karen(hardwareMap);
        }
    }

    public static Pose2d alignBackdrop(SampleMecanumDrive drive, Pose2d startPose, Side side) {
        initBot();
        int distance = 0;
        // distance = bot.distanceSensor.getDistance();

        int offset = 0;
        switch (side) {
            case LEFT:
                offset = 6;
                break;
            case RIGHT:
                offset = -6;
                break;
        }
        Trajectory traj = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(distance - BACKDROP_OFFSET, offset))
                .build();
        drive.followTrajectory(traj);
        return traj.end();
    }

    public static void liftArm() {
        bot.inOutTake.scoopDown();
        Claw.closeBothClaw();
        sleep(500);
        bot.arm.moveArm(2560, true, 0.8);
        for (int i = 0; i < 5; i++) {
            Claw.setClawWristFromAngle(Arm.armAngle());
            sleep(500);
        }
    }

    public static void lowerArm() {
        bot.arm.moveArm(80, true, 0.7);
        sleep(1000);
        Claw.setClawWrist(Claw.ZERO_ANGLE_POS);
        sleep(1000);
        bot.arm.moveArm(0, true);
        sleep(1000);
    }
}
