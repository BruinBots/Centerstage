package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class InOutTake {

    private Servo servo;

    public static final double IN_POSITION = 1;
    public static final double OUT_POSITION = 0;

    InOutTake (Servo servo) {
        this.servo = servo;
    }

    public void intake() {
        servo.setPosition(IN_POSITION);
    }

    public void outtake() {
        servo.setPosition(OUT_POSITION);
    }
}
