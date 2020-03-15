package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickRumble extends CommandBase {
    private final Joystick joystick;
    private final double rumble;

    public JoystickRumble(Joystick joystick, double rumble) {
        this.joystick = joystick;
        this.rumble = rumble;
    }

    @Override
    public void initialize() {
        joystick.setRumble(GenericHID.RumbleType.kLeftRumble, rumble);
        joystick.setRumble(GenericHID.RumbleType.kRightRumble, rumble);
    }

    @Override
    public void end(boolean interrupted) {
        joystick.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        joystick.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    }
}
