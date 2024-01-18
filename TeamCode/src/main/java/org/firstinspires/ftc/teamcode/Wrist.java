package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {

    Servo wrist;
    final double WRIST_UP_POSITION = 0.4; // ToDo: Find correct values
    final double WRIST_DOWN_POSITION = 0.08; // ToDo: Find correct values
    boolean was_Y_pressed = false;
    public Wrist(Servo servo) {
        wrist = servo;
    }
    public void moveUp(){
        wrist.setPosition(WRIST_UP_POSITION);
    }
    public void moveDown() {
        wrist.setPosition(WRIST_DOWN_POSITION);
    }
    public void runWrist(boolean Y_pressed){
        if (Y_pressed && !was_Y_pressed) {
            if (Math.abs(wrist.getPosition() - WRIST_DOWN_POSITION) < Math.abs(wrist.getPosition() - WRIST_UP_POSITION)) {
                moveUp();
            }
            else {
                moveDown();
            }
            was_Y_pressed = true;
        }
        if (!Y_pressed) {
            was_Y_pressed = false;
        }

    }
}
