package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
public class EgnitionSystem {

    static private DcMotor fl_wheel;
    static private DcMotor fr_wheel;
    static private DcMotor bl_wheel;
    static private DcMotor br_wheel;
    static private IMU imu;

    public static void init(DcMotor fl_wheel, DcMotor fr_wheel, DcMotor bl_wheel, DcMotor br_wheel, IMU imu) {
        EgnitionSystem.fl_wheel = fl_wheel;
        EgnitionSystem.fr_wheel = fr_wheel;
        EgnitionSystem.bl_wheel = bl_wheel;
        EgnitionSystem.br_wheel = br_wheel;

        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        br_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        EgnitionSystem.imu = imu;

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();
    }
    public static void run(Gamepad gamepad1, Telemetry telemetry) {
        double lx = gamepad1.left_stick_x;
            double ly = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            double max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

            double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

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

            telemetry.addLine("Press B to reset robot's head direction");
    }


//    @Override
//    public void runOpMode() {
//
//        fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
//        fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
//        bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
//        br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
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
//        waitForStart();
//
//        while (opModeIsActive()) {
//            double lx = gamepad1.left_stick_x;
//            double ly = -gamepad1.left_stick_y;
//            double rx = gamepad1.right_stick_x;
//
//            double max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);
//
//            double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//
//            double adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
//            double adjustedLy = ly  * Math.cos(heading) + lx * Math.sin(heading);
//
//            double power = 0.2 + (0.6 * gamepad1.right_trigger);
//
//            if (gamepad1.b) {
//                imu.resetYaw();
//            }
//
//            fl_wheel.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
//            bl_wheel.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
//            fr_wheel.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
//            br_wheel.setPower(((adjustedLy + adjustedLx - rx) / max) * power);
//
//            telemetry.addLine("Press B to reset robot's head direction");
//        }


}