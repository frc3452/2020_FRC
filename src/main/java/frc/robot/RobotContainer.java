/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.TeleDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;

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
    private final Outtake m_Outtake = new Outtake();
    private final Servo m_BridgeServo = new Servo(Constants.motorIDs.bridgeServoID);

    // The only instance that should be created!
    // private final ExampleArmSubsystem exampleArmSubsystem = new
    // ExampleArmSubsystem();

    // https://docs.wpilib.org/en/latest/docs/software/commandbased/binding-commands-to-triggers.html#binding-a-command-to-a-joystick-button
    private final Joystick driverJoystick = new Joystick(0);

    private JoystickButton driverAButton = new JoystickButton(driverJoystick, Constants.XboxButtons.A);
    private JoystickButton driverRBButton = new JoystickButton(driverJoystick, Constants.XboxButtons.RB);
    private JoystickButton driverLBButton = new JoystickButton(driverJoystick, Constants.XboxButtons.LB);

    // This can be defined inline (or in the configureDefaultSubsystems() method,
    // but I'm doing it here so you can see each argument easier

    // ExampleArmCommand armCommand = new ExampleArmCommand(exampleArmSubsystem,
    // doubleSupplier);
    OuttakeCommand testOuttakeCommand = new OuttakeCommand(m_Outtake, m_BridgeServo, true);
    OuttakeCommand inverseTestOuttakeCommand = new OuttakeCommand(m_Outtake, m_BridgeServo, false);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
    }

    private void configureDefaultCommands() {

        // https://docs.wpilib.org/en/latest/docs/software/commandbased/subsystems.html#setting-default-commands
        // CommandScheduler.getInstance().setDefaultCommand(m_DriveTrain,
        // new TeleDrive(m_DriveTrain, doubleSupplier, 0.0, false);
        CommandScheduler.getInstance().setDefaultCommand(m_DriveTrain,
                new TeleDrive(m_DriveTrain, () -> driverJoystick.getRawAxis(1) * .35,
                        () -> driverJoystick.getRawAxis(3) - driverJoystick.getRawAxis(2), false));

        // Example 1: default command for arm control (I smell something like DriveTele)
        // CommandScheduler.getInstance().setDefaultCommand(exampleArmSubsystem,
        // armCommand);
        // new TeleDrive(m_DriveTrain, driverJoystick, 0.0, false)

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Example 2: schedule command when held with button
        // https://docs.wpilib.org/en/latest/docs/software/commandbased/binding-commands-to-triggers.html#whileactiveonce-whenheld
        // Read difference between whenHeld and whileHeld, irritatingly small.

        // whenHeld will execute while button is held, and interrupt when button let go.
        // but!, if command finishes while
        // button being held, it wont start again!

        // whileHeld is the same as the above, but if the command finishes while the
        // button is being held, it will restart

        // new JoystickButton(driverJoystick, 1).whileHeld(armCommand);
        driverRBButton.whenPressed(testOuttakeCommand, true);
        driverLBButton.whenPressed(testOuttakeCommand, false);

    }
    // driverRBButton.whileActiveContinuous(command)

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
