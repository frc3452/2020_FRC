package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kOuttake.OuttakePositions;
import frc.robot.subsystems.Outtake;

public class MoveOuttakeCommand extends CommandBase {

    private Outtake outtake;
    private OuttakePositions position;

    public MoveOuttakeCommand(OuttakePositions outtakePosition) {
        position = outtakePosition;
    }

    public void execute() {
            outtake.setPosition(position);
    }
}