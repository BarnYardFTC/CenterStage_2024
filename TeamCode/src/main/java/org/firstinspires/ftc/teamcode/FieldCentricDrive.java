package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class FieldCentricDrive extends LinearOpMode {


    DcMotor fl_wheel;
    DcMotor fr_wheel;
    DcMotor bl_wheel;
    DcMotor br_wheel;
    IMU imu;

    @Override
    public void runOpMode() {

        fl_wheel = hardwareMap.get(DcMotor.class, "front left");
        fr_wheel = hardwareMap.get(DcMotor.class, "front right");
        bl_wheel = hardwareMap.get(DcMotor.class, "back left");
        br_wheel = hardwareMap.get(DcMotor.class, "back right");

        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                double lx = gamepad1.left_stick_x;
                double ly = -gamepad1.left_stick_y;
                double rx = gamepad1.right_stick_x;

                double max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

                telemetry.addData("Yaw", "Press B on Gamepad to reset.");

                double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                telemetry.addData("Yaw (Z)", JavaUtil.formatNumber(heading, 2));
                telemetry.update();

                double adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
                double adjustedLy = ly  * Math.cos(heading) + lx * Math.sin(heading);

                double power = 0.2 + (0.6 * gamepad1.right_trigger);

                if (gamepad1.b) {
                    imu.resetYaw();
                }

                fl_wheel.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
                bl_wheel.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
                fr_wheel.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
                br_wheel.setPower(((adjustedLy + adjustedLx - rx) / max) * power);

            }
        }
    }
}