package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakePositions;
import frc.robot.subsystems.Outtake;

public class OuttakeCommand extends SequentialCommandGroup {

    public OuttakeCommand(/*required subsystems go here*/ Outtake outtake, OuttakePositions targetPosition) {
        addRequirements(outtake);

        InstantCommand setPosition = new InstantCommand(() -> { 
        outtake.setPosition(targetPosition);
        });

        WaitCommand waitCommand = new WaitCommand(1.0);

        PrintCommand printCommand = new PrintCommand("Outtake moved to" + targetPosition);

        addCommands(setPosition, waitCommand, printCommand);
        
    }   

}
