package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name="DropperTestMode")
public class DropperTestMode extends LinearOpMode {

    Karen bot;
    Servo dropperServo;

    public static double position = 0;

    @Override
    public void runOpMode() {
        bot = new Karen(hardwareMap);
        dropperServo = bot.dropperServo;

        dropperServo.setPosition(position);
    }
}
