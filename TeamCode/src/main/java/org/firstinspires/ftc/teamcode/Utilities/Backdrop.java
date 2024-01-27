package org.firstinspires.ftc.teamcode.Utilities;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Hanger;
import org.firstinspires.ftc.teamcode.InOutTake;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import static android.os.SystemClock.sleep;

@Config
public class Backdrop {

    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;
    public static double BACKDROP_OFFSET = 6.15;

    public static enum Side {
        LEFT,
        CENTER,
        RIGHT
    }
    public static Karen bot;

    public static void initBot() {
        Backdrop.bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
        bot.init();
        telemetry.update();
    }

    public static Pose2d alignBackdrop(SampleMecanumDrive drive, Pose2d startPose, Side side) {
//        sleep(100);
        double distance = bot.distance.getDistance();
        telemetry.addData("distance", distance);
        int offset = 0;
        switch (side) {
            case LEFT:
                offset = 6;
                break;
            case RIGHT:
                offset = -6;
                break;
        }
        telemetry.addData("RedNearFull backdrop startpose.getx ", startPose.getX());
        telemetry.addData("RedNearFull backdrop startpose.gety ", startPose.getY());
        telemetry.update();
        Trajectory traj = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(startPose.getX() + distance - BACKDROP_OFFSET, startPose.getY() + offset))
                .build();
        drive.followTrajectory(traj);
        return traj.end();
    }

    public static void placePixel() {
        bot.inOutTake.scoopDown();
        sleep(1000);
        bot.arm.goMax();
        sleep(1000);
        for (int i = 0; i < 150; i ++) {
            Claw.setClawWristFromAngle(Arm.clawAngle());
            sleep(10);
        }
        bot.claw.openBothClaw();
        sleep(1500);
        bot.claw.closeBothClaw();
        //sleep(500);
        bot.arm.goDown();
        //sleep(500);
        Claw.setClawWrist(Claw.ZERO_ANGLE_POS);
        sleep(2000); //2500
        bot.inOutTake.scoopMiddle();
        sleep(500);
    }
}
