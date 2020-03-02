package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.intake.NoFinishIntakeCommand;
import frc.robot.commands.outtake.NoFinishOuttakeCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;

public class AutoFeedSomeoneElseCommand extends SequentialCommandGroup {
  public AutoFeedSomeoneElseCommand(double waitTime, boolean useOuttake,DriveTrain driveTrain, Outtake outtake, Intake intake) { 
      addCommands(new WaitCommand(waitTime));
    
      if (useOuttake) {
          addCommands(new NoFinishOuttakeCommand(outtake, OuttakeSpeeds.RUNNING).withTimeout(5.0));
          addCommands(new DriveForTime(driveTrain, 0.7, -.4, .8));
      } else {
          addCommands(new NoFinishIntakeCommand(intake, IntakeSpeeds.BACKWARDS).withTimeout(5.0));
          addCommands(new DriveForTime(driveTrain, -0.7, .4, .8));
      }

    
      //addCommands();
      //drive away
  }
}
