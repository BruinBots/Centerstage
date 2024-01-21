package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.AprilTagsAutonomous;
import org.firstinspires.ftc.teamcode.Autonomous.TensorFlowForAutonomousBlueRed;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class BaseAuto {
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    public SampleMecanumDrive drive;
    private Karen bot;

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

    public Trajectory startPark(Pose2d startPose) {
        Trajectory traj = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(startPose.getX(), Math.copySign(Math.abs(startPose.getY()) - 2, startPose.getY())))
                .build();
        drive.followTrajectory(traj);
        return traj;
    }

    /*
    tfSpike() -> String
    use tensorflow to determine which side the spike mark is on, returning as a string from:
    - "left"
    - "center"
    - "right"
     */
    public String tfSpike(boolean blue) {
        bot.scoopServo.setPosition(0);
        bot.inOutTake.scoopDown();
        TensorFlowForAutonomousBlueRed tf = new TensorFlowForAutonomousBlueRed(hardwareMap, telemetry, blue ? "blue" : "red");
        tf.initTfod();
        tf.visionPortal.resumeStreaming();
        sleep(1000);
        int i = 0;
        String side = "center";
        while (side.equals("none") && i < 2) {
            side = tf.getSide(blue);
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
        bot.dropper.closed();
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
                traj0b = spikeCenter(traj0a.end());
                break;
        }

        Trajectory traj0c = spikeEnd(traj0b.end());

        drive.followTrajectory(traj0a); // move to the spikeStart position to ensure no crashing during navigation
        drive.followTrajectory(traj0b); // move to the spike mark

        bot.dropper.open(); // release the pixel
//        sleep(250);

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
    public Trajectory placePixel(Pose2d startPose, String side, boolean blue, boolean finishPixel) {
        Pose2d startEnd = startPose.plus(new Pose2d(0, 0, Math.toRadians(blue ? -90 : 90)));

        // navigate to backdrop
       Trajectory start1 = backdropStart2(startEnd); //(48,35)
        drive.followTrajectory(start1);

//
//        Trajectory start2 = backdropStart2(startEnd); //(40,35)
//        drive.followTrajectory(start2);


//        AprilTagsAutonomous aprilTags = new AprilTagsAutonomous();
//        AprilTags aprilTags = new AprilTags();
//        AprilTagsUpdated aprilTags = new AprilTagsUpdated();
//        Vector2d aprilVector = aprilTags.getOffset(hardwareMap, telemetry, aprilId);
//        telemetry.addData("x", aprilVector.getX());
//        telemetry.addData("y", aprilVector.getY());
//        telemetry.update();
//        sleep(5000);

//        Trajectory aprilTraj = drive.trajectoryBuilder(startEnd)
//                .lineToConstantHeading(new Vector2d(startEnd.getX() + aprilVector.getX(), startEnd.getY() + aprilVector.getY()))
//                .build();
//        drive.followTrajectory(aprilTraj);

        int offset;
        switch (side) {
            case "left":
                offset = 6;
                break;
            case "right":
                offset = -6;
                break;
            default:
                offset = 0;
                break;
        }
//        Trajectory backTraj = drive.trajectoryBuilder(start1.end())
//                .lineToConstantHeading(new Vector2d(48, 35)) //48 too close need adjust at the field
//                .build();
//        drive.followTrajectory(backTraj);
// TODO: place pixel
        bot.inOutTake.scoopDown();
        bot.claw.closeBothClaw();
        sleep(500);
        telemetry.addData("2560 arm", "2560");
        bot.arm.moveArm(2560, true); // 2560
        sleep(500);
        bot.claw.setClawWrist(0.266);
        sleep(500);
        sleep(2500);
        bot.claw.openBothClaw();
        sleep(500);
        telemetry.addData("80 arm", "80");
        bot.arm.moveArm(80, true);
        sleep(1000);
        bot.claw.setClawWrist(0.1);
        sleep(1000);
        telemetry.addData("0 arm", "0");
        bot.arm.moveArm(0,true);
        //if (finishPixel) {
        Trajectory end = backdropEnd(start1.end());
        drive.followTrajectory(end);
        bot.inOutTake.scoopUp();
          //  return end;
        //}
        return start1;
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
        drive.setPoseEstimate(startPose);
        Trajectory enter = spikeEnter2(startPose); //(13,35) Center spike
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
//                drive.turn(Math.toRadians(90));
//                endEnter = endEnter.plus(new Pose2d(0, 0, Math.toRadians(90)));
                vector = relativeSpikeLeft2();
                traj = drive.trajectoryBuilder(endEnter)
                        .lineToConstantHeading(new Vector2d(endEnter.getX() + vector.getX(), endEnter.getY() + vector.getY()))
                        .build();
                drive.followTrajectory(traj);
                endEnter = traj.end();
                break;
        }
        telemetry.update();
        bot.dropper.open();
        bot.inOutTake.scoopUp();
        sleep(500);

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

    public Vector2d  relativeSpikeCenter2() {
        return new Vector2d(0, 0);
    }

    public Vector2d relativeSpikeRight2() {
        return new Vector2d(0, 0);
    }

    public Trajectory spikeExit2(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }
    //endregion
}