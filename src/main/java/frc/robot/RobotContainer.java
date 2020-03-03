/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.auto.AutoFeedSomeoneElseCommand;
import frc.robot.commands.auto.DriveAndWait;
import frc.robot.commands.auto.DriveFromSideEject;
import frc.robot.commands.auto.DriveStraightAndEject;
import frc.robot.commands.auto.TestAuto;
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.drive.TeleDrive;
import frc.robot.commands.intake.NoFinishIntakeCommand;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.outtake.NoFinishOuttakeCommand;
import frc.robot.commands.outtake.InstantOuttakeCommand;
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
    private JoystickButton driverRightStickPress = new JoystickButton(driverJoystick,
            Constants.kXboxButtons.RIGHT_STICK_BUTTON);
    private JoystickButton driverRBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.RB);
    private JoystickButton driverLBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.LB);

    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    // 0 means slow mode, 1 means fast

    public RobotContainer() {
        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
        addAutosToChooser();
    }

    private void configureDefaultCommands() {
        // https://docs.wpilib.org/en/latest/docs/software/commandbased/subsystems.html#setting-default-commands
        m_DriveTrain.setDefaultCommand(new TeleDrive(m_DriveTrain, () -> -driverJoystick.getRawAxis(1),
                () -> (driverJoystick.getRawAxis(3) - driverJoystick.getRawAxis(2)), false));
    }

    private void configureButtonBindings() {
        driverXButton.whenPressed(new InstantCommand(() -> m_DriveTrain.changeMode()));

        driverAButton.whileHeld(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.FAST));
        driverBButton.whileHeld(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS));

        driverRBButton.whileHeld(new NoFinishOuttakeCommand(m_outtake, OuttakeSpeeds.RUNNING));
        driverLBButton.whileHeld(new NoFinishOuttakeCommand(m_outtake, OuttakeSpeeds.BACKWARDS));
        // driverRBButton.or(driverLBButton).whenInactive(new OuttakeCommand(m_outtake,
        // OuttakeSpeeds.STOPPED));

    }

    private void addAutosToChooser() {
        m_chooser.setDefaultOption("Default - Drive off line", new DriveForTime(m_DriveTrain, -0.3, 0.2, 3.7));
        m_chooser.addOption("Drive Straight", new DriveForTime(m_DriveTrain, 0.5, 0, 3.0));
        m_chooser.addOption("Drive Straight - Eject", new DriveStraightAndEject(m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive From Side & Eject(start on left)", new DriveFromSideEject(true, m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive From Side & Eject(start on right)", new DriveFromSideEject(false, m_DriveTrain, m_outtake));

        m_chooser.addOption("Drive For Time & Eject", new DriveAndWait(m_DriveTrain, m_outtake));

        List<Double> times = List.of(1.0, 2.0, 3.0, 5.0);
        for (Double time : times) {
            m_chooser.addOption("Eject - Outtake (" + time + "s delay)",
                    new AutoFeedSomeoneElseCommand(time, true, m_DriveTrain, m_outtake, m_intake));
            m_chooser.addOption("Eject - Intake (" + time + "s delay)",
                    new AutoFeedSomeoneElseCommand(time, false, m_DriveTrain, m_outtake, m_intake));
        }

        Shuffleboard.getTab("Main").add(m_chooser);
    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_chooser.getSelected();
        // return testAuto;
    }
}
