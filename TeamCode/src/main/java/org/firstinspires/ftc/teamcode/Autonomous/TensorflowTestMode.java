package org.firstinspires.ftc.teamcode.Autonomous;

import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Config
@Autonomous(name="TensorflowTestMode")
public class TensorflowTestMode extends OpMode {

    private TensorFlowForAutonomousBlueRed tf;

    public static boolean blue = true;

    @Override
    public void init() {
        tf = new TensorFlowForAutonomousBlueRed(hardwareMap, telemetry, blue ? "blue" : "red");
        tf.initTfod();
    }

    @Override
    public void loop() {
        telemetry.addData("side", tf.getSide(blue));
        telemetry.update();
        if (tf.isStopRequested()) {
            stop();
        }
        sleep(20);
    }
}
