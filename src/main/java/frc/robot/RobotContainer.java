/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.drive.TeleDrive;
import frc.robot.commands.intake.NoFinishIntakeCommand;
import frc.robot.commands.outtake.NoFinishOuttakeCommand;
import frc.robot.subsystems.AccessoryElectronics;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveTrain m_DriveTrain;
    private final Outtake m_outtake;
    private final Intake m_intake;
    private final AccessoryElectronics electronics;

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

    private final ShuffleboardTab sb_tab_main = Shuffleboard.getTab("Main");
    private final ShuffleboardTab sb_tab_testing = Shuffleboard.getTab("Testing");

    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    public RobotContainer() {
        m_DriveTrain = new DriveTrain();
        m_outtake = new Outtake();
        m_intake = new Intake();
        electronics = new AccessoryElectronics();

        configureDefaultCommands();
        configureButtonBindings();
        addAutosToChooser();
        configureShuffleboard();
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


        //Give this a try, if you would. You could use the
        //Shuffleboard.selectTab("...");
        // in teleInit to have a setup tab, or you could dynamically change tabs whenever you want in the match
        driverRightStickPress.whenPressed(new InstantCommand() {
            private AtomicBoolean toggle = new AtomicBoolean();

            @Override
            public void initialize() {
                if (toggle.get()) {
                    Shuffleboard.selectTab("Main");
                } else {
                    Shuffleboard.selectTab("Testing");
                }
                toggle.set(!toggle.get());
            }
        });
    }

    private void addAutosToChooser() {
        m_chooser.setDefaultOption("Default - Drive off line", new DriveForTime(m_DriveTrain, -0.3, 0.2, 3.7));
        m_chooser.addOption("Drive Straight", new DriveForTime(m_DriveTrain, 0.5, 0, 3.0));
        m_chooser.addOption("Drive Straight - Eject", new DriveStraightAndEject(m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive From Side & Eject(start on left)", new DriveFromSideEject(true, m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive From Side & Eject(start on right)", new DriveFromSideEject(false, m_DriveTrain, m_outtake));


        List<Double> times = List.of(1.0, 2.0, 3.0, 5.0);
        for (Double time : times) {
            m_chooser.addOption("Eject - Outtake (" + time + "s delay)",
                    new AutoFeedSomeoneElseCommand(time, true, m_DriveTrain, m_outtake, m_intake));
            m_chooser.addOption("Eject - Intake (" + time + "s delay)",
                    new AutoFeedSomeoneElseCommand(time, false, m_DriveTrain, m_outtake, m_intake));
        }

        sb_tab_main.add(m_chooser).withSize(2, 1).withPosition(2, 0);
    }

    private void configureShuffleboard() {
        //m_DriveTrain.isFastMode();
        sb_tab_main.addBoolean("Fast speed", () -> false).
                withWidget(BuiltInWidgets.kBooleanBox).
                withSize(2, 2).withPosition(2, 2);

        sb_tab_testing.add(electronics.getPDP()).withPosition(0, 0);
    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_chooser.getSelected();
        // return testAuto;
    }
}
