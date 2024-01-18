package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "test", group = "test")
public class TestingOpMode extends OpMode {

    Karen bot;
    // Mini MG-90S

    @Override
    public void init() {
        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
        bot.clawFirstFinger.setPosition(0.0);
        bot.clawSecondFinger.setPosition(0.0);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up && bot.clawFirstFinger.getPosition() < bot.claw.LOWER_OPEN) {
            bot.clawFirstFinger.setPosition(bot.clawFirstFinger.getPosition() + 0.005);
        } else if (gamepad1.dpad_down && bot.clawFirstFinger.getPosition() > bot.claw.LOWER_CLOSED) {
            bot.clawFirstFinger.setPosition(bot.clawFirstFinger.getPosition() - 0.005);
        } else if (gamepad1.dpad_left && bot.clawSecondFinger.getPosition() > bot.claw.UPPER_CLOSED) {
            bot.clawSecondFinger.setPosition(bot.clawSecondFinger.getPosition() - 0.005);
        } else if (gamepad1.dpad_right && bot.clawSecondFinger.getPosition() < bot.claw.UPPER_OPEN) {
            bot.clawSecondFinger.setPosition(bot.clawSecondFinger.getPosition() + 0.005);
        }

        if (bot.clawFirstFinger.getPosition() < bot.claw.LOWER_CLOSED) {
            bot.clawFirstFinger.setPosition(bot.claw.LOWER_CLOSED);
        } else if (bot.clawFirstFinger.getPosition() > bot.claw.LOWER_OPEN) {
            bot.clawFirstFinger.setPosition(bot.claw.LOWER_OPEN);
        } else if (bot.clawSecondFinger.getPosition() < bot.claw.UPPER_CLOSED) {
            bot.clawSecondFinger.setPosition(bot.claw.UPPER_CLOSED);
        } else if (bot.clawSecondFinger.getPosition() > bot.claw.UPPER_OPEN) {
            bot.clawSecondFinger.setPosition(bot.claw.UPPER_OPEN);
        }

        telemetry.addData("First Claw", bot.clawFirstFinger.getPosition());
        telemetry.addData("Second Claw", bot.clawSecondFinger.getPosition());
        telemetry.update();
    }
}
