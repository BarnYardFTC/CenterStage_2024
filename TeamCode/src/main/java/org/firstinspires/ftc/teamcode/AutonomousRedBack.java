package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red back")
public class AutonomousRedBack extends LinearOpMode {

    private int spike_position = -1;

    private final int PHASE_ONE_ENCODER_FINISH_POSITION = 500;

    private int phase = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();
        initCamera();

        while (opModeInInit()) {
            spike_position = PixelDetector.getSpike_position();
            telemetry.addData("Spike position: ", spike_position);
            telemetry.update();
        }
        Camera.close();

        waitForStart();

        while (opModeIsActive()) {
//            if (spike_position == 0) {
//                run0();
//            }
//            else if (spike_position == 1) {
//                run1();
//            }
//            else {
//                run2();
//            }
        }

    }
    public void initClaws(){
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
    }
    public void initWrist() {
        telemetry.addLine("Press Y to move the wrist Up & Down");
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
    }
    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        Arm.init(motor);
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

    public void run0() {

    }
    public void run1() {

        if (phase == 1) {
            EgnitionSystem.setVerticalPower(0.5);
            if (EgnitionSystem.getFlEncoderPosition() >= PHASE_ONE_ENCODER_FINISH_POSITION) {
                phase++;
            }
        }


        EgnitionSystem.updateVariablesAutonomous();
        EgnitionSystem.runAutonomous();
    }
    public void run2() {

    }
}
