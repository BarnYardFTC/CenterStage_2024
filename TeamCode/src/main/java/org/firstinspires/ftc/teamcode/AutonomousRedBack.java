package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red back")
public class AutonomousRedBack extends LinearOpMode {

    private int spike_position = -1;

    private final int PHASE_1_ENCODER_FINISH_POSITION = 1200;
    private final int PHASE_3_ENCODER_FINISH_POSITION = -300;
    private final int PHASE_4_WHEEL_ENCODER_FINISH_POSITION2 = -1200;
    private final int PHASE_4_WHEEL_ENCODER_FINISH_POSITION1 = -830;

    private final int PHASE_4_ARM_ENCODER_FINISH_POSITION = 800;

    private int phase = 1;

    private boolean phase4_part1_completed = false;
    private boolean phase4_part2_completed = false;
    private boolean phase4_part3_completed = false;

    private boolean arm_moving = false;

    public void run1() {

        if (phase == 1) {
            EgnitionSystem.setVerticalPower(0.5);
            if (EgnitionSystem.getFlEncoderPosition() >= PHASE_1_ENCODER_FINISH_POSITION) {
                phase ++;
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.resetEncoders();
            }
        }
        else if (phase == 2) {
            Claws.openRightClaw();
            phase++;
        }
        else if (phase == 3) {
            EgnitionSystem.setVerticalPower(-0.5);
            if (EgnitionSystem.getFlEncoderPosition() <= PHASE_3_ENCODER_FINISH_POSITION) {
                phase++;
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.resetEncoders();
            }
        }
        else if (phase == 4) {

            Wrist.moveUp();

            if (!phase4_part1_completed) {
                EgnitionSystem.setHorizontalPower(0.5);
                if (EgnitionSystem.getFlEncoderPosition() <= PHASE_4_WHEEL_ENCODER_FINISH_POSITION2) {
                    phase4_part1_completed = true;
                    EgnitionSystem.setHorizontalPower(0);
                }
            }
            if (!phase4_part2_completed) {
                EgnitionSystem.setRotPower(-0.5);
                if (EgnitionSystem.getFlEncoderPosition() <= PHASE_4_WHEEL_ENCODER_FINISH_POSITION1) {
                    phase4_part2_completed = true;
                    EgnitionSystem.setRotPower(0);
                }
            }
            if (!phase4_part3_completed) {
                Arm.moveUp();
                arm_moving = true;
                if (Arm.getArm1Position() >= PHASE_4_ARM_ENCODER_FINISH_POSITION) {
                    phase4_part3_completed = true;
                    Arm.brake();
                    arm_moving = false;
                }
            }

            if (phase4_part1_completed && phase4_part2_completed && phase4_part3_completed) {
                EgnitionSystem.resetEncoders();
                phase++;
            }
        }
        if (!arm_moving) {
            if (Arm.passedMinimalHoldPosition()) {
                Arm.stopMoving();
            }
            else {
                Arm.brake();
            }
        }
        EgnitionSystem.updateVariablesAutonomous();
        EgnitionSystem.runAutonomous();
        telemetry.addLine(String.valueOf(EgnitionSystem.getFlEncoderPosition()));
        telemetry.addData("Arm (motor 1) Position", Arm.getArm1Position());
    }

    @Override
    public void runOpMode() throws InterruptedException {

        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();
        initCamera();

        Wrist.moveDown();

        Claws.closeLeftClaw();
        Claws.closeRightClaw();

        while (opModeInInit()) {
            spike_position = PixelDetector.getSpike_position();
            telemetry.addData("Spike position: ", spike_position);
            telemetry.addData("Right region avg", Camera.getRightRegion_avg());
            telemetry.addData("Left region avg", Camera.getLeftRegion_avg());
            telemetry.update();
        }
        Camera.close();

        waitForStart();

        while (opModeIsActive()) {
//            if (spike_position == 0) {
//                run0();
//            }
//            else if (spike_position == 1) {
//                run1();
//            }
//            else {
//                run2();
//            }
            run1();
            telemetry.update();
        }

    }
    public void initClaws(){
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
    }
    public void initWrist() {
        telemetry.addLine("Press Y to move the wrist Up & Down");
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

    public void run0() {

    }
    public void run2() {

    }
}
