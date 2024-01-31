package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.vision.VisionPortal;



@Autonomous(name = "Hang test")
public class Test extends LinearOpMode {

    int TIME = 10000;
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
        Wrist.moveUp();

        waitForStart();

        while (opModeIsActive() && TIME > 0) {
            Arm.moveUp();
            TIME --;
            telemetry.addLine(String.valueOf(TIME));
            telemetry.update();
        }

        TIME = 10000;
        Arm.brake();
        while (opModeIsActive() && TIME > 0) {
            Arm.brake();
            TIME --;
            telemetry.addLine(String.valueOf(TIME));
            telemetry.update();
        }
        TIME = 10000;
        Arm.moveDown();
        while (opModeIsActive() && TIME > 0) {
            Arm.moveDown();
            TIME --;
            telemetry.addLine(String.valueOf(TIME));
            telemetry.update();
        }

    }
}
