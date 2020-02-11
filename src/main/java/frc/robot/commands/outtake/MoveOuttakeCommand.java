package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Outtake;

public class MoveOuttakeCommand extends CommandBase {

    private Outtake outtake;
    private boolean open;

    public MoveOuttakeCommand(boolean open) {
        this.open = open;
    }

    public void execute() {
        if(open){
            outtake.setPosition(Constants.kOuttake.OuttakePositions.OPEN);
            }
            else{
            outtake.setPosition(Constants.kOuttake.OuttakePositions.CLOSED);
            }
    }
}