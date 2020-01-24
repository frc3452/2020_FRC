/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class PathFollowing {
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double kMaxSpeedMetersPerSecond = Units.feetToMeters(10.0);
        public static final double kMaxAccelerationMetersPerSecondSquared = Units.feetToMeters(3.0);
    }

    public static final class DriveTrain {
        public static final double kWheelRadiusInches = 3.0;
        public static final double SensorUnitsPerRotation = 4096.0;
    }
}
