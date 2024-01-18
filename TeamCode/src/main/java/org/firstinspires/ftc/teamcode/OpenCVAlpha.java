package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.PixelDetector;
import org.firstinspires.ftc.vision.VisionPortal;



@Autonomous(name = "OpenCvAlpha")
public class OpenCVAlpha extends LinearOpMode {

    PixelDetector pixelDetector = new PixelDetector(this);
    VisionPortal camera;
    int intSpikePosition;
    String spikePosition;

    @Override
    public void runOpMode() throws InterruptedException {
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(1280, 720));
        builder.enableLiveView(true);
        builder.addProcessor(new PixelDetector(this));

        camera = builder.build();

        while (opModeInInit()) {
            intSpikePosition = PixelDetector.spike_position;
            telemetry.addData("Spike with white pixel: ", getStringSpikePosition(intSpikePosition));
            telemetry.update();
            sleep(30);
        }
        camera.setProcessorEnabled(pixelDetector,false);
        camera.close();
        waitForStart();

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