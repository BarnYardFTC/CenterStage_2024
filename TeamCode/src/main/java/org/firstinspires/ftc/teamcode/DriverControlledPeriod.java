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
    boolean going_to_hang_position = false;
    boolean going_to_load = false;

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
        boolean wasDpadDownPressed = false;

        Claws.moveToStartPosition();
        Wrist.moveToStartPosition();


        while (opModeIsActive()) {
            runEgnitionSystem();
            runArm();
            runWrist();
            runClaws();

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

            telemetry.addData("arm(1) encoder: ", Arm.getArm1Position());
            telemetry.addData("arm(2) encoder: ", Arm.getArm2Position());
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
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }
    public void runArm() {
        if (gamepad1.right_trigger > 0) {
            Arm.moveUp();
        } else if (gamepad1.left_trigger > 0) {
            Arm.moveDown();
        } else if (gamepad1.a || Arm.HANGING_MODE) {
            Arm.hangingMode();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
        } else if (gamepad1.x || Arm.LOADING_MODE) {
            Arm.loadingMode();
            Wrist.setPosition(Wrist.WRIST_DOWN_POSITION);
            Claws.openLeftClaw();
            Claws.openRightClaw();
        } else {
            Arm.brake();
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
        if (gamepad1.left_stick_button || gamepad1.right_stick_button) {
            EgnitionSystem.slowMode();
        }
        else {
            EgnitionSystem.fastMode();
        }
        EgnitionSystem.updateVariablesTeleop(gamepad1, telemetry);
        EgnitionSystem.runTeleop();
    }

}
