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
    // Declare OpMode members.

    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;

    double armPower = 0.0;
    int slidePos;
    int armPos;
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
        slidePos = bot.arm.slideMotor.getCurrentPosition();
        armPos = bot.arm.armMotor.getCurrentPosition();
    }

    //
    @Override
    public void start() {
    }

    //
    @Override
    public void loop() {
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        bot.moveBotMecanum(drive, turn, strafe, 1);

        if (gamepad1.dpad_down) { // arm down
            slidePos -= 10;
            if (slidePos< Karen.arm.MIN_SLIDE_POSITION  ){ //
                slidePos = Karen.arm.MIN_SLIDE_POSITION;
            }

            bot.arm.moveSlide(slidePos);
            slidePos= bot.arm.getCurrentslidePos();
            telemetry.addData("arm down", "");
        } else if (gamepad1.dpad_up) {
            slidePos += 10;

            if(slidePos> Karen.arm.MAX_SLIDE_POSITION){ //
                slidePos = Karen.arm.MAX_SLIDE_POSITION;
            }

            bot.arm.moveSlide(slidePos);
            slidePos = bot.arm.getCurrentslidePos();
            telemetry.addData("slide up", "");
        } else {
            bot.arm.moveSlide(slidePos);
            telemetry.addData("slide still", "");
        }
        //arm -----------------




        if (gamepad1.dpad_left) { //
            armPos -= 10;
            if (armPos < Karen.arm.MIN_ARM_POSITION){ //
                armPos = Karen.arm.MIN_ARM_POSITION;
            }

            bot.arm.moveArm(armPos);
            armPos = bot.arm.getCurrentArmPos();
            telemetry.addData("arm down", "");
        } else if (gamepad1.dpad_right) {
            armPos += 10;

            if(armPos > Karen.arm.MAX_ARM_POSITION){ //
                armPos = Karen.arm.MAX_ARM_POSITION;

            }

            bot.arm.moveArm(armPos);
            armPos = bot.arm.getCurrentArmPos();
            telemetry.addData("arm up", "");
        } else {
            bot.arm.moveArm(armPos);
            telemetry.addData("arm still", "");
        }


        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        telemetry.addData("armPos:", bot.arm.getCurrentArmPos());
        telemetry.addData("slidePos:", bot.arm.getCurrentslidePos());
    }


    @Override
    public void stop() {
    }



}