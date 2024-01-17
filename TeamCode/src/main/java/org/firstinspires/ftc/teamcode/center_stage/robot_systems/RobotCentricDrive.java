package org.firstinspires.ftc.teamcode.center_stage.robot_systems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Robot Centric Drive")
public class RobotCentricDrive extends LinearOpMode {

    DcMotor fr_wheel;
    DcMotor br_wheel;
    DcMotor bl_wheel;
    DcMotor fl_wheel;


    @Override
    public void runOpMode()  {
        fr_wheel = hardwareMap.get(DcMotor.class, "front right");
        br_wheel = hardwareMap.get(DcMotor.class, "back right");
        bl_wheel = hardwareMap.get(DcMotor.class, "back left");
        fl_wheel = hardwareMap.get(DcMotor.class, "front left");

        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        double powerY;
        double powerX;
        double powerRot;

        while(opModeIsActive()){
            powerY = -gamepad1.left_stick_y;
            powerX = -gamepad1.left_stick_x;
            powerRot = gamepad1.right_stick_x;

            fr_wheel.setPower(powerY + powerX + powerRot);
            fl_wheel.setPower(powerY - powerX - powerRot);
            br_wheel.setPower(powerY - powerX + powerRot);
            bl_wheel.setPower(powerY + powerX - powerRot);
        }

    }
}
