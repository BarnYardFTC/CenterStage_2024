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

    static private double max = 1;
    static private double lx = 0;
    static private double ly = 0;
    static private double rx = 0;
    static private double heading = 0;
    static private double adjustedLx = 0;
    static private double adjustedLy = 0;
    static private double power = 0;

    static private final double AUTONOMOUS_MOVING_POWER = 0.5;

    public static void init(DcMotor fl_wheel, DcMotor fr_wheel, DcMotor bl_wheel, DcMotor br_wheel, IMU imu) {
        EgnitionSystem.fl_wheel = fl_wheel;
        EgnitionSystem.fr_wheel = fr_wheel;
        EgnitionSystem.bl_wheel = bl_wheel;
        EgnitionSystem.br_wheel = br_wheel;

        EgnitionSystem.bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.br_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        EgnitionSystem.imu = imu;

        EgnitionSystem.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        EgnitionSystem.imu.resetYaw();
    }
    public static void runTeleop(Gamepad gamepad1, Telemetry telemetry) {

        fl_wheel.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
        bl_wheel.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
        fr_wheel.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
        br_wheel.setPower(((adjustedLy + adjustedLx - rx) / max) * power);

        telemetry.addLine("Press B to reset robot's head direction");
        if (gamepad1.b) {
            imu.resetYaw();
        }
    }

    public static void updateVariablesTeleop(Gamepad gamepad1) {
        lx = gamepad1.left_stick_x;
        ly =-gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;

        max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
        adjustedLy = ly  * Math.cos(heading) + lx * Math.sin(heading);

        power = 0.2 + (0.6 * gamepad1.right_trigger);
    }
    public static void setHorizontalPower(double power) {
        lx = power;
    }
    public static void setVerticalPower(double power) {
        ly = power;
    }
    public static void setRotPower(double power){
        rx = power;
    }
    public static void updateVariablesAutonomous() {
        max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        adjustedLx = ly * Math.sin(heading) + lx * Math.cos(heading);
        adjustedLy = ly  * Math.cos(heading) + lx * Math.sin(heading);
    }
    public static void runAutonomous() {

        setPowerEncoders(fl_wheel, ((adjustedLy + adjustedLx + rx)) * AUTONOMOUS_MOVING_POWER);
        setPowerEncoders(bl_wheel, ((adjustedLy - adjustedLx + rx) / max) * AUTONOMOUS_MOVING_POWER);
        setPowerEncoders(fr_wheel, ((adjustedLy - adjustedLx - rx) / max) * AUTONOMOUS_MOVING_POWER);
        setPowerEncoders(br_wheel, ((adjustedLy + adjustedLx - rx) / max) * AUTONOMOUS_MOVING_POWER);

    }

    private static void setPowerEncoders(DcMotor motor, double power) {
        motor.setTargetPosition((int) (motor.getCurrentPosition() + power));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void initEncoders() {
        fl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


}