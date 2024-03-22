package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "RRRB")

public class RRRB extends LinearOpMode {
    spike_position position;
    static double SLOW_SPEED = 0.2;
    static double FAST_SPEED = 0.5;

    static double ARM_SPEED = 0.6;

    SampleMecanumDrive drive;

    enum spike_position {
        LEFT,
        RIGHT,
        CENTER
    }

    static class centerTrajectories {
        static Trajectory traj1;
        static Trajectory traj2;
        static Trajectory traj3;
        static Trajectory traj4;
        static Trajectory traj5;
    }
    @Override
    public void runOpMode() {

        // Initialize everything
        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();
        initCamera();
        initLed();

        // Close the claws
        Claws.closeRightClaw();
        Claws.closeLeftClaw();

        Wrist.setPosition(0);

        drive = new SampleMecanumDrive(hardwareMap);

        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d()).build();

        // Find the spike with pixel and print in telemetry
        while (opModeInInit()) {
            if (PixelDetectorRB.getSpike_position() == 0) {
                position = spike_position.LEFT;
                HardwareLocal.green();
            }
            else if (PixelDetectorRB.getSpike_position() == 1) {
                position = spike_position.CENTER;
                HardwareLocal.green();
            }
            else {
                position = spike_position.RIGHT;
                HardwareLocal.green();
            }

            telemetry.addData("Spike Position: ", position);
            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(1));
            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(1));
            telemetry.update();
        }
        Camera.close(1);

        // move the wrist down
        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.2);
        sleep(700);

        // Choose a path according to the spike position
        if (position == spike_position.RIGHT) {
            RightInit();
        }
        else if (position == spike_position.LEFT) {
            LeftInit();
        }
        else {
            CenterInit();
        }

        if (isStopRequested()) return;
    }

    public void RightInit() {}
    public void CenterInit() {
        centerTrajectories.traj1 = drive.trajectoryBuilder(new Pose2d())
                .forward(23)
                .build();
    }
    public void LeftInit() {}
    public void RightRun() {}
    public void CenterRun() {

    }
    public void LeftRun() {

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
//    }
//    public void initCamera() {
//        Camera.init(this, hardwareMap, 1);
//    }
//    public void initLed() {
//        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
//        HardwareLocal.init(ledDriver);
//    }
//}
