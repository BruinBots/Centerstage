package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Karen;

@Autonomous(name="autotest", group="Autonomous: Testing")
public class AutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        Karen bot = new Karen(hardwareMap);

        bot.dropper.open();

        sleep(2000);
    }
}
