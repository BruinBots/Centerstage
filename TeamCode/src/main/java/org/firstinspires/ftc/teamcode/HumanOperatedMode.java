package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Drone Test", group="Iterative Opmode")
public class HumanOperatedMode extends OpMode
{
    Karen bot;
    boolean gp1dpadup;
    boolean gp1dpaddown;
    boolean gp1a;
    boolean gp1b;
    boolean gp1y;

    //
    @Override
    public void init() {

        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
        bot.drone.setServoPos(bot.droneRotateServo, 0.3);
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
            if (bot.droneRotateServo.getPosition() < bot.drone.MAX_ROTATE_POS) {
                bot.drone.rotateServo(0.05);
            }
        }
        if (gamepad1.dpad_down && !gp1dpaddown) {
            if (bot.droneRotateServo.getPosition() > bot.drone.MIN_ROTATE_POS) {
                bot.drone.rotateServo(-0.05);
            }
        }
        bot.drone.loop();

        if (gamepad1.a && !gp1a) {
            bot.drone.launchWithRotation();
        } else if (gamepad1.b && !gp1b) {
            bot.drone.launch(Drone.launchPoses.open);
        } else if (gamepad1.y && !gp1y) {
            bot.drone.launch(Drone.launchPoses.closed);
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
        gp1b = gamepad1.b;
        gp1y = gamepad1.y;
    }


    @Override
    public void stop() {
        bot.stop();
    }
}