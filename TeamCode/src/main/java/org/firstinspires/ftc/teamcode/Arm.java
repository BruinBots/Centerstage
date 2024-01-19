package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private static DcMotorEx armMotor;

    // declare constants
    public static int MAX_ARM_POSITION = 3000;
    public static int PLACING_ARM_POSITION = 2700; // 1900
    public static int STRAIGHT_ARM_POSITION = 1720; // 1268
    public static int MIN_ARM_POSITION = 0;
    public static int ARM_SPEED = 50;
    public static double ARM_POWER = 0.5; // the default power supplied to the arm when being used
    public static double OFFSET_ANGLE = 38.4;
    public static double GEAR_RATIO = 9.1;

    private boolean bypass;

    // note: inOutTake must be initialized before calling this constructor
    public Arm (DcMotorEx armMotor) {
        this.armMotor = armMotor;
    }

    public void holdArmPos() {
        armMotor.setPower(0);
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

    public void moveArm(int targetPos, double power) {
        // if arm pos is greater or less than max/min then set to max/min
        if (targetPos < MIN_ARM_POSITION) {
            targetPos = MIN_ARM_POSITION;
        }
        else if (targetPos > MAX_ARM_POSITION) {
            targetPos = MAX_ARM_POSITION;
        }

        armMotor.setPower(power);
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Claw.setClawWristFromAngle(clawAngle());
    }

    public void moveArm(int targetPos) {
        moveArm(targetPos, ARM_POWER);
    }

    public void safeMoveArm(int targetPos, double power) {
        if (InOutTake.isSafeForArm()) {
            moveArm(targetPos, power);
        }
    }

    public void safeMoveArm(int targetPos) {
        safeMoveArm(targetPos, ARM_POWER);
    }

    public void goMax() {
        moveArm(PLACING_ARM_POSITION);
    }

    public void goStraight() {
        moveArm(STRAIGHT_ARM_POSITION);
    }

    public void goDown() {
        moveArm(MIN_ARM_POSITION);
    }

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }
}