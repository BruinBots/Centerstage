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

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="StemFest TeleOp", group="Iterative Opmode")
public class HumanOperatedMode extends com.qualcomm.robotcore.eventloop.opmode.OpMode
{
    Karen bot;
    boolean lastAButton;
    boolean lastRBumper;
    boolean lastLBumper;

    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;

    @Override
    public void init() {
        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
        bot.pen.servo1.setPosition(bot.pen.upPos);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        bot.moveBotMecanum(drive, turn, strafe, 1);

        if (gamepad1.a && !lastAButton) {
            if (bot.pen.currentServo.getPosition() < 0.9) { // if servo is up
                bot.pen.move(bot.pen.downPos);
            } else if (bot.pen.currentServo.getPosition() > 0.9) { // if servo is down
                bot.pen.move(bot.pen.upPos);
            }
        }

        if (gamepad1.right_bumper && !lastRBumper) {
            bot.pen.switchPen("up");
        }

        if (gamepad1.left_bumper && !lastLBumper) {
            bot.pen.switchPen("down");
        }

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (bot.pen.currentServo.getPosition() > 0.9) { // if servo is down
            bot.pen.move(bot.pen.downPos);
        } else if (bot.pen.currentServo.getPosition() < 0.9) { // if servo is up
            bot.pen.move(bot.pen.upPos);
        }

        lastAButton = gamepad1.a;
        lastRBumper = gamepad1.right_bumper;
        lastLBumper = gamepad1.left_bumper;

        telemetry.addData(bot.pen.currentServo.getDeviceName()+" Value: ", bot.pen.currentServo.getPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        bot.stop();
    }
}