package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import static org.firstinspires.ftc.teamcode.Utilities.Backdrop.bot;
import static org.firstinspires.ftc.teamcode.Utilities.Backdrop.initBot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueFarAuto;
import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Config
@Autonomous(name = "AutoTest", group = "Autonomous: Testing")
public class AutoTest extends LinearOpMode {

    public static Backdrop.Side side = Backdrop.Side.CENTER;
    public static boolean finish = false;

    @Override
    public void runOpMode() throws InterruptedException {
//        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        waitForStart();
//        auto.spike(BlueNearAuto.startingPosition, side, finish);
        Backdrop.hardwareMap = hardwareMap;
        initBot();
//        bot.inOutTake.scoopHalfDown();
//        sleep(500);
//        Claw.closeBothClaw();
//        sleep(500);
        Backdrop.lowerArm();
//        Backdrop.liftArm();
        bot.arm.goMax();
        sleep(3000);
        bot.claw.openBothClaw();
        sleep(500);
//        Backdrop.lowerArm();
        bot.arm.goDown();
        sleep(2500);
    }
}