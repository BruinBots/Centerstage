package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */

@TeleOp(name = "TFOD Orb Testing Blue/Red", group = "Concept")
public class TensorFlowForAutonomousBlueRed extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webc// m, false for phone camera
    //1 is blue 2 is red
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfodProcessor;

    public static final int CAMERA_OFFSET = 70; // positive = left, negative = right, this compensates for the offset of the camera on the robot

    /**z
     * The variable to store our instance of the vision portal.
     */
    public VisionPortal visionPortal;

    public static final String TFOD_MODEL_ASSET = "allorb.tflite";

    //public static final String TFOD_MODEL_FILE = "allorb.tflite";
    public static final String[] LABELS = {
            "blueorb", "redorb"
    };

    String Sides="";
    int xMax=0;
    public TensorFlowForAutonomousBlueRed(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public String getSide() {
        sleep(500);
        Sides=telemetryTfod();
        return Sides;
    }
    @Override

    public void runOpMode() {

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryTfod();
                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer o.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    public void initTfod() {
        // Create the TensorFlow processor by using a builder.
        tfodProcessor = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //          .setModelFileName(TFOD_MODEL_FILE)

                .setModelLabels(LABELS)
                .setIsModelTensorFlow2(true)
                .setIsModelQuantized(true)
                .setModelInputSize(300)
                .setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Front Camera"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(1920, 1080));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfodProcessor);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfodProcessor.setMinResultConfidence(0.40f);
        // Disable or re-enable the TFOD processor at any time.
        visionPortal.setProcessorEnabled(tfodProcessor, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private String telemetryTfod() {
        String direction="";
        double screenWidth = 1920;//tfodProcessor..getCameraView().getWidth();

        if (tfodProcessor != null) {
            // Get updated recognition list.
            List<Recognition> updatedRecognitions = tfodProcessor.getRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Objects Detected", updatedRecognitions.size());

                telemetry.addData("LABELS 0: ", LABELS[0]);
                telemetry.addData("LABELS 1: ", LABELS[1]);
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("getLabel(): ", recognition.getLabel());

                    // Check if the recognized object is the one you are looking for.
                    if (recognition.getLabel().equals(LABELS[0]) || recognition.getLabel().equals(LABELS[1])) {
                        double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                        double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

                        telemetry.addData(""," ");
                        telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                        telemetry.addData("- Position", "%.0f / %.0f", x, y);
                        telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());

                        // Implement logic to determine object position (left, center, right).
                        double objectX = recognition.getLeft();
                        double objectWidth = recognition.getWidth();
                        double objectCenterX = objectX + objectWidth / 2.0;

                        if (objectCenterX < screenWidth / 3.0) {
                            telemetry.addData("Position", "Left");
                        } else if (objectCenterX < 2 * screenWidth / 3.0) {
                            telemetry.addData("Position", "Center");
                        } else {
                            telemetry.addData("Position", "Right");
                        }
                        telemetry.addData("Object Center X", objectCenterX);
                    }
                }
                telemetry.update();
            }
            if (updatedRecognitions.size() < 1) {
                return "none";
            }
            telemetry.addData("The x value",xMax);
            if (xMax<=screenWidth/3) {
                telemetry.addData("is in left",1);
                return "left";
            }
            else if (xMax<=screenWidth*2/3) {

                telemetry.addData("is in center",2);
                return "center";

            }
            else if (xMax>screenWidth*2/3-CAMERA_OFFSET) {

                telemetry.addData("is in right ",3);
                return "right";
            }
            else
                return "none";

        }
        return direction;
    }
}   // end class
