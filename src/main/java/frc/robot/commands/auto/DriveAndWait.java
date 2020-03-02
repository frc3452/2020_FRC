package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.outtake.NoFinishOuttakeCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;

public class DriveAndWait extends SequentialCommandGroup {
public DriveAndWait(DriveTrain driveTrain, Outtake outtake ) {

addCommands(new DriveForTime(driveTrain, -.3, 0, .3 ));
addCommands(new WaitCommand(1));
addCommands(new DriveForTime(driveTrain, -.3, .3, .3));
addCommands(new WaitCommand(1));
addCommands(new NoFinishOuttakeCommand(outtake, OuttakeSpeeds.RUNNING).withTimeout(3));
}

}