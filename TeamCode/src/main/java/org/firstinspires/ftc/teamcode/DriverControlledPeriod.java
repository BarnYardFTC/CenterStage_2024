package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "CenterStage TeleOp")
public class DriverControlledPeriod extends LinearOpMode {

    Servo servo;
    private double LAUNCH_POSITION = 0.5;
    private double HOLD_POSITION = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        initEgnitionSystem();
        initArm();
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
            runEgnitionSystem();
            runArm();
            runClaws();
            runLoadingMode();
            runHangingMode();

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


            if (gamepad1.a){
                EgnitionSystem.resetEncoders();
            }

            telemetry.addData("FL encoder: ", EgnitionSystem.getFlEncoderPosition());
            telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
            telemetry.addData("Left claw: ", Claws.getLeftClawPosition());
            telemetry.addData("Right claw", Claws.getRightClawPosition());
            telemetry.addData("Wrist", Wrist.getPosition());
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
          if (gamepad1.dpad_up) {
              Arm.hangingModeArm2();
          } else if (gamepad1.dpad_down){
              Arm.hangingModeArm3();
          }
        } else if (gamepad1.dpad_up) {
            Arm.moveUp();
        } else if (gamepad1.dpad_down) {
            Arm.moveDown();
        } else if (gamepad1.y || Arm.HANGING_MODE_ACTIVE1) {
            Arm.hangingModeArm1();
        } else if (gamepad1.left_trigger > 0 || Arm.LOADING_MODE_ACTIVE) {
            Arm.loadingModeArm();
        } else {
            Arm.brake();
        }
    }
    public void runLoadingMode() {
        if (gamepad1.left_trigger > 0) {
            Claws.loadingModeClaws();
            Wrist.setPosition(Wrist.WRIST_DOWN_POSITION);
        }
    }
    public void runHangingMode() {
        if (gamepad1.y) {
            Claws.closeLeftClaw();
            Claws.closeRightClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
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