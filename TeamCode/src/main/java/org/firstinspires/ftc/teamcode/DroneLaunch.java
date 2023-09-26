package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Drone Launch TeleOp", group="Iterative Opmode")
public class DroneLaunch extends OpMode
{
    boolean aButtonPrev;
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

        // code here
        if (gamepad1.a && !aButtonPrev) {
            if (bot.DroneMotor.getPower() > 0.9) { // if motor is on
                bot.DroneMotor.setPower(0.0); // turn motor off
            } else if (bot.DroneMotor.getPower() < 0.1) { // if motor is off
                bot.DroneMotor.setPower(1.0); // turn motor on
            }
            bot.DroneMotor.setPower(1.0);
        }

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        aButtonPrev = gamepad1.a;
    }


    @Override
    public void stop() {
        bot.stop();
    }
}