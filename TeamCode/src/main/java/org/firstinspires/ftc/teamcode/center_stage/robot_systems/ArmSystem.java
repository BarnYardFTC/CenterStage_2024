package org.firstinspires.ftc.teamcode.center_stage.robot_systems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ArmProgram")
public class ArmSystem extends LinearOpMode {
    DcMotor arm;
    final int ARM_UP_POSITION = 700;
    final double ARM_POWER = 0.2;

    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();

        // ARM POSITION: 0 = down | 1 = up | 2 = moving up | moving down = 3
        int arm_position = 0;

        while (opModeIsActive()) {

            if (gamepad1.dpad_up && arm_position == 0) {
                arm.setTargetPosition(ARM_UP_POSITION);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm_position = 2;
            }
            if (!gamepad1.dpad_up && arm_position == 2) {
                arm_position = 1;
            }
            if (arm_position == 1) {
                holdMotorPosition(arm, ARM_UP_POSITION);
            }

            if (gamepad1.dpad_down && arm_position == 1) {
                arm.setTargetPosition(0);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm_position = 3;
            }
            if (!gamepad1.dpad_down && arm_position == 3) {
                arm_position = 0;
            }
        }
    }

    public void holdMotorPosition(DcMotor motor, int position) {
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}