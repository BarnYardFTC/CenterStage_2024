package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Drone")
public class Drone extends LinearOpMode {
    Servo servo;
    private double LAUNCH_POSITION = 0;
    private double HOLD_POSITION = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, "drone");
        waitForStart();
        boolean wasDpadDownPressed = false;
        servo.setPosition(HOLD_POSITION);
        while (opModeIsActive()) {
            if (gamepad1.x && !wasDpadDownPressed) {
                if (servo.getPosition() == HOLD_POSITION) {
                    servo.setPosition(LAUNCH_POSITION);
                }
                else {
                    servo.setPosition(HOLD_POSITION);
                }
                wasDpadDownPressed = true;
            }
            if (!gamepad1.x) {
                wasDpadDownPressed = false;
            }
        }
    }
}
