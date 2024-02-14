package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware {
    private HardwareMap hardwaremap;
    AnalogInput analogInput = hardwaremap.get(AnalogInput.class, "myanaloginput");
    double position = analogInput.getVoltage() / 3.3 * 360;
}
