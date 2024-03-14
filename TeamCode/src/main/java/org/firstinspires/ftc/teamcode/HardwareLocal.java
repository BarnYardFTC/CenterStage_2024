package org.firstinspires.ftc.teamcode;

// Imports

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HardwareLocal {

// Variables
    static private ColorRangeSensor distanceSensorLeft;
    static private ColorRangeSensor distanceSensorRight;
    static private RevBlinkinLedDriver ledDrive;
    static public boolean PIXEL_IN_R;
    static public boolean PIXEL_IN_L;
    static private final int DETECTING_DISTANCE = 36;

// Initializing
    public static void init(ColorRangeSensor distanceSensorRight, ColorRangeSensor distanceSensorLeft) {
        HardwareLocal.distanceSensorRight = distanceSensorRight;
        HardwareLocal.distanceSensorLeft = distanceSensorLeft;
        PIXEL_IN_R = false;
        PIXEL_IN_L = false;
    }
    public static void init(RevBlinkinLedDriver ledDrive) {
        HardwareLocal.ledDrive = ledDrive;
        ledDrive.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }

// Getting values
    public static int getRedValueRight() {return distanceSensorRight.red();}
    public static int getBlueValueRight() {return distanceSensorRight.blue();}
    public static int getGreenValueRight() {return distanceSensorRight.green();}
    public static int getAlphaValueRight() {return distanceSensorRight.alpha();}
    public static int getProximityValueRight() {return (int) distanceSensorRight.getDistance(DistanceUnit.MM);}
    public static int getRedValueLeft() {return distanceSensorLeft.red();}
    public static int getBlueValueLeft() {return distanceSensorLeft.blue();}
    public static int getGreenValueLeft() {return distanceSensorLeft.green();}
    public static int getAlphaValueLeft() {return distanceSensorLeft.alpha();}
    public static int getProximityValueLeft() {return (int) distanceSensorLeft.getDistance(DistanceUnit.MM);}

//// System's functions
    public static boolean pixelRight() {
        return distanceSensorRight.getDistance(DistanceUnit.MM) <= DETECTING_DISTANCE;
    }
    public static boolean pixelLeft() {
        return distanceSensorLeft.getDistance(DistanceUnit.MM) <= DETECTING_DISTANCE;
    }
    public static void red() {ledDrive.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }
    public static void blink() {ledDrive.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);}
    public static void green() {ledDrive.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }
}