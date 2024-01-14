package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private final Servo clawServo;


    public static final double CLOSE_BOTH_POS = 0; // 0º
    public static final double OPEN_POS = CLOSE_BOTH_POS + 0.35; // 36º
    public static final double CLOSE_ONE_POS = CLOSE_BOTH_POS + 0.058;


    public Claw (Servo clawServo) {
        this.clawServo = clawServo;
    }

    public void openClaw() {
        clawMove(OPEN_POS);
    }

    public void closeBothClaw() {
        clawMove(CLOSE_BOTH_POS);
    }
    public void closeOneClaw() { clawMove(CLOSE_ONE_POS); }
    private void clawMove(double position) {
        // if position is negative,  go reverse, as servos don't accept negative values. If position is positive, go forwards, and if 0, it doesn't matter so it defaults to forwards
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
