
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

@TeleOp(name="DroneMode", group="Kings Glen")
public class MecanumOpMode extends OpMode
{

    // drive values
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;

    // robot
    Karen bot;
    double pos = 0.4;

    boolean hanging;
    boolean gp1dpadup;
    boolean gp1dpadleft;
    boolean gp1dpaddown;
    boolean gp1y;
    boolean gp1a;

    boolean gp2dpadleft;
    boolean gp2dpadright;
    boolean gp2dpaddown;
    boolean gp2b;
    boolean gp2y;
    boolean gp2a;
    double scaleFactor=0.4;

    double TURRET_SPEED = 0.05;


    @Override
    public void init() {
        bot = new Karen(hardwareMap);
        telemetry.addData("Status", "Initialized");
//        bot.init();
        telemetry.update();
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
        if (gamepad1.dpad_up && bot.droneRotateServo.getPosition() < Drone.MIN_ROTATE_POS) {
            pos += 0.005;
        }
        else if (gamepad1.dpad_down && bot.droneRotateServo.getPosition() > Drone.MAX_ROTATE_POS)
        {
            pos -= 0.005;
        }
        bot.droneRotateServo.setPosition(pos);

        if (gamepad1.dpad_left) {
            bot.drone.setTurret(0.5 - TURRET_SPEED);
        }
        else if (gamepad1.dpad_right) {
            bot.drone.setTurret(0.5 + TURRET_SPEED);
        }
        else {
            bot.drone.setTurret(0.5);
        }

        // drone launcher
        if (gamepad1.y && !gp1y) {
            bot.drone.launchWithRotation(pos); // 0.4
        }
        bot.drone.loop();

        gp1dpadup = gamepad1.dpad_up;
        gp1dpadleft = gamepad1.dpad_left;
        gp1dpaddown = gamepad1.dpad_down;
        gp1y = gamepad1.y;
        gp1a = gamepad1.a;

        gp2dpadleft = gamepad2.dpad_left;
        gp2dpadright = gamepad2.dpad_right;
        gp2dpaddown = gamepad2.dpad_down;
        gp2b = gamepad2.b;
        gp2y = gamepad2.y;
        gp2a = gamepad2.a;

        try {
            sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
