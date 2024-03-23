package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.google.ar.core.Pose;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "RRRB")

public class RRRB extends LinearOpMode {

    double ARM_SPEED = 0.6;
    int ARM_UP_POSITION = -2000;
    int ARM_DOWN_POSITION = -300;

    spike_position position;

    enum spike_position {
        LEFT,
        RIGHT,
        CENTER
    }

    Trajectory traj1;
    Trajectory traj2;
    Trajectory traj3;
    Trajectory traj4;
    Trajectory traj5;
    Trajectory traj6;
    Trajectory traj7;
    Trajectory traj8;
    Trajectory traj9;
    Trajectory traj10;

    @Override
    public void runOpMode() {

        // Initialize everything
        initArm();
        initClaws();
        initWrist();
        initCamera();
//        initLed();

        // Close the claws
        Claws.closeRightClaw();
        Claws.closeLeftClaw();

        Wrist.setPosition(0);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        while (opModeInInit()) {
            if (PixelDetectorRB.getSpike_position() == 0) {
                position = spike_position.LEFT;
                //HardwareLocal.green();
            }
            else if (PixelDetectorRB.getSpike_position() == 1) {
                position = spike_position.CENTER;
                //HardwareLocal.green();
            }
            else {
                position = spike_position.RIGHT;
                //HardwareLocal.green();
            }

            telemetry.addData("Spike Position: ", position);
            telemetry.addData("Right Region avg: ", Camera.getRightRegion_avg(1));
            telemetry.addData("Left Region avg: ", Camera.getLeftRegion_avg(1));
            telemetry.update();
        }
        Camera.close(1);

        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION - 0.05);
        sleep(500);

        waitForStart();

        if (position == spike_position.RIGHT) {

            traj1 = drive.trajectoryBuilder(drive.getPoseEstimate())
                    .forward(10)
                    .build();
            traj2 = drive.trajectoryBuilder(traj1.end())
                    .strafeRight(15)
                    .build();
            traj3 = drive.trajectoryBuilder(new Pose2d(traj2.end().getX(), traj2.end().getY(), Math.toRadians(90)))
                    .lineToConstantHeading(new Vector2d(traj2.end().getX() + 15, traj2.end().getY()))
                    .build();
            traj4 = drive.trajectoryBuilder(traj3.end())
                    .lineToConstantHeading(new Vector2d(traj3.end().getX(), traj3.end().getY() - 10))
                    .build();
            traj5 = drive.trajectoryBuilder(traj4.end())
                    .lineToConstantHeading(new Vector2d(traj4.end().getX() - 5, traj4.end().getY()))
                    .build();
            traj6 = drive.trajectoryBuilder(new Pose2d(traj5.end().getX(), traj5.end().getY(), Math.toRadians(0)))
                    .lineToConstantHeading(new Vector2d(traj5.end().getX() - 19, traj5.end().getY()))
                    .build();
            traj7 = drive.trajectoryBuilder(traj6.end())
                    .lineToConstantHeading(new Vector2d(traj6.end().getX(), traj6.end().getY() - 10))
                    .build();

            // Initialize right path

        } else if (position == spike_position.LEFT) {

            // Initialize left path

            traj1 = drive.trajectoryBuilder(drive.getPoseEstimate())
                    .forward(20)
                    .build();
            traj2 = drive.trajectoryBuilder(new Pose2d(traj1.end().getX(), traj2.end().getY(), Math.toRadians(90)))
                    .lineToConstantHeading(new Vector2d(traj1.end().getX() + 5, traj1.end().getY()))
                    .build();
            traj3 = drive.trajectoryBuilder(traj2.end())
                    .lineToConstantHeading(new Vector2d(traj2.end().getX(), traj2.end().getY() - 25))
                    .build();
            traj4 = drive.trajectoryBuilder(traj3.end())
                    .lineToConstantHeading(new Vector2d(traj3.end().getX() + 5))
                    .build();
            traj5 = drive.trajectoryBuilder(new Pose2d(traj4.end().getX(), traj4.end().getY(), Math.toRadians(0)))
                    .lineToConstantHeading(new Vector2d(traj4.end().getX() - 25, traj4.end().getY()))
                    .build();
            traj6 = drive.trajectoryBuilder(traj5.end())
                    .lineToConstantHeading(new Vector2d(traj5.end().getX(), traj5.end().getY() - 10))
                    .build();

        } else {

            // Initialize Center path

            traj1 = drive.trajectoryBuilder(drive.getPoseEstimate())
                    .forward(30)
                    .build();
            traj2 = drive.trajectoryBuilder(traj1.end())
                    .strafeRight(20)
                    .build();
            traj3 = drive.trajectoryBuilder(traj2.end())
                    .lineToConstantHeading(new Vector2d(traj2.end().getX() - 29, traj2.end().getY()))
                    .build();
            traj4 = drive.trajectoryBuilder(traj3.end())
                    .lineToConstantHeading(new Vector2d(traj3.end().getX(), traj3.end().getY() - 10))
                    .build();
        }

