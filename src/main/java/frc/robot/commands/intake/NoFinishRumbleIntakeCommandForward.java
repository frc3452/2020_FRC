package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kIntakeRumble;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;

public class NoFinishRumbleIntakeCommandForward extends CommandBase {
    private final Intake intake;
    private final IntakeSpeeds runSpeed;
    private final Joystick controller;

    public NoFinishRumbleIntakeCommandForward(final Intake intake, final IntakeSpeeds runSpeed, Joystick controller) {
        this.intake = intake;
        this.runSpeed = runSpeed;
        this.controller = controller;
    }

    @Override
    public void initialize() {
        //Set intake to speed on start
        intake.moveIntake(runSpeed); 
    }


    @Override
    public boolean isFinished() {
        //We won't ever stop this command from here, something else will call cancel() on it from outside.
        controller.setRumble(RumbleType.kLeftRumble, kIntakeRumble.intakeForwardRumble);
        controller.setRumble(RumbleType.kRightRumble, kIntakeRumble.intakeForwardRumble);
        return false;
    }

    @Override
    public void end(final boolean interrupted) {
        //When something does stop this command, stop the intake
        controller.setRumble(RumbleType.kLeftRumble, 0.0);
        controller.setRumble(RumbleType.kRightRumble, 0.0);

        this.intake.moveIntake(IntakeSpeeds.STOPPED);
        
    }
}
