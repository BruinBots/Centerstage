package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    Servo servo1;

    public static final double OPEN_POS = 0;
    public static final int OPEN_TIME = 500;

    public static final double CLOSED_POS = 0;
    public static final int CLOSED_TIME = 600;

    Claw (Servo servo1) {
        this.servo1 = servo1;
    }
    public void clawMove(double position) {
        position = Math.abs(position);
        servo1.setPosition(position);
    }

    public void open() {
        clawMove(OPEN_POS);
        try {
            sleep(OPEN_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }

    public void close() {
        clawMove(CLOSED_POS);
        try {
            sleep(CLOSED_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }

    public void stop() {
        clawMove(0.5);
    }
}
