package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

public class TestAuto extends SequentialCommandGroup {

    public TestAuto(DriveTrain driveTrain, double speed, double rotation, double time) {

        addRequirements(driveTrain);

        InstantCommand move = new InstantCommand(() ->  {
        driveTrain.arcadeDriveControl(speed, rotation, false); 
        });

        addCommands(move);
    }
}