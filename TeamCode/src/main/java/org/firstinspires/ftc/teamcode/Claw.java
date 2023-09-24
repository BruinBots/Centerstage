package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Testing: Claw", group="Iterative OpMode")
public class Claw extends OpMode {
    Servo servo1;

    Karen bot;

    @Override
    public void init() {
        bot = new Karen(hardwareMap);

        servo1 = bot.clawServo1;

        telemetry.addData("Status", "Initialized");
    }

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
        double pos = gamepad1.left_stick_y;

        clawMove(pos);

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clawMove(double position) {
        servo1.setPosition(position);
    }

    @Override
    public void stop() {
    }
}
