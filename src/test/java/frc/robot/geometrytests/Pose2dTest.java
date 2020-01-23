package frc.robot.geometrytests;

import frc.robot.testingutil.JTest;
import frc.robot.util.math.GZUtil;
import frc.robot.util.math.Pose2d;
import frc.robot.util.math.Translation2d;
import org.junit.Test;

public class Pose2dTest extends JTest {

    @Test
    public void testTranslation() {
        Pose2d p = new Pose2d(0, 0, 45);
        var newPose = p.translateBy(new Translation2d(0, 1));
        var newTranslation = newPose.getTranslation();

        final boolean xWhereItShouldBe = GZUtil.epsilonEquals(Math.sqrt(0.5), newTranslation.x(), 1E-4);
        assertTrue("X not where it should be!", xWhereItShouldBe);

        final boolean yWhereItShouldBe = GZUtil.epsilonEquals(Math.sqrt(0.5), newTranslation.y(), 1E-4);
        assertTrue("Y not where it should be!", yWhereItShouldBe);

        final boolean angleWhereItShoudlBe = newPose.getRotation().equals(p.getRotation());
        assertTrue("Angle not where it should be!", angleWhereItShoudlBe);
    }

    @Test
    public void testFieldOfView() {
        //Use link below to verify
//        www.desmos.com/calculator/vnsbvmypvi

        assertFOV(new Pose2d(0, 10, -45).hasPointInFOV(10, new Translation2d(4, 6)));
        assertNoFOV(new Pose2d(0, 10, -45).hasPointInFOV(10, new Translation2d(6, 7)));
        assertFOV(new Pose2d(0, 10, -45).hasPointInFOV(5, new Translation2d(8, 3)));
        assertNoFOV(new Pose2d(0, 10, -45).hasPointInFOV(5, new Translation2d(9, 4)));

        assertNoFOV(new Pose2d(4, 2, 45).hasPointInFOV(45, new Translation2d(3, 9)));

        assertFOV(new Pose2d(4, 2, 45).hasPointInFOV(45, new Translation2d(4, 10)));

        assertFOV(new Pose2d(4, 2, 45).hasPointInFOV(45, new Translation2d(5, 10)));
    }

    private void assertNoFOV(boolean hasPointInFOV) {
        assertTrue("Point within field of view", !hasPointInFOV);
    }


    public void assertFOV(boolean hasPointInFOV) {
        assertTrue("Point not within field of view", hasPointInFOV);
    }
}
