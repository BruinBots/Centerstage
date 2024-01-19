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
        bot.claw.setClawWrist(0.1);
    }

    //
    @Override
    public void init_loop() {
    }

    // place the pixel by the spike mark on given side
    public Trajectory spike(Pose2d startPose, String side, boolean finishSpike) {

        // ensure the pixel is securely in the dropper
        bot.dropper.closed();
        sleep(250);

        Trajectory traj0a = spikeStart(startPose);
        Trajectory traj0b;

        switch (side) {
            case "left":
                telemetry.addData("side", "left");
                traj0b = spikeLeft(traj0a.end());
                break;
            case "center":
                telemetry.addData("side", "center");
                traj0b = spikeCenter(traj0a.end());
                break;
            case "right":
                telemetry.addData("side", "right");
                traj0b = spikeRight(traj0a.end());
                break;
            default:
                telemetry.addData("side", "default");
                traj0b = spikeLeft(traj0a.end());
                break;
        }

        Trajectory traj0c = spikeEnd(traj0b.end());

        drive.followTrajectory(traj0a); // move to the spikeStart position to ensure no crashing during navigation
        drive.followTrajectory(traj0b); // move to the spike mark

        bot.dropper.open(); // release the pixel
        sleep(250);

        if (finishSpike) {
            drive.followTrajectory(traj0c); // if finishing spike, return to spikeEnd position to prepare for parking/pixel placing
            return traj0c;
        }

        return traj0b;
    }

    //
    @Override
    public void loop() {
        // get drive, strafe, and turn values

        drive = gamepad1.left_stick_y - gamepad2.left_stick_y;
        strafe = gamepad2.left_stick_x - gamepad1.left_stick_x;
        turn= gamepad1.right_stick_x + gamepad2.right_stick_x;

        if (drive > 1) { drive = 1; }
        if (strafe > 1) { strafe = 1; }
        if (turn > 1) { turn = 1; }

        strafe = Math.copySign(Math.pow(strafe, 2), strafe);
        drive = Math.copySign(Math.pow(drive, 2), drive);
        turn = Math.copySign(Math.pow(turn, 2), turn);

        bot.moveBotMecanum(drive, turn, strafe,  0.5); // actually move the robot

        // if bumper pressed increase or decrease arm
        if (gamepad2.right_bumper) {
            bot.arm.moveArm(bot.arm.getCurrentArmPos() + Arm.ARM_SPEED);
            bot.claw.setClawWristFromAngle(bot.arm.clawAngle());
        }
        else if (gamepad2.left_bumper) {
            bot.arm.moveArm(bot.arm.getCurrentArmPos() - Arm.ARM_SPEED);
            bot.claw.setClawWristFromAngle(bot.arm.clawAngle());
        }
        else {
            bot.arm.holdArmPos();
        }

        if (gamepad2.left_trigger > 0.1) {
            bot.claw.moveClawWrist(-0.1);
        } else if (gamepad2.right_trigger > 0.1) {
            bot.claw.moveClawWrist(0.1);
        }
        // dropper
        if (gamepad2.y) {
            bot.dropper.dropperUp();
        }

        // lift up the arm
        bot.arm.moveArm(Arm.MAX_ARM_POSITION, true);
        sleep(250);

        // drone launch

        // open the claw to release the pixels onto the backdrop
        bot.claw.openLowerClaw();
        sleep(500);

        // close the claw after releasing the pixels
        bot.claw.closeLowerClaw();
        sleep(100);

        if (gamepad1.left_trigger > 0.5) {
            bot.inOutTake.intake();
        }
        else if (gamepad1.right_trigger > 0.5) {
            bot.inOutTake.outtake();
        }
        else {
            bot.inOutTake.stopTake();
        }

        // retract the arm
        bot.arm.moveArm(Arm.MIN_ARM_POSITION, true);
        sleep(250);

        telemetry.addData("arm", bot.arm.getCurrentArmPos());
        telemetry.addData("armAngle", bot.arm.armAngle());
        telemetry.addData("clawAngle", bot.arm.clawAngle());
        telemetry.addData("clawPos", bot.claw.getCurrentWristPosition());

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