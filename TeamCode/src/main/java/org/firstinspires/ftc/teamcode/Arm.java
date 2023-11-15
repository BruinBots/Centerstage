package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private final DcMotorEx armMotor;
    private final DcMotorEx slideMotor;


    // declare constants
    public static int MAX_SLIDE_POSITION = 2000;
    public static int MIN_SLIDE_POSITION = 0;
    public static int MAX_ARM_POSITION = 2357;
    public static int MIN_ARM_POSITION = 20;
    public static double ARM_POWER = 0.2; // the default power supplied to the arm when being used
    public static double SLIDE_POWER = 0.6; // the default power supplied to the slide when being used to lift the claw
    public static double HANG_POWER = 0.8; // the default power supplied to the slide when being used to lift the robot

    public static double HANG_MOVE = 0;
    public static double HANG_DOWN = 0;
    public static double HANG_UP = 0;

    public static double ARM_DOWN = 20;
    public static double ARM_UP = 200;

    Arm (DcMotorEx armMotor, DcMotorEx slideMotor) {
        this.armMotor = armMotor;
        this.slideMotor = slideMotor;
    }

    public void moveArm(int targetPos) {
        if (targetPos < MIN_ARM_POSITION) {
            moveArm(MIN_ARM_POSITION);
            return;
        }
        else if (targetPos > MAX_ARM_POSITION) {
            moveArm(MAX_ARM_POSITION);
            return;
        }
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (targetPos < getCurrentArmPos()) {
            armMotor.setPower(ARM_POWER / 2); // move arm backwards slower so it doesn't crash into the slide
        }
        else {
            armMotor.setPower(ARM_POWER);
        }
    }

    public void holdArmPos() {
        armMotor.setPower(1);
    }

    public void holdSlidePos() {
        slideMotor.setPower(1);
    }

    public void moveSlide(int targetPos) {
        if (targetPos < MIN_SLIDE_POSITION || targetPos > MAX_SLIDE_POSITION) {
            return;
        }
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(SLIDE_POWER);
    }

    public void hangSlide(int targetPos) {
        if (targetPos < MIN_SLIDE_POSITION || targetPos > MAX_SLIDE_POSITION) {
            return;
        }
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(HANG_POWER);
    }

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }
    public int getCurrentSlidePos() { return slideMotor.getCurrentPosition(); }
}
