package org.firstinspires.ftc.teamcode;

// Imports

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HardwareLocal {

    // Variables
    static private ColorRangeSensor colorSensorLeft;
    static private ColorRangeSensor colorSensorRight;

    // Initializing
    public static void init(ColorRangeSensor colorSensorRight) {
        HardwareLocal.colorSensorRight = colorSensorRight;
//        HardwareLocal.colorSensorLeft = colorSensorLeft;
    }

    // Getting values
    public static int getRedValueRight() {return colorSensorRight.red();}
    public static int getBlueValueRight() {return colorSensorRight.blue();}
    public static int getGreenValueRight() {return colorSensorRight.green();}
    public static int getAlphaValueRight() {return colorSensorRight.alpha();}
    public static int getProximityValueRight() {return (int) colorSensorRight.getDistance(DistanceUnit.CM);}
    public static int getRedValueLeft() {return colorSensorLeft.red();}
    public static int getBlueValueLeft() {return colorSensorLeft.blue();}
    public static int getGreenValueLeft() {return colorSensorLeft.green();}
    public static int getAlphaValueLeft() {return colorSensorLeft.alpha();}
    public static int getProximityValueLeft() {return (int) colorSensorLeft.getDistance(DistanceUnit.CM);}

//// System's functions
//    public static boolean purpleRight() {
//        return colorSensorRight.red() <=  && colorSensorRight.red() >=  && colorSensorRight.blue() <=  && colorSensorRight.blue() >=  && colorSensorRight.green() <=  && colorSensorRight.green() >= ;
//    }
//    public static boolean greenRight() {
//        return colorSensorRight.red() <=  && colorSensorRight.red() >=  && colorSensorRight.blue() <=  && colorSensorRight.blue() >=  && colorSensorRight.green() <=  && colorSensorRight.green() >= ;
//    }
//    public static boolean yellowRight() {
//        return colorSensorRight.red() <=  && colorSensorRight.red() >=  && colorSensorRight.blue() <=  && colorSensorRight.blue() >=  && colorSensorRight.green() <=  && colorSensorRight.green() >= ;
//    }
//    public static boolean whiteRight() {
//        return colorSensorRight.red() <=  && colorSensorRight.red() >=  && colorSensorRight.blue() <=  && colorSensorRight.blue() >=  && colorSensorRight.green() <=  && colorSensorRight.green() >= ;
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