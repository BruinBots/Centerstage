package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class InOutTake {

    private DcMotorEx motor;
    private DigitalChannel sensor;

    public static final double SPIN_SPEED = 0.5;

    InOutTake (DcMotorEx motor, DigitalChannel sensor) {
        this.motor = motor;
        this.sensor = sensor;
    }
    
    public void intake() {
        spin(DcMotorSimple.Direction.FORWARD);
        while (!touch()) {

        }
        stopSpin();
    }

    public void outtake() {
        spin(DcMotorSimple.Direction.REVERSE);
        while (touch()) {

        }
        stopSpin();
    }

    public void stopSpin() {
        motor.setPower(0);
    }

    public boolean touch() {
        return sensor.getState();
    }

    private void spin(DcMotorSimple.Direction direction) {
        motor.setDirection(direction);
        motor.setPower(SPIN_SPEED);
    }
}
