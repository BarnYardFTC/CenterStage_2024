package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "CenterStage TeleOp")
public class DriverControlledPeriod extends LinearOpMode {

    Servo servo;
    private double LAUNCH_POSITION = 0.5;
    private double HOLD_POSITION = 1;

    @Override
    public void runOpMode() {

        initEgnitionSystem();
        initArm();
        initWrist();
        initClaws();
        HardwareLocal.initColorSensor();

        servo = hardwareMap.get(Servo.class, "drone");
        servo.setPosition(HOLD_POSITION);

        telemetry.update();
        waitForStart();
        boolean wasDpadDownPressed = false;

        Claws.moveToStartPosition();
        Wrist.moveToStartPosition();


        while (opModeIsActive()) {
            runEgnitionSystem();
            runArm();
            runClaws();
            runWrist();
            pixelSensing();

            if (gamepad1.dpad_down && !wasDpadDownPressed) {
                if (servo.getPosition() == HOLD_POSITION) {
                    servo.setPosition(LAUNCH_POSITION);
                }
                else {
                    servo.setPosition(HOLD_POSITION);
                }
                wasDpadDownPressed = true;
            }
            if (!gamepad1.dpad_down) {
                wasDpadDownPressed = false;
            }

            if (gamepad1.a){
                EgnitionSystem.resetEncoders();
            }

            telemetry.addData("FL encoder: ", EgnitionSystem.getFlEncoderPosition());
            telemetry.addData("Arm1 encoder: ", Arm.ENCODER1);
            telemetry.update();
        }

    }
    public void initClaws(){
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
    }
    public void runClaws() {
        Claws.runClawsTeleop(gamepad1.left_bumper, gamepad1.right_bumper);
    }
    public void initWrist() {
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
    }
    public void runWrist() {
        Wrist.runWrist(gamepad1.y);
        if (HardwareLocal.getPosition() > 10 && Wrist.getPosition() == Wrist.WRIST_DOWN_POSITION && HardwareLocal.getPosition() < 180) {
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
        } else {
            Wrist.setPosition((HardwareLocal.getPosition() - 180) * 0.01);
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

        if (Arm.HANGING_MODE_ACTIVE2) {
          if (gamepad1.right_trigger > 0) {
              Arm.hangingModeArm2();
          } else if (gamepad1.left_trigger > 0){
              Arm.hangingModeArm3();
          }
        } else if (gamepad1.right_trigger > 0) {
            Arm.moveUp();
        } else if (gamepad1.left_trigger > 0) {
            Arm.moveDown();
        } else if (gamepad1.dpad_up || Arm.HANGING_MODE_ACTIVE1) {
            runHangingMode();
            Arm.hangingModeArm1();
        } else if (gamepad1.x || Arm.LOADING_MODE_ACTIVE) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Arm.loadingModeArm();
                    sleep(500);
                    runLoadingMode();
                }
            }).start();
        } else {
            Arm.brake();
        }
    }
    public void runLoadingMode() {
        if (gamepad1.x) {
            Claws.loadingModeClaws();
            Wrist.setPosition(Wrist.WRIST_DOWN_POSITION);
        }
    }
    public void runHangingMode() {
        if (gamepad1.dpad_up) {
            Claws.closeLeftClaw();
            Claws.closeRightClaw();
        }
    }
    public void pixelSensing() {
        if (HardwareLocal.pixelLeft()) {
            Claws.closeLeftClaw();
        } else if (HardwareLocal.pixelRight()) {
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
        EgnitionSystem.runTeleop();
    }
}