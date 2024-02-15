package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

    private static DcMotor arm1;
    private static DcMotor arm2;

    public static final int MINIMAL_HOLD_POSITION = -300;
    public static final int HANGING_MODE_POSITION = 1200;

    public static final double POWER = 0.3;
    public static final double FAST_POWER = 0.7;

    public static final int MULTIPLY = 30;

    public static int UP_SPEED = 0;
    public static int DOWN_SPEED = 0;

    public static void init(DcMotor motor1, DcMotor motor2) {
        arm1 = motor1;
        arm2 = motor2;

        arm1.setDirection(DcMotorSimple.Direction.REVERSE);
        enableEncoders();
    }
    public static void moveUp() {

        arm1.setPower(POWER);
        arm2.setPower(POWER);

        arm1.setTargetPosition(arm1.getCurrentPosition()+UP_SPEED);
        arm2.setTargetPosition(arm2.getCurrentPosition()+UP_SPEED);

        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown() {

        arm1.setPower(POWER);
        arm2.setPower(POWER);

        arm1.setTargetPosition(arm1.getCurrentPosition()-DOWN_SPEED);
        arm2.setTargetPosition(arm2.getCurrentPosition()-DOWN_SPEED);

        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void brake(){
        arm1.setPower(0);
        arm2.setPower(0);
        arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public static void HangingMode() {

        if (arm1.getCurrentPosition() < HANGING_MODE_POSITION) {
            moveUp();
        }
        else if (arm1.getCurrentPosition() > HANGING_MODE_POSITION) {
            moveDown();
        }
        else {
            arm1.setPower(0);
            arm2.setPower(0);

            arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    public static void loadingMode(){
        disableEncoders();
        arm1.setPower(-FAST_POWER);
        arm2.setPower(-FAST_POWER);
    }
    public static boolean inStopPosition() {
        return arm1.getCurrentPosition() < MINIMAL_HOLD_POSITION;
    }
    public static void stopMoving() {
        disableEncoders();
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
    public static void enableEncoders() {
        arm1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void disableEncoders() {
        arm1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static void setSPEED(double right_trigger, double left_trigger) {
        UP_SPEED = (int) right_trigger * MULTIPLY;
        DOWN_SPEED = (int) left_trigger * MULTIPLY;
    }
}