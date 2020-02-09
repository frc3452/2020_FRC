package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class OuttakeCommand extends SequentialCommandGroup {
    public OuttakeCommand(/*required subsystems go here*/) {
        addCommands(/*commands go here*/
            new WaitCommand(1.1)
        );
        
        
    }
}
