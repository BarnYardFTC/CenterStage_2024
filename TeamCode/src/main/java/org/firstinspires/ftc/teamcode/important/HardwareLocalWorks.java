//package org.firstinspires.ftc.teamcode;
//
//// Imports
//        import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//        import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//        import android.graphics.Color;
//
//        import com.qualcomm.robotcore.hardware.AnalogInput;
//        import com.qualcomm.robotcore.hardware.ColorSensor;
//
//        import com.qualcomm.robotcore.hardware.DistanceSensor;
//        import com.qualcomm.robotcore.hardware.HardwareMap;
//        import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
//        import com.qualcomm.robotcore.hardware.SwitchableLight;
//
//        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//public class HardwareLocal {
//
//    // Variables
//    static private ColorSensor colorSensorLeft;
//    static private ColorSensor colorSensorRight;
//
//    // Initializing
//    public static void init(ColorSensor colorSensorRight) {
//        HardwareLocal.colorSensorRight = colorSensorRight;
////        HardwareLocal.colorSensorLeft = colorSensorLeft;
//    }
//
//    // Getting values
//    public static int getRedValueRight() {return colorSensorRight.red();}
//    public static int getBlueValueRight() {return colorSensorRight.blue();}
//    public static int getGreenValueRight() {return colorSensorRight.green();}
//    public static int getAlphaValueRight() {return colorSensorRight.alpha();}
//    public static int getRedValueLeft() {return colorSensorRight.red();}
//    public static int getBlueValueLeft() {return colorSensorRight.blue();}
//    public static int getGreenValueLeft() {return colorSensorRight.green();}
//    public static int getAlphaValueLeft() {return colorSensorRight.alpha();}
//
//    // System's functions
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
////    public static boolean purpleLeft() {
////
////    }
////    public static boolean greenLeft() {
////
////    }
////    public static boolean yellowLeft() {
////
////    }
////    public static boolean whiteLeft() {
////
////    }
//}