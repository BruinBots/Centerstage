package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Karen {


    // Class variables

    public DcMotorEx Motor0;

    // constructor with map
    public Karen(HardwareMap map) {
        // Drivetrain Motors
        Motor0 = map.get(DcMotorEx.class, "Motor0");
    }

    public void stop(){
        Motor0.setPower(0);
    }
}