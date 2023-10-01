package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Testing: Arm TeleOp", group="Iterative Opmode")
public class Arm extends OpMode {

    DcMotorEx armMotor;
    DcMotorEx slideMotor;

    Karen bot;

    public static int MAX_SLIDE_POSITION = 160;
    public static int MIN_SLIDE_POSITION = -50;
    public static int MAX_ARM_POSITION = 180;
    public static int MIN_ARM_POSITION = -50;
    public static double ARM_POWER = 0.4;

    @Override
    public void init() {
        bot = new Karen(hardwareMap);

        armMotor = bot.armMotor;
        slideMotor = bot.slideMotor;

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    //
    @Override
    public void start() {
    }

    //
    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            moveSlide(50);
        }
        else if (gamepad1.dpad_down) {
            moveSlide(-50);
        }
        else if (gamepad1.dpad_left) {
            moveArm(-50);
        }
        else if (gamepad1.dpad_right) {
            moveArm(50);
        }

        telemetry.addData("Arm Position", getCurrentArmPos());
        telemetry.addData("Slide Position", getCurrentSlidePos());

        try {
            sleep(20);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveArm(int targetPos) {
        if (targetPos < MIN_ARM_POSITION || targetPos > MAX_ARM_POSITION) {
            return;
        }
        armMotor.setTargetPosition(targetPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(ARM_POWER);
    }

    public void moveSlide(int targetPos) {
        if (targetPos < MIN_SLIDE_POSITION || targetPos > MAX_SLIDE_POSITION) {
            return;
        }
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(ARM_POWER);
    }

    public int getCurrentArmPos() { return armMotor.getCurrentPosition(); }
    public int getCurrentSlidePos() { return slideMotor.getCurrentPosition(); }

    @Override
    public void stop() {
    }
}
