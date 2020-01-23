package frc.robot.testingutil;

import java.text.DecimalFormat;

public class TimedTest extends JTest {
    public static DecimalFormat df = new DecimalFormat("#0.000");
    private double t = 0.0;
    private double dt = 0.1;

    public void printTime() {
        System.out.println(getTime());
    }

    protected double getTime() {
        return t;
    }

    protected double getInterval() {
        return this.dt;
    }

    protected void setInterval(double integral) {
        this.dt = integral;
    }

    public void delayTest(double time) {
        double endTime = t + time;

        while (t <= endTime) {
            advanceTime();
        }
    }

    private double advanceTime() {
        t += dt;
        return t;
    }

    public TestCondition getLoopCondition(String name, int loops, boolean fail) {
        return new TestCondition("Loop counter " + name, fail) {

            int mLoops = 0;

            public void onLoop() {
                mLoops++;
            }

            @Override
            public boolean complete() {
                return mLoops >= loops;
            }

        };
    }

    protected void run(Looper l, TestCondition... stopConditions) {
        for (TestCondition c : stopConditions) {
            c.onStart();
        }

        boolean anyConditionsMet = false;
        while (!anyConditionsMet) {
            for (TestCondition c : stopConditions) {
                if (c.complete()) {
                    anyConditionsMet = true;
                    c._onEnd();
                }
            }
            if (anyConditionsMet) {
                return;
            }

            l.run();

            for (TestCondition c : stopConditions) {
                c.onLoop();
            }

            advanceTime();
        }

    }

    protected TestCondition getTimeout(double time, boolean fail) {
        return getTimeout(TestCondition.UNNAMED_TEST_CONDITION, time, fail);
    }

    protected TestCondition getTimeout(String name, double time) {
        return getTimeout(name, time, false);
    }

    protected TestCondition getTimeout(double time) {
        return getTimeout("", time);
    }

    protected TestCondition getTimeout(String name, double time, boolean fail) {
        return new TestCondition("[Timeout " + df.format(time) + " ] " + name, fail) {
            private double startTime;

            public void onStart() {
                startTime = t;
            }

            @Override
            public boolean complete() {
                return t >= (startTime + time);
            }
        };
    }

    public enum SwervePrint {
        POSE, POSE_CSV, MODULE_ANGLE, NONE, TRAJECTORY_ERROR_CSV
    }

    public static abstract class Looper {
        public abstract void run();
    }

    public abstract class TestCondition {
        static final String UNNAMED_TEST_CONDITION = "Unnamed Test Condition";
        final String name;
        private boolean ended;
        private boolean failOnCompletion;


        public TestCondition() {
            this(UNNAMED_TEST_CONDITION);
        }

        public TestCondition(boolean failOnCompletion) {
            this(UNNAMED_TEST_CONDITION, failOnCompletion);
        }

        public TestCondition(String name) {
            this(name, false);
        }

        public TestCondition(String name, boolean failOnCompletion) {
            this.name = name;
            this.failOnCompletion = failOnCompletion;
        }

        public abstract boolean complete();

        public void onStart() {
        }

        void onLoop() {
        }

        void _onEnd() {
            if (!ended) {
                onEnd();
                ended = true;
                if (failOnCompletion) {
                    fail(name);
                }
            }
        }

        public void onEnd() {
        }

        @Override
        public String toString() {
            return name;
        }
    }
}