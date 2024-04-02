//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@Autonomous (name = "Blue Front")
//
//public class AutonomousBF extends LinearOpMode{
////
//    spike_position position;
//    public int phase;
//    static double SLOW_SPEED = 0.2;
//    static double FAST_SPEED = 0.5;
//
//    static double ARM_SPEED = 0.6;
//
//    static int PHASE_1_L = 690; // Forward
//    static int PHASE_2_L = -510; // Rotate left
////    public static int PHASE_3_L = 50; // Left (slow)
////    public static int PHASE_5_L = -50; // Right (slow)
//    static int PHASE_7_L = 600; // Forward
//    static int PHASE_8_L = -1500; // Rotate left 180
//    static int PHASE_9_L = -3300; // Left
//    static int PHASE_10_L = 1000; // Backward
//    static int PHASE_11_L = -2250; // Arm up
//    static int PHASE_13_L = Arm.MINIMAL_HOLD_POSITION; // Arm down
//    static int PHASE_14_L = -800; // Forward
//    static int PHASE_15_L = -300; // Left
//
//    static int PHASE_1_C = 1550; // Forward
//    static int PHASE_2_C = 1530; // Rotate right 180
//    static int PHASE_4_C = -5; // Forward (slow)
//    static int PHASE_6_C = -500; // Rotate left
//    static int PHASE_7_C = -2980; // Left
//    static int PHASE_8_C = 880; // Backward
//    static int PHASE_9_C = -2250; // Arm up
//    static int PHASE_11_C = Arm.MINIMAL_HOLD_POSITION; // Arm down
//    static int PHASE_12_C = -540; // Forward
//    static int PHASE_13_C = -300; // Left
//
//    static int PHASE_1_R = 640; // Forward
//    static int PHASE_2_R = 515; // Rotate right
//    static int PHASE_4_R = -5;  // Left (Slow)
//    static int PHASE_6_R = 5;// Right (Slow)
//    static int PHASE_7_R = -500; // Forward
//    static int PHASE_8_R = -3100; // Left
//    static int PHASE_9_R = 400; // Backward
//    static int PHASE_10_R = -2250; // Arm up
//    static int PHASE_12_R = Arm.MINIMAL_HOLD_POSITION; // Arm down
//    static int PHASE_13_R = -200; // Forward
//    static int PHASE_14_R = -300; // Left
//
//
//    enum spike_position {
//        LEFT,
//        RIGHT,
//        CENTER
//    }
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//
//        // Initialize everything
//        initArm();
//        initClaws();
//        initWrist();
//        initEgnitionSystem();
//        initCamera();
//        initLed();
//        phase = 1;
//
//        // Close the claws
//        Claws.closeRightClaw();
//        Claws.closeLeftClaw();
//
//        Wrist.setPosition(0);
//
//        // Find the spike with pixel and print in telemetry
//        while (opModeInInit()) {
//            if (PixelDetectorBF.getSpike_position() == 0) {
//                position = spike_position.LEFT;
//                HardwareLocal.green();
//            }
//            else if (PixelDetectorBF.getSpike_position() == 1) {
//                position = spike_position.CENTER;
//                HardwareLocal.green();
//            }
//            else {
//                position = spike_position.RIGHT;
//                HardwareLocal.green();
//            }
//
//            telemetry.addData("Spike Position: ", position);
//            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(4));
//            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(4));
//            telemetry.update();
//        }
//        Camera.close(4);
//
//        waitForStart();
//
//        // move the wrist down
//        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.2);
//        sleep(700);
//
//        while (opModeIsActive()) {
//
//             // Choose a path according to the spike position
//            if (position == spike_position.RIGHT) {
//                Right();
//            }
//            else if (position == spike_position.LEFT) {
//                Left();
//            }
//            else {
//                Center();
//            }
//
//            // Adjust the wrist position according to the arm position
//            if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
//                Wrist.setPosition(Wrist.WRIST_UNLOADING_POSITION + 0.018 * ((int) ((Arm.getArm1Position() - Arm.UNLOADING_POSITION) / -50)));
//            }
//
//            // Move the Egnition system
//            EgnitionSystem.updateVariablesAutonomous();
//            EgnitionSystem.runAutonomous();
//
//            // Add data to telemetry
//            telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
//            telemetry.addData("FL encoder position: ", EgnitionSystem.getFlEncoderPosition());
//            telemetry.addData("Heading: ", EgnitionSystem.getHeading());
//            telemetry.update();
//        }
//    }
//    public void Left() {
//
//        if (phase == 1) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_1_L, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(1);
//            }
//        }
//        else if (phase == 2) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_2_L, false)) {
//                EgnitionSystem.setRotPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setRotPower(-1);
//            }
//        }
//        else if (phase == 3) {
////            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_3_L, true)) {
////                EgnitionSystem.setHorizontalPower(0);
////                sleep(500);
////                EgnitionSystem.resetEncoders();
////                phase ++;
////            }
////            else {
////                EgnitionSystem.setHorizontalPower(-1);
////                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
////            }
//            phase ++;
//        }
//        else if (phase == 4) {
//            Claws.openRightClaw();
//            sleep(500);
//            phase ++;
//        }
//        else if (phase == 5) {
////            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_5_L, false)) {
////                EgnitionSystem.setHorizontalPower(0);
////                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
////                sleep(500);
////                EgnitionSystem.resetEncoders();
////                phase ++;
////            }
////            else {
////                EgnitionSystem.setHorizontalPower(1);
////            }
//            phase ++;
//        }
//        else if (phase == 6) {
//            Wrist.moveUp();
//            sleep(500);
//            phase ++;
//        }
//        else if (phase == 7) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_7_L, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(1);
//            }
//        }
//        else if (phase == 8) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_8_L, false)) {
//                EgnitionSystem.setRotPower(0);
//                sleep(10000);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setRotPower(-1);
//            }
//        }
//        else if (phase == 9) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_9_L, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//                EgnitionSystem.setVerticalPower(-0.04);
//            }
//        }
//        else if (phase == 10) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_10_L, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(-1);
//            }
//        }
//        else if (phase == 11) {
//            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_11_L, false)) {
//                Arm.brake();
//                sleep(1500);
//                phase ++;
//            }
//            else {
//                Arm.moveUp(ARM_SPEED);
//            }
//        }
//        else if (phase == 12) {
//            Claws.openLeftClaw();
//            sleep(500);
//            phase ++;
//        }
//        else if (phase == 13) {
//            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_13_L, true)) {
//                Arm.brake();
//                phase ++;
//            }
//            else {
//                Arm.moveDown(ARM_SPEED);
//            }
//        }
//        else if (phase == 14) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_14_L, false)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(1);
//            }
//        }
//        else if (phase == 15) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_15_L, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//            }
//        }
//    }
//    public void Center() {
//
//        if (phase == 1) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_1_C, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.1);
//                EgnitionSystem.setVerticalPower(1);
//            }
//        }
//        else if (phase == 2) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_2_C, true)) {
//                EgnitionSystem.setRotPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setRotPower(1);
//            }
//        }
//        else if (phase == 3) {
//            Claws.openRightClaw();
//            sleep(500);
//            phase ++;
//        }
//        else if (phase == 4) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_4_C, false)) {
//                EgnitionSystem.setVerticalPower(0);
//                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
//                EgnitionSystem.setVerticalPower(1);
//            }
//        }
//        else if (phase == 5) {
//            Wrist.moveUp();
//            phase ++;
//        }
//        else if (phase == 6) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_6_C, false)) {
//                EgnitionSystem.setRotPower(0);
//                sleep(10000);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setRotPower(-1);
//            }
//        }
//        else if (phase == 7) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_7_C, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//            }
//        }
//        else if (phase == 8) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_8_C, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(-1);
//            }
//        }
//        else if (phase == 9) {
//            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_9_C, false)) {
//                Arm.brake();
//                sleep(1800);
//                phase ++;
//            }
//            else {
//                Arm.moveUp(ARM_SPEED);
//            }
//        }
//        else if (phase == 10) {
//            Claws.openLeftClaw();
//            sleep(500);
//            phase ++;
//        }
//        else if (phase == 11) {
//            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_11_C, true)) {
//                Arm.brake();
//                phase ++;
//            }
//            else {
//                Arm.moveDown(ARM_SPEED);
//            }
//        }
//        else if (phase == 12) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_12_C, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(-1);
//            }
//        }
//        else if (phase == 13) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_13_C, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//            }
//        }
//    }
//    public void Right () {
//
//        if (phase == 1) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_1_R, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                Wrist.setPosition(Wrist.WRIST_DOWN_POSITION - 0.1);
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(1);
//            }
//        }
//        else if (phase == 2) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_2_R, true)) {
//                EgnitionSystem.setRotPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setRotPower(1);
//            }
//        }
//
//        else if (phase == 3) {
//            Claws.openRightClaw();
//            phase ++;
//        }
//        else if (phase == 4) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_4_R, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
//            }
//        }
//        else if (phase == 5) {
//            Wrist.moveUp();
//            phase ++;
//        }
//        else if (phase == 6) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_6_R, true)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.setHorizontalPower(FAST_SPEED);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setAutonomousMovingPower(SLOW_SPEED);
//                EgnitionSystem.setHorizontalPower(1);
//            }
//        }
//        else if (phase == 7) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_7_R, false)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(10000);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(1);
//                EgnitionSystem.setHorizontalPower(0);
//                EgnitionSystem.setAutonomousMovingPower(FAST_SPEED);
//            }
//        }
//        else if (phase == 8) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_8_R, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//            }
//        }
//        else if (phase == 9) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_9_R, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(-1);
//            }
//        }
//        else if (phase == 10) {
//            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_10_R, false)) {
//                Arm.brake();
//                sleep(1500);
//                phase ++;
//            }
//            else {
//                Arm.moveUp(ARM_SPEED);
//            }
//        }
//        else if (phase == 11) {
//            Claws.openLeftClaw();
//            sleep(500);
//            phase ++;
//        }
//        else if (phase == 12) {
//            if (Arm.arrivedPosition(Arm.getArm1Position(), PHASE_12_R, true)) {
//                Arm.brake();
//                phase ++;
//            }
//            else {
//                Arm.moveDown(ARM_SPEED);
//            }
//        }
//        else if (phase == 13) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_13_R, true)) {
//                EgnitionSystem.setVerticalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setVerticalPower(-1);
//            }
//        }
//        else if (phase == 14) {
//            if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), PHASE_14_R, false)) {
//                EgnitionSystem.setHorizontalPower(0);
//                sleep(500);
//                EgnitionSystem.resetEncoders();
//                phase ++;
//            }
//            else {
//                EgnitionSystem.setHorizontalPower(-1);
//            }
//        }
//    }
//    public void initClaws(){
//        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
//        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
//        Claws.init(left_claw, right_claw);
//    }
//    public void initWrist() {
//        Servo servo = hardwareMap.get(Servo.class, "wrist");
//        Wrist.init(servo);
//    }
//    public void initArm() {
//        DcMotor motor = hardwareMap.get(DcMotor.class, "rightArm");
//        DcMotor motor2 = hardwareMap.get(DcMotor.class, "leftArm");
//        Arm.init(motor, motor2);
//        Arm.addDataToTelemetry(telemetry);
//    }
//    public void initEgnitionSystem() {
//        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "leftFront");
//        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "rightFront");
//        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "leftBack");
//        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "rightBack");
//        IMU imu = hardwareMap.get(IMU.class, "imu");
//
//        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
//        EgnitionSystem.initEncoders();
//    }
//    public void initCamera() {
//        Camera.init(this, hardwareMap, 4);
//    }
//    public void initLed() {
//        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
//        HardwareLocal.init(ledDriver);
//    }
//}