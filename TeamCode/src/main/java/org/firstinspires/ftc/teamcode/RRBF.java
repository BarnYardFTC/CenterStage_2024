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

@Autonomous(name = "RRBF")

public class RRBF extends LinearOpMode {

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

        Wrist.setPosition(Wrist.WRIST_DOWN_POSITION - 0.05);
        sleep(500);

        waitForStart();

        // Choose a path according to the spike position
        if (position == spike_position.RIGHT) {
            // Initialize right path

            traj1 = drive.trajectoryBuilder(drive.getPoseEstimate())
                    .forward(13)
                    .build();
            traj2 = drive.trajectoryBuilder(traj1.end())
                    .lineToConstantHeading(new Vector2d(traj1.end().getX(), traj1.end().getY() + 15))
                    .build();
            traj3 = drive.trajectoryBuilder(traj2.end())
                    .lineToConstantHeading(new Vector2d(traj2.end().getX() + 7, traj2.end().getY()))
                    .build();
            traj4 = drive.trajectoryBuilder(new Pose2d(traj3.end().getX(), traj3.end().getY(), Math.toRadians(-90)))
                    .lineToConstantHeading(new Vector2d(traj3.end().getX(), traj3.end().getY() + 24))
                    .build();
            traj5 = drive.trajectoryBuilder(traj4.end())
                    .lineToConstantHeading(new Vector2d(traj4.end().getX() + 4, traj4.end().getY()))
                    .build();
            traj6 = drive.trajectoryBuilder(traj5.end())
                    .lineToConstantHeading(new Vector2d(traj5.end().getX() - 48, traj5.end().getY()))
                    .build();
            traj7 = drive.trajectoryBuilder(traj6.end())
                    .lineToConstantHeading(new Vector2d(-77, traj6.end().getY()))
                    .build();
            traj8 = drive.trajectoryBuilder(traj7.end())
                    .lineToConstantHeading(new Vector2d(traj7.end().getX(), traj7.end().getY() - 16))
                    .build();
            traj9 = drive.trajectoryBuilder(new Pose2d(traj8.end().getX(), traj8.end().getY(), Math.toRadians(0)))
                    .lineToConstantHeading(new Vector2d(traj8.end().getX(), traj8.end().getY() + 26))
                    .build();
            traj10 = drive.trajectoryBuilder(traj9.end())
                    .lineToConstantHeading(new Vector2d(traj9.end().getX() - 10, traj9.end().getY()))
                    .build();
        }
        else if (position == spike_position.LEFT) {
            // Initialize left path
            traj1 = drive.trajectoryBuilder(drive.getPoseEstimate())
                    .forward(15)
                    .build();
            traj2 = drive.trajectoryBuilder(new Pose2d(traj1.end().getX(), traj1.end().getY(), Math.toRadians(90)))
                    .lineToConstantHeading(new Vector2d(traj1.end().getX() - 2, traj1.end().getY()))
                    .build();
            traj3 = drive.trajectoryBuilder(new Pose2d(traj2.end().getX(), traj2.end().getY(), Math.toRadians(-90)))
                    .lineToConstantHeading(new Vector2d(traj2.end().getX(), traj2.end().getY() + 24))
                    .build();
            traj4 = drive.trajectoryBuilder(traj3.end())
                    .lineToConstantHeading(new Vector2d(traj3.end().getX() + 26, traj3.end().getY()))
                    .build();
            traj5 = drive.trajectoryBuilder(traj4.end())
                    .lineToConstantHeading(new Vector2d(traj4.end().getX() - 48, traj4.end().getY()))
                    .build();
            traj6 = drive.trajectoryBuilder(traj5.end())
                    .lineToConstantHeading(new Vector2d(-77, traj5.end().getY()))
                    .build();
            traj7 = drive.trajectoryBuilder(traj6.end())
                    .lineToConstantHeading(new Vector2d(traj6.end().getX(), traj6.end().getY() - 28))
                    .build();
            traj8 = drive.trajectoryBuilder(new Pose2d(traj7.end().getX(), traj7.end().getY(), Math.toRadians(0)))
                    .lineToConstantHeading(new Vector2d(traj7.end().getX(), traj7.end().getY() + 31))
                    .build();
            traj9 = drive.trajectoryBuilder(traj8.end())
                    .lineToConstantHeading(new Vector2d(traj8.end().getX() - 10, traj8.end().getY()))
                    .build();
        }
        else {
            // Initialize Center path
        }

        if (isStopRequested()) return;

        if (position == spike_position.RIGHT) {
            // Execute right path

            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            Claws.openRightClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            drive.followTrajectory(traj3);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj4);
            Wrist.setPosition(Wrist.WRIST_DOWN_POSITION);
            drive.followTrajectory(traj5);
            Claws.closeRightClaw();
            drive.followTrajectory(traj6);
            sleep(5000);
            drive.followTrajectory(traj7);
            drive.followTrajectory(traj8);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_UP_POSITION, false)) && opModeIsActive()) {
                Arm.moveUp(ARM_SPEED);
            }
            Arm.brake();
            sleep(800);
            Claws.openLeftClaw();
            Claws.openRightClaw();
            sleep(300);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_DOWN_POSITION, true)) && opModeIsActive()) {
                Arm.moveDown(ARM_SPEED);
            }
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            sleep(500);
            drive.turn(90);
            drive.followTrajectory(traj9);
            drive.followTrajectory(traj10);
        }
        else if (position == spike_position.LEFT) {
            // Execute left path

            drive.followTrajectory(traj1);
            drive.turn(Math.toRadians(90));
            drive.followTrajectory(traj2);
            Claws.openRightClaw();
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            drive.turn(Math.toRadians(180) - 1e-6);
            drive.followTrajectory(traj3);
            Wrist.setPosition(Wrist.WRIST_DOWN_POSITION);
            drive.followTrajectory(traj4);
            Claws.closeRightClaw();
            drive.followTrajectory(traj5);
            sleep(5000);
            drive.followTrajectory(traj6);
            drive.followTrajectory(traj7);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_UP_POSITION, false)) && opModeIsActive()) {
                Arm.moveUp(ARM_SPEED);
            }
            Arm.brake();
            sleep(800);
            Claws.openLeftClaw();
            Claws.openRightClaw();
            sleep(300);
            while (!(Arm.arrivedPosition(Arm.getArm1Position(), ARM_DOWN_POSITION, true)) && opModeIsActive()) {
                Arm.moveDown(ARM_SPEED);
            }
            Wrist.setPosition(Wrist.WRIST_UP_POSITION);
            sleep(500);
            drive.turn(90);
            drive.followTrajectory(traj8);
            drive.followTrajectory(traj9);
        }
        else {
            // Execute Center path
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
        Camera.init(this, hardwareMap, 4);
    }

    public void initLed() {
        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "ledDrive");
        HardwareLocal.init(ledDriver);
    }

}
