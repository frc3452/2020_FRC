package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakePositions;
import frc.robot.subsystems.PositionedSubsystem;

public class PositionCommand extends SequentialCommandGroup {

    public PositionCommand(PositionedSubsystem subsystem, OuttakePositions targetPosition) {

        InstantCommand setPosition = new InstantCommand(() -> {
            subsystem.setPosition(targetPosition);
        });

        WaitCommand waitCommand = new WaitCommand(0.5);

        PrintCommand printCommand = new PrintCommand("Positioned subsystem moved to " + targetPosition);

        addCommands(setPosition, waitCommand, printCommand);
    }
}
