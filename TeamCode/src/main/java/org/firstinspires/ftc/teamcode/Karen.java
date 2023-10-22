package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Karen {
    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightFrontMotor;
    public DcMotorEx leftBackMotor;
    public DcMotorEx rightBackMotor;
    public Pen pen;
    public Servo penServo1;
    public Servo penServo2;
    public Servo penServo3;
    public DcMotorEx droneMotor;
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

        // drone launch
        droneMotor = map.get(DcMotorEx.class, "drone_motor");
        drone = new Drone(droneMotor);
        penServo1 = map.get(Servo.class, "penServo1");
        penServo2 = map.get(Servo.class, "penServo2");
        penServo3 = map.get(Servo.class, "penServo3");
        pen = new Pen(penServo1, penServo2, penServo3);
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
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }

        // setting motor power and scaling down to preference
        leftFrontMotor.setPower(wheelSpeeds[0] * scaleFactor);
        rightFrontMotor.setPower(wheelSpeeds[1] * scaleFactor);
        leftBackMotor.setPower(wheelSpeeds[2] * scaleFactor);
        rightBackMotor.setPower(wheelSpeeds[3] * scaleFactor);
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
}