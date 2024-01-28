package org.firstinspires.ftc.teamcode.Autonomous;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Utilities.Backdrop;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */

@Autonomous(name="Tensorflow")
public class TensorFlowForAutonomousBlueRed extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webc// m, false for phone camera
    //1 is blue 2 is red
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfodProcessor;

    /**z
     * The variable to store our instance of the vision portal.
     */
    public VisionPortal visionPortal;

    public static String TFOD_MODEL_ASSET = "orb1-14-24.tflite"; //default to blue
    public static String RED_TFOD_MODEL_ASSET = "RedSphere1.tflite"; //for red
    public static String[] LABELS = {
            "orb"
    };
    public static String[] RED_LABELS = {
            "redsphere"
    };

    // 6. Need to adjust camera offset
    private static final double CAMERA_OFFSET = 512.00; // TODO 5. assuming divided by 5 segments
    private Backdrop.Side side = null;
    private Backdrop.Side direction = Backdrop.Side.CENTER.CENTER;
    private double CAMERA_SCREEN_WIDTH = 1920; //A Logi Webcam 1080p resolution means the image has 1920 pixels horizontally by 1080 vertically (1920x1080)
    public TensorFlowForAutonomousBlueRed(HardwareMap hardwareMap, Telemetry telemetry, String color) {
        if (color.toLowerCase().equals("red")) {
            TFOD_MODEL_ASSET = RED_TFOD_MODEL_ASSET;
            LABELS = RED_LABELS;
        }
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public Backdrop.Side compute(boolean blue) {
        visionPortal.resumeStreaming(); // start the camera
        //1. Need to adjust time
        sleep(1000); // give tensorflow time to think
        int i = 0;
        Backdrop.Side side = null;
        ArrayList<Backdrop.Side> sideList = new ArrayList<>();
        // First sanitize of TensorFlow by using mode
        while (side == null && i < 10) {
            sideList.add(getPropLocation(blue));
            i++;
            sleep(100); // 2. Review number
        }
        side = mode(sideList);
        telemetry.addData("A-side", side);
        telemetry.update();
        return side;
    }

    private static Backdrop.Side mode(ArrayList<Backdrop.Side> array) {
        Backdrop.Side mode = array.get(0);
        int maxCount = 0;
        for (int i = 0; i < array.size(); i++) {
            Backdrop.Side value = array.get(i);
            int count = 0;
            for (int j = 0; j < array.size(); j++) {
                if (array.get(j) == value) count++;
                if (count > maxCount) {
                    mode = value;
                    maxCount = count;
                }
            }
        }
        return mode;
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

        telemetry.addData("Blue Prop Side: ", getPropLocation(true));
        telemetry.update();
        telemetry.addData("Red Prop Side: ", getPropLocation(false));
        telemetry.update();
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
                .setModelInputSize(300) //input size typically refers to the dimensions of the images that the model expects as input.
                .setModelAspectRatio(16.0 / 9.0)
                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Yellow Camera"));
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
        tfodProcessor.setMinResultConfidence(0f);
        // Disable or re-enable the TFOD processor at any time.
        visionPortal.setProcessorEnabled(tfodProcessor, true);

    }   // end method initTfod()

    private Recognition redTfod() {
        List<Recognition> updatedRecognitions = tfodProcessor.getRecognitions();
        telemetry.addData("updated recognitions: ", updatedRecognitions);
        telemetry.addData("number of recognitions: ", updatedRecognitions.size());
        telemetry.update();
        if (updatedRecognitions != null && updatedRecognitions.size()>0) {
            // Sort the confidence from highest to lowest
            // Tensor Flow Sanitizer
            // TODO 6. Need to test
            Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                public int compare(Recognition r1, Recognition r2) {
                    return (int)((r1.getConfidence()-r2.getConfidence())*100);
                }
            });

            // Get the one with the larger area.
            // Tensor Flow Sanitizer
            // TODO 7. Need to test
            Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                public int compare(Recognition r1, Recognition r2) {
                    return (int)(((r1.getHeight() * r1.getWidth()) - (r2.getHeight() * r2.getWidth())));
                }
            });
            return updatedRecognitions.get(0);
        }
        return null;
    }

    private Recognition blueTfod() {
        List<Recognition> updatedRecognitions = tfodProcessor.getRecognitions();
        telemetry.addData("updated recognitions: ", updatedRecognitions);
        telemetry.addData("number of recognitions: ", updatedRecognitions.size());
        telemetry.update();
        if (updatedRecognitions != null && updatedRecognitions.size()>0) {
            // Sort the confidence from highest to lowest
            // Tensor Flow Sanitizer
            // TODO 4. Need to test
            Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                public int compare(Recognition r1, Recognition r2) {
                    return (int)((r1.getConfidence()-r2.getConfidence())*100);
                }
            });

            // Tensor Flow Sanitizer
            // TODO 5. Need to test
            // Get the one with the most square shape
            Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                public int compare(Recognition r1, Recognition r2) {
                    return (int)((Math.abs((r2.getHeight() / r2.getWidth()) - (r2.getWidth() / r2.getHeight())) - Math.abs((r1.getHeight() / r1.getWidth()) - (r1.getWidth() / r1.getHeight())) * 100));
                }
            });
            return updatedRecognitions.get(0);
        }
        return null;
    }

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private Backdrop.Side telemetryTfod() {
        if (tfodProcessor != null) {
            // Get updated recognition list.
            List<Recognition> updatedRecognitions = tfodProcessor.getRecognitions();

            // Sort recognitions
            telemetry.addData("updated recognitions: ", updatedRecognitions);
            telemetry.addData("number of recognitions: ", updatedRecognitions.size());
            telemetry.update();
            if (updatedRecognitions != null && updatedRecognitions.size()>0) {
                // Sort the confidence from highest to lowest
                Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                    public int compare(Recognition r1, Recognition r2) {
                        return (int)((r1.getConfidence()-r2.getConfidence())*100);
                    }
                });

                // Get the one with the most square shape
                Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                    public int compare(Recognition r1, Recognition r2) {
                        return (int)((Math.abs((r2.getHeight() / r2.getWidth()) - (r2.getWidth() / r2.getHeight())) - Math.abs((r1.getHeight() / r1.getWidth()) - (r1.getWidth() / r1.getHeight())) * 100));
                    }
                });

                // Get the one with the larger area
                Collections.sort(updatedRecognitions, new Comparator<Recognition>() {
                    public int compare(Recognition r1, Recognition r2) {
                        return (int)(((r1.getHeight() * r1.getWidth()) - (r2.getHeight() * r2.getWidth())));
                    }
                });
            }

            if (updatedRecognitions != null) {
                telemetry.addData("# Objects Detected", updatedRecognitions.size());
                telemetry.update();
                for (Recognition recognition:updatedRecognitions) {
                    // Verify area of team prop.
                    double orb_min_area=150;
                    // this is the min or max possable area for the bounding box of the tfod orb
                    double orb_max_area=200;
                    double area = recognition.getWidth() * recognition.getHeight();
                    if (area <= orb_max_area) {// Check if the recognized object is the one you are looking for.
                        telemetry.addData("getLabel(): ", recognition.getLabel());
                    }if (area > orb_min_area) {
                        telemetry.addData("getLabel(): ", recognition.getLabel());
                    }
                    telemetry.update();

                    if (recognition.getLabel().equals(LABELS[0])) {
                        double x = (recognition.getLeft() + recognition.getRight()) / 2;
                        double y = (recognition.getTop() + recognition.getBottom()) / 2;

                        telemetry.addData(""," ");
                        telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                        telemetry.addData("- Position", "%.0f / %.0f", x, y);
                        telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
                        telemetry.update();
                        // Implement logic to determine object position (left, center, right).
                        double objectX = recognition.getLeft();
                        double objectWidth = recognition.getWidth();
                        double objectCenterX = objectX + objectWidth / 2.0;

                        if (objectCenterX < (CAMERA_SCREEN_WIDTH / 3.0)+CAMERA_OFFSET) { //640+512 = 1152
                            telemetry.addData("Position", "Left");
                            telemetry.update();
                            direction = Backdrop.Side.LEFT;
                        } else if (objectCenterX < (2 * CAMERA_SCREEN_WIDTH / 3.0)+CAMERA_OFFSET) { //1280+512=1792
                            telemetry.addData("Position", "Center");
                            telemetry.update();
                            direction = Backdrop.Side.CENTER;
                        } else {
                            telemetry.addData("Position", "Right");
                            direction = Backdrop.Side.RIGHT;
                        }
                        telemetry.addData("Object Center X", objectCenterX);
                        telemetry.update();
                    }
                }
            }
            telemetry.update();
        }
        return direction;
    }

    private Backdrop.Side getPropLocation(boolean blue) {
        Recognition recognition;
        Backdrop.Side direction = Backdrop.Side.CENTER;
        recognition = blue?blueTfod():redTfod();

        if(recognition==null) {
            telemetry.addData("recognition is null", "default center");
            telemetry.update();
            return direction;
        }
        double x = (recognition.getLeft() + recognition.getRight()) / 2;
        double y = (recognition.getTop() + recognition.getBottom()) / 2;

        telemetry.addData(""," ");
        telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
        telemetry.addData("- Position", "%.0f / %.0f", x, y);
        telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        telemetry.update();
        // Implement logic to determine object position (left, center, right).
        double objectX = recognition.getLeft();
        double objectWidth = recognition.getWidth();
        double objectCenterX = objectX + objectWidth / 2.0;

        if (objectCenterX < (CAMERA_SCREEN_WIDTH / 3.0)+CAMERA_OFFSET) { //640+512 = 1152
            telemetry.addData("Position", "Left");
            telemetry.update();
            direction = Backdrop.Side.LEFT;
        } else if (objectCenterX < (2 * CAMERA_SCREEN_WIDTH / 3.0)+CAMERA_OFFSET) { //1280+512=1792
            telemetry.addData("Position", "Center");
            telemetry.update();
            direction = Backdrop.Side.CENTER;
        } else {
            telemetry.addData("Position", "Right");
            direction = Backdrop.Side.RIGHT;
        }
        telemetry.addData("Object Center X", objectCenterX);
        telemetry.update();
        return direction;
    }
}   // end class