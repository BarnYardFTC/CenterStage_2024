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

        if (run2.phase == 1) {
            if (!run2.phase1_part1_completed) {
                EgnitionSystem.setVerticalPower(1);
                if (EgnitionSystem.getFlEncoderPosition() >= run2.PHASE1_PART1_COMPLETED_POSITION) {
                    EgnitionSystem.setVerticalPower(0);
                    run2.phase1_part1_completed = true;
                }
            }
            if (!run2.phase1_part2_completed) {
                EgnitionSystem.setRotPower(-1);
                if (EgnitionSystem.getFlEncoderPosition() >= run2.PHASE1_PART2_COMPLETED_POSITION) {
                    EgnitionSystem.setRotPower(0);
                    run2.phase1_part2_completed = true;
                }
            }
            if (run2.phase1_part1_completed && run2.phase1_part2_completed) {
                EgnitionSystem.resetEncoders();
                run2.phase++;
            }
        }
        else if (run2.phase == 2) {
            Claws.openRightClaw();
            if (Claws.getRightClawPosition() >= Claws.RIGHT_CLAW_OPENED_POSITION) {
                run2.phase++;
            }
        }
        else if (run2.phase == 3) {
            if (!run2.phase4_wheel_completed) {
                EgnitionSystem.setHorizontalPower(1);
                if (EgnitionSystem.getFlEncoderPosition() >= run2.PHASE_3_WHEEL_COMPLETED_POSITION) {
                    EgnitionSystem.setHorizontalPower(0);
                    run2.phase4_wheel_completed = true;
                }
            }
            if (!run2.phase4_arm_completed) {
                Arm.moveUp();
                run2.arm_moving = true;
                if (Arm.getArm1Position() >= run2.PHASE_3_ARM_COMPLETED_POSITION) {
                    run2.phase4_arm_completed = true;
                    Arm.brake();
                    run2.arm_moving = false;
                }
            }
            if (run2.phase4_wheel_completed && run2.phase4_arm_completed) {
                EgnitionSystem.resetEncoders();
                run2.phase++;
                sleep(1000);
            }
        }
        else if (run2.phase == 4) {
            Claws.openLeftClaw();
            if (Claws.getLeftClawPosition() <= Claws.LEFT_CLAW_OPENED_POSITION) {
                run2.phase++;
            }
        }
        else if (run2.phase == 5) {
            Arm.moveDown();
            run2.arm_moving = true;
            if (Arm.getArm1Position() <= Arm.MINIMAL_HOLD_POSITION) {
                Arm.stopMoving();
                run2.arm_moving = false;
                run2.phase++;

                Wrist.moveDown();
            }
        }
        if (!run2.arm_moving) {
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
    public void run1() {

        if (run1.phase == 1) {
            EgnitionSystem.setVerticalPower(1);
            if (EgnitionSystem.getFlEncoderPosition() >= run1.PHASE_1_ENCODER_FINISH_POSITION) {
                run1.phase ++;
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.resetEncoders();
            }
        }
        else if (run1.phase == 2) {
            Claws.openLeftClaw();
            run1.phase++;
        }
        else if (run1.phase == 3) {
            Wrist.moveUp();
            run1.phase++;
        }
        else if (run1.phase == 4) {

            if (!run1.phase4_part1_completed) {
                EgnitionSystem.setHorizontalPower(1);
                if (EgnitionSystem.getFlEncoderPosition() <= run1.PHASE_4_WHEEL_ENCODER_FINISH_POSITION2) {
                    run1.phase4_part1_completed = true;
                    EgnitionSystem.setHorizontalPower(0);
                }
            }
            if (!run1.phase4_part2_completed) {
                EgnitionSystem.setRotPower(-1);
                if (EgnitionSystem.getFlEncoderPosition() <= run1.PHASE_4_WHEEL_ENCODER_FINISH_POSITION1) {
                    run1.phase4_part2_completed = true;
                    EgnitionSystem.setRotPower(0);
                }
            }
            if (!run1.phase4_part3_completed) {
                Arm.moveUp();
                run1.arm_moving = true;
                if (Arm.getArm1Position() >= run1.PHASE_4_ARM_ENCODER_FINISH_POSITION) {
                    run1.phase4_part3_completed = true;
                    Arm.brake();
                    run1.arm_moving = false;
                }
            }
            if (!run1.phase4_part4_completed) {
                EgnitionSystem.setVerticalPower(0.3);
                if (EgnitionSystem.getFlEncoderPosition() <= run1.PHASE_4_WHEEL_ENCODER_FINISH_POSITION3) {
                    run1.phase4_part4_completed = true;
                    EgnitionSystem.setVerticalPower(0);
                }
            }

            if (run1.phase4_part1_completed && run1.phase4_part2_completed && run1.phase4_part3_completed && run1.phase4_part4_completed) {
                EgnitionSystem.resetEncoders();
                run1.phase++;
                sleep(1000);
            }
        }
//        else if (run1.phase == 5) {
//            Claws.openLeftClaw();
//            if (Claws.getLeftClawPosition() <= Claws.LEFT_CLAW_OPENED_POSITION) {
//                run1.phase ++;
//            }
//        }
//        else if (run1.phase == 6) {
//            Arm.moveDown();
//            run1.arm_moving = true;
//            if (Arm.getArm1Position() <= Arm.MINIMAL_HOLD_POSITION) {
//                Arm.stopMoving();
//                run1.arm_moving = false;
//                run1.phase++;
//
//                Wrist.moveDown();
//            }
//        }
//
        if (!run1.arm_moving) {
            if (!Arm.passedMinimalHoldPosition()) {
                Arm.stopMoving();
            }
            else {
                Arm.brake();
            }
        }
        EgnitionSystem.updateVariablesAutonomous();
        EgnitionSystem.runAutonomous();
        telemetry.addData("Fl wheel position: ",String.valueOf(EgnitionSystem.getFlEncoderPosition()));
        telemetry.addData("Arm (motor 1) Position: ", Arm.getArm1Position());
    }
    public void run2() {

        if (run3.phase == 1) {
            if (!run3.phase1_part1_complete) {
                EgnitionSystem.setVerticalPower(1);
                if (EgnitionSystem.getFlEncoderPosition() >= run3.PHASE1_PART1_COMPLETE_POSITION) {
                    EgnitionSystem.setVerticalPower(0);
                    run3.phase1_part1_complete = true;
                }
            }
            if (!run3.phase1_part2_complete) {
                EgnitionSystem.setRotPower(1);
                if (EgnitionSystem.getFlEncoderPosition() >= run3.PHASE1_PART2_COMPLETE_POSITION) {
                    EgnitionSystem.setRotPower(0);
                    run3.phase1_part2_complete = true;
                }
            }
            if (run3.phase1_part1_complete && run3.phase1_part2_complete) {
                EgnitionSystem.resetEncoders();
                run3.phase ++;
            }
        }
        else if (run3.phase == 2) {
            Claws.openRightClaw();
            if (Claws.getRightClawPosition() >= Claws.RIGHT_CLAW_OPENED_POSITION) {
                run3.phase++;
            }
        }
        else if (run3.phase == 3) {
            Wrist.moveUp();
            if (Wrist.getPosition() >= Wrist.WRIST_UP_POSITION) {
                run3.phase++;
            }
        }
        else if (run3.phase == 4) {
            EgnitionSystem.setVerticalPower(1);
            if (EgnitionSystem.getFlEncoderPosition() >= run3.PHASE4_COMPLETE_POSITION) {
                EgnitionSystem.setVerticalPower(0);
                run3.phase ++;
            }
        }
        else if (run3.phase == 5) {
            if (!run3.phase5_part1_completed) {
                EgnitionSystem.setRotPower(-1);
                if (EgnitionSystem.getFlEncoderPosition() >= run3.PHASE5_PART1_COMPLETE_POSITION) {
                    EgnitionSystem.setRotPower(0);
                    run3.phase5_part1_completed = true;
                }
            }
            if (!run3.phase5_part2_completed) {
                Arm.moveUp();
                if (Arm.getArm1Position() <= run3.PHASE5_PART2_COMPLETED_POSITION) {
                    Arm.brake();
                    run3.phase5_part2_completed = true;
                }
            }
        }


        if (!run3.arm_moving) {
            if (Arm.passedMinimalHoldPosition()) {
                Arm.stopMoving();
            }
            else {
                Arm.brake();
            }
        }
        EgnitionSystem.updateVariablesAutonomous();
        EgnitionSystem.runAutonomous();
        telemetry.addData("Fl wheel position: ",String.valueOf(EgnitionSystem.getFlEncoderPosition()));
        Arm.addDataToTelemetry(telemetry);

    }
}
