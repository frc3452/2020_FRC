package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.kIntakeRumble;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;

public class ToggleIntakeWithRumble extends InstantCommand {
    private final Intake intake;
    private final IntakeSpeeds highSpeed;
    private Joystick controller;
    private boolean rumbleBoolean = true;

    public ToggleIntakeWithRumble(Intake intake, IntakeSpeeds highSpeed, Joystick controller) {
        this.intake = intake;
        this.highSpeed = highSpeed;
        this.controller = controller;
    }

    @Override
    public void initialize() {
        if (this.intake.getIntakeSpeed() == IntakeSpeeds.STOPPED.getSpeed()) {
            this.intake.moveIntake(highSpeed);
            if(highSpeed == IntakeSpeeds.BACKWARDS && rumbleBoolean == true){
            controller.setRumble(RumbleType.kLeftRumble, kIntakeRumble.intakeBackwardRumble);
            controller.setRumble(RumbleType.kRightRumble, kIntakeRumble.intakeBackwardRumble);
            }
            else {
                controller.setRumble(RumbleType.kLeftRumble, kIntakeRumble.intakeForwardRumble);
                controller.setRumble(RumbleType.kRightRumble, kIntakeRumble.intakeForwardRumble);
            }
        } else {
            this.intake.moveIntake(IntakeSpeeds.STOPPED);
            controller.setRumble(RumbleType.kLeftRumble, 0.0);
                controller.setRumble(RumbleType.kRightRumble, 0.0);

            rumbleBoolean = true;
        }   
        // rumbleBoolean = false;
    }

}
