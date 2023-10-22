package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import java.util.HashMap;

public class Pen {
    enum PenColor { black, red, blue }
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

        colorServoMap = new HashMap<>();
        colorServoMap.put(PenColor.red, servo3);
        colorServoMap.put(PenColor.black, servo2);
        colorServoMap.put(PenColor.blue, servo1);

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
        raisePen(PenColor.red);
        raisePen(PenColor.black);
        raisePen(PenColor.blue);
    }
}


