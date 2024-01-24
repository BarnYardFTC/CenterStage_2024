package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claws {

    static Servo left_claw;
    static Servo right_claw;

    static final double LEFT_CLAW_CLOSED_POSITION = 0.25;
    static final double LEFT_CLAW_OPENED_POSITION = 0.3;
    static final double RIGHT_CLAW_CLOSED_POSITION = 0.3;
    static final double RIGHT_CLAW_OPENED_POSITION = 0;

    static boolean was_right_bumper_pressed = false;
    static boolean was_left_bumper_pressed = false;
    
    public static void init(Servo left_claw, Servo right_claw) {
        Claws.left_claw = left_claw;
        Claws.right_claw = right_claw;
    }
    public static void moveToStartPosition() {
        left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
        right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
    }
    public static void runClawsTeleop(boolean left_bumper_pressed, boolean right_bumper_pressed) {
        if (right_bumper_pressed && !was_right_bumper_pressed) {
            if (Math.abs(right_claw.getPosition() - RIGHT_CLAW_CLOSED_POSITION)
                    < Math.abs(right_claw.getPosition() - RIGHT_CLAW_OPENED_POSITION)) {
                right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
            }
            else {
                right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
            }
            was_right_bumper_pressed = true;
        }
        if (!right_bumper_pressed) {
            was_right_bumper_pressed = false;
        }

        if (left_bumper_pressed && !was_left_bumper_pressed) {
            if (Math.abs(left_claw.getPosition() - LEFT_CLAW_CLOSED_POSITION)
                    < Math.abs(left_claw.getPosition() - LEFT_CLAW_OPENED_POSITION)) {
                left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
            }
            else {
                left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
            }
            was_left_bumper_pressed = true;
        }
        if (!left_bumper_pressed) {
            was_left_bumper_pressed = false;
        }
    }
}
