package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.vision.VisionPortal;



@Autonomous(name = "Autonomous Path")
public class AutonomousPathSystem extends LinearOpMode {

    DcMotor fr_wheel;
    DcMotor br_wheel;
    DcMotor bl_wheel;
    DcMotor fl_wheel;

    PixelDetector pixelDetector = new PixelDetector(this);
    VisionPortal camera;
    int intSpikePosition;
    String spikePosition;


    public void leftPath(){
        fr_wheel.setPower(0.3);
        fl_wheel.setPower(0.3);
        br_wheel.setPower(0.3);
        bl_wheel.setPower(0.3);

        fr_wheel.setTargetPosition(1100);
        br_wheel.setTargetPosition(1100);
        fl_wheel.setTargetPosition(-1100);
        bl_wheel.setTargetPosition(-1100);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(-1200);
        fl_wheel.setTargetPosition(1200);
        br_wheel.setTargetPosition(1200);
        bl_wheel.setTargetPosition(-1200);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        sleep(2000);

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(-400);
        fl_wheel.setTargetPosition(400);
        br_wheel.setTargetPosition(400);
        bl_wheel.setTargetPosition(-400);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(-1500);
        fl_wheel.setTargetPosition(-1500);
        br_wheel.setTargetPosition(-1500);
        bl_wheel.setTargetPosition(-1500);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        while (opModeIsActive()){

        }
    }
    public void midPath(){

        fr_wheel.setPower(0.3);
        fl_wheel.setPower(0.3);
        br_wheel.setPower(0.3);
        bl_wheel.setPower(0.3);

        fr_wheel.setTargetPosition(1000);
        fl_wheel.setTargetPosition(1000);
        br_wheel.setTargetPosition(1000);
        bl_wheel.setTargetPosition(1000);


        while (opModeIsActive() && fl_wheel.getCurrentPosition() != fl_wheel.getTargetPosition()){
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        sleep(3000);

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(1050);
        br_wheel.setTargetPosition(1050);
        fl_wheel.setTargetPosition(-1050);
        bl_wheel.setTargetPosition(-1050);

        while (opModeIsActive() && fl_wheel.getCurrentPosition() != fl_wheel.getTargetPosition()){
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(-1400);
        br_wheel.setTargetPosition(-1400);
        fl_wheel.setTargetPosition(-1400);
        bl_wheel.setTargetPosition(-1400);

        while (opModeIsActive() && fl_wheel.getCurrentPosition() != fl_wheel.getTargetPosition()){
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (opModeIsActive()){

        }


    }

    public void rightPath(){
        fr_wheel.setPower(0.3);
        fl_wheel.setPower(0.3);
        br_wheel.setPower(0.3);
        bl_wheel.setPower(0.3);

        fr_wheel.setTargetPosition(-1000);
        br_wheel.setTargetPosition(-1000);
        fl_wheel.setTargetPosition(1000);
        bl_wheel.setTargetPosition(1000);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(1200);
        fl_wheel.setTargetPosition(-1200);
        br_wheel.setTargetPosition(-1200);
        bl_wheel.setTargetPosition(1200);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        sleep(2000);

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(2200);
        br_wheel.setTargetPosition(2200);
        fl_wheel.setTargetPosition(-2200);
        bl_wheel.setTargetPosition(-2200);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(450);
        fl_wheel.setTargetPosition(-450);
        br_wheel.setTargetPosition(-450);
        bl_wheel.setTargetPosition(450);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr_wheel.setTargetPosition(-1500);
        fl_wheel.setTargetPosition(-1500);
        br_wheel.setTargetPosition(-1500);
        bl_wheel.setTargetPosition(-1500);

        while (opModeIsActive() && fr_wheel.getCurrentPosition() != fr_wheel.getTargetPosition()) {
            fr_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        while (opModeIsActive()){

        }

    }

    @Override
    public void runOpMode() throws InterruptedException {

        fr_wheel = hardwareMap.get(DcMotor.class, "fr_wheel");
        br_wheel = hardwareMap.get(DcMotor.class, "br_wheel");
        bl_wheel = hardwareMap.get(DcMotor.class, "bl_wheel");
        fl_wheel = hardwareMap.get(DcMotor.class, "fl_wheel");

        bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        fl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fr_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl_wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        VisionPortal.Builder builder = new VisionPortal.Builder();
//        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
//        builder.setCameraResolution(new Size(1280, 720));
//        builder.enableLiveView(true);
//        builder.addProcessor(new PixelDetector(this));
//
//        camera = builder.build();
//
//        while (opModeInInit()) {
//            intSpikePosition = PixelDetector.spike_position;
//            spikePosition = getStringSpikePosition(intSpikePosition);
//            telemetry.addData("Spike with white pixel: ", spikePosition);
//            telemetry.update();
//            sleep(30);
//        }

        waitForStart();
        rightPath();

//        if (spikePosition.equals("middle")) {
//            midPath();
//        }
//        else if (spikePosition.equals("right")){
//            rightPath();
//        }
//        else {
//            leftPath();
//        }

    }

    public static String getStringSpikePosition(int spikePosition){
        if (spikePosition == 0) {
            return "left";
        }
        else if (spikePosition == 1) {
            return  "middle";
        }
        else {
            return "right";
        }
    }
}
