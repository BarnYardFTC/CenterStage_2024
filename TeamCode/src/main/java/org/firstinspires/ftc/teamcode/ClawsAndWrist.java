package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawsAndWrist extends LinearOpMode{
    Servo wrist;
    final double WRIST_UP_POSITION = 0.4;
    final double WRIST_DOWN_POSITION = 0.08;
    final double WRIST_COLLECT_POSITION = 0;

    Servo left_claw;
    Servo right_claw;
    final double LEFT_CLAW_CLOSED_POSITION = 0.5;
    final double LEFT_CLAW_OPENED_POSITION = 1;
    final double RIGHT_CLAW_CLOSED_POSITION = 0;
    final double RIGHT_CLAW_OPENED_POSITION = 1;

    public boolean clawClosed(Servo claw, double claw_previous_position, double claw_target_position) {
        return claw.getPosition() == claw_previous_position;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        wrist = hardwareMap.get(Servo.class, "wrist");

        wrist.setPosition(WRIST_DOWN_POSITION);

        left_claw = hardwareMap.get(Servo.class, "left_claw");
        right_claw = hardwareMap.get(Servo.class, "right_claw");

        left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
        right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);

        waitForStart();

        boolean right_bumper_pressed = false;
        boolean right_claw_collecting = false;
        boolean right_claw_closed = false;


        double right_claw_previous_position = 0;

        while (opModeIsActive()) {
            if (gamepad1.right_bumper && !right_bumper_pressed && wrist.getPosition() == WRIST_DOWN_POSITION) {
                right_claw_collecting = true;
                right_bumper_pressed = true;
            }

            if (right_claw_collecting) {

                if (wrist.getPosition() != WRIST_COLLECT_POSITION) {
                    wrist.setPosition(WRIST_COLLECT_POSITION);
                } else if (!right_claw_closed) {
                    right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
                right_claw_closed = clawClosed(right_claw, right_claw_previous_position, RIGHT_CLAW_CLOSED_POSITION);
                }
                else if (wrist.getPosition() != WRIST_DOWN_POSITION){
                    wrist.setPosition(WRIST_DOWN_POSITION);
                }
                else {
                    right_claw_collecting = false;
                }
                right_claw_previous_position = right_claw.getPosition();
            }
            if (!gamepad1.right_bumper) {
                right_bumper_pressed = false;
            }
        }


    }
}
