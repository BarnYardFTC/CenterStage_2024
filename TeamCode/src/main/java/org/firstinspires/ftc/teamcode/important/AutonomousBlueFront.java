//package org.firstinspires.ftc.teamcode.important;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@Autonomous(name = "Blue front")
//public class AutonomousBlueFront extends LinearOpMode {
//
//    private boolean arm_moving = false;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        initArm();
//        initClaws();
//        initWrist();
//        initEgnitionSystem();
//
//        Claws.closeLeftClaw();
//        Claws.closeRightClaw();
//
//        waitForStart();
//
//        Wrist.setPosition(0.85);
//        BFrun0.phase = 1;
//        while (opModeIsActive()) {
//            if (BFrun0.phase == 1) {
//                if (EgnitionSystem.arrivedPosition(EgnitionSystem.getFlEncoderPosition(), BFrun0.PHASE_1_FINISH, true)) {
//                    EgnitionSystem.setVerticalPower(0);
//                    sleep(500);
//                    EgnitionSystem.resetEncoders();
//                } else {
//                    EgnitionSystem.setVerticalPower(1);
//                }
//            }
//            else if (BFrun0.phase == 2) {
//                Claws.openRightClaw();
//                BFrun0.phase ++;
//            }
//        }
//        EgnitionSystem.updateVariablesAutonomous();
//        EgnitionSystem.runAutonomous();
//    }
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
//        Camera.init(this, hardwareMap, 1);
//    }
//}