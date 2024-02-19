package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HardwareLocal {
//    analog input
    static AnalogInput analogInput = hardwareMap.get(AnalogInput.class, "myanaloginput");
    static double position = analogInput.getVoltage() / 3.3 * 360;

//  distance sensor claws
    private static DistanceSensor distanceSensorLeft;
    private static DistanceSensor distanceSensorRight;

//    color sensor claws
    private static ColorSensor colorSensorLeft;
    private static ColorSensor colorSensorRight;
    public static double getRedValueLeft() {
        return colorSensorLeft.red();
    }
    public static double getGreenValueLeft() {
        return colorSensorLeft.green();
    }
    public static double getBlueValueLeft() {
        return colorSensorLeft.blue();
    }
    public static double getAlphaValueLeft() {
        return colorSensorLeft.alpha();
    }
    public static double getRedValueRight() {
        return colorSensorRight.red();
    }
    public static double getGreenValueRight() {
        return colorSensorRight.green();
    }
    public static double getBlueValueRight() {
        return colorSensorRight.blue();
    }
    public static double getAlphaValueRight() {
        return colorSensorRight.alpha();
    }
    public static void initAnalogInput() {
        AnalogInput analogInput = hardwareMap.get(AnalogInput.class, "local_analog_input");
    }
    public static void initDistanceSensor() {
        DistanceSensor distanceSensorLeft = hardwareMap.get(DistanceSensor.class, "distance_sensor_left");
        DistanceSensor distanceSensorRight = hardwareMap.get(DistanceSensor.class, "distance_sensor_right");
    }
    public static void initColorSensor() {
        ColorSensor colorSensorRight = hardwareMap.get(ColorSensor.class, "color_sensor_right");
        ColorSensor colorSensorLeft = hardwareMap.get(ColorSensor.class, "color_sensor_left");
    }
    public static boolean pixelLeftColor() {
        return colorSensorLeft.red() == 190 && colorSensorLeft.green() == 170 && colorSensorLeft.blue() == 235 || colorSensorLeft.red() == 110 && colorSensorLeft.green() == 200 && colorSensorLeft.blue() == 45 || colorSensorLeft.red() == 255 && colorSensorLeft.green() == 210 && colorSensorLeft.blue() == 20 || colorSensorLeft.red() == 235 && colorSensorLeft.green() == 235 && colorSensorLeft.blue() == 240;
    }
    public static boolean pixelRightColor() {
        return colorSensorRight.red() == 190 && colorSensorRight.green() == 170 && colorSensorRight.blue() == 235 || colorSensorRight.red() == 110 && colorSensorRight.green() == 200 && colorSensorRight.blue() == 45 || colorSensorRight.red() == 255 && colorSensorRight.green() == 210 && colorSensorRight.blue() == 20 || colorSensorRight.red() == 235 && colorSensorRight.green() == 235 && colorSensorRight.blue() == 240;
    }
    public static boolean pixelLeftDistance() {
        return distanceSensorLeft.getDistance(DistanceUnit.INCH) >= 0 && distanceSensorLeft.getDistance(DistanceUnit.INCH) <= 2.5;
    }
    public static boolean pixelRightDistance() {
        return distanceSensorRight.getDistance(DistanceUnit.INCH) >= 0 && distanceSensorRight.getDistance(DistanceUnit.INCH) <= 2.5;
    }
    public static void addTelemetryColorSensor() {
        telemetry.addData("color red right: ", colorSensorRight.red());
        telemetry.addData("color green right: ", colorSensorRight.green());
        telemetry.addData("color blue right: ", colorSensorRight.blue());
        telemetry.addData("color alpha right: ", colorSensorRight.alpha());
        telemetry.addData("color red left: ", colorSensorLeft.red());
        telemetry.addData("color green left: ", colorSensorLeft.green());
        telemetry.addData("color blue left: ", colorSensorLeft.blue());
        telemetry.addData("color alpha left: ", colorSensorLeft.alpha());
    }
    public static void addTelemetryAnalogInput() {
        telemetry.addData("analog angel: ", position);
    }
    public static void addTelemetryDistanceSensor() {
        telemetry.addData("distance right: ", distanceSensorRight.getDistance(DistanceUnit.INCH));
        telemetry.addData("distance left: ", distanceSensorLeft.getDistance(DistanceUnit.INCH));
    }
}

