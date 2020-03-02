package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

public class TestAuto extends CommandBase {

    private DriveTrain driveTrain;
    private double speed; 
    private double rotation;
    private double time;
    private double startTime;

    public TestAuto(DriveTrain driveTrain, double speed, double rotation, double time) {
        this.driveTrain = driveTrain;
        this.speed = speed;
        this.rotation = rotation;
        this.time = time;
        addRequirements(driveTrain);
    }

    @Override 
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    };

    @Override
    public void execute() {
        driveTrain.arcadeDriveControl(speed, rotation, false);
    };

    @Override
    public boolean isFinished() {
        if ((Timer.getFPGATimestamp() - startTime) >= time) {
            return true;
        }
        return false;
    };
}