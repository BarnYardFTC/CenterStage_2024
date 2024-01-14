package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Wrist Program")
public class WristProgram extends LinearOpMode {

    Servo wrist;
    final double WRIST_UP_POSITION = 0.4;
    final double WRIST_DOWN_POSITION = 0.08;

    @Override
    public void runOpMode() throws InterruptedException {
        wrist = hardwareMap.get(Servo.class, "wrist");

        // 0 = down | 1 = up | 2 = moving down | 3 = moving up
        int wrist_position = 0;

        wrist.setPosition(WRIST_DOWN_POSITION);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.y && wrist_position == 0) {
                wrist.setPosition(WRIST_UP_POSITION);
                wrist_position = 3;
            }
            if (gamepad1.y && wrist_position == 1) {
                wrist.setPosition(WRIST_DOWN_POSITION);
                wrist_position = 2;
            }
            if (!gamepad1.y && wrist_position == 3) {
                wrist_position = 1;
            }
            if (!gamepad1.y && wrist_position == 2){
                wrist_position = 0;
            }
        }

    }
}
