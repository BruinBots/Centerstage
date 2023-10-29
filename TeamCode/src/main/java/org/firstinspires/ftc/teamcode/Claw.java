package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo clawServo;

    public static final double OPEN_POS = 0.1; // 36º
    public static final double CLOSED_POS = 0; // 0º


    Claw (Servo clawServo) {
        this.clawServo = clawServo;
    }

    public void openClaw() {
        clawMove(OPEN_POS);
    }

    public void closeClaw() {
        clawMove(CLOSED_POS);
    }
    private void clawMove(double position) {
        // if position is negative, go reverse, as servos don't accept negative values. If position is positive, go forwards, and if 0, it doesn't matter so it defaults to forwards
        if (position < 0) {
            clawServo.setDirection(Servo.Direction.REVERSE);
        }
        else {
            clawServo.setDirection(Servo.Direction.FORWARD);
        }
        position = Math.abs(position);
        clawServo.setPosition(position);
    }




}
