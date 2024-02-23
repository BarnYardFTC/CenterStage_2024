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
    static public double getRedValueRight() {
        return colorSensorRight.red();
    }
    static public double getGreenValueRight() {
        return colorSensorRight.green();
    }
    static public double getBlueValueRight() {
        return colorSensorRight.blue();
    }

// Initializing
    public static void init(ColorSensor colorSensorRight) {
        HardwareLocal.colorSensorRight = colorSensorRight;
//        HardwareLocal.colorSensorLeft = colorSensorLeft;
    }

// System's functions
    public static boolean pixelLeftColor() {
        return colorSensorLeft.red() == 190 && colorSensorLeft.green() == 170 && colorSensorLeft.blue() == 235 || colorSensorLeft.red() == 110 && colorSensorLeft.green() == 200 && colorSensorLeft.blue() == 45 || colorSensorLeft.red() == 255 && colorSensorLeft.green() == 210 && colorSensorLeft.blue() == 20 || colorSensorLeft.red() == 235 && colorSensorLeft.green() == 235 && colorSensorLeft.blue() == 240;
    }
    public static boolean pixelRightColor() {
        return colorSensorRight.red() == 190 && colorSensorRight.green() == 170 && colorSensorRight.blue() == 235 || colorSensorRight.red() == 110 && colorSensorRight.green() == 200 && colorSensorRight.blue() == 45 || colorSensorRight.red() == 255 && colorSensorRight.green() == 210 && colorSensorRight.blue() == 20 || colorSensorRight.red() == 235 && colorSensorRight.green() == 235 && colorSensorRight.blue() == 240;
    }
}

