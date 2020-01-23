package frc.robot.testingutil;

import org.junit.Test;

public class TimedTestTest extends TimedTest {

    private int counter = 0;

    @Test
    public void testTimedTest() {

        Looper l = new Looper() {

            @Override
            public void run() {
                counter++;
            }
        };

        TestCondition c = getTimeout("Timeout", 5, false);
        run(l, c);
        assertEquals("Timed checkAbsoluteValue counted correctly", 51, counter);
    }

}