package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Karen;

@Autonomous(name="AutoTest", group="Autonomous: Testing")
public class AutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Karen bot = new Karen(hardwareMap);
        waitForStart();
        bot.inOutTake.scoopMiddle();
        sleep(500);
        bot.arm.moveArm(1800);
        sleep(2500);
        // TODO: open claw
        bot.arm.moveArm(0);
        sleep(2500);
//        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
//        waitForStart();
//        auto.placePixel(BlueNearAuto.startingPosition, "left", true);
    }
}
