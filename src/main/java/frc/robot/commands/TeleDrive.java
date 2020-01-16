//There's nothing changed in here
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleDrive extends CommandBase {
  private final DriveTrain teleDrive;


  /**
   * Creates a new TeleDrive.
   * @param driveTrain
   */
  
  

  public TeleDrive(DriveTrain driveTrain) {
    teleDrive = driveTrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(teleDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    teleDrive.arcadeDriveControl(speed, rotation, squareInputs);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
