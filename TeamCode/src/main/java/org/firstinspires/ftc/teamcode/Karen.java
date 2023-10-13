package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Karen  {
    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightFrontMotor;
    public DcMotorEx leftBackMotor;
    public DcMotorEx rightBackMotor;
    public DcMotorEx leftOdo;
    public DcMotorEx rightOdo;
    public DcMotorEx backOdo;

    public DcMotorEx droneMotor;

    public final int TICKS_PER_REVOLUTION = 200;
    public final int DEADWHEEL_RADIUS = 2; // cm ??

    private double[] wheelSpeeds;
    Drone drone;
    public Karen(HardwareMap map) {
        // Drivetrain Motors
        leftFrontMotor = map.get(DcMotorEx.class, "left_front");
        rightFrontMotor = map.get(DcMotorEx.class, "right_front");
        leftBackMotor = map.get(DcMotorEx.class, "left_back");
        rightBackMotor = map.get(DcMotorEx.class, "right_back");

        // Reverse left motors
        leftFrontMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorEx.Direction.REVERSE);

        // odometry deadwheels
        leftOdo = map.get(DcMotorEx.class, "right_front");
        rightOdo = map.get(DcMotorEx.class, "left_back");
        backOdo = map.get(DcMotorEx.class, "left_front");

        // drone launch
        droneMotor = map.get(DcMotorEx.class, "drone_motor");
        drone = new Drone(droneMotor);

        wheelSpeeds = new double[4];
    }

    private double rampUp(double x) {
        return 1 / (1 + Math.pow(Math.E, -10 * (1.5 * x - 0.8)));
    }

    public void moveBot(double drive, double rotate, double scaleFactor) {
        drive = rampUp(drive); // S-curve ramp up
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
        double targetTicks = TICKS_PER_REVOLUTION * distance / (DEADWHEEL_RADIUS * Math.PI * 2.0); // calculate total ticks required from distance (cm) and DEADWHEEL_RADIUS (cm)
        moveBotMecanum(drive, rotate, strafe, 1);
        if (distance > 0) {
            while ((leftOdo.getCurrentPosition() + rightOdo.getCurrentPosition()) / 2.0 < targetTicks) {

            }
        }
        else {
            while ((leftOdo.getCurrentPosition() + rightOdo.getCurrentPosition()) / 2.0 > targetTicks) {

            }
        }
        moveBotMecanum(0, 0, 0, 0);
    }

    public void strafeBotDistance(double drive, double rotate, double strafe, double distance) {
        double targetTicks = TICKS_PER_REVOLUTION * distance / (DEADWHEEL_RADIUS * Math.PI * 2.0); // calculate total ticks required from distance (cm) and DEADWHEEL_RADIUS (cm)
        moveBotMecanum(drive, rotate, strafe, 1);
        if (distance > 0) {
            while (backOdo.getCurrentPosition() < targetTicks) {

            }
        }
        else {
            while (backOdo.getCurrentPosition() > targetTicks) {

            }
        }
        moveBotMecanum(0, 0, 0, 0);
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

        // stop drone motor
        droneMotor.setPower(0);
    }

    public double[] getWheelSpeeds() {
        return wheelSpeeds;
    }

    public void setWheelSpeeds(double[] wheelSpeeds) {
        this.wheelSpeeds = wheelSpeeds;
    }

}