package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Testing: Claw", group="Iterative OpMode")
public class Claw extends OpMode {
    Servo servo1;

    Karen bot;

    public static final double OPEN_POS = 0;
    public static final double CLOSED_POS = 1;

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
        position = Math.abs(position);
        servo1.setPosition(position);
    }

    public void open() {
        clawMove(OPEN_POS);
    }

    public void close() {
        clawMove(CLOSED_POS);
    }

    @Override
    public void stop() {
    }
}