        if (isStopRequested()) return;

        // Choose a path according to the spike position
        if (position == spike_position.RIGHT) {

            // Execute right path

            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            drive.turn(Math.toRadians(90));
            drive.followTrajectory(traj3);
            Claws.openRightClaw();
            drive.followTrajectory(traj4);
            drive.followTrajectory(traj5);
            Wrist.setPosition(0.3);
            sleep(200);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_UP_POSITION, false)) && opModeIsActive()) {
                Arm.moveUp(ARM_SPEED);
            }
            Arm.brake();
            sleep(800);
            Claws.openLeftClaw();
            sleep(500);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_DOWN_POSITION, true)) && opModeIsActive()) {
                Arm.moveDown(ARM_SPEED);
            }
            Arm.brake();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            sleep(500);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj6);
            drive.followTrajectory(traj7);
        }
        else if (position == spike_position.LEFT) {

            // Execute left path

            drive.followTrajectory(traj1);
            drive.turn(Math.toRadians(90));
            drive.followTrajectory(traj2);
            Claws.openRightClaw();
            drive.followTrajectory(traj3);
            drive.followTrajectory(traj4);
            Wrist.setPosition(0.3);
            sleep(200);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_UP_POSITION, false)) && opModeIsActive()) {
                Arm.moveUp(ARM_SPEED);
            }
            Arm.brake();
            sleep(800);
            Claws.openLeftClaw();
            sleep(500);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_DOWN_POSITION, true)) && opModeIsActive()) {
                Arm.moveDown(ARM_SPEED);
            }
            Arm.brake();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            sleep(500);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj5);
            drive.followTrajectory(traj6);
        }
        else {

            // Execute Center path

            Wrist.setPosition(Wrist.WRIST_DOWN_POSITION-0.2);
            drive.followTrajectory(traj1);
            Claws.openRightClaw();
            drive.followTrajectory(traj2);
            drive.turn(Math.toRadians(90));
            Wrist.setPosition(0.3);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_UP_POSITION, false)) && opModeIsActive()) {
                Arm.moveUp(ARM_SPEED);
            }
            Arm.brake();
            sleep(800);
            Claws.openLeftClaw();
            sleep(300);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_DOWN_POSITION, true)) && opModeIsActive()) {
                Arm.moveDown(ARM_SPEED);
            }
            Arm.brake();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            sleep(500);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj3);
            drive.followTrajectory(traj4);
        }


    }

    public void initClaws() {
        Servo left_claw = hardwareMap.get(Servo.class, "left_claw");
        Servo right_claw = hardwareMap.get(Servo.class, "right_claw");
        Claws.init(left_claw, right_claw);
    }

    public void initWrist() {
        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);
    }

    public void initArm() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "right_arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "left_arm");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }

    public void initCamera() {
        Camera.init(this, hardwareMap, 1);
    }

    public void initLed() {
        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
        HardwareLocal.init(ledDriver);
    }
}