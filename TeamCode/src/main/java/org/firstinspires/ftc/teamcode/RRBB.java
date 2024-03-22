package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "RRBB")

public class RRBB extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory Trajectory1 = drive.trajectoryBuilder(drive.getPoseEstimate())
                .forward(10)
                .build();
        Trajectory Trajectory2 = drive.trajectoryBuilder(Trajectory1.end())
                .strafeLeft(10)
                .build();
        Trajectory traj3 = drive.trajectoryBuilder(Trajectory2.end())
                .strafeRight(10)
                .build();
        Trajectory traj4 = drive.trajectoryBuilder(new Pose2d(traj3.end().getX(), traj3.end().getY(), Math.toRadians(90)))
                .lineToConstantHeading(new Vector2d(20,0))
                .build();

        waitForStart();

        if(isStopRequested()) return;
//
        drive.followTrajectory(Trajectory1);
        drive.followTrajectory(Trajectory2);
        drive.followTrajectory(traj3);
        drive.turn(Math.toRadians(90));
        drive.followTrajectory(traj4);
        drive.turn(Math.toRadians(drive.getPoseEstimate().getHeading()-90));

    }
}
