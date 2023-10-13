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
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Basic: Mecanum TeleOp", group="Iterative Opmode")
public class MecanumOpMode extends OpMode
{
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;
    Karen bot;
    int i=0;

    public static final int DRAW_SIZE = 1;
    private static final double DRIVE_SPEED = 0.1;
    @Override
    public void init() {
        bot = new Karen(hardwareMap, telemetry);
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
        // get drive, strafe, and turn values
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        telemetry.addData("WHEEL SPEED:", bot.getWheelSpeeds()[i]);
        telemetry.update();
        i++;

        // Draw x or o
        if (gamepad1.dpad_left) {
            bot.drawX(DRAW_SIZE, DRIVE_SPEED);
        }
        else if (gamepad1.dpad_right) {
//             bot.drawO(SIZE);
        }

        /*
        red b
        blue x
        black a
        up y
         */

        if (gamepad1.a && !lastAButton) {
            if (bot.pen.currentServo.getPosition() < 0.9) { // if servo is up
                bot.pen.move(bot.pen.downPos);
            } else if (bot.pen.currentServo.getPosition() > 0.9) { // if servo is down
                bot.pen.move(bot.pen.upPos);
            }
        }

        // drone launch

        if (gamepad1.a) {
            bot.droneLaunch.launchDrone(DroneLaunch.LAUNCH_POWER, DroneLaunch.LAUNCH_TIME);
        }

        if (gamepad1.left_bumper && !lastLBumper) {
            bot.pen.switchPen("down");
        }

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
//             bot.drawO(DRAW_SIZE);
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
        bot.stop(); // stop all motors
    }



}