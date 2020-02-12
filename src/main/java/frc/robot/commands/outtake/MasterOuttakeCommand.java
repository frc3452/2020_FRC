package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakePositions;

public class MasterOuttakeCommand extends SequentialCommandGroup {

    public MasterOuttakeCommand(/*required subsystems go here*/) {
        addCommands(/*commands go here*/
            new MoveOuttakeCommand(OuttakePositions.OPEN),
            new WaitCommand(5.0),
            new MoveOuttakeCommand(OuttakePositions.CLOSED),
            new WaitCommand(0.5)
        );

    }
}
