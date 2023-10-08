package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen  {
    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightFrontMotor;
    public DcMotorEx leftBackMotor;
    public DcMotorEx rightBackMotor;

    public DcMotorEx intakeMotor;
    public DigitalChannel intakeTouchSensor;

    public DcMotorEx slideMotor;
    public DcMotorEx armMotor;

    public DcMotorEx leftOdo;
    public DcMotorEx rightOdo;
    public DcMotorEx backOdo;

    public Servo clawServo1;
    public Servo penServo1;
    public Servo penServo2;
    public Servo penServo3;

    public DcMotorEx droneMotor;

    public final int TICKS_PER_REVOLUTION = 200;
    public final int DEADWHEEL_RADIUS = 2; // cm ??

    // subclasses
    InOutTake inOutTake;
    Claw claw;
    DroneLaunch droneLaunch;
    Arm arm;
    Pen pen;

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

//        // arm and linear slide
//        armMotor = map.get(DcMotorEx.class, "arm_motor");
//        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//
//        slideMotor = map.get(DcMotorEx.class, "slide_motor");
//        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

//        arm = new Arm(armMotor, slideMotor);

        // odometry deadwheels
        leftOdo = map.get(DcMotorEx.class, "left_front");
        rightOdo = map.get(DcMotorEx.class, "right_front");
        backOdo = map.get(DcMotorEx.class, "left_back");

//        // pixel intake
//        intakeMotor = map.get(DcMotorEx.class, "intake_motor");
//        intakeTouchSensor = map.get(DigitalChannel.class, "intake_sensor");
//        inOutTake = new InOutTake(intakeMotor, intakeTouchSensor);
//
//        // claw
//        clawServo1 = map.get(Servo.class, "claw_servo1");
//        claw = new Claw(clawServo1);
//
//        // drone launch
//        droneMotor = map.get(DcMotorEx.class, "drone_motor");
//        droneLaunch = new DroneLaunch(droneMotor);

        penServo1 = map.get(Servo.class, "penServo1");
        penServo2 = map.get(Servo.class, "penServo2");
        penServo3 = map.get(Servo.class, "penServo3");
        pen = new Pen(penServo1, penServo2, penServo3);
    }

    private double rampUp(double x) {
        return 1 / (1 + Math.pow(Math.E, -10 * (1.5 * x - 0.8)));
    }

    public void moveBot(double drive, double rotate, double scaleFactor) {
        drive = rampUp(drive); // S-curve ramp up
        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = drive + rotate;  // left front
        wheelSpeeds[1] = drive - rotate;  // right front
        wheelSpeeds[2] = drive + rotate;  // left rear
        wheelSpeeds[3] = drive - rotate;  // right rear

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

//        telemetry.addData("power", wheelSpeeds);



        // setting motor power and scaling down to preference
        leftFrontMotor.setPower(wheelSpeeds[0] * scaleFactor);
        rightFrontMotor.setPower(wheelSpeeds[1] * scaleFactor);
        leftBackMotor.setPower(wheelSpeeds[2] * scaleFactor);
        rightBackMotor.setPower(wheelSpeeds[3] * scaleFactor);
    }

    public void moveBotMecanum(double drive, double rotate, double strafe, double scaleFactor) {
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

    public void driveBotDistance(double drive, double rotate, double strafe, double distance) {
        double targetTicks = TICKS_PER_REVOLUTION * DEADWHEEL_RADIUS * Math.PI * 2.0 * distance; // calculate total ticks required from distance (cm) and DEADWHEEL_RADIUS (cm)
        while ((leftOdo.getCurrentPosition() + rightOdo.getCurrentPosition()) / 2.0 < targetTicks) {
            this.moveBotMecanum(drive, rotate, strafe, 1);
        }
    }

    public void strafeBotDistance(double drive, double rotate, double strafe, double distance) {
        double targetTicks = TICKS_PER_REVOLUTION * DEADWHEEL_RADIUS * Math.PI * 2.0 * distance; // calculate total ticks required from distance (cm) and DEADWHEEL_RADIUS (cm)
        while (backOdo.getCurrentPosition() < targetTicks) {
            this.moveBotMecanum(drive, rotate, strafe, 1);
        }
    }

    public void drawX(double size) {
        driveBotDistance(-1, 0, -1, size / 2);
        // pen down
        driveBotDistance(1, 0, 1, size);
        // pen up
        driveBotDistance(0, 0, -1, size);
        // pen down
        driveBotDistance(1, 0, -1, size);
        // pen up
        driveBotDistance(-1, 0, 1, size / 2);
    }

    public void stop() {
        // stop drivetrain motors
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);

        // stop slide and arm motors
//        slideMotor.setPower(0);
//        armMotor.setPower(0);
//
//        // stop drone motor
//        droneMotor.setPower(0);
//
//        // stop intake motor
//        intakeMotor.setPower(0);

        // move the pen to up position
        pen.move(pen.upPos);
    }
}