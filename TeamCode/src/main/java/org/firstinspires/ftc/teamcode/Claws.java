package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claws {

    private static Servo left_claw;
    private static Servo right_claw;

    static final double LEFT_CLAW_CLOSED_POSITION = 0.43;
    static final double LEFT_CLAW_OPENED_POSITION = 0.03;
    static final double RIGHT_CLAW_CLOSED_POSITION = 0.15;
    static final double RIGHT_CLAW_OPENED_POSITION = 0.55;

    private static boolean was_right_bumper_pressed;
    private static boolean was_left_bumper_pressed;
    
    public static void init(Servo left_claw, Servo right_claw) {
        Claws.left_claw = right_claw;
        Claws.right_claw = left_claw;

        was_right_bumper_pressed = false;
        was_left_bumper_pressed = false;
    }
    public static void moveToStartPosition() {
        left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
        right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
    }
    public static void closeRightClaw() {
        right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
    }
    public static void openRightClaw() {
        right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
    }
    public static void closeLeftClaw() {
        left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
    }
    public static void openLeftClaw() {
        left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
    }
    public static void runClawsTeleop(boolean left_bumper_pressed, boolean right_bumper_pressed) {
        if (right_bumper_pressed && !was_right_bumper_pressed) {
            if (Math.abs(right_claw.getPosition() - RIGHT_CLAW_CLOSED_POSITION)
                    < Math.abs(right_claw.getPosition() - RIGHT_CLAW_OPENED_POSITION)) {
                right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
            } else {
                right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
            }
            was_right_bumper_pressed = true;
        } else if (!right_bumper_pressed) {
            was_right_bumper_pressed = false;
        }
        if (left_bumper_pressed && !was_left_bumper_pressed) {
            if (Math.abs(left_claw.getPosition() - LEFT_CLAW_CLOSED_POSITION)
                    < Math.abs(left_claw.getPosition() - LEFT_CLAW_OPENED_POSITION)) {
                left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
            } else {
                left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
            }
            was_left_bumper_pressed = true;
        } else if (!left_bumper_pressed) {
            was_left_bumper_pressed = false;
        }
    }
    public static void loadingModeClaws() {
        right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
        left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
    }
    public static double getLeftClawPosition() {
        return left_claw.getPosition();
    }
    public static double getRightClawPosition() {
        return right_claw.getPosition();
    }
}
