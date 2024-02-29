package org.firstinspires.ftc.teamcode;

// Imports

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

// Variables
    static private DcMotor arm1;
    static private DcMotor arm2;
    static public final int MINIMAL_HOLD_POSITION = -300;
    static private double SPEED;
    static private boolean got_position_to_hold = false;
    static private int hold_position1 = 0;
    static private int hold_position2 = 0;
    static public boolean HANGING_MODE_ACTIVE = false;
    static private boolean DPAD_PRESSED = false;
    static public boolean LOADING_MODE_ACTIVE = false;

// Initializing
    public static void init(DcMotor motor1, DcMotor motor2) {
        arm1 = motor1;
        arm2 = motor2;

        arm1.setDirection(DcMotorSimple.Direction.REVERSE);
        arm2.setDirection(DcMotorSimple.Direction.REVERSE);

        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DPAD_PRESSED = false;
        LOADING_MODE_ACTIVE = false;
        HANGING_MODE_ACTIVE = false;
        SPEED = 0;
    }

// System's functions
    public static void moveUp(double SPEED) {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() - (int) (380 * SPEED));
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() + (int) (380 * SPEED));
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown(double SPEED) {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() + (int) (380 * SPEED));
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() - (int) (380 * SPEED));
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void hangingModeArm() {
        got_position_to_hold = false;

        HANGING_MODE_ACTIVE = true;
        if (arm1.getCurrentPosition() > -1180 || arm1.getCurrentPosition() < -1220){
            arm1.setPower(1);
            arm1.setTargetPosition(-1196);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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
        if (arm1.getCurrentPosition() < -5 && arm2.getCurrentPosition() > 5) {
            got_position_to_hold = false;
            arm1.setPower(1);
            arm1.setTargetPosition(0);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(0);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LOADING_MODE_ACTIVE = true;
        } else {
            got_position_to_hold = false;
            arm1.setPower(0);
            arm2.setPower(0);

            arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LOADING_MODE_ACTIVE = false;
        }
    }
    public static void brake(){
        if (!got_position_to_hold) {
            got_position_to_hold = true;
            hold_position1 = arm1.getCurrentPosition();
            hold_position2 = arm2.getCurrentPosition();
        }
        if (!DPAD_PRESSED) {
            if (arm1.getCurrentPosition() < -300) {
                arm1.setPower(1);

                arm2.setPower(1);
                arm1.setTargetPosition(hold_position1);
                arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                arm2.setTargetPosition(hold_position2);
                arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                arm1.setPower(0);
                arm2.setPower(0);
            }
        }
        else {
            arm1.setPower(1);

            arm2.setPower(1);
            arm1.setTargetPosition(hold_position1);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setTargetPosition(hold_position2);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public static void stopMoving() {
        arm1.setPower(0);
        arm2.setPower(0);
    }

// Getting variables
    public static int getArm1Position() {
        return arm1.getCurrentPosition();
    }
    public static int getArm2Position() {
        return arm2.getCurrentPosition();
    }
    public static boolean passedMinimalHoldPosition() {
        return arm1.getCurrentPosition() >= MINIMAL_HOLD_POSITION;
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
        telemetry.addData("arm1 encoder", arm1.getCurrentPosition());
        telemetry.addData("arm2 encoder", arm2.getCurrentPosition());
    }
}