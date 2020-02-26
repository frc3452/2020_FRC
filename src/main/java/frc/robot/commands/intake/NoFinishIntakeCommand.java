package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;

public class NoFinishIntakeCommand extends CommandBase {
    private final Intake intake;
    private final IntakeSpeeds runSpeed;

    public NoFinishIntakeCommand(Intake intake, IntakeSpeeds runSpeed) {
        this.intake = intake;
        this.runSpeed = runSpeed;
    }

    @Override
    public void initialize() {
        //Set intake to speed on start
        intake.moveIntake(runSpeed);
    }

    @Override
    public boolean isFinished() {
        //We won't ever stop this command from here, something else will call cancel() on it from outside.
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //When something does stop this command, stop the intake
        this.intake.moveIntake(IntakeSpeeds.STOPPED);
    }
}
