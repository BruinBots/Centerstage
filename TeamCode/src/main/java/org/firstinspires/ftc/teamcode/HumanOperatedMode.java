package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class HumanOperatedMode extends OpMode
{
    Karen bot;
    boolean lastAButton;
    long currentTime;

    //
    @Override
    public void init() {

        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    //
    @Override
    public void init_loop() {
    }

    //
    @Override
    public void start() {
    }

    //
    @Override
    public void loop() {
        if (gamepad1.a && !lastAButton) {
            bot.drone.launch();
        }
        bot.drone.loop();

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lastAButton = gamepad1.a;
    }


    @Override
    public void stop() {
        bot.stop();
    }
}