package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {
    static Servo wrist;
    static final double WRIST_UP_POSITION = 0;
    static final double WRIST_DOWN_POSITION = 0.94;
    static boolean was_Y_pressed;
    public static void init(Servo servo) {
        wrist = servo;
        was_Y_pressed = false;
    }
    public static void moveUp() {
        wrist.setPosition(WRIST_UP_POSITION);
    }
    public static void moveDown() {
        wrist.setPosition(WRIST_DOWN_POSITION);
    }
    public static void moveToStartPosition() {
        wrist.setPosition(WRIST_DOWN_POSITION);
    }
    public static void setPosition(double position) {
        wrist.setPosition(position);
    }
    public static double getPosition() {
        return wrist.getPosition();
    }
    public static void runWrist(boolean Y_pressed){
        if (Y_pressed && !was_Y_pressed) {
            if (Math.abs(wrist.getPosition() - WRIST_DOWN_POSITION) < Math.abs(wrist.getPosition() - WRIST_UP_POSITION)) {
                wrist.setPosition(WRIST_UP_POSITION);
            }
            else {
                wrist.setPosition(WRIST_DOWN_POSITION);
            }
            was_Y_pressed = true;
        }
        if (!Y_pressed) {
            was_Y_pressed = false;
        }
    }
}
