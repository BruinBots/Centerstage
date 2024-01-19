package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.Karen;

@Config
@Autonomous(name="ClawWristTestMode", group="Test Modes")
public class ClawWristTestMode extends LinearOpMode {
    Karen bot;
    Servo clawWristServo;

    public static double position = 0;

    @Override
    public void runOpMode() {
        bot = new Karen(hardwareMap);
        clawWristServo = bot.clawWristServo;

        waitForStart();

        if (position >= 0 && position <= Claw.CLAW_WRIST_MAX) {
            clawWristServo.setPosition(position);
        }

        while (!isStopRequested()) {
            sleep(1);
        }
    }
}
