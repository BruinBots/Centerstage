package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private final DcMotorEx armMotor;
    private final DcMotorEx slideMotor;


    // declare constants
    public static int MAX_SLIDE_POSITION = 1500; // was 2000
    public static int MIN_SLIDE_POSITION = 0;
    public static int SLIDE_SPEED = 60;
    public static int MAX_ARM_POSITION = 2225;
    public static int MIN_ARM_POSITION = 0;
    public static int ARM_SPEED = 100;
    public static double ARM_POWER = 0.7; // the default power supplied to the arm when being used
    public static double SLIDE_POWER = 0.8; // the default power supplied to the slide when being used to lift the claw
    public static double HANG_POWER = 0.8; // the default power supplied to the slide when being used to lift the robot

    public static int HANG_MOVE = 0;
    public static int HANG_DOWN = 0;
    public static int HANG_UP = 0;

    public static int ARM_DOWN = 20;
    public static int ARM_UP = 200;

    InOutTake inOutTake;

    // note: inOutTake must be initialized before calling this constructor
    public Arm (DcMotorEx armMotor, DcMotorEx slideMotor, InOutTake inOutTake) {
        this.armMotor = armMotor;
        this.slideMotor = slideMotor;
        this.inOutTake = inOutTake;
    }

    public void moveArm(int targetPos) {
        if (targetPos < MIN_ARM_POSITION) {
            targetPos = MIN_ARM_POSITION;
        }
        else if (targetPos > MAX_ARM_POSITION) {
            targetPos = MAX_ARM_POSITION;
        }
        if (inOutTake.isSafeForArm()) {
            armMotor.setTargetPosition(targetPos);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        if (targetPos < MIN_SLIDE_POSITION) {
            targetPos = MIN_SLIDE_POSITION;
        }
        else if (targetPos > MAX_SLIDE_POSITION) {
            targetPos = MAX_SLIDE_POSITION;
        }

        if (inOutTake.isSafeForArm()) {
            slideMotor.setPower(SLIDE_POWER);
            slideMotor.setTargetPosition(targetPos);
            slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
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

    public void dropPixelPos() {
        moveArm(MAX_ARM_POSITION);
        moveSlide(MAX_SLIDE_POSITION);
    }
}
