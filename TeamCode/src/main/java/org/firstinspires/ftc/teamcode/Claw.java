package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    Servo servo1;

    public static final double OPEN_POS = 0;
    public static final double CLOSED_POS = 1;

    Claw (Servo servo1) {
        this.servo1 = servo1;
    }
    public void clawMove(double position) {
        position = Math.abs(position);
        servo1.setPosition(position);
    }

    public void open() {
        clawMove(OPEN_POS);
    }

    public void close() {
        clawMove(CLOSED_POS);
    }
}
