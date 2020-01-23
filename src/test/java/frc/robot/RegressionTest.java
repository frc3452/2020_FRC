package frc.robot;

import frc.robot.testingutil.JTest;
import frc.robot.util.math.GZUtil;
import frc.robot.util.math.PolynomialRegression;
import org.junit.Test;

public class RegressionTest extends JTest {

    @Test
    public void basic() {

        double[] x = {0, 2, 4, 6};
        double[] y = {0, 4, 2, 6};

        PolynomialRegression p = new PolynomialRegression(x, y, 5);

        assertTrue("Regression should be near", GZUtil.epsilonEquals(p.predict(3), 3));
    }
}