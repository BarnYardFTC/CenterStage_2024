package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HardwareLocal {
//    color sensor claws
    static private ColorSensor colorSensorLeft;
    static private ColorSensor colorSensorRight;
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
    public static void initColorSensor() {
        ColorSensor colorSensorRight = hardwareMap.get(ColorSensor.class, "color_sensor_right");
//        ColorSensor colorSensorLeft = hardwareMap.get(ColorSensor.class, "color_sensor_left");
    }
    public static boolean pixelLeftColor() {
        return colorSensorLeft.red() == 190 && colorSensorLeft.green() == 170 && colorSensorLeft.blue() == 235 || colorSensorLeft.red() == 110 && colorSensorLeft.green() == 200 && colorSensorLeft.blue() == 45 || colorSensorLeft.red() == 255 && colorSensorLeft.green() == 210 && colorSensorLeft.blue() == 20 || colorSensorLeft.red() == 235 && colorSensorLeft.green() == 235 && colorSensorLeft.blue() == 240;
    }
    public static boolean pixelRightColor() {
        return colorSensorRight.red() == 190 && colorSensorRight.green() == 170 && colorSensorRight.blue() == 235 || colorSensorRight.red() == 110 && colorSensorRight.green() == 200 && colorSensorRight.blue() == 45 || colorSensorRight.red() == 255 && colorSensorRight.green() == 210 && colorSensorRight.blue() == 20 || colorSensorRight.red() == 235 && colorSensorRight.green() == 235 && colorSensorRight.blue() == 240;
    }
    public static void addTelemetryColorSensor() {
        telemetry.addData("color red right: ", colorSensorRight.red());
        telemetry.addData("color green right: ", colorSensorRight.green());
        telemetry.addData("color blue right: ", colorSensorRight.blue());
//        telemetry.addData("color red left: ", colorSensorLeft.red());
//        telemetry.addData("color green left: ", colorSensorLeft.green());
//        telemetry.addData("color blue left: ", colorSensorLeft.blue());
    }
}

