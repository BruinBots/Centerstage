package org.firstinspires.ftc.teamcode;

<<<<<<< HEAD
=======
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

>>>>>>> 852e4adf295acb0cf15c4aa7bceb2420352654b1
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class  Drone {

    private DcMotorEx droneMotor;
    private boolean launchingDrone;
    private long timeWhenLaunched;
<<<<<<< HEAD
    private static long motorRunTime = 750; // 500 ms
    private static double motorPower = 1; // 85% power

    public Drone(DcMotorEx droneMotor) {
=======
    private static long motorRunTime = 500; // 500 ms
    public static double motorPower = 0.8;
    private boolean leftBumper;
    private boolean rightBumper;

    Drone(DcMotorEx droneMotor) {
>>>>>>> 852e4adf295acb0cf15c4aa7bceb2420352654b1
        this.droneMotor = droneMotor;
    }

    public void loop() {
        if (launchingDrone) {
            if (elapsedTime() > motorRunTime) {
                stop();
            }
        }
    }

    public void launch() {
        droneMotor.setPower(motorPower);
        launchingDrone = true;
        timeWhenLaunched = getCurrentTime();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private long elapsedTime() {
        return getCurrentTime() - timeWhenLaunched;
    }

    private void stop() {
        launchingDrone = false;
        droneMotor.setPower(0);
    }
}