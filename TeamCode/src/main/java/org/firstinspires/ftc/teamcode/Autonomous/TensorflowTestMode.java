package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name="TensorflowTestMode")
public class TensorflowTestMode extends OpMode {

    private TensorFlowForAutonomousBlueRed tf;

    @Override
    public void init() {
        tf = new TensorFlowForAutonomousBlueRed(hardwareMap, telemetry, "blue");
        tf.initTfod();
    }

    @Override
    public void loop() {
        telemetry.addData("blue-side", tf.blueTelemetryTfod());
        telemetry.addData("red-side", tf.redTelemetryTfod());
        telemetry.update();
        if (tf.isStopRequested()) {
            stop();
        }
        sleep(20);
    }
}
