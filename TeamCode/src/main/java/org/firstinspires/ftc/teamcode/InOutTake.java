package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class InOutTake {

    private Servo inServoLeft;
    private Servo inServoRight;
    // above servos are continous rotation servos, rotate backwards of each other to in/out-take the pixel
    private Servo scoopServo;
    // scoop servo is standard servo to move pixel between intake and claw

    public static final double IN_SPEED = 0.5; // untested, should work
    public static final double OUT_SPEED = 0.3;

    // above constants are between 0-1
    // 0            0.5         1
    // backwards    stop        forwards

    public static final double SCOOP_DOWN_POS = 0; // this should work, untested
    public static final double SCOOP_UP_POS = 0.7; // no idea if this works
    // above constants are between 0-1
    // 0        ?       1
    // down     up      too far up

    InOutTake (Servo inServoLeft, Servo inServoRight, Servo scoopServo) {
        this.inServoLeft = inServoLeft;
        this.inServoRight = inServoRight;
        this.scoopServo = scoopServo;
    }

    public void intake() { // suck pixels in
        inServoLeft.setPosition(0.5 + IN_SPEED);
        inServoRight.setPosition(0.5 - IN_SPEED);
    }

    public void outtake() { // eject pixels out
        inServoLeft.setPosition(0.5 + OUT_SPEED);
        inServoRight.setPosition(0.5 - OUT_SPEED);
    }

    public void stopTake() { // stop inServo's
        inServoLeft.setPosition(0.5);
        inServoRight.setPosition(0.5);
    }

    public void scoopUp() { // move pixel form intake --> claw
        scoopServo.setPosition(SCOOP_UP_POS);
    }

    public void scoopDown() { // move intake down to collect next pixel
        scoopServo.setPosition(SCOOP_DOWN_POS);
    }
}
