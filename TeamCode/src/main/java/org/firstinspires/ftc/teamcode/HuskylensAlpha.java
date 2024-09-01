package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "huskyLensAlpha")
public class HuskylensAlpha extends LinearOpMode {

    private final int READ_PERIOD = 1; // Copied from the sdk example program

    private HuskyLens huskyLens; // Create the huskyLens

    // An enum that represents the different options for the prop location on the spike marks
    enum prop_position{
        NOT_FOUND,
        LEFT,
        CENTER,
        RIGHT
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // initiate the huskyLens
        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS); // copied from the sdk example program
        rateLimit.expire(); // "" "" "" "" "" ""

        if (!huskyLens.knock()) { // "" "" "" "" "" ""
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else { // "" "" "" "" "" ""
            telemetry.addData(">>", "Press start to continue");
        }

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION); // work with the COLOR_RECOGNITION built-in algorithm

        telemetry.update();
        waitForStart();

        // Assign the prop positions to not found
        prop_position red_position = prop_position.NOT_FOUND;
        prop_position blue_position = prop_position.NOT_FOUND;

        while (opModeIsActive()) {

            // Copied from the SDK example program
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();

            HuskyLens.Block[] blocks = huskyLens.blocks(); // A list of all the blocks (=colors)
                                                          // the huskyLens has identified

            if (blocks.length > 0) { // If the huskyLens identified a color:
                for (int i = 0; i < blocks.length; i++) { // loop through all the blocks

                    HuskyLens.Block frame = blocks[i]; // assign each block into a variable

                    // If the current frame is red (id=1)
                    if (frame.id == 1) {
                        red_position = locateProp(frame.x); // Assign the red prop position to
                                                            // the corresponding spike mark
                }

                    // If the current frame is blue (id=2)
                    if (frame.id == 2) {
                        blue_position = locateProp(frame.x); // Assign the blue prop position to
                                                            // the corresponding spike mark
                    }
                }

            }

            // Print data to telemetry
            telemetry.addData("blue prop position: ", blue_position.toString());
            telemetry.addData("red prop position: ", red_position.toString());
            telemetry.update();


        }
    }
    // This function returns a prop position based on where on the camera (x) the prop is located
    public prop_position locateProp(int x) {
        if (x < 120) {
            return prop_position.LEFT;
        }
        else if (x < 200) {
            return prop_position.CENTER;
        }
        else {
            return prop_position.RIGHT;
        }
    }
}
