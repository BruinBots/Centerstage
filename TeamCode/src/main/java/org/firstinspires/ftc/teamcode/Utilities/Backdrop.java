package org.firstinspires.ftc.teamcode.Utilities;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Hanger;
import org.firstinspires.ftc.teamcode.InOutTake;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import static android.os.SystemClock.sleep;

public class Backdrop {

    public static HardwareMap hardwareMap;
    public static double BACKDROP_OFFSET = 10;

    public static enum Side {
        LEFT,
        CENTER,
        RIGHT
    }
    public static Karen bot;

    public static void initBot() {
        if (bot == null) {
            Backdrop.bot = new Karen(hardwareMap);
            telemetry.addData("Status", "Initialized");
            bot.claw.setClawWrist(0.1);
            bot.claw.closeBothClaw();
            bot.drone.resetPoses();
            bot.hanger.hangServo.setPosition(bot.hanger.PRIMED_POS);
            bot.dropper.closed();
            bot.inOutTake.scoopMiddle();
            telemetry.update();
        }
    }

    public static Pose2d alignBackdrop(SampleMecanumDrive drive, Pose2d startPose, Side side) {
//        int distance = 0;
        double distance = bot.distance.getDistance();

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
        if (bot.scoopServo.getPosition() > InOutTake.SCOOP_MIDDLE_POS + 0.01) {
            bot.inOutTake.scoopDown();
            sleep(500);
        }
        Claw.closeBothClaw();
        sleep(250);
        bot.arm.moveArm(25, true, 0.3);
        sleep(250);
        bot.arm.moveArm(2560, true);
        sleep(750);
        for (int i = 0; i < 45; i++) {
            Claw.setClawWristFromAngle(Arm.clawAngle());
            sleep(50);
        }
    }

    public static void lowerArm() {
//        if (bot.scoopServo.getPosition() > InOutTake.SCOOP_MIDDLE_POS + 0.01) {
            bot.inOutTake.scoopDown();
            sleep(500);
//        }
        bot.arm.moveArm(25, false);
        sleep(500);
        Claw.setClawWrist(Claw.ZERO_ANGLE_POS);
        sleep(2500);
    }
}
