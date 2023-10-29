package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Drone {

    private DcMotorEx droneMotor;
    private boolean launchingDrone;
    private long timeWhenLaunched;
    private static long MOTOR_RUN_TIME = 500; // 500 ms
    private static double MOTOR_POWER = 1.0;

    Drone(DcMotorEx droneMotor) {
        this.droneMotor = droneMotor;
    }

    public void launch() {
        launchingDrone = true;
        droneMotor.setPower(MOTOR_POWER);
        timeWhenLaunched = getCurrentTime();
    }

    public void checkLaunchStatus() {
        if (launchingDrone) {
            if (elapsedTime() > MOTOR_RUN_TIME) {
                stop();
            }
        }
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