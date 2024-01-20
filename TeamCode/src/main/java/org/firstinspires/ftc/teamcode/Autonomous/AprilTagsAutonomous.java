package org.firstinspires.ftc.teamcode.Autonomous;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
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

@Config
public class AprilTagsAutonomous {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    //this variable is the offset for the robot doesn't hit the backboard in inch to how close you want it to the backboard/ put always if you want a number put add one
    public static double offSetBackboardX = 0;
    public static double offSetBackboardY = 0;
    private AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;

    public Vector2d getOffset(HardwareMap hardwareMap, Telemetry telemetry, int idBackboard) {
        initAprilTag(hardwareMap);
        sleep(3000);
        Vector2d offset = getAprilTagPose(telemetry, idBackboard);
        visionPortal.close();
        return offset;
    }

    public void initAprilTag(HardwareMap hardwareMap) {
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
    }

    private Vector2d getAprilTagPose(Telemetry telemetry, int id) {
        List<AprilTagDetection> detections = aprilTag.getDetections();
        for (AprilTagDetection detection : detections) {
            if (detection.metadata != null && detection.id == id) {
                telemetry.addData("x", detection.ftcPose.x);
                telemetry.addData("y", detection.ftcPose.y);
                telemetry.addData("z", detection.ftcPose.z);
                telemetry.addData("roll", detection.ftcPose.roll);
                telemetry.addData("pitch", detection.ftcPose.pitch);
                telemetry.addData("yaw", detection.ftcPose.yaw);
                telemetry.addData("range", detection.ftcPose.range);
                telemetry.addData("bearing", detection.ftcPose.bearing);
                telemetry.addData("elevation", detection.ftcPose.elevation);

              //  public static double offSetBackboardX = -7.5;
                //public static double offSetBackboardY = 2;
//                return new Vector2d(detection.ftcPose.range + offSetBackboardY, -detection.ftcPose.x + offSetBackboardX);
                return new Vector2d(detection.ftcPose.x + offSetBackboardY, detection.ftcPose.range*Math.cos(Math.toRadians(detection.ftcPose.bearing))+offSetBackboardX);
            }
        }
        return new Vector2d(0, 0);
    }
}