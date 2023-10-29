package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class HumanOperatedMode extends OpMode
{
    Karen bot;
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;
    boolean lastAButton;

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

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

//        bot.moveBotMecanum(drive, turn, strafe, 1);

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