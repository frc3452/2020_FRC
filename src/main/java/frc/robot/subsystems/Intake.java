package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {

public final Spark sparkMax;
//change to spark max after testing
        
// public edu.wpi.first.wpilibj.Timer time = new edu.wpi.first.wpilibj.Timer();

    public Intake() {
        sparkMax = new Spark(kHardwarePorts.kIntakeMotorID);
        // CommandScheduler.getInstance().onCommandFinish(IntakeCommand -> time.start());
        //I'm not sure if putting something like this in the constuctor is bad, but it seems like the cleanest
        //solution. I'll try it.
    } 
    
    public void moveIntake(IntakeSpeeds speed) {

        sparkMax.set(speed.getSpeed());
        // time.stop();
        // time.reset();
        
    }

    public void stopIntake() {

        sparkMax.set(0.0);
        // time.stop();
        // time.reset();
        
    }
    

    @Override
    public void periodic() {

    }

}