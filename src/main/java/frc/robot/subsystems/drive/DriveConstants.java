package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;

public class DriveConstants {
    private final double kWheelRadiusInches;
    private final double kSensorUnitsPerRevolution;
    private final double kTrackWidthInches;
    private final double kSensorVelTimeUnitSeconds;

    private final double kSensorUnitsPerInch;

    private final DifferentialDriveVoltageConstraint kVoltageConstraint;

    private final DifferentialDriveKinematics kDriveKinematics;

    public DriveConstants(double wheelRadiusInches, double sensorUnitsPerRevolution, double trackWidthInches, double sensorVelTimeUnitSeconds, SimpleMotorFeedforward feedforward) {
        this(wheelRadiusInches, sensorUnitsPerRevolution, trackWidthInches, sensorVelTimeUnitSeconds, feedforward, 10);
    }

    public DriveConstants(double wheelRadiusInches, double sensorUnitsPerRevolution, double trackWidthInches, double sensorVelTimeUnitSeconds, SimpleMotorFeedforward feedforward, double maxPathFollowingVoltage) {
        this.kWheelRadiusInches = wheelRadiusInches;
        this.kSensorUnitsPerRevolution = sensorUnitsPerRevolution;
        this.kTrackWidthInches = trackWidthInches;
        this.kSensorVelTimeUnitSeconds = sensorVelTimeUnitSeconds;

        this.kSensorUnitsPerInch = (2.0 * wheelRadiusInches * Math.PI) * sensorUnitsPerRevolution;

        this.kDriveKinematics = new DifferentialDriveKinematics(Units.inchesToMeters(kTrackWidthInches));

        this.kVoltageConstraint = new DifferentialDriveVoltageConstraint(feedforward, kDriveKinematics, maxPathFollowingVoltage);
    }

    public final double getWheelRadiusInches() {
        return kWheelRadiusInches;
    }

    public final double getSensorUnitsPerRevolution() {
        return kSensorUnitsPerRevolution;
    }

    public final double getSensorUnitsPerInch() {
        return kSensorUnitsPerInch;
    }

    public final double getTrackWidthInches() {
        return kTrackWidthInches;
    }

    public final double getSensorVelTimeUnitSeconds() {
        return kSensorVelTimeUnitSeconds;
    }

    public final DifferentialDriveVoltageConstraint getVoltageConstraint() {
        return kVoltageConstraint;
    }

    public final DifferentialDriveKinematics getDriveKinematics() {
        return kDriveKinematics;
    }
}
