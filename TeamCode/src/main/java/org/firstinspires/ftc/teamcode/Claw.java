package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private static Servo wrist;
    private static Servo lowerFinger;
    private static Servo upperFinger;

    public static final double CLAW_WRIST_MIN = 0;
    public static final double CLAW_WRIST_MAX = 0.8;
    public static final double ZERO_ANGLE_POS = 0.1;
    public static final double TICKS_PER_DEGREE = 0.0033333333;

    public static final double LOWER_OPEN = 0.27;
    public static final double LOWER_CLOSED = 0.045;

    public static final double UPPER_OPEN = 0.27;
    public static final double UPPER_CLOSED = 0.03;

    public enum Status {OPEN, CLOSED}
    public static Status upper;
    public static Status lower;

    public Claw(Servo wrist, Servo lowerFinger, Servo upperFinger) {
        Claw.wrist = wrist;
        this.lowerFinger = lowerFinger;
        this.upperFinger = upperFinger;
    }

    public static void setClawWrist(double targetPos) {
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

    public static void setClawWristFromAngle(double angle) {
        double newPos = angle * TICKS_PER_DEGREE + ZERO_ANGLE_POS;
        setClawWrist(newPos);
    }

    public void openLowerClaw() {
        lower = Status.OPEN;
        lowerFinger.setPosition(LOWER_OPEN);
    }

    public static void closeLowerClaw() {
        lower = Status.CLOSED;
        lowerFinger.setPosition(LOWER_CLOSED);
    }

    public void openUpperClaw() {
        upper = Status.OPEN;
        upperFinger.setPosition(UPPER_OPEN);
    }

    public static void closeUpperClaw() {
        upper = Status.CLOSED;
        upperFinger.setPosition(UPPER_CLOSED);
    }

    public void openBothClaw() {
        openUpperClaw();
        openLowerClaw();
    }

    public static void closeBothClaw() {
        closeUpperClaw();
        closeLowerClaw();
    }

    public double getCurrentWristPosition() {
        return wrist.getPosition();
    }
}