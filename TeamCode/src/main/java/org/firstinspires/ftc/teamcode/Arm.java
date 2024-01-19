package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private final DcMotorEx armMotor;

    // declare constants
    public static int MAX_ARM_POSITION = 3000;
    public static int MIN_ARM_POSITION = 0;
    public static int ARM_SPEED = 50;
    public static double ARM_POWER = 0.7; // the default power supplied to the arm when being used
//    public static double ARM_POWER = 0.2; // this is for autonomous safety testing; not final value; use above value
    public static double OFFSET_ANGLE = 44.2;
    public static double GEAR_RATIO = 9.12;

    // note: inOutTake must be initialized before calling this constructor
    public Arm (DcMotorEx armMotor) {
        this.armMotor = armMotor;
    }

    public void holdArmPos() {
        armMotor.setPower(1);
    }

    private double ticksToDegrees(double ticks) {
        return (ticks / GEAR_RATIO) * (360/537.7);
    }

    public double armAngle() {
        double ticks = armMotor.getCurrentPosition();
        double degrees = ticksToDegrees(ticks);
        return degrees - OFFSET_ANGLE;
    }

    public double clawAngle() {
        double armAngle = armAngle();
        double clawAngle = 0;
        if (armAngle > 120) {
            clawAngle = armAngle - 120;
        }
        return clawAngle;
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
