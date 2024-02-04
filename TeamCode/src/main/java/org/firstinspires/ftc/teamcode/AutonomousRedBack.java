package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red back")
public class AutonomousRedBack extends LinearOpMode {

    private int spike_position = 0;
    private boolean arm_moving = false;

    @Override
    public void runOpMode() throws InterruptedException {

        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();

        Wrist.moveDown();

        Claws.closeLeftClaw();
        Claws.closeRightClaw();

        //initCamera();
//
//        while (opModeInInit()) {
//            spike_position = PixelDetector.getSpike_position();
//            telemetry.addData("Spike position: ", spike_position);
//            telemetry.addData("Right region avg", Camera.getRightRegion_avg());
//            telemetry.addData("Left region avg", Camera.getLeftRegion_avg());
//            telemetry.update();
//        }
//        Camera.close();

        waitForStart();

        while (opModeIsActive()) {

            if (spike_position == 0) {
                run0();
            }
            else if (spike_position == 1) {
                run1();
            }
            else{
                run2();
            }

            if (!arm_moving) {
                if (!Arm.passedMinimalHoldPosition()) {
                    Arm.stopMoving();
                }
                else {
                    Arm.brake();
                }
            }

            EgnitionSystem.updateVariablesAutonomous();
            EgnitionSystem.runAutonomous();

            telemetry.addData("Fl encoder: ", EgnitionSystem.getFlEncoderPosition());
            telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
            telemetry.update();
        }

    }
    public void run0() {
        if (RBrun1.phase == 1) {
            EgnitionSystem.setVerticalPower(1);
            if (EgnitionSystem.getFlEncoderPosition() >= RBrun1.PHASE_1_FINISH) {
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.resetEncoders();
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 2) {
            Claws.openRightClaw();
            RBrun1.phase++;
        }
        else if (RBrun1.phase == 3) {
            Wrist.moveUp();
            RBrun1.phase++;
        }
        else if (RBrun1.phase == 4) {
            if (!RBrun1.PHASE_4_WHEEL_FINISHED) {
                EgnitionSystem.setRotPower(-0.5);
                EgnitionSystem.setHorizontalPower(1);
                if (EgnitionSystem.getFlEncoderPosition() <= RBrun1.PHASE_4_FINISH_WHEEL) {
                    EgnitionSystem.setHorizontalPower(0);
                    EgnitionSystem.setRotPower(0);
                    EgnitionSystem.resetEncoders();
                    RBrun1.PHASE_4_WHEEL_FINISHED = true;
                }
            }
            if (!RBrun1.PHASE_4_ARM_COMPLETED) {
                Arm.moveUp();
                arm_moving = true;
                if (Arm.getArm1Position() <= RBrun1.PHASE_4_FINISH_ARM) {
                    arm_moving = false;
                    RBrun1.PHASE_4_ARM_COMPLETED = true;
                }
            }
            if (RBrun1.PHASE_4_ARM_COMPLETED && RBrun1.PHASE_4_WHEEL_FINISHED) {
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 5) {
            Claws.openRightClaw();
            RBrun1.phase++;
        }
        else if (RBrun1.phase == 6) {
            Arm.moveDown();
            arm_moving = true;
            if (!Arm.passedMinimalHoldPosition()) {
                Arm.stopMoving();
                arm_moving = false;
            }
            RBrun1.phase++;
        }




    }
    public void run1() {

    }
    public void run2() {

    }
    public void initClaws(){
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
    }
    public void initWrist() {
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }
    public void initEgnitionSystem() {
        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
        EgnitionSystem.initEncoders();
    }
    public void initCamera() {
        Camera.init(this, hardwareMap);
    }

}
