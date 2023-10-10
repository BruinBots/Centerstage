package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Pen {
    double downPos = 1.0;
    double upPos = 0.5;
    Servo servo1;
    Servo servo2;
    Servo servo3;

    Pen(Servo servo1, Servo servo2, Servo servo3) {
        this.servo1 = servo1;
        this.servo2 = servo2;
        this.servo3 = servo3;
    }
    Servo currentServo = servo1;

    public void move(double pos) {
        currentServo.setPosition(pos);
    }

    public void switchPen(String direction) {
        if (direction.equals("up")) {
            if (currentServo.getPortNumber() == servo1.getPortNumber()) {
                currentServo = servo2;
            } else if (currentServo.getPortNumber() == servo2.getPortNumber()) {
                currentServo = servo3;
            }
        } else if (direction.equals("down")) {
            if (currentServo.getPortNumber() == servo3.getPortNumber()) {
                currentServo = servo2;
            } else if (currentServo.getPortNumber() == servo2.getPortNumber()) {
                currentServo = servo1;
            }
        }
    }
}
