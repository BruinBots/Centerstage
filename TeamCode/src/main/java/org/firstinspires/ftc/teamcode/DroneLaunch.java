package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class DroneLaunch {

    public DcMotorEx droneMotor;

    DroneLaunch (DcMotorEx droneMotor) {
        this.droneMotor = droneMotor;
    }

    public void launchDrone(double motorPower, long waitTime) {
        droneMotor.setPower(motorPower);
        try {
            sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        droneMotor.setPower(0);
    }

    public void stop() {
        droneMotor.setPower(0);
    }
}