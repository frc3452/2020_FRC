package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.Constants.kIntake.IntakeSpeeds;
import frc.robot.subsystems.Intake;
// make stop i ntake command 
public class IntakeCommand extends CommandBase {

    private IntakeSpeeds intakeSpeed;
    private Intake intake;
    private static edu.wpi.first.wpilibj.Timer time = new edu.wpi.first.wpilibj.Timer();
    private final Joystick driverJoystick = new Joystick(0);
    private JoystickButton driverAButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.A);
    private JoystickButton driverBButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.B);
    private JoystickButton driverYButton = new JoystickButton(driverJoystick, Constants.kXboxButtons.Y);
    private static int counter = 0;

    public IntakeCommand(Intake intake, IntakeSpeeds speed) {
        intakeSpeed = speed;
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
            if (counter == 1){
                if (time.get() < 1.0) {
                intake.moveIntake(IntakeSpeeds.BACKWARDS);
                time.stop();
                time.reset();
                System.out.println(counter);
                }
                else {
                intake.moveIntake(intakeSpeed);
                time.stop();
                time.reset();
                counter = 0;
                System.out.println(counter);
                }
            }
            else {
            intake.moveIntake(intakeSpeed);
            time.stop();
            time.reset();
            System.out.println(counter);
            }
        }  
    //this is working just fine as a toggle, but for some reason, the timer doesn't seem to be starting at all.
    //I'll look into that more.

    @Override
    public void end(boolean interrupted) {
        if(counter == 1){
            counter = 0;
            }
            else {
            counter ++;
            }
    
        intake.moveIntake(IntakeSpeeds.STOPPED);
        CommandScheduler.getInstance().onCommandFinish(IntakeCommand -> time.start());
        
    }

    @Override
    public boolean isFinished() {
        if (driverAButton.get() || driverBButton.get() || driverYButton.get()){
            System.out.println("1");
            return false;

        }

        
        return true;
        
    }

};
