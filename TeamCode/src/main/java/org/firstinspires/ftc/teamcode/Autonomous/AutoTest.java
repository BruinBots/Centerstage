package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import static org.firstinspires.ftc.teamcode.Utilities.Backdrop.bot;
import static org.firstinspires.ftc.teamcode.Utilities.Backdrop.initBot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "AutoTest", group = "Autonomous: Testing")
public class AutoTest extends LinearOpMode {

    public static Backdrop.Side side = Backdrop.Side.CENTER;
    public static boolean finish = false;

    @Override
    public void runOpMode() throws InterruptedException {
//        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
//        auto.spike(BlueNearAuto.startingPosition, side, finish);
        Backdrop.telemetry = telemetry;
        Backdrop.bot = new Karen(hardwareMap);
        initBot();

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(180));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        waitForStart();

        Backdrop.alignBackdrop(drive, startPose, side);

        bot.inOutTake.scoopHalfDown();
        sleep(1000);
//        Claw.closeBothClaw();
//        sleep(500);
//        Backdrop.lowerArm();
//        Backdrop.liftArm();
        bot.arm.goMax();
        sleep(1000);
        for (int i = 0; i < 200; i ++) {
            Claw.setClawWristFromAngle(Arm.clawAngle());
            sleep(10);
        }
        bot.claw.openBothClaw();
        sleep(1500);
        bot.claw.closeBothClaw();
        sleep(500);
//        Backdrop.lowerArm();
        bot.arm.goDown();
        sleep(500);
        Claw.setClawWrist(Claw.ZERO_ANGLE_POS);
        sleep(2500);
    }
}