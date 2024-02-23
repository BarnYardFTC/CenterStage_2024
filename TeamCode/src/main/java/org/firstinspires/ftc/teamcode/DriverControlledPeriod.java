package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

// Teleop
@TeleOp(name = "CenterStage TeleOp")
public class DriverControlledPeriod extends LinearOpMode {

// Configuring
    Servo drone;
    @Override
    public void runOpMode() {

// Initialize
        initArm();
        initWrist();
        initClaws();
        initEgnitionSystem();
//        initColorSensor();
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
//            touchAndGoColor();

// Telemetry update
            telemetry.addData("Wrist" , Wrist.getPosition());
//            telemetry.addData("Color sensorR red" , HardwareLocal.getRedValueRight());
//            telemetry.addData("Color sensorR green" , HardwareLocal.getGreenValueRight());
//            telemetry.addData("Color sensorR blue" , HardwareLocal.getBlueValueRight());
//            telemetry.addData("Color sensorR Alpha" , HardwareLocal.getAlphaValueRight());
            telemetry.update();
        }
    }

// Initializing & running system functions
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
            if (Wrist.getPosition() < -1700 && Wrist.getPosition() > -2000) {
                Wrist.setPosition((double) (Arm.getArm1Position() + 1700) / -2210);
            } else if (Wrist.getPosition() < -2000 && Wrist.getPosition() > -2300){
                Wrist.setPosition((double) (Arm.getArm1Position() + 1700) / -2500);
            } else {
                Wrist.setPosition((double) (Arm.getArm1Position() + 1700) / -1950);
            }
        }
        else {
            Wrist.runWrist(gamepad1.y);
        }
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
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
        ColorSensor colorSensorRight = hardwareMap.get(ColorSensor.class, "colorSensorRight");
        HardwareLocal.init(colorSensorRight);
//        ColorSensor colorSensorLeft = hardwareMap.get(ColorSensor.class, "colorSensorLeft");
//        HardwareLocal.init(colorSensorLeft);
    }
    public void touchAndGoColor() {
//        if (HardwareLocal.pixelLeftColor() && Claws.getLeftClawPosition() == Claws.LEFT_CLAW_OPENED_POSITION) {
//            Claws.closeLeftClaw();
//        } else
        if (HardwareLocal.pixelRightColor() && Claws.getRightClawPosition() == Claws.RIGHT_CLAW_OPENED_POSITION) {
            Claws.closeRightClaw();
        }
    }
}