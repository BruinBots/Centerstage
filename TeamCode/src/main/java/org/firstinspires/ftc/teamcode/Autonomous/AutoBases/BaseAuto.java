package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Autonomous.AprilTags;
import org.firstinspires.ftc.teamcode.Autonomous.AprilTagsAutonomous;
import org.firstinspires.ftc.teamcode.Autonomous.AprilTagsUpdated;
import org.firstinspires.ftc.teamcode.Autonomous.TensorFlowForAutonomousBlueRed;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.hardware.Servo;

public class BaseAuto {

    // initialize class variables
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    public SampleMecanumDrive drive;
    private Karen bot;
    private final String TEAM_PROP_COLOR="red"; // default to red

    public BaseAuto(HardwareMap hardwareMap, Telemetry telemetry, Pose2d startingPosition) {

        // assign class variables
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        // create bot from hardwareMap
        bot = new Karen(hardwareMap);

        // create SampleMecanumDrive from hardwareMap and set the startingPosition
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPosition);
    }

    /*
    tfSpike() -> String
    use tensorflow to determine which side the spike mark is on, returning as a string from:
    - "left"
    - "center"
    - "right"
     */
    public String tfSpike() {

        TensorFlowForAutonomousBlueRed tf = new TensorFlowForAutonomousBlueRed(hardwareMap, telemetry,TEAM_PROP_COLOR);
        tf.initTfod();
        sleep(500);

        tf.visionPortal.resumeStreaming();

        int i = 0;
        String side = "none";
        while (side.equals("none") && i < 2) {
            side = tf.getSide(TEAM_PROP_COLOR);
            telemetry.addData("A-side", side);
            telemetry.update();
            i++;
            sleep(20);
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

    public Trajectory park(Trajectory startTraj) {
        return park(startTraj.end());
    }

    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // Place the pixel on the backdrop
    public Trajectory placePixel(Pose2d startPose, int aprilId, boolean blue, boolean finishPixel) {
        // navigate to backdrop

        Trajectory start1 = backdropStart1(startPose);
        drive.followTrajectory(start1);

        Trajectory start2 = backdropStart2(start1.end());
        drive.followTrajectory(start2);
        drive.turn(Math.toRadians(blue ? 90 : -90));

        AprilTagsAutonomous aprilTags = new AprilTagsAutonomous();
//        AprilTags aprilTags = new AprilTags();
//        AprilTagsUpdated aprilTags = new AprilTagsUpdated();
        Vector2d aprilVector = aprilTags.getOffset(hardwareMap, telemetry, aprilId);
        telemetry.addData("x", aprilVector.getX());
        telemetry.addData("y", aprilVector.getY());
        telemetry.update();
        sleep(5000);
        Pose2d startEnd = start2.end().plus(new Pose2d(0, 0, Math.toRadians(blue ? 90 : -90)));
        Trajectory aprilTraj = drive.trajectoryBuilder(startEnd)
                .lineToConstantHeading(new Vector2d(startEnd.getX() + aprilVector.getX(), startEnd.getY() + aprilVector.getY()))
                .build();
        drive.followTrajectory(aprilTraj);

        // TODO: place pixel
        // move arm up
        bot.inOutTake.scoopHalfDown();
        sleep(750);
        bot.arm.moveArm(1500);
        sleep(3000);
        // release claw
        bot.claw.openBothClaw();
        sleep(1000);
        // move arm down
        bot.arm.moveArm(50);
        sleep(3000);
        bot.inOutTake.scoopMiddle();
        sleep(500);


        if (finishPixel) {
            Trajectory end = backdropEnd(aprilTraj.end());
            drive.followTrajectory(end);
            return end;
        }

        return aprilTraj;
    }

    public Trajectory placePixel(Pose2d startPose, int aprilId, boolean blue) {
        return placePixel(startPose, aprilId, blue, false);
    }

    public Trajectory placePixel(Pose2d startPose, String side, boolean blue) {
        return placePixel(startPose, 2, blue, false);
    }

    public Trajectory backdropStart1(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // this is run before pixel placing
    public Trajectory backdropStart2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // this is run after pixel placing
    public Trajectory backdropEnd(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }


    //region ---------- BaseAuto v2.0 ----------

    /*
    spike(Pose2d startPose, String side, boolean finishSpike) -> Trajectory

     parameters:
        - startPose: the position the robot is currently in
        - side: the side to navigate to on the spike
        - finishSpike: if true, the robot will follow the trajectory given by spikeEnd() after placing pixel on spike mark

     places the pixel by the spike mark on given side
     */
    public Pose2d spike2(Pose2d startPose, String side, boolean finishSpike) {

        Trajectory enter = spikeEnter2(startPose);

        drive.followTrajectory(enter);

        Pose2d endEnter = enter.end();
        Vector2d vector;
        Trajectory traj;

        switch (side) {
            case "left":
                telemetry.addData("side", "left");
                drive.turn(Math.toRadians(90));
                endEnter = endEnter.plus(new Pose2d(0, 0, Math.toRadians(90)));
                vector = relativeSpikeLeft2();
                traj = drive.trajectoryBuilder(endEnter)
                        .lineToConstantHeading(new Vector2d(endEnter.getX() + vector.getX(), endEnter.getY() + vector.getY()))
                        .build();
                drive.followTrajectory(traj);
                endEnter = traj.end();
                break;
            case "center":
                telemetry.addData("side", "center");
                vector = relativeSpikeCenter2();
                traj = drive.trajectoryBuilder(endEnter)
                        .lineToConstantHeading(new Vector2d(endEnter.getX() + vector.getX(),endEnter.getY() + vector.getY()))
                        .build();
                drive.followTrajectory(traj);
                endEnter = traj.end();
                break;
            case "right":
                telemetry.addData("side", "right");
                drive.turn(Math.toRadians(-90));
                endEnter = endEnter.plus(new Pose2d(0, 0, Math.toRadians(-90)));
                vector = relativeSpikeRight2();
                traj = drive.trajectoryBuilder(endEnter)
                        .lineToConstantHeading(new Vector2d(endEnter.getX() + vector.getX(), endEnter.getY() + vector.getY()))
                        .build();
                drive.followTrajectory(traj);
                endEnter = traj.end();
                break;
            default:
                telemetry.addData("side", "default");
                drive.turn(Math.toRadians(90));
                endEnter = endEnter.plus(new Pose2d(0, 0, Math.toRadians(90)));
                vector = relativeSpikeLeft2();
                traj = drive.trajectoryBuilder(endEnter)
                        .lineToConstantHeading(new Vector2d(endEnter.getX() + vector.getX(), endEnter.getY() + vector.getY()))
                        .build();
                drive.followTrajectory(traj);
                endEnter = traj.end();
                break;
        }

        // TODO: release the pixel

        if (finishSpike) {
            Trajectory exit = spikeExit2(endEnter);
            drive.followTrajectory(exit); // if finishing spike, return to spikeEnd position to prepare for parking/pixel placing
            return exit.end();
        }

        return endEnter;
    }

    public Trajectory spikeEnter2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    public Vector2d relativeSpikeLeft2() {
        return new Vector2d(0, 0);
    }

    public Vector2d relativeSpikeCenter2() {
        return new Vector2d(0, 0);
    }

    public Vector2d relativeSpikeRight2() {
        return new Vector2d(0, 0);
    }

    public Trajectory spikeExit2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }
}