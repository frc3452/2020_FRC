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

                //boolean press = button.get();

                // if (somethingA)
                //quickRelease.schedule();

                //if (somethingB)
                //whileHeld.schedule();

                //if (somethingC)
                //whileHeld.cancel();
                }        
            });
        return button;
    }
}