package org.firstinspires.ftc.teamcode;

// Imports
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HardwareLocal {

// Variables
    static private ColorSensor colorSensorLeft;
    static private ColorSensor colorSensorRight;

// Getting Variables
    static public double getRedValueLeft() {
        return colorSensorLeft.red();
    }
    static public double getGreenValueLeft() {
        return colorSensorLeft.green();
    }
    static public double getBlueValueLeft() {
        return colorSensorLeft.blue();
    }
    static public double getAlphaValueLeft(){return colorSensorLeft.alpha();}
    static public double getRedValueRight() {
        return colorSensorRight.red();
    }
    static public double getGreenValueRight() {
        return colorSensorRight.green();
    }
    static public double getBlueValueRight() {
        return colorSensorRight.blue();
    }
    static public double getAlphaValueRight(){return colorSensorRight.alpha();}


    // Initializing
    public static void init(ColorSensor colorSensorRight) {
        HardwareLocal.colorSensorRight = colorSensorRight;
//        HardwareLocal.colorSensorLeft = colorSensorLeft;
    }

// System's functions
    public static boolean purpleRight() {
        return colorSensorRight.red() <= 50 && colorSensorRight.red() >= 40 && colorSensorRight.blue() <= 40 && colorSensorRight.blue() >= 30 && colorSensorRight.green() <= 65 && colorSensorRight.green() >= 55 && colorSensorRight.alpha() <= 50 && colorSensorRight.alpha() >= 40;
    }
    public static boolean greenRight() {
        return colorSensorRight.red() <= 45 && colorSensorRight.red() >= 35 && colorSensorRight.blue() <= 25 && colorSensorRight.blue() >= 20 && colorSensorRight.green() <= 60 && colorSensorRight.green() >= 50 && colorSensorRight.alpha() <= 40 && colorSensorRight.alpha() >= 30;
    }
    public static boolean yellowRight() {
        return colorSensorRight.red() <= 75 && colorSensorRight.red() >= 65 && colorSensorRight.blue() <= 40 && colorSensorRight.blue() >= 30 && colorSensorRight.green() <= 85 && colorSensorRight.green() >= 75 && colorSensorRight.alpha() <= 70 && colorSensorRight.alpha() >= 60;
    }
    public static boolean whiteRight() {
        return colorSensorRight.red() <= 60 && colorSensorRight.red() >= 50 && colorSensorRight.blue() <= 40 && colorSensorRight.blue() >= 30 && colorSensorRight.green() <= 80 && colorSensorRight.green() >= 70 && colorSensorRight.alpha() <= 60 && colorSensorRight.alpha() >= 50;
    }
//    public static boolean purpleLeft() {
//
//    }
//    public static boolean greenLeft() {
//
//    }
//    public static boolean yellowLeft() {
//
//    }
//    public static boolean whiteLeft() {
//
//    }
}

