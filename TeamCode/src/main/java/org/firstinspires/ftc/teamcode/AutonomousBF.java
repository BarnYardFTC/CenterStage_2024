package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "Blue Front")

public class AutonomousBF extends LinearOpMode{
//
    spike_position position;
    boolean arm_moving;

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

        // Close the claws
        Claws.closeRightClaw();
        Claws.closeLeftClaw();

        // Find the spike with pixel and print in telemetry
        while (opModeInInit()) {
            if (PixelDetectorRB.getSpike_position() == 0) {
                position = spike_position.LEFT;
            }
            else if (PixelDetectorRB.getSpike_position() == 1) {
                position = spike_position.CENTER;
            }
            else {
                position = spike_position.RIGHT;
            }

            telemetry.addData("Spike Position: ", position);
            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(4));
            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(4));
            telemetry.update();
        }
        Camera.close(4);

        waitForStart();

        // move the wrist down
        Wrist.moveDown();

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

            // In case the arm isn't moving (most of the time) brake/ stop moving the arm
            if (!arm_moving) {
                if ( ! Arm.passedMinimalHoldPosition()) {
                    Arm.stopMoving();
                }
                else {
                    Arm.brake();
                }
            }

            // Adjust the wrist position according to the arm position
            if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
                Wrist.setPosition(Wrist.WRIST_UNLOADING_POSITION + 0.018 * ((int) ((Arm.getArm1Position() - Arm.UNLOADING_POSITION) / -50)));
            }

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

    }
    public void Center() {

    }
    public void Right () {

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
        Camera.init(this, hardwareMap, 4);
    }
}