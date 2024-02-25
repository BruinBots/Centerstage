package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen  {


    // Drive  motors

    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightFrontMotor;
    public DcMotorEx leftBackMotor;
    public DcMotorEx rightBackMotor;


    // arm and slide motors
    public DcMotorEx armMotor;

    // odometry wheels
    public DcMotorEx leftOdo;
    public DcMotorEx rightOdo;
    public DcMotorEx backOdo;

    Servo hangerServo;

    public double lastwheelSpeeds[] = new double[4];     // Tracks the last power sent to the wheels to assist in ramping power




    public static double        SPEED_INCREMENT = 0.09;  // Increment that wheel speed will be increased/decreased
    public final int TICKS_PER_REVOLUTION = 200;
    public final int DEADWHEEL_RADIUS = 2; // cm ??

    // subclasses
    public Arm arm;
    public Hanger hanger;

    // constructor with map
    public Karen(HardwareMap map) {
        // Drivetrain Motors
        leftFrontMotor = map.get(DcMotorEx.class, "left_front");
        rightFrontMotor = map.get(DcMotorEx.class, "right_front");
        leftBackMotor = map.get(DcMotorEx.class, "left_back");
        rightBackMotor = map.get(DcMotorEx.class, "right_back");

        // Reverse left motors
        leftFrontMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorEx.Direction.REVERSE);

        // arm and linear slide - pixel intake must be initialized first
        armMotor = map.get(DcMotorEx.class, "arm_motor");
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setDirection(DcMotorEx.Direction.REVERSE);
        arm = new Arm(armMotor);

        // odometry deadwheels
        leftOdo = map.get(DcMotorEx.class, "left_front");
        rightOdo = map.get(DcMotorEx.class, "right_odo");
        backOdo = map.get(DcMotorEx.class, "left_back");

        hangerServo = map.get(Servo.class, "hanger_servo");
        hanger = new Hanger(hangerServo);
    }

    private double rampUp(double x) {
        return Math.copySign(1 / (1 + Math.pow(Math.E, -10 * (1.5 * x - 0.8))), x);
    }

    public void moveBotMecanum(double drive, double rotate, double strafe, double scaleFactor) {

//        drive = rampUp(drive); // use S-curve to ramp up drive gradually

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = drive + strafe + rotate;  // left front
        wheelSpeeds[1] = drive - strafe - rotate;  // right front
        wheelSpeeds[2] = drive - strafe + rotate;  // left rear
        wheelSpeeds[3] = drive + strafe - rotate;  // right rear

        // finding the greatest power value
        double maxMagnitude = Math.max(Math.max(Math.max(wheelSpeeds[0], wheelSpeeds[1]), wheelSpeeds[2]), wheelSpeeds[3]);

        // dividing everyone by the max power value so that ratios are same (check if sdk automatically clips to see if go build documentation works
        if (maxMagnitude > 1.0)
        {
            for (int i = 0; i < wheelSpeeds.length; i++)
            {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }

        // setting motor power and scaling down to preference
        leftFrontMotor.setPower(wheelSpeeds[0] * scaleFactor);
        rightFrontMotor.setPower(wheelSpeeds[1] * scaleFactor);
        leftBackMotor.setPower(wheelSpeeds[2] * scaleFactor);
        rightBackMotor.setPower(wheelSpeeds[3] * scaleFactor);
    }

    public void moveBotDistance(double drive, double rotate, double strafe, double distance) {
        double targetTicks = TICKS_PER_REVOLUTION * DEADWHEEL_RADIUS * Math.PI * 2.0; // calculate total ticks required from distance (cm) and DEADWHEEL_RADIUS (cm)
        while ((leftOdo.getCurrentPosition() + rightOdo.getCurrentPosition()) / 2.0 < targetTicks) {
            this.moveBotMecanum(drive, rotate, strafe, 1);
        }
    }

    public void moveBotRamping(double drive, double rotate, double strafe, double scaleFactor) {

        // This function is identical to moveBotMecanum, except that it limits the rate of change of the wheels.
        // It saves the last commanded wheel speed and only allows them to change by the constant SPEED_INCREMENT
        // Setting SPEED_INCREMENT to a low value (0.01) will result in a very slow ramp.  0.1 seems like a good middle ground

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = drive + strafe + rotate;  // left front
        wheelSpeeds[1] = drive - strafe - rotate;  // right front
        wheelSpeeds[2] = drive - strafe + rotate;  // left rear
        wheelSpeeds[3] = drive + strafe - rotate;  // right rear

        // finding the greatest power value
        double maxMagnitude = Math.max(Math.max(Math.max(wheelSpeeds[0], wheelSpeeds[1]), wheelSpeeds[2]), wheelSpeeds[3]);

        // dividing everyone by the max power value so that ratios are same (check if sdk automatically clips to see if go build documentation works
        if (maxMagnitude > 1.0)
        {
            for (int i = 0; i < wheelSpeeds.length; i++)
            {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }

        // Compare last wheel speeds to commanded wheel speeds and ramp as necessary
        for (int i = 0; i < lastwheelSpeeds.length; i++){
            // If the commanded speed value is more than SPEED_INCREMENT away from the last known wheel speed
            if (Math.abs(wheelSpeeds[i] - lastwheelSpeeds[i]) > SPEED_INCREMENT){
                // Set the current wheel speed to the last wheel speed plus speed increment in the signed directin of the difference
                wheelSpeeds[i] = lastwheelSpeeds[i] + Math.copySign(SPEED_INCREMENT,wheelSpeeds[i] - lastwheelSpeeds[i]);
            }
        }

        // setting motor power and scaling down to preference
        leftFrontMotor.setPower(wheelSpeeds[0] * scaleFactor);
        rightFrontMotor.setPower(wheelSpeeds[1] * scaleFactor);
        leftBackMotor.setPower(wheelSpeeds[2] * scaleFactor);
        rightBackMotor.setPower(wheelSpeeds[3] * scaleFactor);

        // Save the last wheel speeds to assist in ramping
        for (int i = 0; i < lastwheelSpeeds.length; i++) {
            lastwheelSpeeds[i] = wheelSpeeds[i];
        }
    }

    public void init() {
        hanger.hangServo.setPosition(hanger.PRIMED_POS);

    }


    public void stop() {
        // stop drivetrain motors
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);

        // stop slide and arm motors
        armMotor.setPower(0);
    }
    // ----- ALGORITHMS -----
}