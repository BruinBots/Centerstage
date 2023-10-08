package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class MovePen {
    double downPos = 1.0;
    double upPos = 0.5;
    String currentPos = "up";

    Servo servo1;

    MovePen(Servo servo1) {
        this.servo1 = servo1;
    }

    public void Move(double pos) {
        this.servo1.setPosition(pos);
        if (currentPos.equals("down")) {
            currentPos = "up";
        } else if (currentPos.equals("up")) {
            currentPos = "down";
        }
    }
}
