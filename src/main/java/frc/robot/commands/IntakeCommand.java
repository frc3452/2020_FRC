package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

    private IntakeSpeeds intakeSpeed;
    private Intake intake;

    public IntakeCommand(Intake intake, IntakeSpeeds speed) {
        intakeSpeed = speed;
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        if 
        intake.moveIntake(intakeSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("hah! You found me!");
        // intake.moveIntake(IntakeSpeeds.STOPPED);
    }

    @Override
    public boolean isFinished() {
        
        return true;
    }

};

