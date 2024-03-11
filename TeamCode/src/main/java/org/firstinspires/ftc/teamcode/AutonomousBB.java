package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "Blue Back")
@Config

public class AutonomousBB extends LinearOpMode{

    spike_position position;
    public int phase;
    static double SLOW_SPEED = 0.2;
    static double FAST_SPEED = 0.5;

    static double ARM_SPEED = 0.6;

    static int PHASE_1_L = 100; // Forward
    static int PHASE_2_L = -650; // Left
    static int PHASE_3_L = 410; // Rotate right
    static int PHASE_4_L = -430; // Forward
    static int PHASE_6_L = -265; // Left (Slow)
    static int PHASE_7_L = 480; // Backward (Slow)
    static int PHASE_8_L = -2250; // Arm up
    static int PHASE_10_L = Arm.MINIMAL_HOLD_POSITION; // Arm down
    static int PHASE_11_L = 350; // Backward
    static int PHASE_12_L = -300; // Left

    public static int PHASE_1_C = 600; // Forward
    public static double PHASE_2_C = 0.49; // Wrist position
    public static int PHASE_3_C = -1200; // Left
    public static int PHASE_4_C = 440; // Rotate right
//    public static int PHASE_6_C = 50; // Backward (Slow)
    public static int PHASE_7_C = -2390; // Arm up
    public static int PHASE_9_C = Arm.MINIMAL_HOLD_POSITION; // Arm down
    public static int PHASE_10_C = 600; // Backward
    public static int PHASE_11_C = -400; // Left

    static int PHASE_1_R = 650; // Forward
    static int PHASE_2_R = 470; // Rotate right
    static int PHASE_3_R = -150; // Forward (Slow)
    static int PHASE_4_R = -900; // Left
    static double PHASE_5_R = 0.4; // Wrist position
//    static int PHASE_6_R = -50; // Forward (Slow)
    static int PHASE_7_R = -2350; // Arm up
    static int PHASE_9_R = Arm.MINIMAL_HOLD_POSITION; // Arm down
    static int PHASE_10_R = 1100; // Backward
    static int PHASE_11_R = -500; // Left

    enum spike_position {
        LEFT,
        RIGHT,
        CENTER
    }

    @Override
    public void runOpMode() throws InterruptedException {


        // Initialize everything
        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();
        initCamera();
        phase = 1;

        // Close the claws
        Claws.closeRightClaw();
        Claws.closeLeftClaw();

        Wrist.setPosition(0);

        // Find the spike with pixel and print in telemetry
        while (opModeInInit()) {
            if (PixelDetectorBB.getSpike_position() == 0) {
                position = spike_position.LEFT;
            }
            else if (PixelDetectorBB.getSpike_position() == 1) {
                position = spike_position.CENTER;
            }
            else {
                position = spike_position.RIGHT;
            }

            telemetry.addData("Spike Position: ", position);
            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(3));
            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(3));
            telemetry.update();
        }
        Camera.close(3);

        waitForStart();

        // move the wrist down
        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.2);
        sleep(500);

        while (opModeIsActive()) {

            // Choose a path according to the spike position
            if (position == spike_position.RIGHT) {
                Right();
            }
            else if (position == spike_position.LEFT) {
                Left();
            }
            else {
                Center();
            }

            // Adjust the wrist position according to the arm position
//            if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
//                Wrist.setPosition(Wrist.WRIST_UNLOADING_POSITION + 0.018 * ((int) ((Arm.getArm1Position() - Arm.UNLOADING_POSITION) / -50)));
//            }
//            0.234

            // Move the Egnition system
            EgnitionSystem.updateVariablesAutonomous();
            EgnitionSystem.runAutonomous();

            // Add data to telemetry
            telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
            telemetry.addData("FL encoder position: ", EgnitionSystem.getFlEncoderPosition());
            telemetry.update();
        }
    }

    public void Left() {

        if (phase == 1) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_1_L, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (phase == 2) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_2_L, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
        else if (phase == 3) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_3_L, true)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setRotPower(1);
            }
        }
        else if (phase == 4) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_4_L, false)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (phase == 5) {
            Claws.openRightClaw();
            sleep(500);
            phase ++;
        }
        else if (phase == 6) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_6_L, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
            }
        }
        else if (phase == 7) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_7_L, true)) {
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
                Wrist.setPosition(0.3);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
            }
        }
        else if (phase == 8) {
            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_8_L, false)) {
                Arm.brake();
                sleep(1800);
                phase ++;
            }
            else {
                Arm.moveUp(ARM_SPEED);
            }
        }
        else if (phase == 9) {
            Claws.openLeftClaw();
            sleep(500);
            phase ++;
        }
        else if (phase == 10) {
            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_10_L, true)) {
                Arm.brake();
                sleep(500);
                phase ++;
            }
            else {
                Arm.moveDown(ARM_SPEED);
            }
        }
        else if (phase == 11) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_11_L, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (phase == 12) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_12_L, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
    }
    public void Center() {

        if (phase == 1) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_1_C, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (phase == 2) {
            Claws.openRightClaw();
            sleep(300);
            Wrist.setPosition(PHASE_2_C);
            phase ++;
        }
        else if (phase == 3) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_3_C, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
        else if (phase == 4) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_4_C, true)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setRotPower(1);
            }
        }
        else if (phase == 5) {
            phase ++;
        }
        else if (phase == 6) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_6_C, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(-1);
