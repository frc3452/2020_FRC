package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Outtake;

public class MoveOuttakeCommand extends CommandBase {

    private Outtake outtake;

    public MoveOuttakeCommand(boolean open) {
        if(open){
        outtake.setPosition(Constants.kOuttake.OuttakePositions.OPEN);
        }
        else{
        outtake.setPosition(Constants.kOuttake.OuttakePositions.CLOSED);
        }
        
    }
}