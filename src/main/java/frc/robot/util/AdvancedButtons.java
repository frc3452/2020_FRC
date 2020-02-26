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

            private double longPress = 999999999999999.0;
            private boolean prevButton = false;
            private int flag = 0;

            private Timer testTimer = new Timer();
            

            @Override
            public void run() {
                
                boolean press = button.get();

                if (press) {
                    if (prevButton != press)
                    flag++;

                }

                if (flag == 1) {
                    quickRelease.schedule();
                }

                if ((Timer.getFPGATimestamp() - longPress) > quickReleaseTime) {
                    quickRelease.cancel();
                    flag = 0;
                    whileHeld.schedule();

                }

                if (press == false) {

                    longPress = Timer.getFPGATimestamp();

                    if (flag > 1) {
                        quickRelease.cancel();
                        flag = 0;
                    }

                        whileHeld.cancel();

                    testTimer.stop();
                    testTimer.reset();

                }
                prevButton = press;
            }
        });

        return button;
    }
}
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