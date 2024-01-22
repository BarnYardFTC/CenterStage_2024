package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

    static private DcMotor arm;
    static private final double POWER = 0.2;
    static private final int SPEED = 60;
    static private final int MAXIMAL_POSITION = 900;
    static private final int MINIMAL_HOLD_POSITION = 20;
    static private boolean got_position_to_hold = false;
    static private int target_position = 0;

    public static void init(DcMotor motor) {
        arm = motor;
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void moveUp() {
        arm.setPower(POWER);
        got_position_to_hold = false;
        target_position = arm.getCurrentPosition() + SPEED;
        arm.setTargetPosition(target_position);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown() {
        arm.setPower(POWER);
        got_position_to_hold = false;
        target_position = arm.getCurrentPosition() - SPEED;
        arm.setTargetPosition(target_position);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void brake(){
        arm.setPower(POWER);
        if (!got_position_to_hold) {
            got_position_to_hold = true;
        }
        arm.setTargetPosition(target_position);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static boolean passedMaximalPosition() {
        return arm.getCurrentPosition() > MAXIMAL_POSITION;
    }
    public static boolean passedMinimalHoldPosition() {
        return arm.getCurrentPosition() < MINIMAL_HOLD_POSITION;
    }
    public static void returnToMaximalPosition() {
        target_position = arm.getCurrentPosition() - SPEED;
        arm.setTargetPosition(target_position);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void stopMoving() {
        arm.setPower(0);
    }

    public static void addDataToTelemetry(Telemetry telemetry) {
        telemetry.addData("Arm position: ", arm.getCurrentPosition());
    }


}