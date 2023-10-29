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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class MecanumOpMode extends OpMode
{

    // drive values
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;

    boolean clawClicked = false;

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
        bot.arm.armMotor.setTargetPosition(bot.arm.MIN_ARM_POSITION);
        bot.arm.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bot.arm.armMotor.setPower(bot.arm.ARM_POWER);
    }

    //
    @Override
    public void loop() {
        // get drive, strafe, and turn values
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        bot.moveBotMecanum(drive, turn, strafe, 1); // actually move the robot

        // TODO: arm & slide
        if (gamepad1.dpad_up) {
            bot.arm.moveSlide(bot.arm.getCurrentSlidePos() + 30);
//            bot.arm.moveSlide(2);
        }
        else if (gamepad1.dpad_down) {
            bot.arm.moveSlide(bot.arm.getCurrentSlidePos() - 30);
//            bot.arm.moveSlide(-2);
        }
        else if (gamepad1.dpad_right) {
            bot.arm.moveArm(bot.arm.getCurrentArmPos() + 10);
//            bot.arm.moveArm(2);
        }
        else if (gamepad1.dpad_left) {
            bot.arm.moveArm(bot.arm.getCurrentArmPos() - 10);
//            bot.arm.moveArm(-2);
        }

        telemetry.addData("slide", bot.arm.getCurrentSlidePos());
        telemetry.addData("arm", bot.arm.getCurrentArmPos());
        telemetry.addData("claw", bot.claw.servo1.getPosition());

        // TODO: claw
        bot.claw.servo1.setDirection(Servo.Direction.FORWARD);
        if (gamepad1.left_bumper) {
            bot.claw.servo1.setDirection(Servo.Direction.REVERSE);
            bot.claw.servo1.setPosition(0.0005);
            clawClicked = true;
        }
        else if (gamepad1.right_bumper) {
            bot.claw.servo1.setDirection(Servo.Direction.FORWARD);
            bot.claw.servo1.setPosition(0);
            clawClicked = true;
        }
        else if (clawClicked) {
//            bot.claw.stop();
        }

        // TODO: drone launch

        if (gamepad1.a) {
            bot.drone.launch();
        }
        bot.drone.loop();

        // TODO: intake

        if (gamepad1.x) {
            bot.inOutTake.intake();
        }
        else if (gamepad1.b) {
            bot.inOutTake.outtake();
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