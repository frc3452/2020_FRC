package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.subsystems.Outtake;

public class OuttakeCommand extends SequentialCommandGroup {

    public OuttakeCommand(Outtake outtake, OuttakeSpeeds targetSpeed) {
        addRequirements(outtake);

        InstantCommand setPosition = new InstantCommand(() -> { 
            outtake.setOuttakeSpeed(targetSpeed);
        });

        

        addCommands(setPosition);
    }   

}
