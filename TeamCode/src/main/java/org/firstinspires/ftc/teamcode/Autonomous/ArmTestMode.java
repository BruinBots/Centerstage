package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Karen;

@Config
@Autonomous(name="ArmTestMode", group="Test Modes")
public class ArmTestMode extends LinearOpMode {
    Karen bot;
    DcMotorEx armMotor;

    public static int position = 0;

    @Override
    public void runOpMode() {
        bot = new Karen(hardwareMap);
        armMotor = bot.armMotor;

        waitForStart();

        if (position > 0 && position <= Arm.MAX_ARM_POSITION) {
            bot.arm.moveArm(position, true);
            telemetry.addData("moving", "yes");
        }
        else {
            telemetry.addData("failed", "yes");
        }


        while (!isStopRequested()) {
            telemetry.addData("arm", armMotor.getCurrentPosition());
            telemetry.update();
            sleep(1);
        }
    }
}
