package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private final DcMotorEx armMotor;

    // declare constants
    public static int MAX_ARM_POSITION = 2150;
    public static int MIN_ARM_POSITION = 0;
    public static int ARM_SPEED = 100;
    public static double ARM_POWER = 0.7; // the default power supplied to the arm when being used
    public static double HANG_POWER = 0.8; // the default power supplied to the slide when being used to lift the robot

    public static int HANG_MOVE = 0;
    public static int HANG_DOWN = 0;
    public static int HANG_UP = 0;

    public static int ARM_DOWN = 20;
    public static int ARM_UP = 200;

    InOutTake inOutTake;

    // note: inOutTake must be initialized before calling this constructor
    public Arm (DcMotorEx armMotor, InOutTake inOutTake) {
        this.armMotor = armMotor;
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

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }

    public void dropPixelPos() {
        moveArm(MAX_ARM_POSITION);
    }
}
