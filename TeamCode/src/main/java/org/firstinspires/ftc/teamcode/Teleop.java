package org.firstinspires.ftc.teamcode;

// Imports

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

// Teleop
@TeleOp(name = "CenterStage TeleOp")
public class Teleop extends LinearOpMode {

    // Configuring
//    Servo drone;
//    double DRONE_LUNCH = 1;
//    double DRONE_INIT = 0.3;
    @Override
    public void runOpMode() {

// Initialize
        initArm();
        initWrist();
        initClaws();
        initEgnitionSystem();
        initColorRangeSensor();
//        initLed();
//        initDrone();
//        drone.setDirection(Servo.Direction.REVERSE);
//        drone.setPosition(DRONE_INIT);

        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {

// Running systems
//            runDrone();
            runEgnitionSystem();
            runArm();
            runClaws();
            runWrist();
            touchAndGo();
//            ledChange();

// Telemetry update
            telemetry.addData("right arm", Arm.getArm1Position());
            telemetry.addData("right claw", Claws.getRightClawPosition());
            telemetry.addData("left claw", Claws.getLeftClawPosition());
            telemetry.addData("wrist", Wrist.getPosition());
            telemetry.update();
        }
//        drone.setPosition(0);
    }

// Initializing & running system functions

    //    public void initDrone() {
//        drone = hardwareMap.get(Servo.class, "drone");
//    }
//    public void runDrone() {
//        if (gamepad1.dpad_down) {
//            drone.setPosition(DRONE_LUNCH);
//        }
//    }
    public void initClaws() {
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
        Claws.closeRightClaw();
        Claws.closeLeftClaw();
    }

    public void runClaws() {
        Claws.runClawsTeleop(gamepad1.left_bumper, gamepad1.right_bumper);
        if (!HardwareLocal.pixelLeft() && Claws.getLeftClawPosition() >= Claws.LEFT_CLAW_CLOSED_POSITION && Wrist.getPosition() >= Wrist.WRIST_DOWN_POSITION) {
            Claws.openLeftClaw();
        }
        if (!HardwareLocal.pixelRight() && Claws.getRightClawPosition() >= Claws.RIGHT_CLAW_CLOSED_POSITION && Wrist.getPosition() >= Wrist.WRIST_DOWN_POSITION) {
            Claws.openRightClaw();
        }
    }

    public void initWrist() {
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
        Wrist.setPosition(Wrist.WRIST_UP_POSITION);
    }

