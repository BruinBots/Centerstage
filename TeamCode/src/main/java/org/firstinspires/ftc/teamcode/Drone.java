package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Drone {
    private Servo droneReleaseServo;
    private Servo droneRotateServo;

    Drone(Servo droneReleaseServo, Servo droneRotateServo) {
        this.droneReleaseServo = droneReleaseServo;
        this.droneRotateServo = droneRotateServo;
    }

    public void rotateServo(double pos) {
        droneRotateServo.setPosition(droneRotateServo.getPosition() + pos);
    }

    public void launch() {
        droneReleaseServo.setPosition(1);
    }
}
