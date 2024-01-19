package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Drone {
    public Servo droneReleaseServo;
    public Servo droneRotateServo;
    public boolean launched = false;
    public double OPEN_POS = 0;
    public double CLOSED_POS = 1;
    public long timeWhenLaunched;
    public long rotateAndLaunchDelay = 400;
    public long timeBeforeReset = 1000;
    public enum launchPoses {open, closed}
    public double MAX_ROTATE_POS = 0.65;
    public double MIN_ROTATE_POS = 0.4;

    Drone(Servo droneReleaseServo, Servo droneRotateServo) {
        this.droneReleaseServo = droneReleaseServo;
        this.droneRotateServo = droneRotateServo;
    }

    public void setServoPos(Servo servo, double pos) {
        servo.setPosition(pos);
    }
    public void rotateServo(double pos) {
        droneRotateServo.setPosition(droneRotateServo.getPosition() + pos);
    }

    public void setRotateServo(double pos) {
        droneRotateServo.setPosition(pos);
    }

    public void launch(launchPoses pose) {
        if (pose == launchPoses.open) {
            setServoPos(droneReleaseServo, OPEN_POS);
        } else if (pose == launchPoses.closed) {
            setServoPos(droneReleaseServo, CLOSED_POS);
        }
    }

    public void launchWithRotation() {
        timeWhenLaunched = getCurrentTime();
        launched = true;
        setRotateServo(0.5);
    }

    public void resetPoses() {
        setServoPos(droneRotateServo, MAX_ROTATE_POS);
        setServoPos(droneReleaseServo, CLOSED_POS);
    }

    public void loop() {
        if (launched && getCurrentTime() > timeWhenLaunched + rotateAndLaunchDelay) {
            launch(launchPoses.open);
            if (getCurrentTime() > timeWhenLaunched + rotateAndLaunchDelay + timeBeforeReset) {
                resetPoses();
                launched = false;
            }
        }

        if (droneRotateServo.getPosition() > MAX_ROTATE_POS) {
            droneRotateServo.setPosition(MAX_ROTATE_POS);
        } else if (droneRotateServo.getPosition() < MIN_ROTATE_POS) {
            droneRotateServo.setPosition(MIN_ROTATE_POS);
        }
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
