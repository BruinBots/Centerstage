package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class HumanOperatedMode extends OpMode
{
    Karen bot;
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;
    boolean lastAButton;
    private boolean leftBumper;
    private boolean rightBumper;

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

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

//        bot.moveBotMecanum(drive, turn, strafe, 1);

        if (gamepad1.a && !lastAButton) {
            bot.drone.launch();
        }
        bot.drone.loop();

        if (gamepad1.left_bumper && !leftBumper) {
            Drone.motorPower -= 0.01;
        } else if (gamepad1.right_bumper && !rightBumper) {
            Drone.motorPower += 0.01;
        }
        leftBumper = gamepad1.left_bumper;
        rightBumper = gamepad1.right_bumper;

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lastAButton = gamepad1.a;
        telemetry.addData("MotorPower: ", Drone.motorPower);
        telemetry.update();
    }


    @Override
    public void stop() {
        bot.stop();
    }
}