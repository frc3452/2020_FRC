package frc.robot.geometrytests;


import frc.robot.testingutil.JTest;
import frc.robot.util.math.GZUtil;
import frc.robot.util.math.Rotation2d;
import org.junit.Test;

import java.text.DecimalFormat;

import static frc.robot.util.math.GZUtil.kEpsilon;

/**
 * This test insures the absolute value in Rotation2d.equals(final Rotation2d other) is being applied correctly
 */
public class Rotation2dTest extends JTest {

    private final DecimalFormat df = new DecimalFormat("#0.00");

    @Test
    public void checkAbsoluteValue() {
        var a = new Rotation2d(-135);
        var b = new Rotation2d(95);
        System.out.println(a);
        System.out.println(b);

        System.out.println("equal: " + a.equals(b));

        System.out.println("Distance: " + a.distance(b));

        assertFalse(a.equals(b));
    }

    @Test
    public void checkMiddle() {
        for (int i = 0; i < 10; i++) {
            cm(GZUtil.getRandInt(0, 360), GZUtil.getRandInt(0, 360));
        }
        for (int i = 0; i < 10; i++) {
            cm(GZUtil.getRandInt(0, 180), GZUtil.getRandInt(-180, 0));
        }
        for (int i = 0; i < 10; i++) {
            cm(GZUtil.getRandInt(-180, 0), GZUtil.getRandInt(0, 180));
        }


    }

    private void cm(double a, double b) {
        Rotation2d first = new Rotation2d(a);
        Rotation2d second = new Rotation2d(b);

        Rotation2d middle = first.findMiddle(second);

        double firstDistance = Math.abs(middle.distanceDeg(first));
        double secondDistance = Math.abs(middle.distanceDeg(second));

        assertEquals("First distance should equal second distance: (FIRST)" + first + "\t(SECOND)" + second + "(MIDDLE)" + middle, firstDistance, secondDistance,
                kEpsilon);
    }

}
