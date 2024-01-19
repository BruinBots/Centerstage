package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private static DcMotorEx armMotor;

    // declare constants
    public static int MAX_ARM_POSITION = 21000;
    public static int PLACING_ARM_POSITION = 1900;
    public static int STRAIGHT_ARM_POSITION = 1268;
    public static int MIN_ARM_POSITION = 0;
    public static int ARM_SPEED = 6000;
    public static double ARM_POWER = 1; // the default power supplied to the arm when being used
    public static double OFFSET_ANGLE = 38.4;
    public static double GEAR_RATIO = 6.6862745;

    private boolean bypass;

    // note: inOutTake must be initialized before calling this constructor
    public Arm (DcMotorEx armMotor) {
        this.armMotor = armMotor;
    }

    public void holdArmPos() {
        armMotor.setPower(0);
    }

    private static double ticksToDegrees(double ticks) {
        return (ticks / GEAR_RATIO) * (360/5281.1); // 537.7 old motor
    }

    public static double armAngle() {
        double ticks = armMotor.getCurrentPosition();
        double degrees = ticksToDegrees(ticks);
        return degrees - OFFSET_ANGLE;
    }

    public static double clawAngle() {
        double armAngle = armAngle();
        double clawAngle = 0;
        if (armAngle > 120) {
            clawAngle = armAngle - 120;
        }
        return clawAngle;
    }

    public void moveArm(int targetPos, boolean safety) {
        if (safety) {
            if (!(InOutTake.scoopServo.getPosition() > InOutTake.SCOOP_MIDDLE_POS + 0.001)) {
                bypass = true;
            } else {
                bypass = false;
                return;
            }
        } else {
            bypass = true;
        }
        if (bypass) {
            // if arm pos is greater or less than max/min then set to max/min
            if (targetPos < MIN_ARM_POSITION) {
                targetPos = MIN_ARM_POSITION;
            } else if (targetPos > MAX_ARM_POSITION) {
                targetPos = MAX_ARM_POSITION;
            }

            armMotor.setPower(ARM_POWER);
            armMotor.setTargetPosition(targetPos);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Claw.setClawWristFromAngle(Arm.clawAngle());
        }
    }

    public void goMax() {
        moveArm(PLACING_ARM_POSITION, true);
    }

    public void goStraight() {
        moveArm(STRAIGHT_ARM_POSITION, true);
    }

    public void goDown() {
        moveArm(MIN_ARM_POSITION, true);
    }

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }
}
