package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.outtake.NoFinishOuttakeCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;

public class DriveStraightAndEject extends SequentialCommandGroup {
    public DriveStraightAndEject(DriveTrain driveTrain, Outtake outtake) {
        addCommands(new DriveForTime(driveTrain, -0.4, 0, 1.1));
        addCommands(new DriveForTime(driveTrain, -0.2, 0, 1));
        addCommands(new DriveForTime(driveTrain, -0.16, 0, 1.1));
        addCommands(new WaitCommand(1.0));
        addCommands(new NoFinishOuttakeCommand(outtake, OuttakeSpeeds.RUNNING).
            withTimeout(3.0).
            beforeStarting(() -> System.out.println("Running outtake command")));
        // addCommands(new InstantOuttakeCommand(outtake, OuttakeSpeeds.STOPPED));
        addCommands(new PrintCommand("Outtake should be stopped"));
    }

}
//lineup: you can see the auto line between the two back wheels. Facing the power port.