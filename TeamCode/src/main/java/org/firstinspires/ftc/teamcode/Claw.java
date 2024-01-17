package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo wrist;
    private final Servo firstFinger;
    private final Servo secondFinger;

    private static final double CLAW_WRIST_MIN = 0;
    private static final double CLAW_WRIST_MAX = 0.8;
    private static final double ZERO_ANGLE_POS = 0.1;
    private static final double TICKS_PER_DEGREE = 0.0033333333;

    public static final double LOWER_OPEN = 0.27;
    public static final double LOWER_CLOSED = 0;

    public static final double UPPER_OPEN = 0.73;
    public static final double UPPER_CLOSED = 1;

    public Claw(Servo wrist, Servo firstFinger, Servo secondFinger) {
        this.wrist = wrist;
        this.firstFinger = firstFinger;
        this.secondFinger = secondFinger;
    }

    public void setClawWrist(double targetPos) {
        if (targetPos > CLAW_WRIST_MAX) {
            targetPos = CLAW_WRIST_MAX;
        } else if (targetPos < CLAW_WRIST_MIN) {
            targetPos = CLAW_WRIST_MIN;
        }
        wrist.setPosition(targetPos);
    }

    public void moveClawWrist(double deltaPos) {
        double newPos = wrist.getPosition() + deltaPos;
        setClawWrist(newPos);
    }

    public void setClawWristFromAngle(double angle) {
        double newPos = angle * TICKS_PER_DEGREE + ZERO_ANGLE_POS;
        setClawWrist(newPos);
    }

    public void openLowerClaw() {
        firstFinger.setPosition(LOWER_OPEN);
    }

    public void closeLowerClaw() {
        firstFinger.setPosition(LOWER_CLOSED);
    }

    public void openUpperClaw() {
        secondFinger.setPosition(UPPER_OPEN);
    }

    public void closeUpperClaw() {
        secondFinger.setPosition(UPPER_CLOSED);
    }

    public double getCurrentWristPosition() {
        return wrist.getPosition();
    }
}