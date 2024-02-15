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

    static public boolean HANGING_MODE_ACTIVE1 = false;

    static public boolean HANGING_MODE_ACTIVE2 = false;

    static public boolean LOADING_MODE_ACTIVE = false;

    static public int ENCODER1 = 0;
    static public int ENCODER2 = 0;

    public static void init(DcMotor motor1, DcMotor motor2) {
        arm1 = motor1;
        arm2 = motor2;
        arm1.setDirection(DcMotorSimple.Direction.REVERSE);
        arm2.setDirection(DcMotorSimple.Direction.REVERSE);
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void moveUp() {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() - 350);
        ENCODER1 -= 350;
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() + 350);
        ENCODER2 += 350;
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown() {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() + 300);
        ENCODER1 += 300;
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() - 300);
        ENCODER2 -= 300;
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void brake(){
        if (!got_position_to_hold) {
            got_position_to_hold = true;
            hold_position1 = arm1.getCurrentPosition();
        }

        if (ENCODER1 < -300 && ENCODER1 / 100 == arm1.getCurrentPosition() / 100) {
            arm1.setPower(1);
            arm1.setTargetPosition(hold_position1);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm2.setPower(0);
        }
    }
    public static void hangingModeArm1() {
        if (ENCODER1 > -1180){
            arm1.setPower(1);
            arm1.setTargetPosition(-1196);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(1196);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (ENCODER1 < -1220) {
            arm1.setPower(1);
            arm1.setTargetPosition(-1196);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(1196);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            brake();
            ENCODER1 = -1200;
            ENCODER2 = 1200;
            HANGING_MODE_ACTIVE2 = true;
        }
    }
    public static void hangingModeArm2() {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() + 80);
        ENCODER1 += 80;
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() - 80);
        ENCODER2 -= 80;
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void hangingModeArm3() {
        got_position_to_hold = false;

        arm1.setPower(1);
        arm1.setTargetPosition(arm1.getCurrentPosition() - 80);
        ENCODER1 -= 80;
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(1);
        arm2.setTargetPosition(arm2.getCurrentPosition() + 80);
        ENCODER2 += 80;
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void loadingModeArm(){
        if (ENCODER1 < 0) {
            arm1.setPower(1);
            arm1.setTargetPosition(0);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(1);
            arm2.setTargetPosition(0);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);;
        } else {
            arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Arm.stopMoving();
            ENCODER1 = 0;
            ENCODER2 = 0;
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
}