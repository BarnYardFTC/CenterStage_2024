package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claws {

    Servo left_claw;
    Servo right_claw;
    final double LEFT_CLAW_CLOSED_POSITION = 0;
    final double LEFT_CLAW_OPENED_POSITION = 0.3;
    final double RIGHT_CLAW_CLOSED_POSITION = 1;
    final double RIGHT_CLAW_OPENED_POSITION = 0;
    boolean was_right_bumper_pressed = false;
    boolean was_left_bumper_pressed = false;
    
    public Claws(Servo left_claw, Servo right_claw) {
        this.left_claw = left_claw;
        this.right_claw = right_claw;
    }
    
    private void closeClaw(Servo claw, double closed_position) {
        claw.setPosition(closed_position);
    }
    private void openClaw(Servo claw, double opened_position) {
        claw.setPosition(opened_position);
    }
    public void openBothClaws() {
        left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
        right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
    }
    public void runClaws(boolean left_bumper_pressed, boolean right_bumper_pressed) {
        if (right_bumper_pressed && !was_right_bumper_pressed) {
            if (Math.abs(right_claw.getPosition() - RIGHT_CLAW_CLOSED_POSITION)
                    < Math.abs(right_claw.getPosition() - RIGHT_CLAW_OPENED_POSITION)) {
                openClaw(right_claw, RIGHT_CLAW_OPENED_POSITION);
            }
            else {
                closeClaw(right_claw, RIGHT_CLAW_CLOSED_POSITION);
            }
            was_right_bumper_pressed = true;
        }
        if (!right_bumper_pressed) {
            was_right_bumper_pressed = false;
        }

        if (left_bumper_pressed && !was_left_bumper_pressed) {
            if (Math.abs(left_claw.getPosition() - LEFT_CLAW_CLOSED_POSITION)
                    < Math.abs(left_claw.getPosition() - LEFT_CLAW_OPENED_POSITION)) {
                openClaw(left_claw, LEFT_CLAW_OPENED_POSITION);
            }
            else {
                closeClaw(left_claw, LEFT_CLAW_CLOSED_POSITION);
            }
            was_left_bumper_pressed = true;
        }
        if (!left_bumper_pressed) {
            was_left_bumper_pressed = false;
        }
    }
}
