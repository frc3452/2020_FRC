package frc.robot.geometrytests;

import frc.robot.testingutil.JTest;
import frc.robot.util.math.Rotation2d;
import frc.robot.util.math.Translation2d;
import org.junit.Test;

public class AngleCheck extends JTest {

    @Test
    public void checkPushTranslationAway() {
        testTranslation(5, 0, 5, 0);
        testTranslation(5, 90, 0, 5);
        testTranslation(5, 180, -5, 0);
        testTranslation(5, 270, 0, -5);
    }

    private void testTranslation(double distance, double angle, double end_x, double end_y) {
        Translation2d t = new Translation2d();
        Translation2d newT = t.translateBy(distance, new Rotation2d(angle));

        Translation2d guess = new Translation2d(end_x, end_y);

        // System.out.println(guess);
        assertTrue("checkTranslations --> " + guess + "\tshould be " + distance + " away from (0,0) at " + angle
                + " degs" + ", actually at " + newT, newT.equals(guess));
    }

    @Test
    public void checkPointsToAngles() {
        testPointToAngle(1, 0, 0);
        testPointToAngle(1, 1, 45);
        testPointToAngle(0, 1, 90);
        testPointToAngle(-1, 0, 180);
        testPointToAngle(0, -1, -90);
        testPointToAngle(-1, -1, 225);
        testPointToAngle(-1, -1, -(180 - 45));

    }

    private void testPointToAngle(double x, double y, double theta) {
        Translation2d t = new Translation2d(x, y);
        Rotation2d r = t.direction();

        var guess = new Rotation2d(theta);

        boolean guessAccurate = r.equals(guess);

        assertTrue("Point " + t.toString() + " produces angle " + r + ", but expected " + guess, guessAccurate);
    }

}