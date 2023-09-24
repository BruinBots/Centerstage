package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Basic: MotorTest TeleOp", group="Iterative Opmode")
public class MotorTest extends OpMode
{
    boolean dpadDownPrev; // Previous state of dpad_down button
    boolean dpadUpPrev;   // Previous state of dpad_up button
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
        if (dpadDown && !dpadDownPrev) {
            if (bot.Motor0.getPower() < 1.0) {
                bot.Motor0.setPower(bot.Motor0.getPower() - 0.1);
            }
        }

        // Check if dpad_up was pressed and was not pressed in the previous iteration
        if (dpadUp && !dpadUpPrev) {
            if (bot.Motor0.getPower() < 1.0) {
                bot.Motor0.setPower(bot.Motor0.getPower() + 0.1);
            }
        }

        // Check if the "A" button is pressed
        if (aButtonPressed) {
            if (bot.Motor0.getDirection() == DcMotorSimple.Direction.REVERSE) {
                bot.Motor0.setDirection(DcMotorSimple.Direction.FORWARD);
            } else {
                bot.Motor0.setDirection(DcMotorSimple.Direction.REVERSE);
            }
        }

        // Update the previous state variables
        dpadDownPrev = dpadDown;
        dpadUpPrev = dpadUp;

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telemetry.addData("Motor0 Power: ", bot.Motor0.getPower());
        telemetry.addData("Motor0 Direction: ", bot.Motor0.getDirection());
    }


    @Override
    public void stop() {
        bot.stop();
    }
}