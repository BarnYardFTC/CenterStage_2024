package org.firstinspires.ftc.teamcode.important;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

public class Camera {

    private static PixelDetectorRB pixel_detector_RB;
    private static PixelDetectorRF pixel_detector_RF;
    private static PixelDetectorBB pixel_detector_BB;
    private static VisionPortal camera;

    public static void init(OpMode opMode, HardwareMap hardwareMap, int side) {
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(1280, 720));
        builder.enableLiveView(true);

        if (side == 1) { // RB
            pixel_detector_RB = new PixelDetectorRB(opMode);
            builder.addProcessor(pixel_detector_RB);
        }
        else if (side == 2) { // RF
            pixel_detector_RF = new PixelDetectorRF(opMode);
            builder.addProcessor(pixel_detector_RF);
        }
        else if (side == 3) { // BB
            pixel_detector_BB = new PixelDetectorBB(opMode);
            builder.addProcessor(pixel_detector_BB);
        }

        camera = builder.build();
    }
    public static int getLeftRegion_avg(int side) {
        if (side == 1) {
            return pixel_detector_RB.getLeftRegion_avg();
        }
        else if (side == 2) {
            return pixel_detector_RF.getLeftRegion_avg();
        }
        else if (side == 3) {
            return pixel_detector_BB.getLeftRegion_avg();
        }
        else if (side == 4) {
            return pixel_detector_RF.getLeftRegion_avg();
        }
        return -1;
    }
    public static int getRightRegion_avg(int side) {
        if (side == 1) {
            return pixel_detector_RB.getRightRegion_avg();
        }
        else if (side == 2) {
            return pixel_detector_RF.getRightRegion_avg();
        }
        else if (side == 3) {
            return pixel_detector_BB.getRightRegion_avg();
        }
        else if (side == 4) {
            return pixel_detector_RF.getRightRegion_avg();
        }
        return -1;
    }
    public static void close(int side) {
        if (side == 1) {
            camera.setProcessorEnabled(pixel_detector_RB,false);
        }
        else if (side == 2) {
            camera.setProcessorEnabled(pixel_detector_RF, false);
        }
        else if (side == 3) {
            camera.setProcessorEnabled(pixel_detector_BB,false);
        }
        else if (side == 4) {
            camera.setProcessorEnabled(pixel_detector_RF, false);
        }

        camera.close();
    }

}