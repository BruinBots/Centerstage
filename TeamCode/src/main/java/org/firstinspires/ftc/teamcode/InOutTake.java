package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class InOutTake {

    DcMotorEx motor;
    DigitalChannel sensor;

    public void init(DcMotorEx motor, DigitalChannel sensor) {
        this.motor = motor;
        this.sensor = sensor;
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
}
