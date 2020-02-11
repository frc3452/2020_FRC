package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Outtake;

public class MasterOuttakeCommand extends SequentialCommandGroup {

    public Outtake outtake;

    public MasterOuttakeCommand(/*required subsystems go here*/ Outtake outtake) {
        this.outtake = outtake;
        addCommands(/*commands go here*/
            new MoveOuttakeCommand(true),
            new WaitCommand(5.0),
            new MoveOuttakeCommand(false)
        );

    }
}
