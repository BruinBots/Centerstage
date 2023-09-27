package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name="Testing: In-outtake TeleOp", group="Iterative Opmode")
public class InOutTake extends OpMode {

    DcMotorEx motor;
    DigitalChannel sensor;

    Karen bot;

    boolean testing = false;

    @Override
    public void init() {
        bot = new Karen(hardwareMap);

        motor = bot.intakeMotor;
        sensor = bot.intakeTouchSensor;

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
        boolean value = touch();

        if (gamepad1.a) {
            testing = true;
        }
        else if (gamepad1.b) {
            testing = false;
        }

        if (value) {
            testing = false;
        }

        if (testing) {
            spin(0.5, DcMotorSimple.Direction.FORWARD);
        }
        else {
            stopSpin();
        }
        telemetry.addData("sensor", value);

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void spin(double speed, DcMotorSimple.Direction direction) {
        motor.setDirection(direction);
        motor.setPower(speed);
    }

    public void stopSpin() {
        motor.setPower(0);
    }

    public boolean touch() {
        return sensor.getState();
    }

    @Override
    public void stop() {
    }
}
