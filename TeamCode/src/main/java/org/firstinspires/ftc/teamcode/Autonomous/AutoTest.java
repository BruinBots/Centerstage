package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoBases.BlueNearAuto;
import org.firstinspires.ftc.teamcode.Karen;

@Autonomous(name="AutoTest", group="Autonomous: Testing")
public class AutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        Karen bot = new Karen(hardwareMap);
//        waitForStart();
//        bot.arm.moveArm(1800);
//        sleep(5000);
        BlueNearAuto auto = new BlueNearAuto(hardwareMap, telemetry);
        waitForStart();
        auto.placePixel(BlueNearAuto.startingPosition, "left", true);
    }
}
