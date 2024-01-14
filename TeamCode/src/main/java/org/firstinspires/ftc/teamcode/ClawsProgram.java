package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Claws Program")
public class ClawsProgram extends LinearOpMode {

    Servo left_claw;
    Servo right_claw;
    final double LEFT_CLAW_CLOSED_POSITION = 0.5;
    final double LEFT_CLAW_OPENED_POSITION = 1;
    final double RIGHT_CLAW_CLOSED_POSITION = 0;
    final double RIGHT_CLAW_OPENED_POSITION = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        left_claw = hardwareMap.get(Servo.class, "left_claw");
        right_claw = hardwareMap.get(Servo.class, "right_claw");
        waitForStart();
        // 0 = closed | 1 = open | 2 moving to close | 3 moving to open
        int right_claw_position = 0;
        int left_claw_position = 0;

        while (opModeIsActive()) {
            if (gamepad1.right_bumper && right_claw_position == 1) {
                right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
                right_claw_position = 2;
            }
            if (gamepad1.right_bumper && right_claw_position == 0) {
                right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
                right_claw_position = 3;
            }
            if (!gamepad1.right_bumper && right_claw_position == 2) {
                right_claw_position = 0;
            }
            if (!gamepad1.right_bumper && right_claw_position == 3) {
                right_claw_position = 1;
            }

            if (gamepad1.left_bumper && left_claw_position == 1) {
                left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
                left_claw_position = 2;
            }
            if (gamepad1.left_bumper && left_claw_position == 0) {
                left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
                left_claw_position = 3;
            }
            if (!gamepad1.left_bumper && left_claw_position == 2) {
                left_claw_position = 0;
            }
            if (!gamepad1.left_bumper && left_claw_position == 3) {
                left_claw_position = 1;
            }
        }
    }
}
