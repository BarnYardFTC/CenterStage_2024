package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class driveTest extends LinearOpMode {
    DcMotor fl_wheel;
    DcMotor fr_wheel;
    DcMotor bl_wheel;
    DcMotor br_wheel;

    final double POWER = 0.5;

    private static final class FORWARD_TARGET_POSITIONS {
        static int fl_wheel = 1500;
        static int fr_wheel = 1500;
        static int bl_wheel = 1500;
        static int br_wheel = 1500;

    }
    private static final class RIGHT_TARGET_POSITIONS {
        static int fl_wheel = 500;
        static int fr_wheel = -500;
        static int bl_wheel = -500;
        static int br_wheel = 500;
    }
    private static final class ROTATE_RIGHT_TARGET_POSITIONS {
        static int fl_wheel = 500;
        static int fr_wheel = -500;
        static int bl_wheel = 500;
        static int br_wheel = -500;
    }

    private enum test{
        FORWARD,
        RIGHT,
        ROTATE_RIGHT
    }
    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();
        boolean choice_made = false;
        test choice = test.FORWARD;
        fl_wheel.setPower(POWER);
        fr_wheel.setPower(POWER);
        bl_wheel.setPower(POWER);
        br_wheel.setPower(POWER);
        telemetry.addLine("Chose test:");
        telemetry.addData("X:", "Forward");
        telemetry.addData("Y:", "Right");
        telemetry.addData("A:", "Rotate right");
        telemetry.update();
        while (opModeIsActive() && !choice_made) {
            if (gamepad1.x) {
                choice = test.FORWARD;
                choice_made = true;
            }
            else if (gamepad1.y) {
                choice = test.RIGHT;
                choice_made = true;
            }
            else if (gamepad1.a) {
                choice = test.ROTATE_RIGHT;
                choice_made = true;
            }
        }
        telemetry.clear();
        telemetry.update();
        if (choice == test.FORWARD) {
            while (opModeIsActive()) {
                fl_wheel.setTargetPosition(FORWARD_TARGET_POSITIONS.fl_wheel);
                fr_wheel.setTargetPosition(FORWARD_TARGET_POSITIONS.fr_wheel);
                bl_wheel.setTargetPosition(FORWARD_TARGET_POSITIONS.bl_wheel);
                br_wheel.setTargetPosition(FORWARD_TARGET_POSITIONS.br_wheel);

                runToPosition(fl_wheel, POWER);
                runToPosition(fr_wheel, POWER);
                runToPosition(bl_wheel, POWER);
                runToPosition(br_wheel, POWER);
            }
        }
        else if (choice == test.RIGHT) {
            while (opModeIsActive()) {
                fl_wheel.setTargetPosition(RIGHT_TARGET_POSITIONS.fl_wheel);
                fr_wheel.setTargetPosition(RIGHT_TARGET_POSITIONS.fr_wheel);
                bl_wheel.setTargetPosition(RIGHT_TARGET_POSITIONS.bl_wheel);
                br_wheel.setTargetPosition(RIGHT_TARGET_POSITIONS.br_wheel);

                fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
        else {
            while (opModeIsActive()) {
                fl_wheel.setTargetPosition(ROTATE_RIGHT_TARGET_POSITIONS.fl_wheel);
                fr_wheel.setTargetPosition(ROTATE_RIGHT_TARGET_POSITIONS.fr_wheel);
                bl_wheel.setTargetPosition(ROTATE_RIGHT_TARGET_POSITIONS.bl_wheel);
                br_wheel.setTargetPosition(ROTATE_RIGHT_TARGET_POSITIONS.br_wheel);

                fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
    }
    public void initialize() {
        fl_wheel = hardwareMap.get(DcMotor.class, "leftFront");
        fr_wheel = hardwareMap.get(DcMotor.class, "rightFront");
        bl_wheel = hardwareMap.get(DcMotor.class, "leftBack");
        br_wheel = hardwareMap.get(DcMotor.class, "rightBack");

        fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        fl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runToPosition(DcMotor motor, double starting_power) {
        if (motor.getTargetPosition() > 0) {
            if (starting_power > 0.4 && motor.getPower() > 0.1 && motor.getCurrentPosition() >= motor.getTargetPosition() - 500) {
                motor.setPower(motor.getPower()-0.1);
            }
            else {
                motor.setPower(starting_power);
            }
        }
        else {
            if (starting_power > 0.4 && motor.getPower() > 0.1 && motor.getCurrentPosition() <= motor.getTargetPosition() + 500) {
                motor.setPower(motor.getPower()-0.1);
            }
            else {
                motor.setPower(starting_power);
            }
        }
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
