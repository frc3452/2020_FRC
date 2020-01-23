package frc.robot.subsystems.drive;

public class DriveConstants {
    private final double kWheelRadiusInches;
    private final double kSensorUnitsPerRevolution;
    private final double kTrackWidthInches;
    private final double kSensorVelTimeUnitSeconds;

    private final double kSensorUnitsPerInch;

    public DriveConstants(double wheelRadiusInches, double sensorUnitsPerRevolution, double trackWidthInches, double sensorVelTimeUnitSeconds) {
        this.kWheelRadiusInches = wheelRadiusInches;
        this.kSensorUnitsPerRevolution = sensorUnitsPerRevolution;
        this.kTrackWidthInches = trackWidthInches;
        this.kSensorVelTimeUnitSeconds = sensorVelTimeUnitSeconds;
        this.kSensorUnitsPerInch = (2.0 * wheelRadiusInches * Math.PI) * sensorUnitsPerRevolution;
    }

    public double getWheelRadiusInches() {
        return kWheelRadiusInches;
    }

    public double getSensorUnitsPerRevolution() {
        return kSensorUnitsPerRevolution;
    }

    public double getSensorUnitsPerInch() {
        return kSensorUnitsPerInch;
    }

    public double getTrackWidthInches() {
        return kTrackWidthInches;
    }

    public double getSensorVelTimeUnitSeconds() {
        return kSensorVelTimeUnitSeconds;
    }
}
