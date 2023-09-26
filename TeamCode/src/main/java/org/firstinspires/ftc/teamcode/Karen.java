package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen {


    // Class variables

    DcMotorEx DroneMotor;

    // constructor with map
    public Karen(HardwareMap map) {
        DroneMotor = map.get(DcMotorEx.class, "DroneMotor");
    }

    public void stop(){
        DroneMotor.setPower(0);
    }
}