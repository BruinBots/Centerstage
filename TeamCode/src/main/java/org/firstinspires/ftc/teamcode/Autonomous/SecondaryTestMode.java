package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Utilities.Backdrop;

@Autonomous(name="SecondaryTestMode")
public class SecondaryTestMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Backdrop.hardwareMap = hardwareMap;
        Backdrop.initBot();
        waitForStart();
    }
}
