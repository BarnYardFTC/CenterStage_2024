//package org.firstinspires.ftc.teamcode;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.trajectory.Trajectory;
//import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@Autonomous(name = "RRRB")
//
//public class RRBB extends LinearOpMode {
//    spike_position position;
//    static double SLOW_SPEED = 0.2;
//    static double FAST_SPEED = 0.5;
//
//    static double ARM_SPEED = 0.6;
//
//    enum spike_position {
//        LEFT,
//        RIGHT,
//        CENTER
//    }
//    @Override
//    public void runOpMode() {
//
//        // Initialize everything
//        initArm();
//        initClaws();
//        initWrist();
//        initEgnitionSystem();
//        initCamera();
//        initLed();
//
//        // Close the claws
//        Claws.closeRightClaw();
//        Claws.closeLeftClaw();
//
//        Wrist.setPosition(0);
//
//        // Find the spike with pixel and print in telemetry
//        while (opModeInInit()) {
//            if (PixelDetectorBB.getSpike_position() == 0) {
//                position = spike_position.LEFT;
//                HardwareLocal.green();
//            }
//            else if (PixelDetectorBB.getSpike_position() == 1) {
//                position = spike_position.CENTER;
//                HardwareLocal.green();
//            }
//            else {
//                position = spike_position.RIGHT;
//                HardwareLocal.blink();
//            }
//
//            telemetry.addData("Spike Position: ", position);
//            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(3));
//            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(3));
//            telemetry.update();
//        }
//        Camera.close(3);
//
//        waitForStart();
//
//        // move the wrist down
//        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.2);
//        sleep(700);
//
//        while (opModeIsActive()) {
//
//            // Choose a path according to the spike position
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
//            telemetry.update();
//        }
//    }
//
//    public void Right() {}
//    public void Center() {}
//    public void Left() {}
//
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
//        Camera.init(this, hardwareMap, 1);
//    }
//    public void initLed() {
//        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
//        HardwareLocal.init(ledDriver);
//    }
//}
