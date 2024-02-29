package org.firstinspires.ftc.teamcode;

// Imports

import com.qualcomm.robotcore.hardware.ColorSensor;

public class HardwareLocal {

    // Variables
    static private ColorSensor colorSensorLeft;
    static private ColorSensor colorSensorRight;

    // Initializing
    public static void init(ColorSensor colorSensorRight) {
        HardwareLocal.colorSensorRight = colorSensorRight;
//        HardwareLocal.colorSensorLeft = colorSensorLeft;
    }

    // Getting values
    public static int getRedValueRight() {return colorSensorRight.red();}
    public static int getBlueValueRight() {return colorSensorRight.blue();}
    public static int getGreenValueRight() {return colorSensorRight.green();}
    public static int getAlphaValueRight() {return colorSensorRight.alpha();}
    public static int getRedValueLeft() {return colorSensorRight.red();}
    public static int getBlueValueLeft() {return colorSensorRight.blue();}
    public static int getGreenValueLeft() {return colorSensorRight.green();}
    public static int getAlphaValueLeft() {return colorSensorRight.alpha();}
}