package frc.robot;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.subsystems.drive.DriveBase;
import frc.robot.subsystems.drive.DriveConstants;
import org.junit.Test;

public class DriveBaseTest {
    @Test
    public void test() {
        DriveBase base = new DriveBase(new DriveConstants(3, 4096, 30, 0.1)) {
            @Override
            public Rotation2d getGyroHeading() {
                return new Rotation2d(1, 1);
            }

            @Override
            public void periodicTasks() {

            }

            @Override
            public double getLeftSensorTicks() {
                return 0;
            }

            @Override
            public double getRightSensorTicks() {
                return 0;
            }

            @Override
            public double getLeftSensorTickVelocity() {
                return 0;
            }

            @Override
            public double getRightSensorTickVelocity() {
                return 0;
            }
        };

    }
}
