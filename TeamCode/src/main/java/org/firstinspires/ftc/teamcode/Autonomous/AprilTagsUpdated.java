package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Config
public class AprilTagsUpdated {

    private static final boolean USE_WEBCAM = true;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    public static int BackboardX = 36;
    public static int BackboardY = 4;

    public static int robotDesiredDistanceFromBackboard = 3;
    public static int cameraOffsetFromCenter = 4;

    public Vector2d getTraj(HardwareMap hardwareMap, SampleMecanumDrive drive, Telemetry telemetry, int idBackboard) {
        initAprilTag(hardwareMap);
        sleep(3000);
        // all these are now in roadrunner coordinate system
        int apriltagSideways = alignHorizontal(idBackboard);// I would call this apriltagX or apriltagHorizontal
        int apriltagDistance = getRangeViaAprilTag(idBackboard); // better name
        int desiredX = apriltagDistance - robotDesiredDistanceFromBackboard;
        int desiredY = apriltagSideways + cameraOffsetFromCenter - BackboardY;
        // critical debugging information below
        // if they are not reasonable numbers something is very wrong
        telemetry.addData("apriltagSideways = ", apriltagSideways); // should be -4
        telemetry.addData("apriltagDistance = ", apriltagDistance); // should be 10
        telemetry.addData("desiredX =", desiredX); // 10 + 24 - 3 = 31
        telemetry.addData("desiredY =", desiredY); // 0 + 4 + 4 = 8
        telemetry.update();
        sleep(5000);
        visionPortal.close();
        Vector2d offset = new Vector2d(desiredX, desiredY); // notice this should be going to -7, 5
        return offset;
    }

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
    private int getRangeViaAprilTag(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null && detection.id == id) {
                return (int) detection.ftcPose.range;
            }
        }
        return 0;
    }

    private int alignHorizontal(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {

            if (detection.metadata != null && detection.id == id) {
                return (int) detection.ftcPose.x;
            }
        }
        return 0;
    }

    // end class
}