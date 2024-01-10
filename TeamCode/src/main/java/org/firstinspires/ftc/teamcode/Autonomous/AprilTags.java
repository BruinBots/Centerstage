package org.firstinspires.ftc.teamcode.Autonomous;

import java.util.List;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Karen;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This 2023-2024 OpMode illustrates the basics of AprilTag recognition and pose estimation,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "AprilTag", group = "Concept")
public class AprilTags extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    /**
     * {@link #aprilTag} is the variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;
    /**
     * {@link #visionPortal} is the variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    //this variable is the offset for the robot dosen't hit the backboard in inch to how close you whant it to the backboard/ put always if you whant a number put add one
    double offSetBackboardX = 5;
    double offSetBackboardY = 5;

    @Override
    public void runOpMode() throws InterruptedException {
        Karen bot = new Karen(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(36, 36, Math.toRadians(0));
        drive.setPoseEstimate(startPose);
        initAprilTag();
        //The variable that stores the distance that the apritag is from the backboard
        double apriltagDistance;
        double y = 36;
        //the id you whant the robot to go
        int idBackboard = 1;
        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        double apriltagSideWays = alignHorizontal(idBackboard);
        telemetry.update();
        if (apriltagSideWays > 0) {
            y = y - apriltagSideWays + offSetBackboardY;
            telemetry.addData("the Y 1:", y);
            telemetry.update();
            Trajectory traj0b = drive.trajectoryBuilder(startPose, true)
                    //put y minus y value like 36-pich y
                    .lineToConstantHeading(new Vector2d(36, y))
                    .build();
            drive.followTrajectory(traj0b);
        } else if (apriltagSideWays < 0) {
            y = y - apriltagSideWays + offSetBackboardY;
            telemetry.addData("the Y 2:", y);
            telemetry.update();

            Trajectory traj0b = drive.trajectoryBuilder(startPose, true)
                    //put y minus y value like 36-pich y
                    .lineToConstantHeading(new Vector2d(36, y))
                    .build();
            drive.followTrajectory(traj0b);
        }
        //two is the id that you want to make the robot scan and go to
        apriltagDistance = telemetryAprilTag(idBackboard);
        telemetry.addData("the distance:", apriltagDistance);
        // Push telemetry to the Driver Station.
        telemetry.update();
        if (apriltagDistance > 0) {
            telemetry.addData("the Y:", y);
            telemetry.update();
            Trajectory traj0a = drive.trajectoryBuilder(startPose, true)
                    //put y minus y value like 36-pitch y
                    .lineToConstantHeading(new Vector2d(36 - apriltagDistance + offSetBackboardX,y))
                    .build();
            drive.followTrajectory(traj0a);
        }
        // Save more CPU resources when camera is no longer needed.
        //\
        visionPortal.close();
    }   // end method runOpMode(

    private void initAprilTag() {
        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.
                .build();
        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();
        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Back Camera"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableCameraMonitoring(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()


    /**
     * Function to add telemetry about AprilTag detections.
     */
    //it gives the distance to the apriltag with the id of the number you gave it
    private double telemetryAprilTag(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null && detection.id == id) {
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (x,y,z)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (pitch, yaw, roll)", detection.ftcPose.pitch, detection.ftcPose.yaw, detection.ftcPose.roll));
                return detection.ftcPose.range;
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }
        return 0;// end for() loop
    }   // end method telemetryAprilTag()u

    // end class
    private double alignHorizontal(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null && detection.id == id) {
                if (detection.metadata != null && detection.id == id) {
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (x,y,z)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (pitch, yaw, roll)", detection.ftcPose.pitch, detection.ftcPose.yaw, detection.ftcPose.roll));
                    double horizontal = detection.ftcPose.range * Math.sin(detection.ftcPose.bearing);
                    telemetry.addLine(String.format(" Distance horizontal", horizontal));
                    if (detection.ftcPose.x < 0) {
                        return -horizontal;
                    }
                    return horizontal;
                } else {
                    telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                    telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
                }
            }
        }
        return 0;
    }
}



