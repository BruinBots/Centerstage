package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class  Drone {

    private DcMotorEx droneMotor;
    private boolean launchingDrone;
    private long timeWhenLaunched;
    private static long motorRunTime = 750; // 500 ms
    private static double motorPower = 1; // 85% power

    public Drone(DcMotorEx droneMotor) {
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