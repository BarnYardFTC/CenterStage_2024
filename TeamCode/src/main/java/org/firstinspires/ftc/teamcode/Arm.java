package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

    static private DcMotor arm1;
    static private DcMotor arm2;

    static private final double POWER = 1;

    static private final int SPEED = -150;

    static private final int SPEED2 = -SPEED;

    static public final int MINIMAL_HOLD_POSITION = -300;
    static private boolean got_position_to_hold = false;

    static private int target_position = 0;
    static private int target_position2 = 0;

    static private int hold_position1 = 0;

    static private int POSITION_EQUAL_DIFFERENCE = 30;

    static public boolean HANGING_MODE_ACTIVE = false;

    static public boolean LOADING_MODE_ACTIVE = false;

    static public boolean UNLOADING_MODE_MIN_ACTIVE = false;

    static public boolean UNLOADING_MODE_MAX_ACTIVE = false;

    public static void init(DcMotor motor1, DcMotor motor2) {
        arm1 = motor1;
        arm1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        arm2 = motor2;
        arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void moveUp() {

        got_position_to_hold = false;

        arm1.setPower(POWER);
        target_position = arm1.getCurrentPosition() + SPEED;
        arm1.setTargetPosition(target_position);
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(POWER);
        target_position2 = arm2.getCurrentPosition() + SPEED2;
        arm2.setTargetPosition(target_position2);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown() {
        got_position_to_hold = false;

        arm1.setPower(POWER);
        target_position = arm1.getCurrentPosition() - SPEED;
        arm1.setTargetPosition(target_position);
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(POWER);
        target_position2 = arm2.getCurrentPosition() - SPEED2;
        arm2.setTargetPosition(target_position2);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void brake(){

        if (!got_position_to_hold) {
            got_position_to_hold = true;
            hold_position1 = arm1.getCurrentPosition();
        }

        arm1.setPower(POWER);
        arm1.setTargetPosition(hold_position1);
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(0);
    }
    public static void hangingModeArm(boolean HANGING_MODE_ACTIVE) {
        if (arm1.getCurrentPosition() > -1180){
            arm1.setPower(0.6);
            arm1.setTargetPosition(-1200);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(0.6);
            arm2.setTargetPosition(1200);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (arm1.getCurrentPosition() < -1220) {
            arm1.setPower(0.6);
            arm1.setTargetPosition(-1200);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(0.6);
            arm2.setTargetPosition(1200);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            brake();
        }
    }
    public static void loadingModeArm(boolean LOADING_MODE_ACTIVE){
        if (arm1.getCurrentPosition() < 0) {
            arm1.setPower(1);
            arm1.setTargetPosition(0 - (arm1.getCurrentPosition() + arm2.getCurrentPosition()));
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(0 - (arm1.getCurrentPosition() + arm2.getCurrentPosition()));
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);;
        } else {
            Arm.stopMoving();
        }
    }
//    public static void unloadingModeMinArm(boolean UNLOADING_MODE_MIN_ACTIVE){
//        if (arm1.getCurrentPosition() < 0) {
//            arm1.setPower(1);
//            arm1.setTargetPosition(0 - (arm1.getCurrentPosition() + arm2.getCurrentPosition()));
//            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            arm2.setPower(1);
//            arm2.setTargetPosition(0 - (arm1.getCurrentPosition() + arm2.getCurrentPosition()));
//            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);;
//        } else {
//            Arm.stopMoving();
//        }
//    }
//    public static void unloadingModeMaxArm(boolean UNLOADING_MODE_MAX_ACTIVE){
//        if (arm1.getCurrentPosition() < 0) {
//            arm1.setPower(1);
//            arm1.setTargetPosition(0 - (arm1.getCurrentPosition() + arm2.getCurrentPosition()));
//            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            arm2.setPower(1);
//            arm2.setTargetPosition(0 - (arm1.getCurrentPosition() + arm2.getCurrentPosition()));
//            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);;
//        } else {
//            Arm.stopMoving();
//        }
//    }
    public static boolean passedMinimalHoldPosition() {
        return arm1.getCurrentPosition() < MINIMAL_HOLD_POSITION;
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
    public static boolean arrivedPosition (int current_position, int finish_position, boolean finish_bigger_than_start) {
        if (finish_bigger_than_start) {
            return current_position >= finish_position;
        }
        else {
            return current_position <= finish_position;
        }
    }


}