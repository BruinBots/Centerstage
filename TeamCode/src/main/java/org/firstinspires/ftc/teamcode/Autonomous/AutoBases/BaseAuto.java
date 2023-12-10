package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Arm;
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

    // place the pixel by the spike mark on given side
    public Trajectory spike(Pose2d startPose, String side, boolean finishSpike) {

        // ensure the pixel is securely in the dropper
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

        drive.followTrajectory(traj0a); // move to the spikeStart position to ensure no crashing during navigation
        drive.followTrajectory(traj0b); // move to the spike mark

        bot.dropper.dropperUp(); // release the pixel
        sleep(250);

        if (finishSpike) {
            drive.followTrajectory(traj0c); // if finishing spike, return to spikeEnd position to prepare for parking/pixel placing
            return traj0c;
        }

        return traj0b;
    }

    // will be run before doing spike placement
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

    // will be run after doing spike placement
    public Trajectory spikeEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // park the bot in the corner by the backdrop
    public Trajectory park(Pose2d startPose) {

        Trajectory traj2 = parkTraj(startPose);

        drive.followTrajectory(traj2);

        return traj2;
    }

    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // Place the pixel on the backdrop
    public Trajectory placePixel(Pose2d startPose) {
        // TODO: navigate to backdrop
        Trajectory startTraj = backdropStart(startPose);
        drive.followTrajectory(startTraj);

        // EXTREMELY IMPORTANT; STUFF WILL BREAK WITHOUT THIS
        bot.inOutTake.scoopMiddle();
        sleep(250);

        // lift up the arm
        bot.arm.moveArm(Arm.MAX_ARM_POSITION);
        sleep(250);

        // lift up the slide
        bot.arm.moveSlide(Arm.MIN_SLIDE_POSITION); // TODO: change this to an actual value
        sleep(500);

        // open the claw to release the pixels onto the backdrop
        bot.claw.openClaw();
        sleep(500);

        // close the claw after releasing the pixels
        bot.claw.closeBothClaw();
        sleep(100);

        // retract the slide
        bot.arm.moveSlide(Arm.MIN_SLIDE_POSITION);
        sleep(500);

        // retract the arm
        bot.arm.moveArm(Arm.MIN_ARM_POSITION);
        sleep(250);

        // TODO: may need to back up to let the pixels fall down

        // TODO: return to position for parking
        Trajectory endTraj = backdropEnd(startTraj.end());
        drive.followTrajectory(endTraj);

        return endTraj;
    }

    // this is run before pixel placing
    public Trajectory backdropStart(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // this is run after pixel placing
    public Trajectory backdropEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }
}
