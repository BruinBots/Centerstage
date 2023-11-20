package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "BlueFarBackdrop", group = "Autonomous: Testing")
public class BlueFarBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Karen bot = new Karen(hardwareMap);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // We want to start the bot at x: -36, y: 65, heading: 270 degrees
        Pose2d startPose = new Pose2d(-36,65, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose, true)
                .splineTo(new Vector2d(-36, 60), Math.toRadians(0))
                .splineTo(new Vector2d(18, 60), Math.toRadians(0))
                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end(), true)
                .splineTo(new Vector2d(40, 36), Math.toRadians(0))
                .splineTo(new Vector2d(60, 60), Math.toRadians(0))
                .build();

        // -36, 28 center
        // -26, 28 left
        // -42, 28 right

        // y=36 center
        // y=28 right
        // y=42 left

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(traj1); // navigate to backboard

        bot.arm.moveArm(Arm.MAX_ARM_POSITION); // move arm up
        bot.arm.moveSlide(Arm.MAX_SLIDE_POSITION - 300); // move slide up
        bot.claw.openClaw(); // release the pixels

        bot.claw.closeBothClaw(); // close claw so it doesn't get caught on wires
        bot.arm.moveArm(Arm.MIN_ARM_POSITION); // retract arm
        bot.arm.moveSlide(Arm.MIN_SLIDE_POSITION); // retract slide

        drive.followTrajectory(traj2);

    }
}
