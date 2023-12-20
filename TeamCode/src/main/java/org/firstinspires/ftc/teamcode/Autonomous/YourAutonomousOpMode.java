//package org.firstinspires.ftc.teamcode.Autonomous;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetectorClassFactory;
//
//@Autonomous(name = "YourAutonomousOpMode")
//public class YourAutonomousOpMode extends LinearOpMode {
//
//    private HardwareMap hardwareMap;
//    private TFObjectDetector tfod;
//
//    @Override
//    public void runOpMode() {
//        // Initialize hardwareMap and other components
//
//        // Initialize TFOD
//        initTFOD();
//
//        // Wait for the start button to be pressed
//        waitForStart();
//
//        // Run the autonomous routine
//        while (opModeIsActive()) {
//            // Your autonomous code here
//
//            // Detect and process the team prop
//            detectTeamProp();
//
//            // Continue with other autonomous actions
//        }
//
//        // Stop TFOD when the op mode is stopped
//        if (tfod != null) {
//            tfod.shutdown();
//        }
//    }
//
//    private void initTFOD() {
//        // Configure the camera and TFOD parameters
//        TFObjectDetector tfodParameters = new TFObjectDetector();
//        tfodParameters.minResultConfidence = 0.8; // Adjust confidence threshold as needed
//        tfod = TFObjectDetectorClassFactory.getInstance().createTFObjectDetector(tfodParameters, hardwareMap.get(WebcamName.class, "yourWebcamName"));
//
//        // Load the model assets and labels
//        tfod.loadModelFromAsset("yourModel.tflite", "blueorb", "redorb"); // Adjust labels as needed
//    }
//
//    private void detectTeamProp() {
//        // Activate TFOD to begin detecting objects
//        if (tfod != null) {
//            tfod.activate();
//        }
//
//        // Check for objects detected by TFOD
//        if (tfod != null && tfod.getUpdatedRecognitions() != null) {
//            // Loop through all recognitions
//            for (Recognition recognition : tfod.getUpdatedRecognitions()) {
//                // Check if the detected object is your team prop
//                if (recognition.getLabel().equals("YourTeamPropLabel")) {
//                    // Perform actions based on the detection (e.g., move the robot accordingly)
//                    telemetry.addData("Team Prop Detected", "Proceeding with actions");
//                    telemetry.update();
//                    // Your actions here
//                }
//            }
//        }
//
//        // Deactivate TFOD after processing
//        if (tfod != null) {
//            tfod.deactivate();
//        }
//    }
//}


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetectorParameters;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous(name = "ObjectRecognitionAutonomous", group = "Autonomous")
public class ObjectRecognitionAutonomous extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "your_model.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        initTensorFlow();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // Get updated recognition list.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Objects Detected", updatedRecognitions.size());

                        for (Recognition recognition : updatedRecognitions) {
                            // Check if the recognized object is the one you are looking for.
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                // Implement logic to determine object position (left, center, right).
                                double objectX = recognition.getLeft();
                                double objectWidth = recognition.getWidth();
                                double screenWidth = tfod.getCameraView().getWidth();

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
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initTensorFlow() {
        // Initialize the TensorFlow Object Detection engine.
        TFObjectDetectorParameters tfodParameters = new TFObjectDetectorParameters();
        // Configure the parameters as needed.

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, hardwareMap);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);

        if (tfod != null) {
            tfod.activate();
        }
    }
}


