/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;
import frc.robot.commands.auto.*;
import frc.robot.commands.climb.NerdsClimbCommand;
import frc.robot.commands.drive.DriveForTime;
import frc.robot.commands.drive.TeleDrive;
import frc.robot.commands.drive.TestDriveConfig;
import frc.robot.commands.drive.TestDriveTrain;
import frc.robot.commands.intake.NoFinishIntakeCommand;
import frc.robot.commands.intake.NoFinishRumbleIntakeCommandBackward;
import frc.robot.commands.intake.NoFinishRumbleIntakeCommandForward;
import frc.robot.commands.intake.ToggleIntakeWithRumble;
import frc.robot.commands.outtake.NoFinishOuttakeCommand; 
import frc.robot.subsystems.*;

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
    private DriveTrain m_DriveTrain;
    private Outtake m_outtake;
    private Intake m_intake;
    private NerdsClimber m_climber;
    private Camera m_camera;
    private AccessoryElectronics electronics;

    // https://docs.wpilib.org/en/latest/docs/software/commandbased/binding-commands-to-triggers.html#binding-a-command-to-a-joystick-button
    private final Joystick driverJoystick = new Joystick(0);
    private final Joystick operatorJoystick = new Joystick(1);
    //driver buttons
    private JoystickButton driverAButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.A);
    private JoystickButton driverBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.B);
    private JoystickButton driverXButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.X);
    private JoystickButton driverYButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.Y);
    private JoystickButton driverBack = new JoystickButton(driverJoystick, XboxController.Button.kBack.value);
    private JoystickButton driverStart = new JoystickButton(driverJoystick, XboxController.Button.kStart.value);
    private JoystickButton driverRightStickPress = new JoystickButton(driverJoystick,
            Constants.kXboxButtons.RIGHT_STICK_BUTTON);
    private JoystickButton driverRBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.RB);
    private JoystickButton driverLBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.LB);
    
    //operator buttons
    private JoystickButton operatorAButton = new JoystickButton(operatorJoystick, Constants.kXboxButtons.A);
    private JoystickButton operatorBButton = new JoystickButton(operatorJoystick, Constants.kXboxButtons.B);
    private JoystickButton operatorXButton = new JoystickButton(operatorJoystick, Constants.kXboxButtons.X);
    private JoystickButton operatorYButton = new JoystickButton(operatorJoystick, Constants.kXboxButtons.Y);
    private JoystickButton operatorRightStickPress = new JoystickButton(operatorJoystick,
            Constants.kXboxButtons.RIGHT_STICK_BUTTON);
    private JoystickButton operatorRBButton = new JoystickButton(operatorJoystick, Constants.kXboxButtons.RB);
    private JoystickButton operatorLBButton = new JoystickButton(operatorJoystick, Constants.kXboxButtons.LB);
    private JoystickButton operatorBack = new JoystickButton(operatorJoystick, Constants.kXboxButtons.LOGO_LEFT);
    private JoystickButton operatorStart = new JoystickButton(operatorJoystick, Constants.kXboxButtons.LOGO_RIGHT);
    

    private final ShuffleboardTab sb_tab_main = Shuffleboard.getTab("Main");
    private final ShuffleboardTab sb_tab_testing = Shuffleboard.getTab("Testing");
    private final ShuffleboardTab sb_motor_testing = Shuffleboard.getTab("Motor testing");

    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    public RobotContainer() {
        final boolean skip = false;
        m_DriveTrain = new DriveTrain(skip);
        m_outtake = new Outtake(skip);
        m_intake = new Intake(skip);
        m_climber = new NerdsClimber();
        m_camera = new Camera(skip);
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
        //driver controls
        driverXButton.whenPressed(new InstantCommand(() -> m_DriveTrain.changeMode()));
        driverYButton.whenPressed(new InstantCommand(() -> m_camera.changeCameras()));

        // driverAButton.toggleWhenActive(new NoFinishRumbleIntakeCommandForward(m_intake, IntakeSpeeds.FAST, driverJoystick));
        driverAButton.whenPressed(new ToggleIntakeWithRumble(m_intake, IntakeSpeeds.FAST, driverJoystick));
        driverBButton.whenPressed(new ToggleIntakeWithRumble(m_intake, IntakeSpeeds.BACKWARDS, driverJoystick));

        driverRBButton.whileHeld(new NoFinishOuttakeCommand(m_outtake, OuttakeSpeeds.RUNNING));
        driverLBButton.whileHeld(new NoFinishOuttakeCommand(m_outtake, OuttakeSpeeds.BACKWARDS));


        
        //operator controls
        //operatorXButton.whenPressed(new InstantCommand(() -> m_DriveTrain.changeMode()));
        operatorYButton.whenPressed(new InstantCommand(() -> m_camera.changeCameras()));

        operatorAButton.whileHeld(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.FAST));
        operatorBButton.whileHeld(new NoFinishIntakeCommand(m_intake, IntakeSpeeds.BACKWARDS));

        operatorRBButton.whileHeld(new NoFinishOuttakeCommand(m_outtake, OuttakeSpeeds.RUNNING));
        operatorLBButton.whileHeld(new NoFinishOuttakeCommand(m_outtake, OuttakeSpeeds.BACKWARDS));

        Command climbCommand = new NerdsClimbCommand(m_climber, 1.0);

        operatorStart.and(operatorBack).whileActiveOnce(climbCommand);

        driverBack.and(driverStart).whileActiveOnce(climbCommand);


        //Give this a try, if you would. You could use the
        //Shuffleboard.selectTab("...");
        // in teleInit to have a setup tab, or you could dynamically change tabs whenever you want in the match
        // driverRightStickPress.whenPressed(new InstantCommand() {
        //     private AtomicBoolean toggle = new AtomicBoolean();

        //     @Override
        //     public void initialize() {
        //         if (toggle.get()) {
        //             Shuffleboard.selectTab("Main");
        //         } else {
        //             Shuffleboard.selectTab("Testing");
        //         }
        //         toggle.set(!toggle.get());
        //     }
        // });

        // operatorRightStickPress.whenPressed(new InstantCommand() {
        //     private AtomicBoolean toggle = new AtomicBoolean();

        //     @Override
        //     public void initialize() {
        //         if (toggle.get()) {
        //             Shuffleboard.selectTab("Main");
        //         } else {
        //             Shuffleboard.selectTab("Testing");
        //         }
        //         toggle.set(!toggle.get());
        //     }
        // });
    }
    
    private void addAutosToChooser() {
        m_chooser.setDefaultOption("Default - Drive off line (turning right)", new DriveForTime(m_DriveTrain, -0.3, 0.1, 3.7));
        m_chooser.addOption("Drive off line (turning left)", new DriveForTime(m_DriveTrain, -0.3, -0.1, 3.7));
        m_chooser.addOption("Drive Backwards(towards Outtake)", new DriveForTime(m_DriveTrain, -0.2, 0, 2.3));
        m_chooser.addOption("Drive Forwards(towards Intake)", new DriveForTime(m_DriveTrain, 0.2, 0, 2.3));
        m_chooser.addOption("Drive Straight - Eject", new DriveStraightAndEject(m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive Straight - Eject - Back Up", new DriveStraightEjectAndBackUp(m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive Straight - Eject(short)", new DriveStraightAndEjectShort(m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive From Side & Eject(start on left)", new DriveFromSideEject(true, m_DriveTrain, m_outtake));
        m_chooser.addOption("Drive From Side & Eject(start on right)", new DriveFromSideEject(false, m_DriveTrain, m_outtake));
        m_chooser.addOption("Push Someone Else (Untested)", new DriveForTime(m_DriveTrain, 0.7, 0, 8));


        List<Double> times = List.of(1.0, 2.0, 3.0, 5.0);
        for (Double time : times) {
            m_chooser.addOption("Eject - Outtake (" + time + "s delay) (Nerds)",
                    new AutoFeedSomeoneElseCommand(time, true, m_DriveTrain, m_outtake, m_intake));
            m_chooser.addOption("Eject - Intake (" + time + "s delay) (Nerds)",
                    new AutoFeedSomeoneElseCommand(time, false, m_DriveTrain, m_outtake, m_intake));
        }
        m_chooser.addOption("Test drive train", getDriveTrainTest());

        m_chooser.addOption("Do nothing", new WaitCommand(16));

        sb_tab_main.add(m_chooser).withSize(2, 1).withPosition(2, 0);
    }

    public Command getDriveTrainTest() {
        TestDriveConfig config = new TestDriveConfig();
        config.initialJogPower = 0.3;
        config.initialJogTime = 0.5;
        config.delayInBetween = 1.0;
        config.runPercent = 0.75;
        config.runTime = 4.0;
        return new TestDriveTrain(m_DriveTrain, sb_motor_testing, config);
    }

    private void configureShuffleboard() {
        //m_DriveTrain.isFastMode();
        sb_tab_main.addBoolean("Fast speed", () -> m_DriveTrain.isFastMode())
                .withWidget(BuiltInWidgets.kBooleanBox).
                withSize(2, 2).withPosition(2, 2);

        sb_tab_main.addString("Drive train errors", m_DriveTrain.getErrors())
                .withPosition(5, 0)
                .withSize(2, 1);

        sb_tab_main.addBoolean("Left Drive Encoder", m_DriveTrain::leftEncoderValid)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withSize(1, 1).withPosition(7, 0);
        sb_tab_main.addBoolean("Right Drive Encoder", m_DriveTrain::rightEncoderValid)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withSize(1, 1).withPosition(8, 0);

        sb_tab_main.addBoolean("Left Encoder Always Good", m_DriveTrain::hasLeftEncoderBeenGood)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withSize(1, 1).withPosition(7, 1);
        sb_tab_main.addBoolean("Right Encoder Always Good", m_DriveTrain::hasRightEncoderBeenGood)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withSize(1, 1).withPosition(8, 1);

        sb_tab_testing.add(electronics.getPDP()).withPosition(0, 0);

    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_chooser.getSelected();
        // return testAuto;
    }
}
