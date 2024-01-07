package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen {
    Servo droneReleaseServo;
    Servo droneRotateServo;
    Drone drone;
    // constructor with map
    public Karen(HardwareMap map) {
        droneReleaseServo = map.get(Servo.class, "drone_release_servo");
        droneRotateServo = map.get(Servo.class, "drone_rotate_servo");
        drone = new Drone(droneReleaseServo, droneRotateServo);
    }

    public void stop() {

    }
}