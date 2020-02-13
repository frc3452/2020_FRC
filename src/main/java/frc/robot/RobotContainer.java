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
import frc.robot.Constants.kOuttake.OuttakePositions;
import frc.robot.commands.PositionCommand;
import frc.robot.commands.drive.TeleDrive;
import frc.robot.commands.outtake.OuttakeCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;
import frc.robot.subsystems.PositionedSubsystem;

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
    private final PositionedSubsystem positionedSubsystem = new PositionedSubsystem();

    Command m_OpenOuttakeCommand = new OuttakeCommand(m_outtake, Constants.kOuttake.TestEnum.OPEN);
    Command m_CloseOuttakeCommand = new OuttakeCommand(m_outtake, Constants.kOuttake.TestEnum.CLOSE);
    Command m_OuttakeCommand = new OuttakeCommand(m_outtake, Constants.kOuttake.TestEnum.DOFULLCOMMAND);

    // https://docs.wpilib.org/en/latest/docs/software/commandbased/binding-commands-to-triggers.html#binding-a-command-to-a-joystick-button
    private final Joystick driverJoystick = new Joystick(0);

    private JoystickButton driverAButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.A);
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
                () -> driverJoystick.getRawAxis(3) - driverJoystick.getRawAxis(2), false));

    }

    private void configureButtonBindings() {
        // https://docs.wpilib.org/en/latest/docs/software/commandbased/binding-commands-to-triggers.html#whileactiveonce-whenheld
        driverAButton.whenPressed(m_OuttakeCommand);
        driverRBButton.whenPressed(m_CloseOuttakeCommand);
        driverLBButton.whenPressed(m_OpenOuttakeCommand);


        driverAButton.whenPressed(new PositionCommand(positionedSubsystem, OuttakePositions.OPEN));
        driverRBButton.whenPressed(new PositionCommand(positionedSubsystem, OuttakePositions.CLOSED));
    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
