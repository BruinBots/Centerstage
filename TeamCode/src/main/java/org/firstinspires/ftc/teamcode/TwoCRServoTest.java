package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: 2 Continuous Servos TeleOp", group="Iterative Opmode")
public class TwoCRServoTest extends OpMode
{
    boolean dpadDownPrev; // Previous state of dpad_down button
    boolean dpadUpPrev;   // Previous state of dpad_up button
    boolean dpadUp;
    boolean dpadDown;
    boolean aButtonPressed;
    boolean aButtonPrev;
    String direction = "Forward";

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
        bot.CRServo1.setPower(0.0);
        bot.CRServo2.setPower(0.0);
    }

    @Override
    public void loop() {
        dpadDown = gamepad1.dpad_down;
        dpadUp = gamepad1.dpad_up;
        aButtonPressed = gamepad1.a; // Check if the "A" button is pressed

        // Check if dpad_down was pressed and was not pressed in the previous iteration
        if (dpadDown && !dpadDownPrev) {
            if (direction.equals("Forward")) {
                if (bot.CRServo1.getPower() <= 1 && bot.CRServo1.getPower() > 0) {
                    bot.CRServo1.setPower(Math.round((bot.CRServo1.getPower() - 0.1) * 10.0) / 10.0);
                    bot.CRServo2.setPower(Math.round((bot.CRServo2.getPower() + 0.1) * 10.0) / 10.0);
                }
            } else if (direction.equals("Backward")) {
                if (bot.CRServo1.getPower() >= -1 && bot.CRServo1.getPower() < 0) {
                    bot.CRServo1.setPower(Math.round((bot.CRServo1.getPower() + 0.1) * 10.0) / 10.0);
                    bot.CRServo2.setPower(Math.round((bot.CRServo2.getPower() - 0.1) * 10.0) / 10.0);
                }
            }
        }

        // Check if dpad_up was pressed and was not pressed in the previous iteration
        if (dpadUp && !dpadUpPrev) {
            if (direction.equals("Forward")) {
                if (bot.CRServo1.getPower() < 1 && bot.CRServo1.getPower() >= 0.0) {
                    bot.CRServo1.setPower(Math.round((bot.CRServo1.getPower() + 0.1) * 10.0) / 10.0);
                    bot.CRServo2.setPower(Math.round((bot.CRServo2.getPower() - 0.1) * 10.0) / 10.0);
                }
            } else if (direction.equals("Backward")) {
                if (bot.CRServo1.getPower() > -1 && bot.CRServo1.getPower() <= 0.0) {
                    bot.CRServo1.setPower(Math.round((bot.CRServo1.getPower() - 0.1) * 10.0) / 10.0);
                    bot.CRServo2.setPower(Math.round((bot.CRServo2.getPower() + 0.1) * 10.0) / 10.0);
                }
            }
        }

        // Check if the "A" button is pressed
        if (aButtonPressed && !aButtonPrev) {
            if (direction.equals("Forward")) {
                direction = "Backward";
                bot.CRServo1.setPower(-bot.CRServo1.getPower());
                bot.CRServo2.setPower(-bot.CRServo2.getPower());
            } else if (direction.equals("Backward")) {
                direction = "Forward";
                bot.CRServo1.setPower(-bot.CRServo1.getPower());
                bot.CRServo2.setPower(-bot.CRServo2.getPower());
            }
        }

        // Update the previous state variables
        dpadDownPrev = dpadDown;
        dpadUpPrev = dpadUp;
        aButtonPrev = aButtonPressed;

        bot.CRServo1.setPower(Math.round(bot.CRServo1.getPower() * 10.0) / 10.0);
        bot.CRServo2.setPower(Math.round(bot.CRServo2.getPower() * 10.0) / 10.0);


        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telemetry.addData("CRServo1 Power: ", bot.CRServo1.getPower());
        telemetry.addData("CRServo2 Power: ", bot.CRServo2.getPower());
        telemetry.addData("CRServo Direction: ", direction);
        telemetry.update();
    }


    @Override
    public void stop() {
        bot.stop();
    }
}