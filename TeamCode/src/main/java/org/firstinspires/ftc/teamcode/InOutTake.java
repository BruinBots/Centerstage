package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name="Testing: In-outtake TeleOp", group="Iterative Opmode")
public class InOutTake extends OpMode {

    DcMotorEx motor;
    TouchSensor sensor;

    Karen bot;

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
    }

    public void spin(double speed, DcMotorSimple.Direction direction) {
        motor.setDirection(direction);
        motor.setPower(speed);
    }

    public void stopSpin() {
        motor.setPower(0);
    }

    public boolean touch() {
        return sensor.isPressed();
    }

    @Override
    public void stop() {
    }
}
