package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareLocal {
    public static HardwareMap hardwaremap;
    public static AnalogInput analogInput = hardwaremap.get(AnalogInput.class, "myanaloginput");
    public static double position = analogInput.getVoltage() / 3.3 * 360;
    public static double analogInputPosition() {
        return position;
    }
}

