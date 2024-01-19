package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Dropper {
    public final Servo dropperServo;

    public static final double DROPPER_CLOSED_POS = 0.2; // 0-1 this is counterclockwise from down, when facing the servo face
    public static final double DROPPER_OPEN_POS = 0;
    boolean dropped;

    public Dropper (Servo dropperServo) { this.dropperServo = dropperServo; }

    public void open() {
        dropped = true;
        dropperServo.setPosition(DROPPER_OPEN_POS);
    }

    public void closed() {
        dropped = false;
        dropperServo.setPosition(DROPPER_CLOSED_POS);
    }

    public void toggle() {
        if (dropped) {
            closed();
        } else if (!dropped) {
            open();
        }
    }
}