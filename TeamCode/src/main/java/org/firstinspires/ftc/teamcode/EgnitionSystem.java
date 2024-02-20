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
    static private double max;
    static private double lx;
    static private double ly;
    static private double rx;
    static private double heading;
    static private double adjustedLx;
    static private double adjustedLy;
    static public double power;

    static private final double AUTONOMOUS_MOVING_POWER_ORIGINAL = 0.4;

    static private double AUTONOMOUS_MOVING_POWER = AUTONOMOUS_MOVING_POWER_ORIGINAL;
    static private final int ENCODER_CHANGING_SPEED = 1000;

    public static void init(DcMotor fl_wheel, DcMotor fr_wheel, DcMotor bl_wheel, DcMotor br_wheel, IMU imu) {
        EgnitionSystem.fl_wheel = fl_wheel;
        EgnitionSystem.fr_wheel = fr_wheel;
        EgnitionSystem.bl_wheel = bl_wheel;
        EgnitionSystem.br_wheel = br_wheel;

        EgnitionSystem.bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.br_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        EgnitionSystem.imu = imu;

        EgnitionSystem.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        EgnitionSystem.imu.resetYaw();

        max = 1;
        lx = 0;
        ly = 0;
        rx = 0;
        heading = 0;
        adjustedLx = 0;
        adjustedLy = 0;
        power = 1;
        AUTONOMOUS_MOVING_POWER = AUTONOMOUS_MOVING_POWER_ORIGINAL;
    }
    public static void runTeleop1() {
        fl_wheel.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
        bl_wheel.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
        fr_wheel.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
        br_wheel.setPower(((adjustedLy + adjustedLx - rx) / max) * power);
    }
    public static void runTeleop2() {
        fl_wheel.setPower(((adjustedLy + adjustedLx + rx) / max) * 0.3 * power);
        bl_wheel.setPower(((adjustedLy - adjustedLx + rx) / max) * 0.3 * power);
        fr_wheel.setPower(((adjustedLy - adjustedLx - rx) / max) * 0.3 * power);
        br_wheel.setPower(((adjustedLy + adjustedLx - rx) / max) * 0.3 * power);
    }
    public static void updateVariablesTeleop(Gamepad gamepad1, Telemetry telemetry) {
        lx = gamepad1.left_stick_x;
        ly = -gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;

        max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
        adjustedLy = ly  * Math.cos(heading) + lx * Math.sin(heading);

        telemetry.addLine("Press B to reset robot's head direction");
        telemetry.addData("heading: ", heading);
        telemetry.addData("adjusted ly ", adjustedLy);
        telemetry.addData("adjusted lx ", adjustedLx);
        if (gamepad1.b) {
            imu.resetYaw();
        }
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

        adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
        adjustedLy = ly  * Math.cos(heading) + lx * Math.sin(heading);

    }
    public static void runAutonomous() {

        setPowerEncoders(fl_wheel, ((adjustedLy + adjustedLx + rx) / max) * AUTONOMOUS_MOVING_POWER);
        setPowerEncoders(bl_wheel, ((adjustedLy - adjustedLx + rx) / max) * AUTONOMOUS_MOVING_POWER);
        setPowerEncoders(fr_wheel, ((adjustedLy - adjustedLx - rx) / max) * AUTONOMOUS_MOVING_POWER);
        setPowerEncoders(br_wheel, ((adjustedLy + adjustedLx - rx) / max) * AUTONOMOUS_MOVING_POWER);
    }

    private static void setPowerEncoders(DcMotor motor, double power) {
        motor.setPower(power);
        motor.setTargetPosition((int) (motor.getCurrentPosition() + power*ENCODER_CHANGING_SPEED));
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
    public static void resetEncoders() {
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static int getFlEncoderPosition() {
        return fl_wheel.getCurrentPosition();
    }
    public static int getFrEncoderPosition() {
        return fr_wheel.getCurrentPosition();
    }
    public static int getBlEncoderPosition() {
        return bl_wheel.getCurrentPosition();
    }
    public static int getBrEncoderPosition() {
        return br_wheel.getCurrentPosition();
    }

    public static boolean arrivedPosition(int current_position, int finish_position, boolean finish_positive) {
        if (finish_positive) {
            return current_position >= finish_position;
        }
        else {
            return current_position <= finish_position;
        }
    }
    public static void setAutonomousMovingPower(double power){
        AUTONOMOUS_MOVING_POWER = power;
    }
    public static void slowMode() {
        power = 0.5;
    }
    public static void fastMode() {
        power = 1;
    }

}