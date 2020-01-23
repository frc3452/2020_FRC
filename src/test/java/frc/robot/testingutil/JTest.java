package frc.robot.testingutil;

import frc.robot.util.math.GZUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class JTest {

    private static PrintStream realConsole = System.out;
    private ByteArrayOutputStream newConsole = new ByteArrayOutputStream();
    private PrintStream printStream = new PrintStream(newConsole);

    protected JTest() {
        enableRealConsole(false);
    }

    public void enableRealConsole() {
        enableRealConsole(true);
    }

    public void disableRealConsole() {
        enableRealConsole(false);
    }

    public void enableRealConsole(boolean real) {
        if (real) {
            System.setOut(realConsole);
        } else {
            System.setOut(printStream);
        }
    }

    public void fail() {
        print();
        org.junit.Assert.fail();
    }

    protected void fail(String message) {
        print();
        org.junit.Assert.fail(message);
    }

    protected void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    protected void assertTrue(String message, boolean condition) {
        if (!condition) {
            print();
            org.junit.Assert.fail(message);
        }
    }

    public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    public void assertEquals(String message, int number1, int number2) {
        assertTrue(message, number1 == number2);
    }

    public void assertEquals(double expected, double actual) {
        assertEquals(expected, actual, GZUtil.kEpsilon);
    }

    public void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    public void assertEquals(String message, double expected, double actual, double delta) {
        String text = "";

        if (message != null) {
            text = "[" + message + "]\t";
        }

        text += "Expected <" + expected + ">" + " should have been <" + delta + "> away from <" + actual + ">";

        assertTrue(text, GZUtil.epsilonEquals(expected, actual, delta));
    }

    private void print() {
        realConsole.println(newConsole.toString());
    }
}