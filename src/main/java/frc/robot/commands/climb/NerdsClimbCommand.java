package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NerdsClimber;

public class NerdsClimbCommand extends CommandBase {
    private final NerdsClimber nerdsClimber;
    private final double power;

    public NerdsClimbCommand(NerdsClimber climber, double command) {
        this.nerdsClimber = climber;
        this.power = command;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        nerdsClimber.setCommand(power);
    }

    @Override
    public void end(boolean interrupted) {
        nerdsClimber.resetClimber();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //Default, but also good. We don't have any stop conditions for this command
        return false;
    }
}