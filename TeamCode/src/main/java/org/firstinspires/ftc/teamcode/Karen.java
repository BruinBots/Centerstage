package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Karen {


    // Class variables

    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightFrontMotor;
    public DcMotorEx leftBackMotor;
    public DcMotorEx rightBackMotor;





    public DcMotorEx slideMotor;
    public DcMotorEx armMotor;
    public static int MAX_SLIDE_POSITION = 160;
    public static int MIN_SLIDE_POSITION = 0;
    public static int MAX_ARM_POSITION = 180;
    public static int MIN_ARM_POSITION = 0;
    public static double ARM_POWER = 0.8;


    // constructor with map
    public Karen(HardwareMap map) {
        // Drivetrain Motors
        leftFrontMotor = map.get(DcMotorEx.class, "left_front");
        rightFrontMotor = map.get(DcMotorEx.class, "right_front");
        leftBackMotor = map.get(DcMotorEx.class, "left_back");
        rightBackMotor = map.get(DcMotorEx.class, "right_back");

        leftFrontMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorEx.Direction.REVERSE);

        armMotor = map.get(DcMotorEx.class, "armMotor");
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        slideMotor = map.get(DcMotorEx.class, "slideMotor");
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);




    }

    public void moveBot(double drive, double rotate, double scaleFactor) {
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
    public void moveSlide(int targetPos){
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(ARM_POWER);
    }
    public void moveArm(int targetPos) {
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(ARM_POWER);
    }
    public int getCurrentArmPos(){
        return armMotor.getCurrentPosition();
    }
    public int getCurrentslidePos(){
        return slideMotor.getCurrentPosition();
    }


    public void stop(){
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);

        slideMotor.setPower(0);
        armMotor.setPower(0);
    }
}