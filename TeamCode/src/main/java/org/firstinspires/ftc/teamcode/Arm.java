package org.firstinspires.ftc.teamcode;

// Imports

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

// Variables
    static private DcMotor rightArm;
    static private DcMotor arm2;
    static public final int MINIMAL_HOLD_POSITION = -300;
    static private boolean got_position_to_hold = false;
    static private int hold_position1 = 0;
    static private int hold_position2 = 0;
    static public boolean HANGING_MODE_ACTIVE = false;
    static private boolean DPAD_PRESSED = false;
    static public boolean LOADING_MODE_ACTIVE = false;

// Initializing
    public static void init(DcMotor motor1, DcMotor motor2) {
        rightArm = motor1;
        arm2 = motor2;

        rightArm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm2.setDirection(DcMotorSimple.Direction.REVERSE);

        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DPAD_PRESSED = false;
        LOADING_MODE_ACTIVE = false;
        HANGING_MODE_ACTIVE = false;
        double SPEED = 0;
    }

// System's functions
    public static void moveUp(double SPEED) {
        got_position_to_hold = false;

        rightArm.setPower(1);
        rightArm.setTargetPosition(rightArm.getCurrentPosition() - (int) (380 * SPEED));
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() + (int) (380 * SPEED));
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown(double SPEED) {
        got_position_to_hold = false;

        rightArm.setPower(1);
        rightArm.setTargetPosition(rightArm.getCurrentPosition() + (int) (380 * SPEED));
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() - (int) (380 * SPEED));
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void hangingModeArm() {
        got_position_to_hold = false;

        HANGING_MODE_ACTIVE = true;
        if (rightArm.getCurrentPosition() > -1180 || rightArm.getCurrentPosition() < -1220){
            rightArm.setPower(1);
            rightArm.setTargetPosition(-1196);
            rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(1196);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            HANGING_MODE_ACTIVE = false;
            if (DPAD_PRESSED) {
                DPAD_PRESSED = false;
            } else {
                DPAD_PRESSED = true;
            }
        }
    }
    public static void loadingModeArm(){
        if (rightArm.getCurrentPosition() < -5 && arm2.getCurrentPosition() > 5) {
            got_position_to_hold = false;
            rightArm.setPower(1);
            rightArm.setTargetPosition(rightArm.getCurrentPosition() - rightArm.getCurrentPosition());
            rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(arm2.getCurrentPosition() - arm2.getCurrentPosition());
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LOADING_MODE_ACTIVE = true;
        } else {
            got_position_to_hold = false;
            rightArm.setPower(0);
            arm2.setPower(0);

            rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LOADING_MODE_ACTIVE = false;
        }
    }
    public static void brake(){
        if (!got_position_to_hold) {
            got_position_to_hold = true;
            hold_position1 = rightArm.getCurrentPosition();
            hold_position2 = arm2.getCurrentPosition();
        }
        if (!DPAD_PRESSED) {
            if (rightArm.getCurrentPosition() < -300 && !LOADING_MODE_ACTIVE) {
                rightArm.setPower(1);

                arm2.setPower(1);
                rightArm.setTargetPosition(hold_position1);
                rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                arm2.setTargetPosition(hold_position2);
                arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                rightArm.setPower(0);
                arm2.setPower(0);
            }
        }
        else {
            rightArm.setPower(1);

            arm2.setPower(1);
            rightArm.setTargetPosition(hold_position1);
            rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setTargetPosition(hold_position2);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public static void stopMoving() {
        rightArm.setPower(0);
        arm2.setPower(0);
    }

// Getting variables
    public static int getArm1Position() {
        return rightArm.getCurrentPosition();
    }
    public static int getArm2Position() {
        return arm2.getCurrentPosition();
    }
    public static boolean passedMinimalHoldPosition() {
        return rightArm.getCurrentPosition() >= MINIMAL_HOLD_POSITION;
    }
    public static boolean arrivedPosition (int current_position, int finish_position, boolean finish_bigger_than_start) {
        if (finish_bigger_than_start) {
            return current_position >= finish_position;
        }
        else {
            return current_position <= finish_position;
        }
    }

// Telemetry
    public static void addDataToTelemetry(Telemetry telemetry) {
        telemetry.addData("rightArm encoder", rightArm.getCurrentPosition());
        telemetry.addData("arm2 encoder", arm2.getCurrentPosition());
    }
}