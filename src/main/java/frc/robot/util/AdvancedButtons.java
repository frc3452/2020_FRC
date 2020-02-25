package frc.robot.util;

import java.security.Timestamp;
import java.sql.Time;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;

public class AdvancedButtons {
    public static Button quickReleaseAndWhileHeld(Button button, Command quickRelease, Command whileHeld,
            double quickReleaseTime) {
        CommandScheduler.getInstance().addButton(new Runnable() {

            // We can use variables out here to store the last button press, or the last
            // timestamp
            private double currentTime = 0.0;
            private double timePressed = 0.0;
            private Timer testTimer = new Timer();

            @Override
            public void run() {
                // Timer.getFPGATimestamp() to get current time

                // You can store the previous button press every cycle, so that you can use both
                // the
                // current value (button.get()) and the previous value to determine when it was
                // released or pressed
                // (if it was not pressed, and now it is, we do

                // to figure out how long you've been doing something, store time when that
                // thing starts, and then use
                // Timer.getFPGATimeStamp() - thatTime to figure out how long it's been since
                // you've done it

                // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~/

                boolean press = button.get();
                boolean prevButton = false;
                int toggleCount = 0;

                currentTime = Timer.getFPGATimestamp();

                if (testTimer.get() > .5) {
                    whileHeld.schedule();
                }

                if (press) {
                    timePressed = Timer.getFPGATimestamp();
                    quickRelease.schedule();
                    testTimer.start();
                    if (toggleCount == 1)
                        toggleCount = 0;

                }

                // if (press) {
                //     quickRelease.schedule();
                // }


                // if (time.get() > quickReleaseTime) {
                // time.stop();
                // time.reset();
                // }

                if (press == false) {
                    whileHeld.cancel();
                    testTimer.stop();
                    testTimer.reset();
                    if(toggleCount == 0){
                    quickRelease.cancel();
                    }
                }
                prevButton = press;
            }
        });

        return button;
    }
}