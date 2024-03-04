package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

public class LEDCODE extends LinearOpMode {

    private RevBlinkinLedDriver led;
    private boolean blinkin_timer = false;

    @Override
    public void runOpMode() throws InterruptedException {

        led = hardwareMap.get(RevBlinkinLedDriver.class, "led");

        waitForStart();


    }
}