//                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
//            }
            phase ++;
        }
        else if (phase == 7) {
            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_7_C, false)) {
                Arm.brake();
                sleep(1500);
                phase ++;
            }
            else {
                Arm.moveUp(ARM_SPEED);
            }
        }
        else if (phase == 8) {
            Claws.openLeftClaw();
            sleep(500);
            phase ++;
        }
        else if (phase == 9) {
            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_9_C, true)) {
                Arm.brake();
                phase ++;
            }
            else {
                Arm.moveDown(ARM_SPEED);
            }
        }
        else if (phase == 10) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_10_C, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (phase == 11) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_11_C, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
    }
    public void Right () {

        if (phase == 1) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_1_R, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.05);
                EgnitionSystem.setVerticalPower(1);
            }
        }
        else if (phase == 2) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_2_R, true)) {
                EgnitionSystem.setRotPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setRotPower(1);
            }
        }
        else if (phase == 3) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_3_R, false)) {
                EgnitionSystem.setVerticalPower(0);
                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
                sleep(500);
                Claws.openRightClaw();
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(1);
                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
            }
        }
        else if (phase == 4) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_4_R, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
            }
        }
        else if (phase == 5) {
            Wrist.setPosition(PHASE_5_R);
            phase ++;
        }
        else if (phase == 6) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_6_R, false)) {
//                EgnitionSystem.setVerticalPower(0);
//                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(1);
//                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
//            }
            phase ++;
        }
        else if (phase == 7) {
            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_7_R, false)) {
                Arm.brake();
                sleep(1500);
                phase ++;
            }
            else {
                Arm.moveUp(ARM_SPEED);
            }
        }
        else if (phase == 8) {
            Claws.openLeftClaw();
            sleep(500);
            phase ++;
        }
        else if (phase == 9) {
            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_9_R, true)) {
                Arm.brake();
                sleep(500);
                phase ++;
            }
            else {
                Arm.moveDown(ARM_SPEED);
            }
        }
        else if (phase == 10) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_10_R, true)) {
                EgnitionSystem.setVerticalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setVerticalPower(-1);
            }
        }
        else if (phase == 11) {
            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_11_R, false)) {
                EgnitionSystem.setHorizontalPower(0);
                sleep(500);
                EgnitionSystem.resetEncoders();
                phase ++;
            }
            else {
                EgnitionSystem.setHorizontalPower(-1);
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
        DcMotor motor = hardwareMap.get(DcMotor.class, "rightArm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "leftArm");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }
    public void initEgnitionSystem() {
        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "rightBack");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
        EgnitionSystem.initEncoders();
    }
    public void initCamera() {
        Camera.init(this, hardwareMap, 3);
    }
}