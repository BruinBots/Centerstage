package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {

    // declare  motors
    private static DcMotorEx armMotor;

    // declare constants
    public static int MAX_ARM_POSITION = 3000;
    public static int PLACING_ARM_POSITION = 2700;
    public static int STRAIGHT_ARM_POSITION = 1720;
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

    private static double ticksToDegrees(double ticks) {
        return (ticks / GEAR_RATIO) * (360/537.7); // 537.7 old motor
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
            clawAngle = armAngle - 120 + 10; // + 3 to make it offset a little bit
        }
        return clawAngle;
    }

    public void moveArm(int targetPos, boolean safety) {
        if (Claw.lower == Claw.Status.CLOSED && Claw.upper == Claw.Status.CLOSED) {
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
        } else {
            Claw.closeBothClaw();
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

    public void emergencyLower() {
        // This is a Dangerous function!
        // In the case that autonomous leaves the arm in a raised position, the arm zero position will be reset when Teleop is initialized
        // This function allows a means to manually lower the arm back down to close to it's normal zero position

        // Use a low power - we don't want this to be fast, and gravity will be pulling on the arm
        armMotor.setPower(0.2);
        // Set the target position to 10 ticks below where the arm is
        armMotor.setTargetPosition(armMotor.getCurrentPosition() - 10);
        // Call RunToPosition - The arm will start to move, then...
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // We stop and reset the encoder.  This will likely result in a very jerky downward movement
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        // There is a deprecated RESET_ENCODERS mode that we could try if this proves problematic
    }
}