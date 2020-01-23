package frc.robot.geometrytests;

import frc.robot.testingutil.JTest;
import frc.robot.util.math.Rotation2d;
import org.junit.Test;

public class AngleDifferenceTest extends JTest {

    @Test
    public void testAngleDifference() {
        {
            var tar = new Rotation2d(45);
            var actual = new Rotation2d(46);
            assertTrue(tar.near(actual, 2));
        }
        {
            var tar = new Rotation2d(45);
            var actual = new Rotation2d(44);
            assertTrue(tar.near(actual, 2));
        }
        {
            var tar = new Rotation2d(45);
            var actual = new Rotation2d(360 + 45);
            assertTrue(tar.near(actual, 2));
        }
        {
            var tar = new Rotation2d(45);
            var actual = new Rotation2d(45 + 90);
            assertFalse(tar.near(actual, 2));
        }
    }

}