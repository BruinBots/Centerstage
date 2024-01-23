package org.firstinspires.ftc.teamcode.Autonomous.AutoBases;

import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Autonomous.TensorFlowForAutonomousBlueRed;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class BaseAuto {
    public static final double BACKDROP_DISTANCE_FROM_WALL = 13;
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private final Karen bot;
    public SampleMecanumDrive drive;

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
        Trajectory traj = drive.trajectoryBuilder(startPose).lineToConstantHeading(new Vector2d(startPose.getX(), Math.copySign(Math.abs(startPose.getY()) - 2, startPose.getY()))).build();
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
    public Backdrop.Side tfSpike(boolean blue) {
        // move the flipper down to let the camera see the orbs
        bot.scoopServo.setPosition(0);
        bot.inOutTake.scoopDown();
        sleep(500); // let the flipper move down

        // instantiate tensorflow
        TensorFlowForAutonomousBlueRed tf = new TensorFlowForAutonomousBlueRed(hardwareMap, telemetry, blue ? "blue" : "red");
        Backdrop.Side side = tf.compute(blue);
        bot.inOutTake.scoopMiddle(); // move the flipper back up to not hit it against the field
        sleep(1000); // let the flipper move up
        return side;
    }

    // park the bot in the corner by the backdrop
    public Trajectory park(Pose2d startPose) {
        Trajectory traj = parkTraj(startPose);
        drive.followTrajectory(traj);
        return traj;
    }

    public Trajectory park(Trajectory startTraj) {
        return park(startTraj.end());
    }

    public Trajectory parkTraj(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // Place the pixel on the backdrop
    public Pose2d placePixel(Pose2d startPose, Backdrop.Side side, boolean blue, boolean finishPixel) {
        // navigate to backdrop
        Trajectory start1 = backdropStart1(startPose); //(,35)
        drive.followTrajectory(start1);

        drive.turn(blue ? -90 : 90);
        Pose2d startEnd = startPose.plus(new Pose2d(0, 0, Math.toRadians(blue ? -90 : 90)));

        Trajectory start2 = backdropStart2(startEnd);
        drive.followTrajectory(start2);

        Pose2d endAlign = Backdrop.alignBackdrop(drive, start2.end(), side);
        Backdrop.liftArm();
        bot.claw.openBothClaw();
        sleep(500);
        Backdrop.lowerArm();

        if (finishPixel) {
            Trajectory end = backdropEnd(endAlign);
            drive.followTrajectory(end);
            return end.end();
        }

        return endAlign;
    }

    public Trajectory backdropAlign(Pose2d startPose, int offset) {
        return drive.trajectoryBuilder(startPose).build();
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
    public Pose2d spike(Pose2d startPose, Backdrop.Side side, boolean finishSpike) {
        Trajectory enter = spikeEnter(startPose, side == Backdrop.Side.CENTER); //(15,35) blue near Center spike
        drive.followTrajectory(enter);

        Pose2d endEnter = enter.end();
        Vector2d vector = null;
        Trajectory traj = null;

        telemetry.addData("side", side);
        telemetry.update();

        int angle = 0;

        switch (side) {
            case LEFT:
                angle = 90;
                vector = relativeSpikeLeft();
                break;
            case RIGHT:
                angle = -90;
                vector = relativeSpikeRight();
                break;
        }

        drive.turn(Math.toRadians(angle));
        endEnter = endEnter.plus(new Pose2d(0, 0, Math.toRadians(angle)));

        if (vector != null) {
            traj = drive.trajectoryBuilder(endEnter).lineToConstantHeading(new Vector2d(endEnter.getX() + vector.getX(), endEnter.getY() + vector.getY())).build();
        }
        if (traj != null) {
            drive.followTrajectory(traj);
            endEnter = traj.end();
        }
        bot.inOutTake.scoopDown();
        sleep(500);
        bot.inOutTake.outtake();
        sleep(500);
        bot.inOutTake.stopTake();
        sleep(250);
        bot.inOutTake.scoopUp();
        sleep(750);

        drive.turn(Math.toRadians(-angle));
        endEnter = endEnter.plus(new Pose2d(0, 0, Math.toRadians(-angle)));

        if (finishSpike) {
            Pose2d finish = endEnter;
            if (side != Backdrop.Side.CENTER) {
                Trajectory finishEnter = spikeEnter(endEnter, side == Backdrop.Side.CENTER);
                drive.followTrajectory(finishEnter);
                finish = finishEnter.end();
            }
            Trajectory exit = spikeExit(finish);
            drive.followTrajectory(exit); // if finishing spike, return to spikeEnd position to prepare for parking/pixel placing
            return exit.end();
        }
        return endEnter;
    }

    // this will move the robot in the center of all the spike marks
    public Trajectory spikeEnter(Pose2d startPose, boolean isCenter) {
        return drive.trajectoryBuilder(startPose).build();
    }

    // if the spike is left, the robot will move this vector relative to the current position to drop the pixel
    public Vector2d relativeSpikeLeft() {
        return new Vector2d(0, 0);
    }

    // if the spike is center, the robot will move this vector relative to the current position to drop the pixel
    public Vector2d relativeSpikeCenter() {
        return new Vector2d(0, 0);
    }

    public Vector2d relativeSpikeRight() {
        return new Vector2d(0, 0);
    }

    public Trajectory spikeExit(Pose2d startPose) {
        return drive.trajectoryBuilder(startPose).build();
    }
    //endregion
}