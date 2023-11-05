package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class InOutTake {

    private Servo inServo1;
    private Servo inServo2;
    private Servo scoopServo;

    public static final double IN_POSITION = 1;
    public static final double OUT_POSITION = 0;

    public static final double SCOOP_DOWN_POS = 0;
    public static final double SCOOP_UP_POS = 0.7;

    InOutTake (Servo inServo1, Servo inServo2, Servo scoopServo) {
        this.inServo1 = inServo1;
        this.inServo2 = inServo2;
        this.scoopServo = scoopServo;
    }

    public void intake() {
        inServo1.setPosition(IN_POSITION);
        inServo2.setPosition(IN_POSITION);
    }

    public void outtake() {
        inServo1.setPosition(OUT_POSITION);
        inServo2.setPosition(OUT_POSITION);
    }

    public void scoopUp() {
        scoopServo.setPosition(SCOOP_UP_POS);
    }

    public void scoopDown() {
        scoopServo.setPosition(SCOOP_DOWN_POS);
    }
}
