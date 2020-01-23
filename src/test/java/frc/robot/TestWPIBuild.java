package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import frc.robot.testingutil.JTest;
import org.junit.Test;

public class TestWPIBuild extends JTest {

    @Test
    public void constructPIDController() {
        PIDController controller = new PIDController(1, 0, 0);
    }

    @Test
    public void constructDifferentialDrive() {
        SpeedController leftSpeedController = new Spark(0);
        SpeedController rightSpeedController = new Spark(1);
        DifferentialDrive drive = new DifferentialDrive(leftSpeedController, rightSpeedController);
    }

    @Test
    public void constructDiffOdometry() {
        DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), new Pose2d());
    }
}
