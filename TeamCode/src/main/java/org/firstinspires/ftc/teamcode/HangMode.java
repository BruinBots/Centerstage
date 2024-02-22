package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="HangMode", group="Kings Glen")
public class HangMode extends OpMode {
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;

    boolean hanging;

    Karen bot;

    @Override
    public void init() {
        bot = new Karen(hardwareMap);
        bot.init();
    }

    @Override
    public void loop() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        if (drive > 1) { drive = 1; }
        if (strafe > 1) { strafe = 1; }
        if (turn > 1) { turn = 1; }

        strafe = Math.copySign(Math.pow(strafe, 3), strafe);
        drive = Math.copySign(Math.pow(drive, 3), drive);
        turn = Math.copySign(Math.pow(turn, 3), turn);

        // emergency brake if we need it (on controller 2)
        if (gamepad2.b) {
            bot.leftBackMotor.setPower(0);
            bot.leftFrontMotor.setPower(0);
            bot.rightBackMotor.setPower(0);
            bot.rightFrontMotor.setPower(0);
            drive = 0;
            strafe = 0;
            turn = 0;
        }

        // basic arm controls
        if (gamepad1.dpad_up) {
            bot.arm.goStraight();
        }
        else if (gamepad1.dpad_down) {
            bot.arm.goDown();
        }

        // hang
        if (gamepad1.left_stick_button && gamepad1.right_stick_button && !hanging) {
            hanging = true;
            bot.hanger.hang();
        }

        if (!gamepad1.left_stick_button && !gamepad1.right_stick_button) {
            hanging = false;
        }

        bot.moveBotMecanum(drive, turn, strafe, 0.25); // actually move the robot
    }
}
