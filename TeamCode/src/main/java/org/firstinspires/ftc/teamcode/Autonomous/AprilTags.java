package org.firstinspires.ftc.teamcode.Autonomous;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Karen;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

/**
 * This 2023-2024 OpMode illustrates the basics of AprilTag recognition and pose estimation,
 * including Java Builder structures for specifying Vision parameters.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Config
public class AprilTags {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    //this variable is the offset for the robot doesn't hit the backboard in inch to how close you want it to the backboard/ put always if you want a number put add one
    public static double offSetBackboardX = -3;
    public static double offSetBackboardY = 5;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    public Vector2d getTraj(HardwareMap hardwareMap, SampleMecanumDrive drive, Telemetry telemetry, int idBackboard) {
        Karen bot = new Karen(hardwareMap);
        initAprilTag(hardwareMap);
        sleep(5000);
        //The variable that stores the distance that the apriltag is from the backboard
        double apriltagDistance;
        double x = 36;

        double apriltagSideWays = alignHorizontal(idBackboard);

        apriltagDistance = telemetryAprilTag(idBackboard);
        /* If robot is facing right in RoadRunner scheme:
        AprilTags   RoadRunner  Robot
        +x          -y          right
        -x          +y          left
        +y          +x          forwards
        -y          -x          backwards
         */
        if (apriltagSideWays > 0 || apriltagDistance > 0) {
            x = x + apriltagSideWays + offSetBackboardX;
            apriltagDistance = telemetryAprilTag(idBackboard);
            Vector2d offset = new Vector2d(x, (36 - apriltagDistance + offSetBackboardY));
            visionPortal.close();
            return offset;
        }
        // Save more CPU resources when camera is no longer needed.
        //\
        visionPortal.close();
        return new Vector2d(0, 0);
    }   // end method runOpMode(

    private void initAprilTag(HardwareMap hardwareMap) {
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
            builder.setCamera(hardwareMap.get(WebcamName.class, "Purple Camera"));
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
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null && detection.id == id) {
                return detection.ftcPose.range;
            }
        }
//        if (currentDetections[0]==null) {
//            telemetry.addLine(String.format("Nothing done ", "no detection"));
//        }
        return 0;// end for() loop
    }   // end method telemetryAprilTag()

    // end class
    private double alignHorizontal(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {

            if (detection.metadata != null && detection.id == id) {
                return detection.ftcPose.x;
            }
        }
        return 0;
    }
}



