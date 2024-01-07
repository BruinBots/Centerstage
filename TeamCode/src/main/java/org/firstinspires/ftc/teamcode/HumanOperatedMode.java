package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class HumanOperatedMode extends OpMode
{
    Karen bot;
    boolean gp1dpadup;
    boolean gp1dpaddown;
    boolean gp1a;

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

        if (gamepad1.dpad_up && !gp1dpadup) {
            bot.drone.rotateServo(0.1);
        }
        if (gamepad1.dpad_down && !gp1dpaddown) {
            bot.drone.rotateServo(-0.1);
        }

        if (gamepad1.a && !gp1a) {
            bot.drone.launch();
        }

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telemetry.addData("Drone Rotation Servo: ", bot.droneRotateServo.getPosition());
        telemetry.addData("Drone Release Servo: ", bot.droneReleaseServo.getPosition());
        telemetry.update();

        gp1dpadup = gamepad1.dpad_up;
        gp1dpaddown = gamepad1.dpad_down;
        gp1a = gamepad1.a;
    }


    @Override
    public void stop() {
        bot.stop();
    }
}