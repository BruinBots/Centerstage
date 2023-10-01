package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    DcMotorEx armMotor;
    DcMotorEx slideMotor;


    public static int MAX_SLIDE_POSITION = 160;
    public static int MIN_SLIDE_POSITION = -50;
    public static int MAX_ARM_POSITION = 180;
    public static int MIN_ARM_POSITION = -50;
    public static double ARM_POWER = 0.4;

    Arm (DcMotorEx armMotor, DcMotorEx slideMotor) {
        this.armMotor = armMotor;
        this.slideMotor = slideMotor;
    }

    public void moveArm(int targetPos) {
        if (targetPos < MIN_ARM_POSITION || targetPos > MAX_ARM_POSITION) {
            return;
        }
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(ARM_POWER);
    }

    public void moveSlide(int targetPos) {
        if (targetPos < MIN_SLIDE_POSITION || targetPos > MAX_SLIDE_POSITION) {
            return;
        }
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(ARM_POWER);
    }

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }
    public int getCurrentSlidePos() { return slideMotor.getCurrentPosition(); }
}
