package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red Front")
public class AutonomousRedFront extends LinearOpMode {

    private int spike_position = -1;
    private boolean arm_moving = false;

    @Override
    public void runOpMode() throws InterruptedException {
        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();

        setVariables();

        Wrist.moveDown();

        Claws.closeLeftClaw();
        Claws.closeRightClaw();

        initCamera();

        while (opModeInInit()) {
            spike_position = PixelDetectorRF.getSpike_position();
            telemetry.addData("Spike position: ", spike_position);
            telemetry.addData("Right region avg", Camera.getRightRegion_avg(2));
            telemetry.addData("Left region avg", Camera.getLeftRegion_avg(2));
            telemetry.update();
        }
        Camera.close(2);

        waitForStart();
        while (opModeIsActive()) {
            if (!gamepad1.y) {
                if (spike_position == 0) {
                    run0();
                } else if (spike_position == 1) {
                    run1();
                } else {
                    run2();
                }

                if (!arm_moving) {
                    if (!Arm.passedMinimalHoldPosition()) {
                        Arm.stopMoving();
                    } else {
                        Arm.brake();
                    }
                }

                EgnitionSystem.updateVariablesAutonomous();
                EgnitionSystem.runAutonomous();

                telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
                telemetry.addData("FL encoder: ", EgnitionSystem.getFlEncoderPosition());
                telemetry.update();
            }

        }
    }
    public void run0() {
        if (RFrun0.phase == 1) { // move forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_1_FINISH, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RFrun0.phase == 2) { // rotate
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_2_FINISH, false)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setRotPower(-1);
            }
        }
        else if (RFrun0.phase == 3) { // move backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_3_FINISH, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun0.phase == 4) { // open right claw
            Claws.openRightClaw();
            RFrun0.phase ++;
        }
        else if (RFrun0.phase == 5) { // move wrist up
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            RFrun0.phase ++;
        }
        else if (RFrun0.phase == 6) { // move forward
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_6_FINISH, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                EgnitionSystem.setAutonomousMovingPower(0.5);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                RFrun0.phase ++;
//            }
//            else {
//                EgnitionSystem.setAutonomousMovingPower(0.2);
//                EgnitionSystem.setVerticalPower(1);
//            }
            RFrun0.phase ++;
        }
        else if (RFrun0.phase == 7) { // rotate
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_7_FINISH, false)) {
//                EgnitionSystem.setRotPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                RFrun0.phase ++;
//            }
//            else {
//                EgnitionSystem.setRotPower(-1);
//            }
            RFrun0.phase ++;
        }
        else if (RFrun0.phase == 8) { // move horizontally (toward back board)
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_8_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RFrun0.phase == 9) { // move a bit forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_9_FINISH,true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RFrun0.phase == 10) { // move arm up
            if (Arm.arrivedPosition(Arm.getArm1Position(), RFrun0.PHASE_10_FINISH, false)) {
                arm_moving = false;
                RFrun0.phase ++;
            }
            else {
                Arm.moveUp();
                arm_moving = true;
            }
        }
        else if (RFrun0.phase == 11) { // open left claw (let go of pixel)
            Claws.openLeftClaw();
            RFrun0.phase ++;
        }
        else if (RFrun0.phase == 12) { // move arm down
            if (Arm.arrivedPosition(Arm.getArm1Position(), RFrun0.PHASE_12_FINISH, true)) {
                arm_moving = false;
                RFrun0.phase ++;
            }
            else {
                Arm.moveDown();
                arm_moving = true;
            }
        }
        else if (RFrun0.phase == 13) { // move backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_13_FINISH, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun0.phase == 14) { // move horizontally
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun0.PHASE_14_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun0.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
    }
    public void run1() {
        if (RFrun1.phase == 1) { // move forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_1_FINISH,true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RFrun1.phase == 2) { // open right claw
            Claws.openRightClaw();
            RFrun1.phase ++;
        }
        else if (RFrun1.phase == 3) { // move wrist up
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            RFrun1.phase ++;
        }
        else if (RFrun1.phase == 4) { // move backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_4_FINISH,false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun1.phase == 5) { // rotate
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_5_FINISH,false)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setRotPower(-1);
            }
        }
        else if (RFrun1.phase == 6) { // move forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_6_FINISH,true)) {
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.setAutonomousMovingPower(0.5);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
                EgnitionSystem.setAutonomousMovingPower(0.3);
            }

        }
        else if (RFrun1.phase == 7) { // move horizontally (towards the back board)
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_7_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RFrun1.phase == 8) { // move a bit backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_8_FINISH, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun1.phase == 9) { // move arm up
            if (Arm.arrivedPosition(Arm.getArm1Position(), RFrun1.PHASE_9_FINISH, false)) {
                arm_moving = false;
                RFrun1.phase ++;
            }
            else {
                Arm.moveUp();
                arm_moving = true;
            }
        }
        else if (RFrun1.phase == 10) { // open left claw (let go of the pixel)
            sleep(500);
            Claws.openLeftClaw();
            RFrun1.phase ++;
            sleep(500);
        }
        else if (RFrun1.phase == 11) { // move arm down
            if (Arm.arrivedPosition(Arm.getArm1Position(), RFrun1.PHASE_11_FINISH, true)) {
                arm_moving = false;
                RFrun1.phase ++;
            }
            else {
                Arm.moveDown();
                arm_moving = true;
            }
        }
        else if (RFrun1.phase == 12) { // move backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_12_FINISH, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun1.phase == 13) { // move horizontally
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun1.PHASE_13_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun1.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
    }
    public void run2() {
        if (RFrun2.phase == 1) { // run forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_1_FINISH, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RFrun2.phase == 2) { // move sideways
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_2_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
        else if (RFrun2.phase == 3) { // rotate
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_3_FINISH, false)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setRotPower(-1);
            }
        }
        else if (RFrun2.phase == 4) { // move horizontally
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_4_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RFrun2.phase == 5) { // open right claw
            Claws.openRightClaw();
            RFrun2.phase ++;
        }
        else if (RFrun2.phase == 6) { // move wrist up
            Wrist.moveUp();
            RFrun2.phase ++;
        }
        else if (RFrun2.phase == 7) { // move horizontally (towards back board)
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_7_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RFrun2.phase == 8) { // move a bit backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_8_FINISH, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun2.phase == 9) { // move arm up
            if (Arm.arrivedPosition(Arm.getArm1Position(), RFrun2.PHASE_9_FINISH, false)) {
                arm_moving = false;
                RFrun2.phase ++;
            }
            else {
                Arm.moveUp();
                arm_moving = true;
            }
        }
        else if (RFrun2.phase == 10) { // open left claw
            sleep(500);
            Claws.openLeftClaw();
            RFrun2.phase ++;
            sleep(500);
        }
        else if (RFrun2.phase == 11) { // move arm down
            if (Arm.arrivedPosition(Arm.getArm1Position(), RFrun2.PHASE_11_FINISH,true)) {
                arm_moving = false;
                RFrun2.phase ++;
            }
            else {
                Arm.moveDown();
                arm_moving = true;
            }
        }
        else if (RFrun2.phase == 12) { // move backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_12_FINISH, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RFrun2.phase == 13) { // move horizontally
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RFrun2.PHASE_13_FINISH, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RFrun2.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
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
        Camera.init(this, hardwareMap, 2);
    }
    public void setVariables() {
        RFrun0.phase = 1;
        RFrun1.phase = 1;
        RFrun2.phase = 1;
    }
}
