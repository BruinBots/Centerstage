package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: ServoTest TeleOp", group="Iterative Opmode")
public class ServoTest extends OpMode
{
    boolean dpadUp;
    boolean dpadDown;
    boolean aButtonPressed;

    Karen bot;

    @Override
    public void init() {
        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        dpadDown = gamepad1.dpad_down;
        dpadUp = gamepad1.dpad_up;
        aButtonPressed = gamepad1.a; // Check if the "A" button is pressed

        // Check if dpad_down was pressed and was not pressed in the previous iteration
        if (dpadDown) {
            bot.Servo0.setPosition(bot.Servo0.getPosition()-0.05);
        }

        // Check if dpad_up was pressed and was not pressed in the previous iteration
        if (dpadUp) {
            bot.Servo0.setPosition(bot.Servo0.getPosition()+0.05);
        }


        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telemetry.addData("Servo0 Position: ", bot.Servo0.getPosition());
        telemetry.update();
    }


    @Override
    public void stop() {
        bot.stop();
    }
}