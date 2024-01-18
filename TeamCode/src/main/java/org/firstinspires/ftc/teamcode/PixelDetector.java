package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class PixelDetector implements VisionProcessor {

    static int spike_position = 0;

    OpMode OpMode;
    Mat leftRegion = new Mat();
    Mat rightRegion = new Mat();
    Mat midRegion = new Mat();
    int leftRegion_avg, rightRegion_avg, midRegion_avg;
    Mat YCrCb = new Mat();
    Mat Y = new Mat();

    static int frameWidth;
    static int frameHeight;

    public PixelDetector(OpMode opMode){
        this.OpMode = opMode;

    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        frameWidth = frame.width();
        frameHeight = frame.height();

        Imgproc.rectangle(frame, new Point(155,200), new Point(155+100, 200+150), new Scalar(255, 0, 0)); // left
        Imgproc.rectangle(frame, new Point(1030, 200),new Point(1030+100, 200 +150), new Scalar(255, 0, 0)); // right
        Imgproc.rectangle(frame, new Point(530, 150), new Point(530 + 260, 150+100), new Scalar(255,0,0)); // mid

        leftRegion = frame.submat(new Rect(155, 200, 100, 150));
        rightRegion = frame.submat(new Rect(1030, 200, 100, 150));
        midRegion = frame.submat(new Rect(530,150,260,100));

        Imgproc.cvtColor(frame, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Y, 2);

        leftRegion_avg = (int) Core.mean(leftRegion).val[0];
        rightRegion_avg = (int) Core.mean(rightRegion).val[0];
        midRegion_avg = (int) Core.mean(midRegion).val[0];

        int brightest_avg = Math.max(Math.max(leftRegion_avg, rightRegion_avg), Math.max(leftRegion_avg, midRegion_avg));

        if (brightest_avg == leftRegion_avg) {
            spike_position = 0;
        }
        else if (brightest_avg == midRegion_avg) {
            spike_position = 1;
        }
        else {
            spike_position = 2;
        }

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
