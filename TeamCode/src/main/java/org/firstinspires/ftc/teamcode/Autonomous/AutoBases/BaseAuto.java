package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.TensorFlowForAutonomousBlue;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class BaseAuto {

    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public SampleMecanumDrive drive;

    Karen bot;

    public static Pose2d startingPosition;

    public BaseAuto(HardwareMap hardwareMap, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.drive = drive;

        bot = new Karen(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPosition);
    }

    public String tfSpike() {

        TensorFlowForAutonomousBlue tf = new TensorFlowForAutonomousBlue(hardwareMap, telemetry);
        tf.initTfod();
        sleep(500);

        tf.visionPortal.resumeStreaming();

        int i = 0;
        String side = "none";
        while (side.equals("none") && i < 15) {
            side = tf.getSide();
            telemetry.addData("A-side", side);
            telemetry.update();
            i++;
            sleep(30);
        }

        tf.visionPortal.close();

        return side;
    }

    public Trajectory spike(Pose2d startPose, String side) {

        bot.dropper.dropperDown();

        sleep(250);

        Trajectory traj0a = spikeStart(startPose);

        Trajectory traj0b;

        switch (side) {
            case "left":
                telemetry.addData("side", "left");
                traj0b = spikeLeft(traj0a.end());
                break;
            case "center":
                telemetry.addData("side", "center");
                traj0b = spikeCenter(traj0a.end());
                break;
            case "right":
                telemetry.addData("side", "right");
                traj0b = spikeRight(traj0a.end());
                break;
            default:
                telemetry.addData("side", "default");
                traj0b = spikeLeft(traj0a.end());
                break;
        }

        Trajectory traj0c = spikeEnd(traj0b.end());

        drive.followTrajectory(traj0a);
        drive.followTrajectory(traj0b);

        bot.dropper.dropperUp();

        sleep(250);

        drive.followTrajectory(traj0c);

        return traj0c;
    }

    public Trajectory spikeStart(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    public Trajectory spikeLeft(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    public Trajectory spikeCenter(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    public Trajectory spikeRight(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    public Trajectory spikeEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }



    public Trajectory park(Pose2d startPose) {

        Trajectory traj2 = parkTraj(startPose);

        drive.followTrajectory(traj2);

        return traj2;
    }

    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }
}
