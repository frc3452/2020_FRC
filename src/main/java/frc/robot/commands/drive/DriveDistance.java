package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveDistance extends CommandBase {

    private final double VELOCITY_TOLERANCE = 3.0;

    private final DriveTrain drivetrain;
    private final double distance;
    private double startingLeftDistance;
    private double startingRightDistance;
    private double tolerance_inches;

    public DriveDistance(DriveTrain drivetrain, double distance_inches, double tolerance_inches) {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
        this.distance = distance_inches;
        this.tolerance_inches = tolerance_inches;
    }

    @Override
    public void initialize() {
        drivetrain.disableMotorSaftey();
        startingLeftDistance = drivetrain.getLeftInches();
        startingRightDistance = drivetrain.getRightInches();
        drivetrain.driveDistance(distance);
    }

    @Override
    public boolean isFinished() {
        boolean left_distance = Math.abs(drivetrain.getLeftInches() - (startingLeftDistance + distance)) < tolerance_inches;
        boolean right_distance = Math.abs(drivetrain.getRightInches() - (startingRightDistance + distance)) < tolerance_inches;

        System.out.println(drivetrain.getLeftRPM() + "\t" + drivetrain.getRightRPM());
        boolean left_speed = Math.abs(drivetrain.getLeftRPM()) < VELOCITY_TOLERANCE;
        boolean right_speed = Math.abs(drivetrain.getRightRPM()) < VELOCITY_TOLERANCE;

        return left_distance && right_distance
                && left_speed && right_speed;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.enableMotorSaftey();
        drivetrain.arcadeDriveControl(0, 0, false);
    }
}
