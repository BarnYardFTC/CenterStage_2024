package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red back")
public class AutonomousRedBack extends LinearOpMode {

    private int spike_position = 1;
    private boolean arm_moving = false;

    @Override
    public void runOpMode() throws InterruptedException {

        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();

        Wrist.moveDown();

        setVariables();

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

            telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
            telemetry.addData("FL encoder position: ", EgnitionSystem.getFlEncoderPosition());
            telemetry.update();
        }

    }
    public void run0() {
        if (RBrun0.phase == 1) { // move forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun0.PHASE_1_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun0.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RBrun0.phase == 2) { // move a bit sideways
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun0.PHASE_2_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun0.phase++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RBrun0.phase == 3) { // rotate
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun0.PHASE_3_POSITION)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun0.phase ++;
            }
            else {
                EgnitionSystem.setRotPower(-1);
            }
        }
        else if (RBrun0.phase == 4) { // move sideways
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun0.PHASE_4_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun0.phase++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
        else if (RBrun0.phase == 5) { // open the right claw
            Claws.openRightClaw();
            RBrun0.phase++;
        }
        else if (RBrun0.phase == 6) { // move wrist up
            Wrist.moveUp();
            RBrun0.phase++;
        }
        else if (RBrun0.phase == 7) { // move sideways (towards back board)
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun0.PHASE_7_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun0.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RBrun0.phase == 8) { // move a bit forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun0.PHASE_8_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun0.phase++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RBrun0.phase == 9) { // move the arm up
            if (Arm.arrivedPosition(Arm.getArm1Position(), RBrun0.PHASE_9_POSITION)) {
                arm_moving = false;
                RBrun0.phase ++;
            }
            else {
                Arm.moveUp();
                arm_moving = true;
            }
        }
        else if (RBrun0.phase == 10) { // open left claw (let go of the pixel)
            Claws.openLeftClaw();
            RBrun0.phase++;
            sleep(500);
        }
        else if (RBrun0.phase == 11) { // move the arm down
            if (Arm.arrivedPosition(Arm.getArm1Position(), RBrun0.PHASE_11_POSITION)) {
                arm_moving = false;
                RBrun0.phase ++;
            }
            else {
                Arm.moveDown();
                arm_moving = true;
            }
        }

    }
    public void run1() {
        
        if (RBrun1.phase == 1) { // move forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun1.PHASE_1_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun1.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RBrun1.phase == 2) { // open right claw
            Claws.openRightClaw();
            RBrun1.phase ++;
        }
        else if (RBrun1.phase == 3) { // move wrist up
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            RBrun1.phase ++;
        }
        else if (RBrun1.phase == 4) { // move backwards
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun1.PHASE_4_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun1.phase++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RBrun1.phase == 5) { // move sideways (toward back board)
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun1.PHASE_5_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun1.phase++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RBrun1.phase == 6) { // rotate
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun1.PHASE_6_POSITION)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun1.phase++;
            }
            else {
                EgnitionSystem.setRotPower(-1);
            }
        }
        else if (RBrun1.phase == 7) { // move a bit forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun1.PHASE_7_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun1.phase++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RBrun1.phase == 8) { // move the arm up
            if (Arm.arrivedPosition(Arm.getArm1Position(), RBrun1.PHASE_8_POSITION)) {
                arm_moving = false;
                RBrun1.phase ++;
            }
            else {
                Arm.moveDown();
                arm_moving = true;
            }
        }
        else if (RBrun1.phase == 9) { // open left claw (let go of the pixel)
            Claws.openLeftClaw();
            RBrun1.phase++;
            sleep(500);
        }
        else if (RBrun1.phase == 10) { // move the arm down
            if (Arm.arrivedPosition(Arm.getArm1Position(), RBrun1.PHASE_10_POSITION)) {
                arm_moving = false;
                RBrun1.phase ++;
            }
            else {
                Arm.moveDown();
                arm_moving = true;
            }
        }
    }
    public void run2() {
        if (RBrun2.phase == 1) { // move forward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_1_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (RBrun2.phase == 2) { // move sideways
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_2_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
        else if (RBrun2.phase == 3) { // rotate
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_3_POSITION)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase++;
            }
            else {
                EgnitionSystem.setRotPower(1);
            }
        }
        else if (RBrun2.phase == 4) { // move sideways
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_4_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RBrun2.phase == 5) { // open right claw
            Claws.openRightClaw();
            RBrun2.phase ++;
        }
        else if (RBrun2.phase == 6) { // move wrist up
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            RBrun2.phase++;
        }
        else if (RBrun2.phase == 7) { // move sideways (towards back board)
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_7_POSITION)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(1);
            }
        }
        else if (RBrun2.phase == 8) { // move a bit backward
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_8_POSITION)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (RBrun2.phase == 9) { // rotate in 180 degrees
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), RBrun2.PHASE_9_POSITION)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                RBrun2.phase++;
            }
            else {
                EgnitionSystem.setRotPower(1);
            }
        }
        else if (RBrun2.phase == 10) { // move Arm up
            if (Arm.arrivedPosition(Arm.getArm1Position(), RBrun2.PHASE_10_POSITION)) {
                arm_moving = false;
                RBrun2.phase ++;
            }
            else {
                Arm.moveUp();
                arm_moving = true;
            }
        }
        else if (RBrun2.phase == 11) { // open left claw (let go of the pixel)
            Claws.openLeftClaw();
            RBrun2.phase ++;
            sleep(500);
        }
        else if (RBrun2.phase == 12) { // move Arm down
            if (Arm.arrivedPosition(Arm.getArm1Position(), RBrun2.PHASE_12_POSITION)) {

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
        Camera.init(this, hardwareMap);
    }

    public void setVariables() {
        RBrun0.phase = 1;
        RBrun1.phase = 1;
        RBrun2.phase = 1;
    }

}
