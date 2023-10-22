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

@TeleOp(name="STEMFEST TeleOp", group="Iterative Opmode")
public class HumanOperatedMode extends OpMode
{
    Karen bot;

    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;

    boolean lastAButton;
    boolean lastBButton;
    boolean lastXButton;
    boolean lastYButton;

    boolean droneLaunching;

    private static final double DRIVE_SPEED = 0.3;
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
        // get drive, strafe, and turn values
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        bot.moveBotMecanum(drive, turn, strafe, DRIVE_SPEED);

        if (gamepad1.x && !lastXButton) {
            if (bot.pen.colorServoMap.get(Pen.PenColor.blue).getPosition() == bot.pen.downPos) {
                bot.pen.raisePen(Pen.PenColor.blue);
            } else {
                bot.pen.lowerPen(Pen.PenColor.blue);
            }
        }

        if (gamepad1.y && !lastYButton) {
            if (bot.pen.colorServoMap.get(Pen.PenColor.black).getPosition() == bot.pen.downPos) {
                bot.pen.raisePen(Pen.PenColor.black);
            } else {
                bot.pen.lowerPen(Pen.PenColor.black);
            }
        }

        if (gamepad1.b && !lastBButton) {
            if (bot.pen.colorServoMap.get(Pen.PenColor.red).getPosition() == bot.pen.downPos) {
                bot.pen.raisePen(Pen.PenColor.red);
            } else {
                bot.pen.lowerPen(Pen.PenColor.red);
            }
        }

        if (gamepad1.a && lastAButton) {
            bot.pen.raiseAllPens();
        }

        if (gamepad1.left_stick_button && gamepad1.right_stick_button && !droneLaunching) {
            droneLaunching = true;
            bot.drone.launchDrone();
        }
        if (!gamepad1.left_stick_button && !gamepad1.right_stick_button) {
            droneLaunching = false;
        }

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lastAButton = gamepad1.a;
        lastBButton = gamepad1.b;
        lastXButton = gamepad1.x;
        lastYButton = gamepad1.y;

        telemetry.update();
    }

    @Override
    public void stop() {
        bot.stop(); // stop all motors
    }

}