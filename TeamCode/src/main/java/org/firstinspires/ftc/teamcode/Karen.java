package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen {


    // Class variables

    DcMotorEx Motor0;
    Servo Servo0;
    CRServo CRServo1;
    CRServo CRServo2;

    // constructor with map
    public Karen(HardwareMap map) {
        // Drivetrain Motors


//        CRServo1 = map.get(CRServo.class, "CRServo1");
//        CRServo2 = map.get(CRServo.class, "CRServo2");
        Servo0 = map.get(Servo.class, "Servo0");
    }

    public void stop(){
//        Motor0.setPower(0);
        Servo0.setPosition(0);
//        CRServo1.setPower(0);
//        CRServo2.setPower(0);
    }
}