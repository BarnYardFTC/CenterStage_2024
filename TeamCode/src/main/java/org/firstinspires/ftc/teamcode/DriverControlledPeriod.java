package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "CenterStage TeleOp")
public class DriverControlledPeriod extends LinearOpMode {

    Arm arm;

    DcMotor fl_wheel;
    DcMotor fr_wheel;
    DcMotor bl_wheel;
    DcMotor br_wheel;
    IMU imu;

    Wrist wrist;

    Claws claws;

    @Override
    public void runOpMode() throws InterruptedException {

        initArm();
        initEgnitionSystem();
        initWrist();
        initClaws();

        telemetry.update();
        waitForStart();

        wrist.moveDown();
        claws.openBothClaws();
        while (opModeIsActive()) {
            runArm();
            runEgnitionSystem();
            runWrist();
            runClaws();

            telemetry.update();
        }

    }
    public void initClaws(){
        telemetry.addLine("Press left/right bumpers to move the corresponding claws");
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        claws = new Claws(left_claw, right_claw);
    }
    public void runClaws(){
        telemetry.addLine("Press left/right bumpers to move the corresponding claws");
        claws.runClaws(gamepad1.left_bumper, gamepad1.right_bumper);

    }
    public void initWrist() {
        telemetry.addLine("Press Y to move the wrist Up & Down");
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        wrist = new Wrist(servo);
    }
    public void runWrist() {
        telemetry.addLine("Press Y to move the wrist Up & Down");
        wrist.runWrist(gamepad1.y);
    }
    public void initArm() {
        arm = new Arm(hardwareMap.get(DcMotor.class, "arm"));
        arm.addDataToTelemetry(telemetry);
    }
    public void runArm() {
        arm.addDataToTelemetry(telemetry);
        if (gamepad1.dpad_up) {
            if (arm.passedMaximalPosition()) {
                arm.returnToMaximalPosition();
            } else {
                arm.moveUp();
            }
        } else if (gamepad1.dpad_down) {
            if (arm.passedMinimalHoldPosition()) {
                arm.stopMoving();
            } else {
                arm.moveDown();
            }
        } else {
            if (arm.passedMinimalHoldPosition()) {
                arm.stopMoving();
            } else if (arm.passedMaximalPosition()) {
                arm.returnToMaximalPosition();
            }
            else{
                arm.brake();
            }
        }
    }
    public void initEgnitionSystem() {
        fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
        fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
        bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
        br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");

        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();

    }
    public void runEgnitionSystem() {
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
}
