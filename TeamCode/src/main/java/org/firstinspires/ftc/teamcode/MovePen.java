package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class MovePen {
    double downPos = 0.0;
    double upPos = 0.3;
    String currentPos = "up";

    Servo servo1;

    MovePen(Servo servo1) {
        this.servo1 = servo1;
    }

    public void Move(String pos) {
        if (pos.equals("down")) {
            this.servo1.setPosition(downPos);
            currentPos = "down";
        } else if (pos.equals("up")) {
            this.servo1.setPosition(upPos);
            currentPos = "up";
        }
    }
}
