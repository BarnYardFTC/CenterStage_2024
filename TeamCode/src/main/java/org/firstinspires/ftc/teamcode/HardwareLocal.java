package org.firstinspires.ftc.teamcode;

// Imports
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HardwareLocal {

    // Variables
    static private NormalizedColorSensor colorSensorLeft;
    static private NormalizedColorSensor colorSensorRight;

    // Initializing
    public static void init(NormalizedColorSensor colorSensorRight) {
        HardwareLocal.colorSensorRight = colorSensorRight;
//        HardwareLocal.colorSensorLeft = colorSensorLeft;
    }

 Getting values
    public static int getRedValueRight() {return colorSensorRight.red();}
    public static int getBlueValueRight() {return colorSensorRight.blue();}
    public static int getGreenValueRight() {return colorSensorRight.green();}
    public static int getAlphaValueRight() {return colorSensorRight.alpha();}
    public static int getRedValueLeft() {return colorSensorRight.red();}
    public static int getBlueValueLeft() {return colorSensorRight.blue();}
    public static int getGreenValueLeft() {return colorSensorRight.green();}
    public static int getAlphaValueLeft() {return colorSensorRight.alpha();}

// System's functions
//    public static boolean purpleRight() {
//        return colorSensorRight.red() <= 50 && colorSensorRight.red() >= 40 && colorSensorRight.blue() <= 40 && colorSensorRight.blue() >= 30 && colorSensorRight.green() <= 65 && colorSensorRight.green() >= 55;
//    }
//    public static boolean greenRight() {
//        return colorSensorRight.red() <= 45 && colorSensorRight.red() >= 35 && colorSensorRight.blue() <= 25 && colorSensorRight.blue() >= 20 && colorSensorRight.green() <= 60 && colorSensorRight.green() >= 50;
//    }
//    public static boolean yellowRight() {
//        return colorSensorRight.red() <= 75 && colorSensorRight.red() >= 65 && colorSensorRight.blue() <= 40 && colorSensorRight.blue() >= 30 && colorSensorRight.green() <= 85 && colorSensorRight.green() >= 75;
//    }
//    public static boolean whiteRight() {
//        return colorSensorRight.red() <= 60 && colorSensorRight.red() >= 50 && colorSensorRight.blue() <= 40 && colorSensorRight.blue() >= 30 && colorSensorRight.green() <= 80 && colorSensorRight.green() >= 70;
//    }
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