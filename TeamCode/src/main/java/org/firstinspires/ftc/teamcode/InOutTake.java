package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

public class InOutTake {

    // inServo's are continuous rotation servos, rotate backwards of each other to in/out-take the pixel
    private final Servo inServoLeft;
    private final Servo inServoRight;

    // scoop servo is standard servo to move pixel between intake and claw
    private Servo scoopServo;

    // untested, should work
    // SPEED constants are between 0-0.5
    // 0            0.5
    // stop         full speed
    public static final double IN_SPEED = -0.5;
    public static final double OUT_SPEED = -0.3;


    // STOP_POS constant is  between 0-1
    // 0            0.5         1
    // backwards    stop        forwards
    // 0.5 is (should be) stop
    public static final double STOP_POS = 0.5;


    // left and right spin opposite of each other, at the same speed
    public static final double LEFT_IN_POS = STOP_POS + IN_SPEED;
    public static final double RIGHT_IN_POS = STOP_POS - IN_SPEED;
    public static final double LEFT_OUT_POS = STOP_POS - OUT_SPEED;
    public static final double RIGHT_OUT_POS = STOP_POS + OUT_SPEED;

    // SCOOP_POS constants are between 0-1
    // 0        TBD     1
    // down     up      too far up
    public static final double SCOOP_DOWN_POS = 0; // this should work, untested
    public static final double SCOOP_UP_POS = 0.5; // no idea if this works, untested

    InOutTake (Servo inServoLeft, Servo inServoRight, Servo scoopServo) {
        this.inServoLeft = inServoLeft;
        this.inServoRight = inServoRight;
        this.scoopServo = scoopServo;
    }

    // suck pixels in
    public void intake() {
        inServoLeft.setPosition(LEFT_IN_POS);
        inServoRight.setPosition(RIGHT_IN_POS);
    }

    // eject pixels out
    public void outtake() {
        inServoLeft.setPosition(LEFT_OUT_POS);
        inServoRight.setPosition(RIGHT_OUT_POS);
    }

    // stop inServo's
    public void stopTake() {
        inServoLeft.setPosition(STOP_POS);
        inServoRight.setPosition(STOP_POS);
    }

    // move pixel from intake --> claw
    public void scoopUp() {
        scoopServo.setPosition(SCOOP_UP_POS);
    }

    // move intake down to collect next pixel
    public void scoopDown() {
        scoopServo.setPosition(SCOOP_DOWN_POS);
    }
}
