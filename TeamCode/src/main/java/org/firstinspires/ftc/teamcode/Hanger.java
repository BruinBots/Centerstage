package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Hanger {

    public final Servo hangServo;

    public boolean launched = false;

    public static final double PRIMED_POS = 0.6;
    public static final double RELEASED_POS = 0;

    public int timeToMoveServo = 100;

    boolean movingHanger;
    long hangTime;

    public Hanger(Servo hangServo) { this.hangServo = hangServo; }

//    public void moveHanger(double power) {
//        boolean movingHanger = true;
//        hangTime = getCurrentTime();
//        hangServo.setPower(power);
//    }

//    public void loop() {
//        if (getCurrentTime() > hangTime + timeToMoveServo) {
//            hangServo.setPower(0);
//            movingHanger = false;
//        }
//    }

    public void hangerPlus() {
//        moveHanger(-0.1);
        hangServo.setPosition(hangServo.getPosition() + 0.01);
    }

    public void hangerNegative() {
//        moveHanger(0.1);
        hangServo.setPosition(hangServo.getPosition() - 0.01);
    }
    
    public void hang() {
        if (launched) {
            hangServo.setPosition(PRIMED_POS);
            launched = false;
        } else if (!launched) {
            hangServo.setPosition(RELEASED_POS);
            launched = true;
        }
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
