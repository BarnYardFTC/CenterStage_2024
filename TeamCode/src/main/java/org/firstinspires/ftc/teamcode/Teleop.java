package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.units.qual.C;

// Teleop
@TeleOp(name = "CenterStage TeleOp")
public class Teleop extends LinearOpMode {

// Configuring
    Servo drone;
    @Override
    public void runOpMode() {

// Initialize
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

// Running systems
            if (gamepad1.dpad_down) {
                drone.setPosition(0.5);
            }
            runEgnitionSystem();
            runArm();
            runClaws();
            runWrist();
            touchAndGo();

// Telemetry update
            telemetry.update();
        }
    }

// Initializing & running system functions
    public void initClaws(){
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
        Claws.openRightClaw();
        Claws.openLeftClaw();
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
        if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
            Wrist.setPosition(Wrist.WRIST_UNLOADING_POSITION + 0.018 * ((int) ((Arm.getArm1Position() - Arm.UNLOADING_POSITION) / -50)));
            EgnitionSystem.SLOW_MODE = true;
            EgnitionSystem.WAS_PRESSED = false;
        } else {
            Wrist.runWrist(gamepad1.y);
        }
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "rightArm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "leftArm");
        Arm.init(motor, motor2);
    }
    public void runArm() {
        if (gamepad1.right_trigger > 0) {
            Arm.moveUp(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger > 0) {
            Arm.moveDown(gamepad1.left_trigger);
        } else if (gamepad1.dpad_up && !Arm.HANGING_MODE_ACTIVE || !gamepad1.dpad_up && Arm.HANGING_MODE_ACTIVE) {
            Claws.closeRightClaw();
            Claws.closeLeftClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = false;
            Arm.hangingModeArm();
        } else if (gamepad1.x && !Arm.LOADING_MODE_ACTIVE || !gamepad1.x && Arm.LOADING_MODE_ACTIVE) {
            Claws.openRightClaw();
            Claws.openLeftClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = false;
            Arm.loadingModeArm();
        } else {
            Arm.brake();
        }
    }
    public void initEgnitionSystem() {
        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "rightBack");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
    }
    public void runEgnitionSystem() {
        EgnitionSystem.updateVariablesTeleop(gamepad1, telemetry);
        if (gamepad1.b) {
            initEgnitionSystem();
            initEgnitionSystem();
        }
        if (Wrist.getPosition() == Wrist.WRIST_DOWN_POSITION) {
            EgnitionSystem.SLOW_MODE = true;
            EgnitionSystem.WAS_PRESSED = false;
            EgnitionSystem.PIXELS_IN = false;
        } else if (Wrist.getPosition() == Wrist.WRIST_UP_POSITION && EgnitionSystem.SLOW_MODE && !EgnitionSystem.PIXELS_IN) {
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = false;
            EgnitionSystem.PIXELS_IN = true;
        }
        if ((gamepad1.left_stick_button || gamepad1.right_stick_button) && !EgnitionSystem.WAS_PRESSED && !EgnitionSystem.SLOW_MODE) {
            EgnitionSystem.SLOW_MODE = true;
            EgnitionSystem.WAS_PRESSED = true;
        } else if ((gamepad1.left_stick_button || gamepad1.right_stick_button) && !EgnitionSystem.WAS_PRESSED && EgnitionSystem.SLOW_MODE) {
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = true;
        }
        if (!gamepad1.left_stick_button && !gamepad1.right_stick_button) {
            EgnitionSystem.WAS_PRESSED = false;
        }
        if (!EgnitionSystem.SLOW_MODE) {
            EgnitionSystem.runTeleop1();
        } else {
            EgnitionSystem.runTeleop2();
        }
    }
    public void initColorSensor() {
        ColorRangeSensor distanceSensorRight = hardwareMap.get(ColorRangeSensor.class, "distanceSensorRight");
        ColorRangeSensor distanceSensorLeft = hardwareMap.get(ColorRangeSensor.class, "distanceSensorLeft");
        HardwareLocal.init(distanceSensorRight, distanceSensorLeft);
    }
    public void touchAndGo() {
        if (HardwareLocal.pixelRight() && Claws.getRightClawPosition() == Claws.RIGHT_CLAW_OPENED_POSITION && !HardwareLocal.PIXEL_IN_R) {
            Claws.closeRightClaw();
            HardwareLocal.PIXEL_IN_R = true;
        }
        if (HardwareLocal.pixelLeft() && Claws.getLeftClawPosition() == Claws.LEFT_CLAW_OPENED_POSITION && !HardwareLocal.PIXEL_IN_L) {
            Claws.closeLeftClaw();
            HardwareLocal.PIXEL_IN_L = true;
        }
        if (Claws.getRightClawPosition() == Claws.RIGHT_CLAW_OPENED_POSITION && !HardwareLocal.pixelRight()) {
            HardwareLocal.PIXEL_IN_R = false;
        }
        if (Claws.getLeftClawPosition() == Claws.LEFT_CLAW_OPENED_POSITION && !HardwareLocal.pixelLeft()) {
            HardwareLocal.PIXEL_IN_L = false;
        }
    }

}