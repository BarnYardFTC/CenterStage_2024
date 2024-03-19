package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmTeleop {

    static private DcMotor arm1;
    static private DcMotor arm2;

    static private final double POWER = 1;

    static private final double SPEED = -80;

    static private final double SPEED2 = -1 * SPEED;

    static public final int MAXIMAL_POSITION = -900;
    static public final int MINIMAL_HOLD_POSITION = -200;
    static private boolean got_position_to_hold = false;

    static private double target_position = 0;
    static private double target_position2 = 0;

    static private int hold_position1 = 0;
    static private int hold_position2 = 0;

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
        arm1.setTargetPosition((int) (arm1.getCurrentPosition() + SPEED));
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(POWER);
        arm2.setTargetPosition((int) (arm2.getCurrentPosition() + SPEED2));
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void moveDown() {
        got_position_to_hold = false;

        arm1.setPower(POWER);
        arm1.setTargetPosition((int) (arm1.getCurrentPosition() - SPEED));
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm2.setPower(POWER);
        arm2.setTargetPosition((int) (arm2.getCurrentPosition() - SPEED2));
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void brake(){
        if (!got_position_to_hold) {
            got_position_to_hold = true;
            hold_position1 = arm1.getCurrentPosition();
            hold_position2 = arm2.getCurrentPosition();
        }
        if (passedMinimalHoldPosition()) {
            arm1.setPower(POWER);
            arm1.setTargetPosition(hold_position1);
            arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            arm2.setPower(POWER);
            arm2.setTargetPosition(hold_position2);
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else {
            stopMoving();
        }
    }
    public static boolean passedMaximalPosition() {
        return arm1.getCurrentPosition() > MAXIMAL_POSITION;
    }
    public static boolean passedMinimalHoldPosition() {
        return arm1.getCurrentPosition() < MINIMAL_HOLD_POSITION;
    }
    public static void returnToMaximalPosition() {
        if (passedMaximalPosition()) {
            moveDown();
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


}