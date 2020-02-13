package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakePositions;
import frc.robot.Constants.kOuttake.TestEnum;
import frc.robot.subsystems.Outtake;

public class OuttakeCommand extends SequentialCommandGroup {

    public OuttakeCommand(/*required subsystems go here*/ Outtake outtake, TestEnum wantDo) {
        if(wantDo == TestEnum.DOFULLCOMMAND){
        addCommands(/*commands go here*/
            testOuttakeCommand(OuttakePositions.OPEN, outtake),
            new WaitCommand(5.0),
            testOuttakeCommand(OuttakePositions.CLOSED, outtake),
            new WaitCommand(0.5)
        );
        }
        else if(wantDo == TestEnum.OPEN) {
            testOuttakeCommand(OuttakePositions.OPEN, outtake);
        }
        else {
            testOuttakeCommand(OuttakePositions.CLOSED, outtake);
        }

    }

    public Command testOuttakeCommand(OuttakePositions position, Outtake outtake) {
        outtake.setPosition(position);
        return this;
    }

	public class getInstance {
	}
}
