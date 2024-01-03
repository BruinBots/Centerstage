package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class OldDrone {

    private DcMotorEx droneMotor;
    private boolean launchingDrone;
    private long timeWhenLaunched;
    private static long motorRunTime = 500; // 500 ms
    public static double motorPower = 0.8;
    private boolean leftBumper;
    private boolean rightBumper;

    OldDrone(DcMotorEx droneMotor) {
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