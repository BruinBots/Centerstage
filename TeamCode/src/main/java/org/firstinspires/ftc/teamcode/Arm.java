package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {
    public DcMotorEx slideMotor;
    public DcMotorEx armMotor;
    public static int MAX_SLIDE_POSITION = 160;
    public static int MIN_SLIDE_POSITION = 0;
    public static int MAX_ARM_POSITION = 180;
    public static int MIN_ARM_POSITION = 0;
    public static double ARM_POWER = 0.8;

    public void moveSlide(int targetPos){
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(ARM_POWER);
    }
    public void moveArm(int targetPos) {
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(ARM_POWER);
    }
    public int getCurrentArmPos(){
        return armMotor.getCurrentPosition();
    }
    public int getCurrentslidePos(){
        return slideMotor.getCurrentPosition();
    }

}
