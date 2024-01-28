package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

public class Camera {

    private static PixelDetector pixel_detector;
    private static VisionPortal camera;

    public static void init(OpMode opMode, HardwareMap hardwareMap) {
        pixel_detector = new PixelDetector(opMode);
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(1280, 720));
        builder.enableLiveView(true);
        builder.addProcessor(pixel_detector);

        camera = builder.build();
    }
    public static int getLeftRegion_avg() {
        return pixel_detector.getLeftRegion_avg();
    }
    public static int getRightRegion_avg() {
        return pixel_detector.getRightRegion_avg();
    }
    public static void close() {
        camera.setProcessorEnabled(pixel_detector,false);
        camera.close();
    }

}