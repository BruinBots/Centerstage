package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public class TensorFlowForAutonomous {
    private TfodProcessor tfod;
    int CamSize = 0;
    String Sides="";
    public VisionPortal visionPortal;

    HardwareMap hardwareMap;
    Telemetry telemetry;

    public static final String TFOD_MODEL_ASSET_BLUE = "farblueorb.tflite";

    public static final String TFOD_MODEL_ASSET_RED = "RedSphere1.tflite";
    public static final String[] LABELS = {
//            "redSphere",
            "farblueorb"

    };

    public TensorFlowForAutonomous(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public String getSide() {
        sleep(500);
        Sides=telemetryTfod();
        return Sides;
    }

    public void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET_BLUE)
                //.setModelFileName(TFOD_MODEL_FILE)

                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).


        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

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
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.5f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    private String telemetryTfod() {
        // 1 left 2 center 3 right
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        //size of pikel
        CamSize=640;
        int MaxConfident=0;
        int xMax=0;
        telemetry.addData("camara size",CamSize);
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2;
            telemetry.addData("camara size R",recognition.getRight());
            telemetry.addData("camera size C", x);
            telemetry.addData("camara size L",recognition.getLeft());
            if (MaxConfident<(int)(recognition.getConfidence() * 100)){
                MaxConfident= (int)(recognition.getConfidence() * 100);
                xMax=(int)x;
            }

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);


        }   // end for() loop
        if (currentRecognitions.size() < 1) {
            return "none";
        }
        telemetry.addData("The x value",xMax);
        if (xMax<=CamSize/3-120) {

            telemetry.addData("is in left",1);
            return "left";
        }
        else if (xMax<=CamSize*2/3-120) {

            telemetry.addData("is in center",2);
            return "center";

        }
        else if (xMax>CamSize*2/3-120) {

            telemetry.addData("is in right ",3);
            return "right";
        }
        else
            return "none";
    }   // end method telemetryTfod()


}

