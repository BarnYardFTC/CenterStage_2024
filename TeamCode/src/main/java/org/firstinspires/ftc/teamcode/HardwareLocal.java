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
//    public static boolean purpleRight() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean greenRight() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean yellowRight() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean whiteRight() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean purpleLeft() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean greenLeft() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean yellowLeft() {
//        if () {
//            return true;
//        }
//    }
//    public static boolean whiteLeft() {
//        if () {
//            return true;
//        }
//    }
}

