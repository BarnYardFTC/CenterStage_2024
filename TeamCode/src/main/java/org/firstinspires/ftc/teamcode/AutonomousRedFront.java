//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@Autonomous(name = "Red Front")
//public class AutonomousRedFront extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        initArm();
//        initClaws();
//        initWrist();
//        initEgnitionSystem();
//        initCamera();
//
//        Wrist.moveDown();
//
//        Claws.closeLeftClaw();
//        Claws.closeRightClaw();
//
//        waitForStart();
//        while (opModeIsActive()) {
//            if (RFrun1.phase == 1) {
//                EgnitionSystem.setVerticalPower(1);
//                if (EgnitionSystem.getFlEncoderPosition() >= RFrun1.PHASE1_COMPLETED_POSITION) {
//                    RFrun1.phase++;
//                    EgnitionSystem.setVerticalPower(0);
//                }
//            }
//            else if (RFrun1.phase == 2) {
//                EgnitionSystem.setHorizontalPower(1);
//                if (EgnitionSystem.getFlEncoderPosition() <= RFrun1.PHASE2_COMPLETED_POSITION) {
//                    RFrun1.phase++;
//                    EgnitionSystem.setHorizontalPower(0);
//                }
//            }
//            else if (RFrun1.phase == 3) {
//                Claws.openRightClaw();
//                Claws.openLeftClaw();
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
//        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
//        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
//        Arm.init(motor, motor2);
//        Arm.addDataToTelemetry(telemetry);
//    }
//    public void initEgnitionSystem() {
//        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
//        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
//        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
//        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
//        IMU imu = hardwareMap.get(IMU.class, "imu");
//
//        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
//        EgnitionSystem.initEncoders();
//    }
//    public void initCamera() {
//        Camera.init(this, hardwareMap);
//    }
//}
