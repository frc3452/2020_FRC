package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

    private IntakeSpeeds intakeSpeed;
    private Intake intake = new Intake();

    public IntakeCommand(Intake intake, IntakeSpeeds speed) {
        intakeSpeed = speed;
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.moveIntake(intakeSpeed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

};
