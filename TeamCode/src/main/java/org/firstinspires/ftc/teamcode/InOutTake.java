package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class InOutTake {

    private DcMotorEx motor;
    private DigitalChannel sensor;

    public static final double SPIN_SPEED = 0.5;

    private boolean intaking = false;
    private boolean outtaking = false;

    InOutTake (DcMotorEx motor, DigitalChannel sensor) {
        this.motor = motor;
        this.sensor = sensor;
    }

    public void checkInOutTakeState() {
        if (intaking) {
            if (touch()) {
                stopSpin();
            }
            intaking = false;
        }
        else if (outtaking) {
            if (!touch()) {
                stopSpin();
            }
            outtaking = false;
        }
    }

    public void intake() {
        spin(DcMotorSimple.Direction.FORWARD);
        intaking = true;
    }

    public void outtake() {
        spin(DcMotorSimple.Direction.REVERSE);
        outtaking = true;
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
