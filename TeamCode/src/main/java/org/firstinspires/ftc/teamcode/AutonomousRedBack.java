package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red back")
public class AutonomousRedBack extends LinearOpMode {

    private int spike_position = 0;
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
        if (RBrun1.phase == 1) {
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
        else if (RBrun1.phase == 2) {
            Claws.openRightClaw();
            RBrun1.phase++;
        }
        else if (RBrun1.phase == 3) {
            Wrist.setPosition(0.3);
            RBrun1.phase++;
        }
        else if (RBrun1.phase == 4) {

        }

    }
    public void run1() {

    }
    public void run2() {

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
        RBrun1.PHASE_4_WHEEL_FINISHED = false;
        RBrun1.PHASE_4_ARM_COMPLETED = false;
    }

}
