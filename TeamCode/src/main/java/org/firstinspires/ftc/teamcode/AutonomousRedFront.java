package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red Front")
public class AutonomousRedFront extends LinearOpMode {

    private int spike_position = 1;
    private boolean arm_moving = false;

    private int TIME = -1;
    private boolean got_time = false;

    @Override
    public void runOpMode() throws InterruptedException {
        initArm();
        initClaws();
        initWrist();
        initEgnitionSystem();

        setVariables();

        //initCamera();
//
//        while (opModeInInit()) {
//            spike_position = PixelDetector.getSpike_position();
//            telemetry.addData("Spike position: ", spike_position);
//            telemetry.addData("Right region avg", Camera.getRightRegion_avg());
//            telemetry.addData("Left region avg", Camera.getLeftRegion_avg());
//            telemetry.update();
//        }
//        Camera.close();

        Wrist.moveDown();

        Claws.closeLeftClaw();
        Claws.closeRightClaw();

        waitForStart();
        while (opModeIsActive()) {
            if (spike_position == 0) {
                run0();
            }
            else if (spike_position == 1) {
                run1();
            }
            else {
                run2();
            }

            if (!arm_moving) {
                if (!Arm.passedMinimalHoldPosition()) {
                    Arm.stopMoving();
                }
                else {
                    Arm.brake();
                }
            }

            EgnitionSystem.updateVariablesAutonomous();
            EgnitionSystem.runAutonomous();

            telemetry.addData("Arm1 encoder: ", Arm.getArm1Position());
            telemetry.addData("TIME: ", TIME);
            telemetry.update();
        }
    }
    public void run0() {
        if (RFrun0.phase == 1) { // move forward

        }
        else if (RFrun0.phase == 2) { // rotate

        }
        else if (RFrun0.phase == 3) { // open right claw

        }
        else if (RFrun0.phase == 4) { // move wrist up

        }
        else if (RFrun0.phase == 5) { // move horizontally (toward back board)

        }
        else if (RFrun0.phase == 6) { // move a bit forward

        }
        else if (RFrun0.phase == 7) { // move arm up

        }
        else if (RFrun0.phase == 8) { // open left claw (let go of pixel)

        }
        else if (RFrun1.phase == 9) { // move arm down

        }
    }
    public void run1() {
        if (RFrun1.phase == 1) { // move forward

        }
        else if (RFrun1.phase == 2) { // open right claw

        }
        else if (RFrun1.phase == 3) { // move wrist up

        }
        else if (RFrun1.phase == 4) { // move backward

        }
        else if (RFrun1.phase == 5) { // rotate

        }
        else if (RFrun1.phase == 6) { // move forward

        }
        else if (RFrun1.phase == 7) { // move horizontally (towards the back board)

        }
        else if (RFrun1.phase == 8) { // move a bit backward

        }
        else if (RFrun1.phase == 9) { // move arm up

        }
        else if (RFrun1.phase == 10) { // open left claw (let go of the pixel)

        }
        else if (RFrun1.phase == 11) { // move arm down

        }
    }
    public void run2() {
        if (RFrun2.phase == 1) { // run forward

        }
        else if (RFrun2.phase == 2) { // rotate

        }
        else if (RFrun2.phase == 3) { // open right claw

        }
        else if (RFrun2.phase == 4) { // move wrist up

        }
        else if (RFrun2.phase == 5) { // move sideways

        }
        else if (RFrun2.phase == 6) { // rotate

        }
        else if (RFrun2.phase == 7) { // move sideways (toward back board)

        }
        else if (RFrun2.phase == 8) { // move a bit backward

        }
        else if (RFrun2.phase == 9) { // move arm up

        }
        else if (RFrun2.phase == 10) { // open left claw (let go of pixel)

        }
        else if (RFrun2.phase == 11) { // move arm down

        }
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
        DcMotor motor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "arm2");
        Arm.init(motor, motor2);
        Arm.addDataToTelemetry(telemetry);
    }
    public void initEgnitionSystem() {
        DcMotor fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");
        DcMotor fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
        DcMotor bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
        DcMotor br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        EgnitionSystem.init(fl_wheel, fr_wheel, bl_wheel, br_wheel, imu);
        EgnitionSystem.initEncoders();
    }
    public void initCamera() {
        Camera.init(this, hardwareMap);
    }
    public void setVariables() {
        RFrun0.phase = 1;
        RFrun1.phase = 1;
        RFrun2.phase = 1;
    }
}
