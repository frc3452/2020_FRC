package frc.robot;

import frc.robot.testingutil.JTest;
import frc.robot.util.DCMotor;
import org.junit.Test;

public class DCMotorTest extends JTest {
    //so you dont touch my constants >:(
    //use motor.printConstants()

    @Test
    public void checkCIM() {
        DCMotor cim = DCMotor.makeCIM();

        var kt = 0.018396946564885497;
        var kv = 47.49186452143417;
        var R = 0.0916030534351145;

        assertEquals(kt, cim.getKt());
        assertEquals(kv, cim.getKv());
        assertEquals(R, cim.getResistance());
    }

    @Test
    public void checkMiniCIM() {
        DCMotor mini = DCMotor.makeMiniCIM();
        var kt = 0.015842696629213483;
        var kv = 52.741414652126316;
        var R = 0.1348314606741573;

        assertEquals(kt, mini.getKt());
        assertEquals(kv, mini.getKv());
        assertEquals(R, mini.getResistance());
    }

    @Test
    public void checkBagMotor() {
        DCMotor bag = DCMotor.makeBagMotor();

        var kt = 0.008113207547169812;
        var kv = 119.06077106329288;
        var R = 0.22641509433962265;

        assertEquals(kt, bag.getKt());
        assertEquals(kv, bag.getKv());
        assertEquals(R, bag.getResistance());
    }

    @Test
    public void check775Motor() {
        DCMotor sevenSevenFive = DCMotor.make775Pro();

        var kt = 0.005298507462686567;
        var kv = 164.30841197450897;
        var R = 0.08955223880597014;

        assertEquals(kt, sevenSevenFive.getKt());
        assertEquals(kv, sevenSevenFive.getKv());
        assertEquals(R, sevenSevenFive.getResistance());
    }

    @Test
    public void checkBaneBotRs_550() {
        DCMotor fiveFifty = DCMotor.makeBaneBotsRs_550();

        var kt = 0.004523809523809524;
        var kv = 166.59961041764055;
        var R = 0.14285714285714285;

        assertEquals(kt, fiveFifty.getKt());
        assertEquals(kv, fiveFifty.getKv());
        assertEquals(R, fiveFifty.getResistance());
    }
}
