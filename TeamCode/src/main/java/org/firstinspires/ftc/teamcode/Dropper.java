package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Dropper {

    private final Servo dropperServo;

    public static final double DROPPER_UP_POS = 0.5;
    public static final double DROPPER_DOWN_POS = 0;

    Dropper (Servo dropperServo) { this.dropperServo = dropperServo; }

    public void dropperDown() {
        dropperServo.setPosition(DROPPER_DOWN_POS);
    }

    public void dropperUp() {
        dropperServo.setPosition(DROPPER_UP_POS);
    }
}
