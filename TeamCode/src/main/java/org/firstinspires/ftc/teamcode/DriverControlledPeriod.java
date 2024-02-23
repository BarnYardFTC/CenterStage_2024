package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "CenterStage TeleOp")
public class DriverControlledPeriod extends LinearOpMode {

    Servo drone;

    @Override
    public void runOpMode() {

        initArm();
        initWrist();
        initClaws();
        initEgnitionSystem();
        initColorSensor();

        drone = hardwareMap.get(Servo.class, "drone");

        telemetry.update();
        waitForStart();

        drone.setPosition(drone.getPosition());

        while (opModeIsActive()) {

            if (gamepad1.dpad_down) {
                drone.setPosition(0.5);
            }

            runEgnitionSystem();
            runArm();
            runClaws();
            runWrist();
//            touchAndGoColor();

            if (gamepad1.a){
                EgnitionSystem.resetEncoders();
            }

            telemetry.addData("Encoder1: ", Arm.ENCODER1);
            telemetry.addData("Wrist" , Wrist.getPosition());
            telemetry.addData("Color sensor red" , HardwareLocal.getRedValueRight());
            telemetry.addData("Color sensor green" , HardwareLocal.getGreenValueRight());
            telemetry.addData("Color sensor blue" , HardwareLocal.getBlueValueRight());
            telemetry.update();
        }
    }
    public void initClaws(){
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
        Claws.closeRightClaw();
        Claws.closeLeftClaw();
    }
    public void runClaws() {
        Claws.runClawsTeleop(gamepad1.left_bumper, gamepad1.right_bumper);
    }
    public void initWrist() {
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
        Wrist.setPosition(Wrist.WRIST_UP_POSITION);
    }
    public void runWrist() {
        if (Arm.getArm1Position() <= -1700) {
            Wrist.setPosition((double) Arm.getArm1Position() / -6500);
        }
        else {
            Wrist.runWrist(gamepad1.y);
        }
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }
    public void runArm() {
        Arm.addDataToTelemetry(telemetry);

        if (gamepad1.right_trigger > 0) {
            Arm.moveUp();
        } else if (gamepad1.left_trigger > 0) {
            Arm.moveDown();
        } else if (gamepad1.dpad_up && !Arm.HANGING_MODE_ACTIVE || !gamepad1.dpad_up && Arm.HANGING_MODE_ACTIVE) {
            Claws.closeRightClaw();
            Claws.closeLeftClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            Arm.hangingModeArm();
        } else if (gamepad1.x && !Arm.LOADING_MODE_ACTIVE || !gamepad1.x && Arm.LOADING_MODE_ACTIVE) {
            Claws.openRightClaw();
            Claws.openLeftClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            Arm.loadingModeArm();
        } else {
            Arm.brake();
        }
    }
    public void touchAndGoColor() {
//        if (HardwareLocal.pixelLeftColor() && Claws.getLeftClawPosition() == Claws.LEFT_CLAW_OPENED_POSITION) {
//            Claws.closeLeftClaw();
//        } else
        if (HardwareLocal.pixelRightColor() && Claws.getRightClawPosition() == Claws.RIGHT_CLAW_OPENED_POSITION) {
            Claws.closeRightClaw();
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
        if (gamepad1.b) {
            initEgnitionSystem();
        }
        else if (gamepad1.left_stick_button || gamepad1.right_stick_button) {
            EgnitionSystem.runTeleop2();
        } else {
            EgnitionSystem.runTeleop1();
        }
    }
    public void initColorSensor() {
        ColorSensor colorSensorRight = hardwareMap.get(ColorSensor.class, "color_Sensor_right");
        HardwareLocal.init(colorSensorRight);
    }
}