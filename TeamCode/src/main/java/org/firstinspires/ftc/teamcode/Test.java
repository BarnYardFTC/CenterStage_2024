package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.VisionPortal;



@TeleOp(name = "Wrist test")
public class Test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.get(Servo.class, "wrist");
        Wrist.init(servo);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.y && servo.getPosition() < 1) {
                servo.setPosition(servo.getPosition() + 0.05);
                sleep(1000);
            }
            if (gamepad1.a && servo.getPosition() > 0) {
                servo.setPosition(servo.getPosition() - 0.05);
                sleep(1000);
            }
            telemetry.addData("Wrist position: ", Wrist.getPosition());
            telemetry.update();
        }
    }
}
