package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareLocal {
//    analog input
    public static HardwareMap hardwaremap;
//    public static AnalogInput analogInput = hardwaremap.get(AnalogInput.class, "localAnalogInput");
//    public static double position = analogInput.getVoltage() / 3.3 * 360;

//    color sensor claws
    private ColorSensor colorSensorLeft;
    private ColorSensor colorSensorRight;
    private static double redValueLeft;
    private static double greenValueLeft;
    private static double blueValueLeft;
    private static double alphaValueLeft;
    private static double redValueRight;
    private static double greenValueRight;
    private static double blueValueRight;
    private static double alphaValueRight;

    public static double getRedValueLeft() {
        return redValueLeft;
    }
    public static double getGreenValueLeft() {
        return greenValueLeft;
    }
    public static double getBlueValueLeft() {
        return blueValueLeft;
    }
    public static double getAlphaValueLeft() {
        return alphaValueLeft;
    }
    public static double getRedValueRight() {
        return redValueRight;
    }
    public static double getGreenValueRight() {
        return greenValueRight;
    }
    public static double getBlueValueRight() {
        return blueValueRight;
    }
    public static double getAlphaValueRight() {
        return alphaValueRight;
    }
    public static void initColorSensor() {
        ColorSensor colorSensorRight = hardwaremap.get(ColorSensor.class, "color_sensor_right");
        ColorSensor colorSensorLeft = hardwaremap.get(ColorSensor.class, "color_sensor_left");
    }
    public void getColorRight() {
        redValueRight = colorSensorRight.red();
        greenValueRight = colorSensorRight.green();
        blueValueRight = colorSensorRight.blue();
        alphaValueRight = colorSensorRight.alpha();
    }
    public void getColorLeft() {
        redValueLeft = colorSensorLeft.red();
        greenValueLeft = colorSensorLeft.green();
        blueValueLeft = colorSensorLeft.blue();
        alphaValueLeft = colorSensorLeft.alpha();
    }
    public static boolean pixelLeft() {
        return redValueLeft == 190 && greenValueLeft == 170 && blueValueLeft == 235 && alphaValueLeft == 1 || redValueLeft == 110 && greenValueLeft == 200 && blueValueLeft == 45 && alphaValueLeft == 1 || redValueLeft == 255 && greenValueLeft == 210 && blueValueLeft == 20 && alphaValueLeft == 1 || redValueLeft == 235 && greenValueLeft == 235 && blueValueLeft == 240 && alphaValueLeft == 1;
    }
    public static boolean pixelRight() {
        return redValueRight == 190 && greenValueRight == 170 && blueValueRight == 235 && alphaValueRight == 1 || redValueRight == 110 && greenValueRight == 200 && blueValueRight == 45 && alphaValueRight == 1 || redValueRight == 255 && greenValueRight == 210 && blueValueRight == 20 && alphaValueRight == 1 || redValueRight == 235 && greenValueRight == 235 && blueValueRight == 240 && alphaValueRight == 1;
    }
    public static void addTelemetryColor() {
        telemetry.addData("color red right: ", redValueRight);
        telemetry.addData("color green right: ", greenValueRight);
        telemetry.addData("color blue right: ", blueValueRight);
        telemetry.addData("color alpha right: ", alphaValueRight);
    }
}

