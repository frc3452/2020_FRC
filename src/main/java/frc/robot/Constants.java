/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static class kHardwarePorts {

        public static final int kLeftMasterID = 1;
        public static final int kLeftFollower1ID = 2;
        public static final int kRightMasterID = 3;
        public static final int kRightFollower1ID = 4;

        public static final int kBridgeServoID = 8;
        public static final int kIntakeMotorID = 5;
        public static final int kTestSparkMaxID = 5;

    }

    public static class kXboxButtons {

        // Xbox joysticks
        public static final int LEFT_STICK_X = 0;
        public static final int LEFT_STICK_Y = 1;
        public static final int LEFT_TRIGGER = 2;
        public static final int RIGHT_TRIGGER = 3;
        public static final int RIGHT_STICK_X = 4;
        public static final int RIGHT_STICK_Y = 5;

        // Xbox buttons
        public static final int A = 1;
        public static final int B = 2;
        public static final int X = 3;
        public static final int Y = 4;
        public static final int LB = 5;
        public static final int RB = 6;
        public static final int LOGO_LEFT = 7;
        public static final int LOGO_RIGHT = 8;
        public static final int LEFT_STICK_BUTTON = 9;
        public static final int RIGHT_STICK_BUTTON = 10;

    }

    public static class kOuttake {

        public static enum OuttakePositions {
            OPEN(90.0), CLOSED(0.0);

            private double angle;

            private OuttakePositions(double angle) {
                this.angle = angle;
            }

            public double getAngle() {
                return angle;
            }

        };

    }

    public static class kIntake {
        public static enum IntakeSpeeds {
            STOPPED(0.0), SLOW(0.3), MEDIUM(0.5), FAST(0.9);

            private double speed;

            private IntakeSpeeds(double speed) {
                this.speed = speed;
            }

            public double getSpeed() {
                return speed;
            }
        }
    }

    
}
