package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Hanger {

    private final Servo hangServo;

    public static final double HANGER_UP_POS = 0.2; // 0-1 this is counterclockwise from down, when facing the servo face
    public static final double HANGER_DOWN_POS = 0.95;

    public Hanger(Servo hangServo) { this.hangServo = hangServo; }

    public void hangerDown() {
        hangServo.setPosition(HANGER_DOWN_POS);
    }

    public void hangerUp() {
        hangServo.setPosition(HANGER_UP_POS);
    }
}
