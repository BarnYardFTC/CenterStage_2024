package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareLocal {
//    analog input
    public static HardwareMap hardwaremap;
    public static AnalogInput analogInput = hardwaremap.get(AnalogInput.class, "myanaloginput");
    public static double position = analogInput.getVoltage() / 3.3 * 360;

    public static double getPosition() {
        return position;
    }
    public static void setPosition(double position) {
        HardwareLocal.position = position;
    }
//    color sensor claws
    private ColorSensor colorSensorLeft;
    private ColorSensor colorSensorRight;
    private double redValueLeft;
    private double greenValueLeft;
    private double blueValueLeft;
    private double alphaValueLeft;
    private double redValueRight;
    private double greenValueRight;
    private double blueValueRight;
    private double alphaValueRight;
    public static void initColorSensor() {
        ColorSensor colorSensorRight = hardwaremap.get(ColorSensor.class, "ColorSensorR");
        ColorSensor colorSensorLeft = hardwaremap.get(ColorSensor.class, "ColorSensorL");
    }
    public  void getColorRight() {
        redValueRight = colorSensorRight.red();
        greenValueRight = colorSensorRight.green();
        blueValueRight = colorSensorRight.blue();
        alphaValueRight = colorSensorRight.alpha();
    }
    public  void getColorLeft() {
        redValueLeft = colorSensorLeft.red();
        greenValueLeft = colorSensorLeft.green();
        blueValueLeft = colorSensorLeft.blue();
        alphaValueLeft = colorSensorLeft.alpha();
    }
}

