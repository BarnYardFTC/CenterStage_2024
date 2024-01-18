package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "CenterStage TeleOp")
public class DriverControlledPeriod extends LinearOpMode {

//
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor arm2 = hardwareMap.get(DcMotor.class, "arm");

        Arm arm = new Arm(arm2);

        arm.addDataToTelemetry(telemetry);
        telemetry.addLine("Program updated");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.dpad_up) {
                arm.moveUp();
            }
            else if (gamepad1.dpad_down) {
                arm.moveDown();
            }
            else {
                arm.brake();
            }
        }
    }




//    Servo left_claw;
//    Servo right_claw;
//    final double LEFT_CLAW_CLOSED_POSITION = 0.25;
//    final double LEFT_CLAW_OPENED_POSITION = 0.3;
//    final double RIGHT_CLAW_CLOSED_POSITION = 0.3;
//    final double RIGHT_CLAW_OPENED_POSITION = 0;
//
//    DcMotor bl_wheel;
//    DcMotor br_wheel;
//    DcMotor fl_wheel;
//    DcMotor fr_wheel;
//
//    IMU imu;
//
//    DcMotor arm;
//    final int ARM_UP_POSITION = 700;
//    final double ARM_POWER = 0.2;
//
//    Servo wrist;
//    final double WRIST_UP_POSITION = 0.4;
//    final double WRIST_DOWN_POSITION = 0.07;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
//        br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
//        fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
//        fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
//
//        wrist = hardwareMap.get(Servo.class, "wrist");
//
//        left_claw = hardwareMap.get(Servo.class, "left_claw");
//        right_claw = hardwareMap.get(Servo.class, "right_claw");
//
//        arm = hardwareMap.get(DcMotor.class, "arm");
//        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
//        br_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        imu = hardwareMap.get(IMU.class, "imu");
//
//        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
//        imu.resetYaw();
//
//
//        waitForStart();
//
//
//        // 0 = down | 1 = up | 2 = moving down | 3 = moving up
//        int wrist_position = 0;
//
//        // 0 = closed | 1 = open | 2 moving to close | 3 moving to open
//        int right_claw_position = 1;
//        int left_claw_position = 1;
//
//        // ARM POSITION: 0 = down | 1 = up | 2 = moving up | moving down = 3
//        int arm_position = 0;
//
//        arm.setPower(ARM_POWER);
//
//        left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
//        right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
//
//        wrist.setPosition(WRIST_DOWN_POSITION);
//
//        while (opModeIsActive()) {
//            // DRIVE
//            // -------------------------------------------------------------------------------------
//            double lx = gamepad1.left_stick_x;
//            double ly = -gamepad1.left_stick_y;
//            double rx = gamepad1.right_stick_x;
//
//            double max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);
//
//            telemetry.addLine("Press B on Gamepad to set new center (for the imu)");
//            if (gamepad1.b) {
//                imu.resetYaw();
//            }
//
//            double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//
//            telemetry.addData("Yaw (Z)", JavaUtil.formatNumber(heading, 2));
//
//            double adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
//            double adjustedLy = ly * Math.cos(heading) + lx * Math.sin(heading);
//
//            double power = 0.2 + (0.6 * gamepad1.right_trigger);
//
//            fl_wheel.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
//            bl_wheel.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
//            fr_wheel.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
//            br_wheel.setPower(((adjustedLy + adjustedLx - rx) / max) * power);
//            // -------------------------------------------------------------------------------------
//
//            // WRIST
//            // -------------------------------------------------------------------------------------
//            if (gamepad1.y && wrist_position == 0) {
//                wrist.setPosition(WRIST_UP_POSITION);
//                wrist_position = 3;
//            }
//            if (gamepad1.y && wrist_position == 1) {
//                wrist.setPosition(WRIST_DOWN_POSITION);
//                wrist_position = 2;
//            }
//            if (!gamepad1.y && wrist_position == 3) {
//                wrist_position = 1;
//            }
//            if (!gamepad1.y && wrist_position == 2) {
//                wrist_position = 0;
//            }
//            // -------------------------------------------------------------------------------------
//
//            // CLAWS
//            // -------------------------------------------------------------------------------------
//            if (gamepad1.right_bumper && right_claw_position == 1) {
//                right_claw.setPosition(RIGHT_CLAW_CLOSED_POSITION);
//                right_claw_position = 2;
//            }
//            if (gamepad1.right_bumper && right_claw_position == 0) {
//                right_claw.setPosition(RIGHT_CLAW_OPENED_POSITION);
//                right_claw_position = 3;
//            }
//            if (!gamepad1.right_bumper && right_claw_position == 2) {
//                right_claw_position = 0;
//            }
//            if (!gamepad1.right_bumper && right_claw_position == 3) {
//                right_claw_position = 1;
//            }
//
//            if (gamepad1.left_bumper && left_claw_position == 1) {
//                left_claw.setPosition(LEFT_CLAW_CLOSED_POSITION);
//                left_claw_position = 2;
//            }
//            if (gamepad1.left_bumper && left_claw_position == 0) {
//                left_claw.setPosition(LEFT_CLAW_OPENED_POSITION);
//                left_claw_position = 3;
//            }
//            if (!gamepad1.left_bumper && left_claw_position == 2) {
//                left_claw_position = 0;
//            }
//            if (!gamepad1.left_bumper && left_claw_position == 3) {
//                left_claw_position = 1;
//            }
//            // -------------------------------------------------------------------------------------
//
//            // ARM
//            // -------------------------------------------------------------------------------------
//            if (gamepad1.dpad_up && arm_position == 0) {
//                arm.setTargetPosition(ARM_UP_POSITION);
//                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                arm_position = 2;
//            }
//            if (!gamepad1.dpad_up && arm_position == 2) {
//                arm_position = 1;
//            }
//            if (arm_position == 1) {
//                holdMotorPosition(arm, ARM_UP_POSITION);
//            }
//
//            if (gamepad1.dpad_down && arm_position == 1) {
//                arm.setTargetPosition(0);
//                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                arm_position = 3;
//            }
//            if (!gamepad1.dpad_down && arm_position == 3) {
//                arm_position = 0;
//            }
//            // -------------------------------------------------------------------------------------
//
//            telemetry.addData("left claw", left_claw.getPosition());
//            telemetry.addData("right claw", right_claw.getPosition());
//            telemetry.update();
//        }
//
//    }
//
//    public void holdMotorPosition(DcMotor motor, int position) {
//        motor.setTargetPosition(position);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//    }
}