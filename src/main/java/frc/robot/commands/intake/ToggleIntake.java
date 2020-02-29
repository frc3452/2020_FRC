package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;

public class ToggleIntake extends InstantCommand {
    private final Intake intake;
    private final IntakeSpeeds highSpeed;

    public ToggleIntake(Intake intake, IntakeSpeeds highSpeed) {
        this.intake = intake;
        this.highSpeed = highSpeed;
    }

    @Override
    public void initialize() {
        if (this.intake.getIntakeSpeed() == IntakeSpeeds.STOPPED.getSpeed()) {
            this.intake.moveIntake(highSpeed);
        } else {
            this.intake.moveIntake(IntakeSpeeds.STOPPED);
        }
    }
}
