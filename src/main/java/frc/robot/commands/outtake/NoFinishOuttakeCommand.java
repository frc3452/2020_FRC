package frc.robot.commands.outtake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.subsystems.Outtake;

public class NoFinishOuttakeCommand extends SequentialCommandGroup {
    private Outtake outtake; 
    private OuttakeSpeeds speed;

    public NoFinishOuttakeCommand(Outtake outtake, OuttakeSpeeds targetSpeed) {
        this.outtake = outtake;
        speed = targetSpeed;
        addRequirements(outtake);
    }

    @Override
    public void initialize() {
        //Set intake to speed on start
        outtake.setOuttakeSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        //We won't ever stop this command from here, something else will call cancel() on it from outside.
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //When something does stop this command, stop the intake
        this.outtake.setOuttakeSpeed(OuttakeSpeeds.STOPPED);
    }
}