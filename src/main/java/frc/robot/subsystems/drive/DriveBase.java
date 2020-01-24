package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class DriveBase extends SubsystemBase {

    private final Object lock;
    private DriveConstants constants;

    private IO io = new IO();

    private DifferentialDriveOdometry odometry;

    public DriveBase(DriveConstants constants) {
        lock = new Object();

        this.constants = constants;
        odometry = new DifferentialDriveOdometry(getGyroHeading(), new Pose2d());
    }

    @Override
    public final void periodic() {
        periodicTasks();
        readSensors();
        updateOdometry();
    }

    public abstract Rotation2d getGyroHeading();

    public final void resetOdometry(Pose2d poseMeters) {
        io.zero();
        odometry.resetPosition(poseMeters, getGyroHeading());
    }

    private void updateOdometry() {
        synchronized (lock) {
            final double leftInches = getInches(getLeftTicks());
            final double rightInches = getInches(getRightTicks());
            odometry.update(getGyroHeading(), Units.inchesToMeters(leftInches), Units.inchesToMeters(rightInches));
        }
    }

    public double getAverageEncoderDistanceMeters() {
        return (getLeftDistanceMeters() + getRightDistanceMeters()) / 2.0;
    }

    public DifferentialDriveWheelSpeeds getWheelSpeedsMetersPerSecond() {
        double leftInchesPerSecond = getInchesPerSecond(getLeftSensorTickVelocity());
        double rightInchesPerSecond = getInchesPerSecond(getRightSensorTickVelocity());
        return new DifferentialDriveWheelSpeeds(Units.inchesToMeters(leftInchesPerSecond), Units.inchesToMeters(rightInchesPerSecond));
    }

    public abstract void periodicTasks();

    public abstract void setVoltage(double leftVoltage, double rightVoltage);

    public abstract double getLeftSensorTicks();

    public abstract double getRightSensorTicks();

    public abstract double getLeftSensorTickVelocity();

    public abstract double getRightSensorTickVelocity();

    public final double getLeftDistanceMeters() {
        double inches = getInches(getLeftTicks());
        return Units.inchesToMeters(inches);
    }

    public final double getRightDistanceMeters() {
        double inches = getInches(getRightTicks());
        return Units.inchesToMeters(inches);
    }

    private final double getLeftTicks() {
        return io.rawLeftTicks - io.leftTickOffset;
    }

    private final double getRightTicks() {
        return io.rawRightTicks - io.rightTickOffset;
    }

    public final double getInches(double ticks) {
        return ticks / constants.getSensorUnitsPerInch();
    }

    public final double getInchesPerSecond(double ticks_per_time) {
        return getInches(ticks_per_time) / constants.getSensorVelTimeUnitSeconds();
    }

    public void readSensors() {
        synchronized (lock) {
            io.rawLeftTicks = getLeftSensorTicks();
            io.rawRightTicks = getRightSensorTicks();

            io.leftTickVelocity = getLeftSensorTickVelocity();
            io.rightTickVelocity = getRightSensorTickVelocity();
        }
    }

    private class IO {

        public double rawLeftTicks = 0;
        public double leftTickVelocity = 0;
        public double leftTickOffset = 0;

        public double rawRightTicks = 0;
        public double rightTickVelocity = 0;
        public double rightTickOffset = 0;

        public void zero() {
            leftTickOffset = rawLeftTicks;
            rightTickOffset = rawRightTicks;
        }
    }
}
