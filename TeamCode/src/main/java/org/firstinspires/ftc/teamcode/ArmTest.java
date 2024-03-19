package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ArmTest")
public class ArmTest extends LinearOpMode{
    @Override
    public void runOpMode() {
        initArmTeleop();
        waitForStart();
        while (opModeIsActive()) {
        }
    }
    public void initArmTeleop() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm1");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
    }
    public void runArnTeleop() {
        if (gamepad1.right_trigger > 0) {
            ArmTeleop.moveUp();
        }
        else if (gamepad1.left_trigger > 0) {
            ArmTeleop.moveDown();
        }
        else {
            ArmTeleop.brake();
        }
    }
}
