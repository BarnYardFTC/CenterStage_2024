package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

    private static DcMotor arm1;
    private static DcMotor arm2;

    public static final int MINIMAL_HOLD_POSITION = -300;

    public static final double POWER = 1;

    public static int UP_SPEED = -50;
    public static int DOWN_SPEED = 50;

    public static boolean HANGING_MODE = false;
    public static boolean LOADING_MODE = false;

    public static void init(DcMotor motor1, DcMotor motor2) {
        arm1 = motor1;
        arm2 = motor2;

        enableEncoders();
    }
    public static void moveUp() {

        enableEncoders();

        arm1.setPower(POWER);
        arm2.setPower(POWER);

        arm1.setTargetPosition(arm1.getCurrentPosition() + UP_SPEED);
        arm2.setTargetPosition(arm2.getCurrentPosition() - UP_SPEED);

        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void moveDown() {

        enableEncoders();

        arm1.setPower(POWER);
        arm2.setPower(POWER);

        arm1.setTargetPosition(arm1.getCurrentPosition() + DOWN_SPEED);
        arm2.setTargetPosition(arm2.getCurrentPosition() - DOWN_SPEED);

        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void brake() {
        enableEncoders();
        if (arm1.getCurrentPosition() > -1400) {
            arm1.setPower(0);
            arm2.setPower(0);
            arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    public static void hangingMode() {
        enableEncoders();

        if (arm1.getCurrentPosition() < -1220) {
            HANGING_MODE = true;
            arm1.setPower(POWER);
            arm2.setPower(POWER);
            arm1.setTargetPosition(-1200);
            arm2.setTargetPosition(1200);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (arm1.getCurrentPosition() > -1180) {
            HANGING_MODE = true;
            arm1.setPower(POWER);
            arm2.setPower(POWER);
            arm1.setTargetPosition(-1200);
            arm2.setTargetPosition(1200);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            HANGING_MODE = false;
            brake();
        }
    }
    public static void loadingMode(){
        enableEncoders();
        if (arm1.getCurrentPosition() < 0) {
            LOADING_MODE = true;
            arm1.setPower(POWER);
            arm2.setPower(POWER);
            arm1.setTargetPosition(10);
            arm2.setTargetPosition(-10);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            LOADING_MODE = false;
            Arm.stopMoving();
        }
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
    public static void resetEncoders() {
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
//    public static void setSPEED(double right_trigger, double left_trigger) {
//        UP_SPEED = (int) right_trigger * MULTIPLY;
//        DOWN_SPEED = (int) left_trigger * MULTIPLY;
//    }
}