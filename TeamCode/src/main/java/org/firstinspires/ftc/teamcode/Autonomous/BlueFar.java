package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class BlueFar {

    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public SampleMecanumDrive drive;

    Karen bot;

    public BlueFar(HardwareMap hardwareMap, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.drive = drive;

        bot = new Karen(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-36, 65, Math.toRadians(90));
        drive.setPoseEstimate(startPose);
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

        sleep(300);

        Trajectory traj0a = drive.trajectoryBuilder(startPose, true)
                .lineToConstantHeading(new Vector2d(-44, 55))
                .build();

        Trajectory traj0b;

        switch (side) {
            case "left":
                telemetry.addData("side", "left");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(-35, 34))
                        .build();
                break;
            case "center":
                telemetry.addData("side", "center");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(-42, 30))
                        .build();
                break;
            case "right":
                telemetry.addData("side", "right");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(-57, 32))
                        .build();
                break;
            default:
                telemetry.addData("side", "default");
                traj0b = drive.trajectoryBuilder(traj0a.end())
                        .lineToConstantHeading(new Vector2d(-35, 34))
                        .build();
                break;
        }

        Trajectory traj0c = drive.trajectoryBuilder(traj0b.end())
                .lineTo(new Vector2d(-44, 60))
                .build();

        drive.followTrajectory(traj0a);
        drive.followTrajectory(traj0b);

        bot.dropper.dropperUp();

        sleep(500);

        drive.followTrajectory(traj0c);

        return traj0c;
    }

    public Trajectory park(Pose2d startPose) {

        Trajectory traj2 = drive.trajectoryBuilder(startPose, true)
                .lineTo(new Vector2d(60, 60))
                .build();

        drive.followTrajectory(traj2);

        return traj2;
    }
}
