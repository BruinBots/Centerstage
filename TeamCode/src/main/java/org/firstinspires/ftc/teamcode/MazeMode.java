//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//@TeleOp(name="MazeMode", group="Kings Glen")
//public class MazeMode extends OpMode {
//    double drive = 0.0;
//    double turn = 0.0;
//    double strafe = 0.0;
//
//    Karen bot;
//
//    @Override
//    public void init() {
//        bot = new Karen(hardwareMap);
////        bot.init();
//    }
//
//    @Override
//    public void loop() {
//        drive = gamepad1.left_stick_y;
//        strafe = gamepad2.left_stick_x;
//        turn= gamepad1.right_stick_x;
//
//        if (drive > 1) { drive = 1; }
//        if (strafe > 1) { strafe = 1; }
//        if (turn > 1) { turn = 1; }
//
//        strafe = Math.copySign(Math.pow(strafe, 3), strafe);
//        drive = Math.copySign(Math.pow(drive, 3), drive);
//        turn = Math.copySign(Math.pow(turn, 3), turn);
//
//        // emergency brake if we need it
//        if (gamepad2.b) {
//            bot.leftBackMotor.setPower(0);
//            bot.leftFrontMotor.setPower(0);
//            bot.rightBackMotor.setPower(0);
//            bot.rightFrontMotor.setPower(0);
//            drive = 0;
//            strafe = 0;
//            turn = 0;
//        }
//
//        bot.moveBotMecanum(drive, turn, strafe, 0.25); // actually move the robot
//    }
//}
