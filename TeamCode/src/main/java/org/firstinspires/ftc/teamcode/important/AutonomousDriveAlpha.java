//package org.firstinspires.ftc.teamcode.important;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//@Autonomous(name = "Autonomous Drive Alpha")
//public class AutonomousDriveAlpha extends LinearOpMode{
//
//    DcMotor fl_wheel;
//    DcMotor fr_wheel;
//    DcMotor bl_wheel;
//    DcMotor br_wheel;
//
//    @Override
//    public void runOpMode(){
//
//        fr_wheel = hardwareMap.get(DcMotor.class, "front_right");
//        br_wheel = hardwareMap.get(DcMotor.class, "back_right");
//        bl_wheel = hardwareMap.get(DcMotor.class, "back_left");
//        fl_wheel = hardwareMap.get(DcMotor.class, "front_left");
//
//        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
//        fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        fl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        fr_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        bl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        br_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        waitForStart();
//
//        fl_wheel.setPower(0.5);
//        fr_wheel.setPower(0.5);
//        bl_wheel.setPower(0.5);
//        br_wheel.setPower(0.5);
//
//        fl_wheel.setTargetPosition(800);
//        fr_wheel.setTargetPosition(800);
//        bl_wheel.setTargetPosition(800);
//        br_wheel.setTargetPosition(800);
//
//
//        boolean arrived_target_position = false;
//        boolean reset = false;
//
//        while(opModeIsActive()){
//            if (fl_wheel.getCurrentPosition() != 800 && !arrived_target_position){
//                fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                continue;
//            }
//            arrived_target_position = true;
//            if (!reset){
//                fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//                fl_wheel.setTargetPosition(800);
//                fr_wheel.setTargetPosition(-800);
//                bl_wheel.setTargetPosition(-800);
//                br_wheel.setTargetPosition(800);
//
//                reset = true;
//            }
//            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
//    }
//}