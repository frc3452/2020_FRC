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
import frc.robot.Constants.kOuttake.OuttakePositions;
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
//        ^^^
        //if we control click through this, we can see we get to a spot in Trigger.java
        // which is directly adding a runnable to the command scheduler, which will check button state and
        // schedule commands when we call
        // CommandScheduler.getInstance().run() in robotPeriodic in Robot.java;

        //Part of Trigger.java:
//        CommandScheduler.getInstance().addButton(new Runnable() {
//      private boolean m_pressedLast = get();
//
//      @Override
//      public void run() {
//        boolean pressed = get();
//
//        if (pressed) {
//          command.schedule(interruptible);
//        } else if (m_pressedLast) {
//          command.cancel();
//        }
//
//        m_pressedLast = pressed;
//      }
//    });

//Modify the code here
        // AdvancedButtons.quickReleaseAndWhileHeld(driverAButton,
        //         new NoFinishIntakeCommand(m_intake, IntakeSpeeds.SLOW),
        //         new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS), 0.15);

        // AdvancedButtons.quickReleaseAndWhileHeld(driverBButton,
        //         new NoFinishIntakeCommand(m_intake, IntakeSpeeds.MEDIUM),
        //         new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS), 0.15);

        // AdvancedButtons.quickReleaseAndWhileHeld(driverYButton,
        //         new NoFinishIntakeCommand(m_intake, IntakeSpeeds.FAST),
        //         new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS), 0.15);

        driverAButton.whenPressed(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.MEDIUM));
        driverXButton.whenPressed(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS));
        driverAButton.whenInactive(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.STOPPED));
        driverXButton.whenInactive(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.STOPPED));

        AdvancedButtons.buttonToggle(driverRBButton, 
        new OuttakeCommand(m_outtake, OuttakePositions.CLOSED), 
        new OuttakeCommand(m_outtake, OuttakePositions.OPEN));
    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
