package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.subsystems.DriveTrain;

public class EncoderMovementAutoExample extends SequentialCommandGroup {
    public EncoderMovementAutoExample(DriveTrain driveTrain) {
        addCommands(new PrintCommand("Starting drive"));
        addCommands(new DriveDistance(driveTrain, 5.0 * 12.0, 5));
        addCommands(new PrintCommand("Ending drive"));
    }
}
