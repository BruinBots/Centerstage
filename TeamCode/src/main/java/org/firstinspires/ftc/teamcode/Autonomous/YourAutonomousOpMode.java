import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "YourAutonomousOpMode")
public class YourAutonomousOpMode extends LinearOpMode {

    private HardwareMap hardwareMap;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // Initialize hardwareMap and other components

        // Initialize TFOD
        initTFOD();

        // Wait for the start button to be pressed
        waitForStart();

        // Run the autonomous routine
        while (opModeIsActive()) {
            // Your autonomous code here

            // Detect and process the team prop
            detectTeamProp();

            // Continue with other autonomous actions
        }

        // Stop TFOD when the op mode is stopped
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initTFOD() {
        // Configure the camera and TFOD parameters
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters();
        tfodParameters.minResultConfidence = 0.8; // Adjust confidence threshold as needed
        tfod = TFObjectDetectorClassFactory.getInstance().createTFObjectDetector(tfodParameters, hardwareMap.get(WebcamName.class, "yourWebcamName"));

        // Load the model assets and labels
        tfod.loadModelFromAsset("yourModel.tflite", "label1", "label2", ...); // Adjust labels as needed
    }

    private void detectTeamProp() {
        // Activate TFOD to begin detecting objects
        if (tfod != null) {
            tfod.activate();
        }

        // Check for objects detected by TFOD
        if (tfod != null && tfod.getUpdatedRecognitions() != null) {
            // Loop through all recognitions
            for (Recognition recognition : tfod.getUpdatedRecognitions()) {
                // Check if the detected object is your team prop
                if (recognition.getLabel().equals("YourTeamPropLabel")) {
                    // Perform actions based on the detection (e.g., move the robot accordingly)
                    telemetry.addData("Team Prop Detected", "Proceeding with actions");
                    telemetry.update();
                    // Your actions here
                }
            }
        }

        // Deactivate TFOD after processing
        if (tfod != null) {
            tfod.deactivate();
        }
    }
}
