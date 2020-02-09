/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveForTime extends CommandBase {
    private final DriveTrain m_DriveTrain;

    private double startTime, time, rotate, move;

    /**
     * Creates a new DriveForTime.
     */
    public DriveForTime(DriveTrain driveTrain, double move, double rotate, double time) {
        m_DriveTrain = driveTrain;
        this.move = move;
        this.rotate = rotate;
        this.time = time;

        addRequirements(m_DriveTrain);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_DriveTrain.arcadeDriveControl(move, rotate, false);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_DriveTrain.arcadeDriveControl(0, 0, false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (Timer.getFPGATimestamp() - startTime) >= time;
    }
}
