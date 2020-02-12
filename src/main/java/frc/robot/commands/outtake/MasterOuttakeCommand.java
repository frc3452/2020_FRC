package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class MasterOuttakeCommand extends SequentialCommandGroup {

    public MasterOuttakeCommand(/*required subsystems go here*/) {
        addCommands(/*commands go here*/
            new MoveOuttakeCommand(true),
            new WaitCommand(5.0),
            new MoveOuttakeCommand(false),
            new WaitCommand(0.5)
        );

    }
}
