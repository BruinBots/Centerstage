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
            if (currentServo.equals(servo1)) {
                currentServo = servo2;
            } else if (currentServo.equals(servo2)) {
                currentServo = servo3;
            }
        } else if (direction.equals("down")) {
            if (currentServo.equals(servo3)) {
                currentServo = servo2;
            } else if (currentServo.equals(servo2)) {
                currentServo = servo1;
            }
        }
    }

}
