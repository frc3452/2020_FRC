package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.outtake.NoFinishOuttakeCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;

public class DriveFromSideEject extends SequentialCommandGroup {

    public DriveFromSideEject(boolean leftSide, DriveTrain driveTrain, Outtake outtake) {

        addCommands(new DriveForTime(driveTrain, -.3, 0, .7));
        if(leftSide) {
            addCommands(new DriveForTime(driveTrain, -.3, .2, .4));
            addCommands(new DriveForTime(driveTrain, -.3, -.2, .4));
        }
        else {
            addCommands(new DriveForTime(driveTrain, -.3, -.2, .4));
            addCommands(new DriveForTime(driveTrain, -.3, .2, .4));
        }
        // addCommands(new DriveForTime(driveTrain, -.3, 0, .7)); For if we need to move forward more after.
        addCommands(new WaitCommand(1));
        addCommands(new NoFinishOuttakeCommand(outtake, OuttakeSpeeds.RUNNING).withTimeout(4));
    } 
}