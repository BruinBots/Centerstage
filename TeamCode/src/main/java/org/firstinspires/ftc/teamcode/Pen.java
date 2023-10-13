package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Pen {
    enum PenDirection { up, down };
    enum PenColor { black, red, blue };
    double downPos = 0.53;
    double upPos = 0;
    Servo servo1;
    Servo servo2;
    Servo servo3;

    HashMap<PenColor, Servo> colorServoMap;

    Pen(Servo servo1, Servo servo2, Servo servo3) {
        this.servo1 = servo1;
        this.servo2 = servo2;
        this.servo3 = servo3;

        colorServoMap = new HashMap<PenColor, Servo>();
        colorServoMap.put(PenColor.red, servo3);
        colorServoMap.put(PenColor.black, servo2);
        colorServoMap.put(PenColor.blue, servo1);

        currentServo = this.servo1;
    }
    Servo currentServo;

    public void move(double pos)
    {
        move(currentServo, pos);
    }
    public void move(Servo servo, double pos)
    {
        servo.setPosition(pos);
    }

    public void switchPen(PenDirection direction) {
        if (direction == PenDirection.up) {
            if (currentServo.getPortNumber() == servo1.getPortNumber()) {
                currentServo = servo2;
            } else if (currentServo.getPortNumber() == servo2.getPortNumber()) {
                currentServo = servo3;
            }
        } else if (direction == PenDirection.down) {
            if (currentServo.getPortNumber() == servo3.getPortNumber()) {
                currentServo = servo2;
            } else if (currentServo.getPortNumber() == servo2.getPortNumber()) {
                currentServo = servo1;
            }
        }
    }

    public void lowerPen(PenColor color)
    {
        colorServoMap.get(color).setPosition(downPos);
    }

    public void raisePen(PenColor color)
    {
        colorServoMap.get(color).setPosition(upPos);
    }

    public void raiseAllPens()
    {
        move(servo1, upPos);
        move(servo2, upPos);
        move(servo3, upPos);
    }
}