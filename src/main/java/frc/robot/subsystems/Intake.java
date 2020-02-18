package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {

public final CANSparkMax sparkMax;
        
public edu.wpi.first.wpilibj.Timer time = new edu.wpi.first.wpilibj.Timer();

    public Intake() {
        sparkMax = new CANSparkMax(kHardwarePorts.kIntakeMotorID, MotorType.kBrushless);
        CommandScheduler.getInstance().onCommandFinish(IntakeCommand -> time.start());
        //I'm not sure if putting something like this in the constuctor is bad, but it seems like the cleanest
        //solution. I'll try it.
    } 

    public void moveIntake(IntakeSpeeds speed) {
        if (time.hasPeriodPassed(.5)){
        sparkMax.set(speed.getSpeed());
        time.stop();
        time.reset(); 
        }
        else {
        sparkMax.set(IntakeSpeeds.BACKWARDS.getSpeed());
        time.stop();
        time.reset();
        }
    }

    @Override
    public void periodic() {
        
    }

}