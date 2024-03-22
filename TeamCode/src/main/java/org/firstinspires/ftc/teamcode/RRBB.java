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

@Autonomous(name = "RRBB")

//        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//        Trajectory Trajectory1 = drive.trajectoryBuilder(drive.getPoseEstimate())
//                .forward(10)
//                .build();
//        Trajectory Trajectory2 = drive.trajectoryBuilder(Trajectory1.end())
//                .strafeLeft(10)
//                .build();
//        Trajectory traj3 = drive.trajectoryBuilder(Trajectory2.end())
//                .strafeRight(10)
//                .build();
//        Trajectory traj4 = drive.trajectoryBuilder(new Pose2d(traj3.end().getX(), traj3.end().getY(), Math.toRadians(90)))
//                .lineToConstantHeading(new Vector2d(20,0))
//                .build();
//
//        waitForStart();
//
//        if(isStopRequested()) return;
////
//        drive.followTrajectory(Trajectory1);
//        drive.followTrajectory(Trajectory2);
//        drive.followTrajectory(traj3);
//        drive.turn(Math.toRadians(90));
//        drive.followTrajectory(traj4);
//        drive.turn(Math.toRadians(drive.getPoseEstimate().getHeading()-90));

public class RRBB extends LinearOpMode {

    spike_position position;

    enum spike_position {
        LEFT,
        RIGHT,
        CENTER
    }

    @Override
    public void runOpMode() {

        // Initialize everything
        initArm();
        initClaws();
        initWrist();
        initCamera();
        initLed();

        // Close the claws
        Claws.closeRightClaw();
        Claws.closeLeftClaw();

        Wrist.setPosition(0);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        while (opModeInInit()) {
            if (PixelDetectorBF.getSpike_position() == 0) {
                position = spike_position.LEFT;
                HardwareLocal.green();
            }
            else if (PixelDetectorBF.getSpike_position() == 1) {
                position = spike_position.CENTER;
                HardwareLocal.green();
            }
            else {
                position = spike_position.RIGHT;
                HardwareLocal.green();
            }

            telemetry.addData("Spike Position: ", position);
            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(4));
            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(4));
            telemetry.update();
        }
        Camera.close(4);

        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.2);
        sleep(500);

        waitForStart();

        if(isStopRequested()) return;

        // Choose a path according to the spike position
        if (position == spike_position.RIGHT) {

            // Execute right path

        }
        else if (position == spike_position.LEFT) {

            // Execute left path

        }
        else {

            // Execute Center path

        }

        // Adjust the wrist position according to the arm position
        if (Arm.getArm1Position() <= Arm.UNLOADING_POSITION) {
            Wrist.setPosition(Wrist.WRIST_UNLOADING_POSITION + 0.018 * ((int) ((Arm.getArm1Position() - Arm.UNLOADING_POSITION) / -50)));
        }

        // Add data to telemetry
        telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
        telemetry.addData("FL encoder position: ", EgnitionSystem.getFlEncoderPosition());
        telemetry.addData("Heading: ", EgnitionSystem.getHeading());
        telemetry.update();


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
    public void initCamera() {
        Camera.init(this, hardwareMap, 4);
    }
    public void initLed() {
        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
        HardwareLocal.init(ledDriver);
    }
}
