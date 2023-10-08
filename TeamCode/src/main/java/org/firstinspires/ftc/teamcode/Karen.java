package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Karen {


    // Class variables

    DcMotorEx droneMotor;

    // constructor with map
    public Karen(HardwareMap map) {
        droneMotor = map.get(DcMotorEx.class, "drone_motor");
    }
}