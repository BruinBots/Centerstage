package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="CameraMode")
public class CameraMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AprilTagsAutonomous a = new AprilTagsAutonomous();
        a.initAprilTag(hardwareMap);
        waitForStart();
        a.visionPortal.close();
    }
}
