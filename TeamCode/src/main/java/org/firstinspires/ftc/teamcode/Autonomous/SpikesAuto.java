package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Spikes Autonomous", group = "Autonomous: Testing (for now)")
public class SpikesAuto extends LinearOpMode {

    // SPIKE_LOCATION
    // -1       0       1       2
    // unknown  left    center  right
    private int SPIKE_LOCATION = -1;

    Karen bot;

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Karen(hardwareMap);

        // TODO: detect spike

        switch (SPIKE_LOCATION) {
            case 0:
                dropLeftSpike();
                parkLeftBackdrop();
                break;
            case 1:
                dropCenterSpike();
                parkCenterBackdrop();
                break;
            case 2:
                dropRightSpike();
                parkRightBackdrop();
                break;
            default:
                return;
        }

        bot.arm.moveArm(Arm.ARM_UP);
        bot.claw.closeOneClaw();
        bot.arm.moveArm(Arm.ARM_DOWN);
    }

    // TODO: drop at spike roadrunner

    private void dropLeftSpike() {
        // roadrunner
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory dropLeftSpikeTrajectory = drive.trajectoryBuilder(new Pose2d())
                .lineTo(new Vector2d(01, 2)) // TODO: need actual values here
                .build();

        Trajectory revertLeftSpikeTrajectory = drive.trajectoryBuilder(dropLeftSpikeTrajectory.end())
                .lineTo(new Vector2d(1, -2))
                .build();

        drive.followTrajectory(dropLeftSpikeTrajectory);

        bot.dropper.dropperUp();
        // roadrunner back

        drive.followTrajectory(revertLeftSpikeTrajectory);
    }

    private void dropCenterSpike() {
        // roadrunner
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory dropCenterSpikeTrajectory = drive.trajectoryBuilder(new Pose2d())
                .forward(2) // TODO: need actual values here
                .build();

        Trajectory revertCenterSpikeTrajectory = drive.trajectoryBuilder(dropCenterSpikeTrajectory.end())
                .back(2)
                .build();

        drive.followTrajectory(dropCenterSpikeTrajectory);

        bot.dropper.dropperUp();
        // roadrunner back

        drive.followTrajectory(revertCenterSpikeTrajectory);
    }

    private void dropRightSpike() {
        // roadrunner
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory dropRightSpikeTrajectory = drive.trajectoryBuilder(new Pose2d())
                .lineTo(new Vector2d(1, 2)) // TODO: need actual values here
                .build();

        Trajectory revertRightSpikeTrajectory = drive.trajectoryBuilder(dropRightSpikeTrajectory.end())
                .lineTo(new Vector2d(-1, -2))
                .build();

        drive.followTrajectory(dropRightSpikeTrajectory);

        bot.dropper.dropperUp();
        // roadrunner back

        drive.followTrajectory(revertRightSpikeTrajectory);
    }

    // TODO: park at backdrop

    private void parkLeftBackdrop() {

    }

    private void parkCenterBackdrop() {

    }

    private void parkRightBackdrop() {

    }
}
