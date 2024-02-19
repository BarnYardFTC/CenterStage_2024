package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

    static private DcMotor arm1;
    static private DcMotor arm2;

    static public final int MINIMAL_HOLD_POSITION = -300;

    static private boolean got_position_to_hold = false;

    static private int hold_position1 = 0;

    static public int ENCODER1 = 0;

    static public boolean HANGING_MODE_ACTIVE = false;

    static private boolean DPAD_PRESSED = false;

    static public boolean LOADING_MODE_ACTIVE = false;

    public static void init(DcMotor motor1, DcMotor motor2) {
        arm1 = motor1;
        arm2 = motor2;
        arm1.setDirection(DcMotorSimple.Direction.REVERSE);
        arm2.setDirection(DcMotorSimple.Direction.REVERSE);
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ENCODER1 = 0;
        DPAD_PRESSED = false;
        LOADING_MODE_ACTIVE = false;
        HANGING_MODE_ACTIVE = false;
    }
    public static void moveUp() {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() - 200);
        ENCODER1 -= 200;
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() + 200);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown() {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() + 200);
        ENCODER1 += 200;
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() - 200);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void hangingModeArm() {
        got_position_to_hold = false;

        HANGING_MODE_ACTIVE = true;
        DPAD_PRESSED = false;
        if (arm1.getCurrentPosition() > -1180 || arm1.getCurrentPosition() < -1220){
            arm1.setPower(1);
            arm1.setTargetPosition(-1196);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(1196);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            ENCODER1 = 1196;
            DPAD_PRESSED = true;
            HANGING_MODE_ACTIVE = false;
        }
    }
    public static void loadingModeArm(){
        got_position_to_hold = false;

        if (arm1.getCurrentPosition() < 0 && arm2.getCurrentPosition() > 0) {
            arm1.setPower(1);
            arm1.setTargetPosition(0);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(0);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LOADING_MODE_ACTIVE = true;
        } else {
            arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ENCODER1 = 0;
            LOADING_MODE_ACTIVE = false;
        }
    }
    public static void brake(){
        if (!got_position_to_hold) {
            got_position_to_hold = true;
            hold_position1 = arm1.getCurrentPosition();
        }
        if (!DPAD_PRESSED) {
            if (hold_position1 < -300) {
                arm1.setPower(1);
                arm2.setPower(0);
                arm1.setTargetPosition(hold_position1);
                arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                ENCODER1 = 0;
                arm1.setPower(0);
                arm2.setPower(0);
            }
        } else {
            arm1.setPower(1);
            arm2.setPower(0);
            arm1.setTargetPosition(arm1.getCurrentPosition());
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public static void stopMoving() {
        arm1.setPower(0);
        arm2.setPower(0);
    }
    public static void addDataToTelemetry(Telemetry telemetry) {
        telemetry.addData("arm1 position: ", arm1.getCurrentPosition());
        telemetry.addData("arm2 position: ", arm2.getCurrentPosition());
    }
    public static int getArm1Position() {
        return arm1.getCurrentPosition();
    }
    public static int getArm2Position() {
        return arm2.getCurrentPosition();
    }
    public static void resetEncoders() {
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
    public static double getPower() {
        return arm1.getPower();
    }
}