    public void runWrist() {
        if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
            Wrist.setPosition(Wrist.WRIST_UNLOADING_POSITION + 0.01 * ((int) ((Arm.getArm1Position() - Arm.UNLOADING_POSITION) / -25)));
            EgnitionSystem.SLOW_MODE = true;
            EgnitionSystem.WAS_PRESSED = false;
        } else if (Wrist.getPosition() >= Wrist.WRIST_DOWN_POSITION && HardwareLocal.pixelRight() && HardwareLocal.pixelLeft() && Claws.getLeftClawPosition() >= Claws.LEFT_CLAW_CLOSED_POSITION && Claws.getRightClawPosition() == Claws.RIGHT_CLAW_CLOSED_POSITION && !Wrist.UP) {
            sleep(100);
            Wrist.UP = true;
            Wrist.moveUp();
        } else if (Wrist.getPosition() >= Wrist.WRIST_DOWN_POSITION && (!HardwareLocal.pixelRight() || !HardwareLocal.pixelLeft()) && Wrist.UP) {
            Wrist.UP = false;
        } else {
            Wrist.runWrist(gamepad1.y);
        }
    }

    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "right_arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "left_arm");
        Arm.init(motor, motor2);
    }

    public void runArm() {
        if (gamepad1.right_trigger > 0 && Arm.getArm1Position() >= -3000) {
            Arm.moveUp(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger > 0 && Arm.getArm1Position() <= -20 && Arm.getArm2Position() >= 20) {
            Arm.moveDown(gamepad1.left_trigger);
        } else if (gamepad1.dpad_up && !Arm.HANGING_MODE_ACTIVE || !gamepad1.dpad_up && Arm.HANGING_MODE_ACTIVE) {
            Claws.closeRightClaw();
            Claws.closeLeftClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = false;
            Arm.hangingModeArm();
        } else if (gamepad1.x && !Arm.LOADING_MODE_ACTIVE || !gamepad1.x && Arm.LOADING_MODE_ACTIVE) {
            Claws.closeLeftClaw();
            Claws.closeRightClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = false;
            Wrist.UP = false;
            Arm.HANGING_MODE_ACTIVE = false;
            Arm.DPAD_PRESSED = false;
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
        if (Wrist.getPosition() >= Wrist.WRIST_DOWN_POSITION) {
            EgnitionSystem.SLOW_MODE = true;
            EgnitionSystem.WAS_PRESSED = false;
            EgnitionSystem.PIXELS_IN = false;
        } else if (Wrist.getPosition() == Wrist.WRIST_UP_POSITION && EgnitionSystem.SLOW_MODE && !EgnitionSystem.PIXELS_IN) {
            EgnitionSystem.SLOW_MODE = false;
            EgnitionSystem.WAS_PRESSED = false;
            EgnitionSystem.PIXELS_IN = true;
        }
        if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
            EgnitionSystem.SLOW_MODE = true;
            EgnitionSystem.WAS_PRESSED = false;
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

    public void initColorRangeSensor() {
        ColorRangeSensor distanceSensorRight = hardwareMap.get(ColorRangeSensor.class, "distanceSensorRight");
        ColorRangeSensor distanceSensorLeft = hardwareMap.get(ColorRangeSensor.class, "distanceSensorLeft");
        HardwareLocal.init(distanceSensorRight, distanceSensorLeft);
    }

    public void initLed() {
        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
        HardwareLocal.init(ledDriver);
    }

    public void touchAndGo() {
        if (Arm.getArm1Position() <= Arm.MINIMAL_HOLD_POSITION) {
            HardwareLocal.PIXEL_IN_L = true;
            HardwareLocal.PIXEL_IN_R = true;
        }
        if (!HardwareLocal.PIXEL_IN_R && HardwareLocal.pixelRight() && Claws.getRightClawPosition() == Claws.RIGHT_CLAW_OPENED_POSITION) {
            Claws.closeRightClaw();
            HardwareLocal.PIXEL_IN_R = true;
        } else if (!HardwareLocal.pixelRight()) {
            HardwareLocal.PIXEL_IN_R = false;
        }
        if (!HardwareLocal.PIXEL_IN_L && HardwareLocal.pixelLeft() && Claws.getLeftClawPosition() == Claws.LEFT_CLAW_OPENED_POSITION) {
            Claws.closeLeftClaw();
            HardwareLocal.PIXEL_IN_L = true;
        } else if (!HardwareLocal.pixelLeft()) {
            HardwareLocal.PIXEL_IN_L = false;
        }
    }

    public void ledChange() {
        if (HardwareLocal.pixelRight() && HardwareLocal.pixelLeft()) {
            HardwareLocal.green();
        } else if (!HardwareLocal.pixelRight() && HardwareLocal.pixelLeft() || HardwareLocal.pixelRight() && !HardwareLocal.pixelLeft()) {
            if (HardwareLocal.BLINK_IN_TIME >= 0 && HardwareLocal.BLINK_IN_TIME < 20) {
                HardwareLocal.black();
                HardwareLocal.BLINK_IN_TIME++;
            } else if (HardwareLocal.BLINK_IN_TIME >= 20 && HardwareLocal.BLINK_IN_TIME < 40) {
                HardwareLocal.red();
                HardwareLocal.BLINK_IN_TIME++;
            } else {
                HardwareLocal.BLINK_IN_TIME = 0;
            }
        } else {
            HardwareLocal.red();
        }
    }
}