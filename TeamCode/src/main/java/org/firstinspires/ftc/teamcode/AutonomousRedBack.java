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

    private int TIME = -1;
    private boolean got_time = false;

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
            telemetry.addData("TIME: ", TIME);
            telemetry.update();
        }

    }
    public void run0() {
        if (RBrun0.phase == 1) { // move forward
            if (!got_time) {
                TIME = RBrun0.PHASE_1_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setVerticalPower(1);
                TIME --;
            }
            else {
                got_time = false;
                EgnitionSystem.setVerticalPower(0);
                RBrun0.phase ++;
            }
        }
        else if (RBrun0.phase == 2) { // move a bit sideways
            if (!got_time) {
                TIME = RBrun0.PHASE_2_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(1);
                TIME --;
            }
            else {
                got_time = false;
                EgnitionSystem.setHorizontalPower(0);
                RBrun0.phase++;
            }
        }
        else if (RBrun0.phase == 3) { // rotate
            if (!got_time) {
                TIME = RBrun0.PHASE_3_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setRotPower(-1);
                TIME --;
            }
            else {
                got_time = false;
                EgnitionSystem.setRotPower(0);
                RBrun0.phase++;
            }
        }
        else if (RBrun0.phase == 4) { // move sideways
            if (!got_time) {
                TIME = RBrun0.PHASE_4_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(-1);
                TIME --;
            }
            else {
                got_time = false;
                EgnitionSystem.setHorizontalPower(0);
                RBrun0.phase++;
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
            if (!got_time) {
                TIME = RBrun0.PHASE_7_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(1);
                TIME --;
            }
            else {
                EgnitionSystem.setHorizontalPower(0);
                got_time = false;
                RBrun0.phase ++;
            }
        }
        else if (RBrun0.phase == 8) { // move a bit forward
            if (!got_time) {
                TIME = RBrun0.PHASE_8_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setVerticalPower(1);
                TIME --;
            }
            else {
                EgnitionSystem.setVerticalPower(0);
                got_time = false;
                RBrun0.phase ++;
            }
        }
        else if (RBrun0.phase == 9) { // move the arm up
            if (!got_time) {
                TIME = RBrun0.PHASE_9_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                Arm.moveUp();
                arm_moving = true;
                TIME --;
            }
            else {
                arm_moving = false;
                got_time = false;
                RBrun0.phase ++;
            }
        }
        else if (RBrun0.phase == 10) { // open left claw (let go of the pixel)
            Claws.openLeftClaw();
            RBrun0.phase++;
            sleep(500);
        }
        else if (RBrun0.phase == 11) { // move the arm down
            if (!got_time) {
                TIME = RBrun0.PHASE_11_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                Arm.moveDown();
                arm_moving = true;
                TIME --;
            }
            else {
                arm_moving = false;
                got_time = false;
                RBrun0.phase ++;
            }
        }

    }
    public void run1() {
        
        if (RBrun1.phase == 1) { // move forward
            if (!got_time) {
                TIME = RBrun1.PHASE_1_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setVerticalPower(1);
                TIME --;
            }
            else {
                got_time = false;
                EgnitionSystem.setVerticalPower(0);
                RBrun1.phase ++;
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
            if (!got_time) {
                TIME = RBrun1.PHASE_4_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setVerticalPower(-1);
                TIME--;
            }
            else {
                got_time = false;
                EgnitionSystem.setVerticalPower(0);
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 5) { // rotate
            if (!got_time) {
                TIME = RBrun1.PHASE_5_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setRotPower(-1);
                TIME--;
            }
            else {
                EgnitionSystem.setRotPower(0);
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 6) { // move sideways (towards back bord)
            if (!got_time) {
                TIME = RBrun1.PHASE_6_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(1);
                TIME--;
            }
            else {
                got_time = false;
                EgnitionSystem.setHorizontalPower(0);
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 7) { // move a bit forward
            if (!got_time) {
                TIME = RBrun1.PHASE_7_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setVerticalPower(1);
                TIME--;
            }
            else {
                EgnitionSystem.setVerticalPower(0);
                got_time = false;
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 8) { // move the arm up
            if (!got_time) {
                TIME = RBrun1.PHASE_8_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                Arm.moveUp();
                arm_moving = true;
                TIME --;
            }
            else {
                arm_moving = false;
                got_time = false;
                RBrun1.phase++;
            }
        }
        else if (RBrun1.phase == 9) { // open left claw (let go of the pixel)
            Claws.openLeftClaw();
            RBrun1.phase++;
            sleep(500);
        }
        else if (RBrun1.phase == 10) { // move the arm down
            if (!got_time) {
                TIME = RBrun1.PHASE_8_FINISH;
                got_time = true;
            }
            if (TIME > 0) {
                Arm.moveDown();
                arm_moving = true;
                TIME --;
            }
            else {
                arm_moving = false;
                got_time = false;
                RBrun1.phase ++;
            }
        }
    }
    public void run2() {
        if (RBrun2.phase == 1) { // move forward
            if (!got_time) {
                TIME = RBrun2.PHASE_1_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setVerticalPower(1);
                TIME --;
            }
            else {
                EgnitionSystem.setVerticalPower(0);
                got_time = false;
                RBrun2.phase++;
            }
        }
        else if (RBrun2.phase == 2) { // move sideways
            if (!got_time) {
                TIME = RBrun2.PHASE_2_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(-1);
                TIME --;
            }
            else {
                EgnitionSystem.setHorizontalPower(0);
                got_time = false;
                RBrun2.phase ++;
            }
        }
        else if (RBrun2.phase == 3) { // rotate
            if (!got_time) {
                TIME = RBrun2.PHASE_3_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setRotPower(1);
                TIME --;
            }
            else {
                EgnitionSystem.setRotPower(0);
                got_time = false;
                RBrun2.phase++;
            }
        }
        else if (RBrun2.phase == 4) { // move sideways
            if (!got_time) {
                TIME = RBrun2.PHASE_4_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(1);
                TIME --;
            }
            else {
                EgnitionSystem.setHorizontalPower(0);
                got_time = false;
                RBrun2.phase ++;
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
            if (!got_time) {
                TIME = RBrun2.PHASE_7_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setHorizontalPower(1);
                TIME--;
            }
            else {
                EgnitionSystem.setHorizontalPower(0);
                got_time = false;
                RBrun2.phase ++;
            }
        }
        else if (RBrun2.phase == 8) { // rotate in 180 degrees
            if (!got_time) {
                TIME = RBrun2.PHASE_8_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                EgnitionSystem.setRotPower(-1);
                TIME --;
            }
            else {
                EgnitionSystem.setRotPower(0);
                got_time = false;
                RBrun2.phase ++;
            }
        }
        else if (RBrun2.phase == 9) { // move Arm up
            if (!got_time) {
                TIME = RBrun2.PHASE_9_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                Arm.moveUp();
                arm_moving = true;
                TIME --;
            }
            else {
                arm_moving = false;
                got_time = false;
                RBrun2.phase ++;
            }
        }
        else if (RBrun2.phase == 10) { // open left claw (let go of the pixel)
            Claws.openLeftClaw();
            RBrun2.phase ++;
            sleep(500);
        }
        else if (RBrun2.phase == 11) { // move Arm down
            if (!got_time) {
                TIME = RBrun2.PHASE_11_TIME;
                got_time = true;
            }
            if (TIME > 0) {
                Arm.moveDown();
                arm_moving = true;
                TIME --;
            }
            else {
                arm_moving = false;
                got_time = false;
                RBrun2.phase ++;
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
        RBrun1.phase = 1;
        RBrun0.phase = 1;
    }

}
