package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Drone {
    private Servo droneReleaseServo;
    private Servo droneRotateServo;
    private boolean launched = false;
    private double OPEN_POS = 0.5;
    private double CLOSED_POS = 1;
    private long timeWhenLaunched;
    private boolean Launched = false;
    private long rotateAndLaunchDelay = 300;
    private enum launchPoses {open, closed};

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
        rotateServo(0.5);
    }

    public void loop() {
        if (launched && getCurrentTime() > timeWhenLaunched + rotateAndLaunchDelay) {
            launched = false;
            launch(launchPoses.open);
        }
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
