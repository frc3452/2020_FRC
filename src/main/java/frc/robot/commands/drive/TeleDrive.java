/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TeleDrive extends CommandBase {
    private final DriveTrain teleDrive;

    private Supplier<Double> driveSpeed;

    private Supplier<Double> driveRotation;
  
    private boolean inputsSquare;

    public TeleDrive(DriveTrain driveTrain, Supplier<Double> speed, Supplier<Double> rotation, 
    boolean squareInputs) {
        teleDrive = driveTrain;
        driveSpeed = speed;
        driveRotation = rotation;
        inputsSquare = squareInputs;
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
        teleDrive.arcadeDriveControl(driveSpeed.get() *.35, driveRotation.get() *.5, inputsSquare);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //Default, but also good. We don't have any stop conditions for this command
        return false;
    }
}
