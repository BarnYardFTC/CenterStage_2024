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

    Servo servo;
    private double LAUNCH_POSITION = 0;
    private double HOLD_POSITION = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        initArm();
        initEgnitionSystem();
        initWrist();
        initClaws();

        servo = hardwareMap.get(Servo.class, "drone");
        servo.setPosition(HOLD_POSITION);

        telemetry.update();
        waitForStart();
        boolean wasXPressed = false;

        Claws.moveToStartPosition();
        Wrist.moveToStartPosition();


        while (opModeIsActive()) {
            runArm();
            runEgnitionSystem();
            runWrist();
            runClaws();

            if (gamepad1.x && !wasXPressed) {
                if (servo.getPosition() == HOLD_POSITION) {
                    servo.setPosition(LAUNCH_POSITION);
                }
                else {
                    servo.setPosition(HOLD_POSITION);
                }
                wasXPressed = true;
            }
            if (!gamepad1.x) {
                wasXPressed = false;
            }

            telemetry.update();
        }

    }
    public void initClaws(){
        telemetry.addLine("Press left/right bumpers to move the corresponding claws");
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
    }
    public void runClaws(){
        telemetry.addLine("Press left/right bumpers to move the corresponding claws");
        Claws.runClawsTeleop(gamepad1.left_bumper, gamepad1.right_bumper);
    }
    public void initWrist() {
        telemetry.addLine("Press Y to move the wrist Up & Down");
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
    }
    public void runWrist() {
        telemetry.addLine("Press Y to move the wrist Up & Down");
        Wrist.runWrist(gamepad1.y);
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }
    public void runArm() {
        Arm.addDataToTelemetry(telemetry);
        if (gamepad1.dpad_up) {
            if (Arm.passedMaximalPosition()) {
                Arm.returnToMaximalPosition();
            } else {
                Arm.moveUp();
            }
        } else if (gamepad1.dpad_down) {
            if (Arm.passedMinimalHoldPosition()) {
                Arm.stopMoving();
            } else {
                Arm.moveDown();
            }
        } else {
            if (Arm.passedMinimalHoldPosition()) {
                Arm.stopMoving();
            } else if (Arm.passedMaximalPosition()) {
                Arm.returnToMaximalPosition();
            }
            else{
                Arm.brake();
            }
        }
    }
    public void initEgnitionSystem() {
        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
    }
    public void runEgnitionSystem() {
        EgnitionSystem.updateVariablesTeleop(gamepad1, telemetry);
        EgnitionSystem.runTeleop();
    }

}
