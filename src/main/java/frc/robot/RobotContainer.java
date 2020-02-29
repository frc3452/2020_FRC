/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.drive.TeleDrive;
import frc.robot.commands.intake.NoFinishIntakeCommand;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.outtake.OuttakeCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;
import frc.robot.util.AdvancedButtons;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveTrain m_DriveTrain = new DriveTrain();
    private final Outtake m_outtake = new Outtake();
    private final Intake m_intake = new Intake();

    // https://docs.wpilib.org/en/latest/docs/software/commandbased/binding-commands-to-triggers.html#binding-a-command-to-a-joystick-button
    private final Joystick driverJoystick = new Joystick(0);

    private JoystickButton driverAButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.A);
    private JoystickButton driverBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.B);
    private JoystickButton driverXButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.X);
    private JoystickButton driverYButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.Y);
    private JoystickButton driverRBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.RB);
    private JoystickButton driverLBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.LB);

    public RobotContainer() {
        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
    }

    private void configureDefaultCommands() {
        // https://docs.wpilib.org/en/latest/docs/software/commandbased/subsystems.html#setting-default-commands
        m_DriveTrain.setDefaultCommand(new TeleDrive(m_DriveTrain, () -> -driverJoystick.getRawAxis(1),
                () -> (driverJoystick.getRawAxis(3) - driverJoystick.getRawAxis(2)), false));

    }

    private void configureButtonBindings() {
        driverAButton.whileHeld(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.FAST));
        driverXButton.whileHeld(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS));

        

        // AdvancedButtons.buttonToggle(driverRBButton, 
        // new OuttakeCommand(m_outtake, OuttakeSpeeds.CLOSED), 
        // new OuttakeCommand(m_outtake, OuttakeSpeeds.OPEN));

        driverRBButton.whileHeld(new OuttakeCommand(m_outtake, OuttakeSpeeds.RUNNING));
        driverRBButton.whenInactive(new OuttakeCommand(m_outtake, OuttakeSpeeds.STOPPED));
        driverLBButton.whileHeld(new OuttakeCommand(m_outtake, OuttakeSpeeds.BACKWARDS));
        driverLBButton.whenInactive(new OuttakeCommand(m_outtake, OuttakeSpeeds.STOPPED));
    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
