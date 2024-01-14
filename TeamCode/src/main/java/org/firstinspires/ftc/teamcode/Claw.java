package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo clawWrist;
    private static final double CLAW_WRIST_MIN = 0;
    private static final double CLAW_WRIST_MAX = 0.8;
    private static final double ZERO_ANGLE_POS = 0.1;
    private static final double TICKS_PER_DEGREE = 0.0033333333;

    public Claw(Servo clawWrist) {
        this.clawWrist = clawWrist;
    }

    public void setClawWrist(double targetPos) {
        if (targetPos > CLAW_WRIST_MAX) {
            targetPos = CLAW_WRIST_MAX;
        } else if (targetPos < CLAW_WRIST_MIN) {
            targetPos = CLAW_WRIST_MIN;
        }
        clawWrist.setPosition(targetPos);
    }

    public void moveClawWrist(double deltaPos) {
        double newPos = clawWrist.getPosition() + deltaPos;
        setClawWrist(newPos);
    }

    public void setClawWristFromAngle(double angle) {
        double newPos = angle * TICKS_PER_DEGREE + ZERO_ANGLE_POS;
        setClawWrist(newPos);
    }

    public double getCurrentWristPosition() {
        return clawWrist.getPosition();
    }
}
