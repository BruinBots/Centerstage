package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen {
    Servo penServo;
    MovePen Pen;
    // constructor with map
    public Karen(HardwareMap map) {
        penServo = map.get(Servo.class, "penServo");

    }

    public void stop(){
    }
}