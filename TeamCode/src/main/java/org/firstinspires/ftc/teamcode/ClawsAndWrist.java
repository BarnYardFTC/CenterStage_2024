package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawsAndWrist extends LinearOpMode{
    Servo wrist;
    final double WRIST_UP_POSITION = 0.4;
    final double WRIST_DOWN_POSITION = 0.08; // ToDo: Find the correct value
    final double WRIST_COLLECT_POSITION = 0;// ToDo: Find the correct value

    Servo left_claw;
    Servo right_claw;
    final double LEFT_CLAW_CLOSED_POSITION = 0.5;
    final double LEFT_CLAW_OPENED_POSITION = 1;
    final double LEFT_CLAW_MINIMAL_CLOSED_POSITION = 0.7; // ToDo: Find the correct value
    final double RIGHT_CLAW_CLOSED_POSITION = 0;
    final double RIGHT_CLAW_OPENED_POSITION = 1;
    final double RIGHT_CLAW_MINIMAL_CLOSED_POSITION = 0.7; // ToDo: Find the correct value
    @Override
    public void runOpMode() throws InterruptedException {
        wrist = hardwareMap.get(Servo.class, "wrist");

        // 0 = down | 1 = up | 2 = moving down | 3 = moving up
        int wrist_position = 0;

        wrist.setPosition(WRIST_DOWN_POSITION);

        left_claw = hardwareMap.get(Servo.class, "left_claw");
        right_claw = hardwareMap.get(Servo.class, "right_claw");

        // 0 = closed | 1 = open | 2 moving to close | 3 moving to open
        int right_claw_position = 0;
        int left_claw_position = 0;

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.y && wrist_position == 0) {
                wrist.setPosition(WRIST_UP_POSITION);
                wrist_position = 3;
            }
            if (gamepad1.y && wrist_position == 1) {
                wrist.setPosition(WRIST_DOWN_POSITION);
                wrist_position = 2;
            }
            if (!gamepad1.y && wrist_position == 3) {
                wrist_position = 1;
            }
            if (!gamepad1.y && wrist_position == 2){
                wrist_position = 0;
            }

            if (gamepad1.right_bumper && right_claw_position == 1) {
                wrist.setPosition(WRIST_COLLECT_POSITION);

                if (wrist.getPosition() == WRIST_COLLECT_POSITION) {
                    right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
                    right_claw_position = 2;
                }

                if (right_claw.getPosition() >= RIGHT_CLAW_MINIMAL_CLOSED_POSITION) {
                    wrist.setPosition(WRIST_DOWN_POSITION);
                }
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
                wrist.setPosition(WRIST_COLLECT_POSITION);

                if (wrist.getPosition() == WRIST_COLLECT_POSITION) {
                    left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
                    left_claw_position = 2;
                }

                if (left_claw.getPosition() >= LEFT_CLAW_MINIMAL_CLOSED_POSITION) {
                    wrist.setPosition(WRIST_DOWN_POSITION);
                }
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
