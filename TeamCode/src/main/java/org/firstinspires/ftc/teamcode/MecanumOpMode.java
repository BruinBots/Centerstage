/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  */

package org.firstinspires.ftc.teamcode;


import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class MecanumOpMode extends OpMode
{

    // drive values
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;


    // robot
    Karen bot;

    //
    @Override
    public void init() {

        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    //
    @Override
    public void init_loop() {
    }

    //
    @Override
    public void start() {
        //bot.arm.moveArm(Arm.MIN_ARM_POSITION);
    }

    //
    @Override
    public void loop() {
        // get drive, strafe, and turn values
//        drive = -gamepad2.left_stick_x;
//        strafe = gamepad2.left_stick_y;
//        turn = gamepad2.right_stick_y;

        drive = gamepad1.left_stick_y - gamepad2.left_stick_y;
        strafe = gamepad2.left_stick_x - gamepad1.left_stick_x;
        turn= gamepad1.right_stick_x + gamepad2.right_stick_x;

        strafe = Math.copySign(Math.pow(strafe, 2), strafe);
        drive = Math.copySign(Math.pow(drive, 2), drive);
        turn = Math.copySign(Math.pow(turn, 2), turn);

        bot.moveBotMecanum(drive, turn, strafe,  0.5); // actually move the robot

        // arm & slid
        if (gamepad2.right_trigger  > 0.5) {
            bot.arm.moveSlide(bot.arm.getCurrentSlidePos() + 30);
        }
        else if (gamepad2.left_trigger  > 0.5) {
            bot.arm.moveSlide(bot.arm.getCurrentSlidePos() - 30);
        }
        else {
            bot.arm.holdSlidePos();
        }

        if (gamepad2.right_bumper ) {
            bot.arm.moveArm(bot.arm.getCurrentArmPos() + 50);
        }
        else if (gamepad2.left_bumper) {
            bot.arm.moveArm(bot.arm.getCurrentArmPos() - 50);
        }
        else {
            bot.arm.holdArmPos();
        }

        telemetry.addData("arm", bot.arm.getCurrentArmPos());
        telemetry.addData("slide", bot.arm.getCurrentSlidePos());

        // claw
        if (gamepad2.x ) {
            bot.claw.closeBothClaw();
        }
        else if (gamepad2.b) {
            bot.claw.closeOneClaw();
        }
        else if (gamepad2.a) {
            bot.claw.openClaw();
        } else if (gamepad2.y) {
            bot.arm.moveArm(bot.arm.MIN_ARM_POSITION);
            bot.arm.moveSlide(bot.arm.MIN_SLIDE_POSITION);
        }


        // drone launch

        if (gamepad1.y) {
            bot.drone.launch();
        }
        bot.drone.loop();

        // TODO: intake



        if (gamepad1.left_trigger > 0.5) {
            bot.inOutTake.intake();
        }
        else if (gamepad1.right_trigger > 0.5) {
            bot.inOutTake.outtake();
        }
        else {
            bot.inOutTake.stopTake();
        }

        if (gamepad1.dpad_up) {
            bot.claw.openClaw();
            bot.inOutTake.scoopUp();
        }
        else if (gamepad1.dpad_down) {
            bot.claw.openClaw();
            bot.inOutTake.scoopDown();
            bot.arm.moveSlide(bot.arm.MIN_SLIDE_POSITION);

        } else if (gamepad1.dpad_left) {
            bot.inOutTake.scoopMiddle();
        }




        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void stop() {
        bot.stop(); // stop all motors
    }



}