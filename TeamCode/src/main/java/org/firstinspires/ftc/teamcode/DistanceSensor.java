package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensor {
    public ModernRoboticsI2cRangeSensor distanceSensor;
    DistanceSensor(ModernRoboticsI2cRangeSensor distanceSensor) {
        this.distanceSensor = distanceSensor;
    }

    public double getDistance() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }

}
