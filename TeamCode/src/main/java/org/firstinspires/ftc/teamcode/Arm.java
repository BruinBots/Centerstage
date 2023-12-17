package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private final DcMotorEx armMotor;


    // declare constants
    public static int MAX_ARM_POSITION = 1500;
    public static int MIN_ARM_POSITION = 0;
    public static int ARM_SPEED = 60;
    public static double ARM_POWER = 0.7; // the default power supplied to the arm when being used

    // note: inOutTake must be initialized before calling this constructor
    public Arm (DcMotorEx armMotor) {
        this.armMotor = armMotor;
    }


    public void holdArmPos() {
        armMotor.setPower(1);
    }

    public void moveArm(int targetPos) {
        // if arm pos is greater or less than max/min then set to max/min
        if (targetPos < MIN_ARM_POSITION) {
            targetPos = MIN_ARM_POSITION;
        }
        else if (targetPos > MAX_ARM_POSITION) {
            targetPos = MAX_ARM_POSITION;
        }

        armMotor.setPower(ARM_POWER);
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }
}
