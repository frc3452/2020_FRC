package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public clas final CANSparkMax sparkMax;
        

    public Intake() {
        sparkMax = new CANSparkMax(kHardwarePorts.kIntakeMotorID, MotorType.kBrushless);
        CommandScheduler.getInstance().onCommandFinish(IntakeCommand -> time.start());
        //I'm not sure if putting something like this in the constuctor is bad, but it seems like the cleanest
        //solution. I'll try it.
    }

    public void moveIntake(final 
    public void moveIntake(IntakeSpeeds speed) {
        if (time < 1.0){
        sparkMax.set(speed.getSpeed());
        time.reset; //this is sudo code, my software is not working proporly and won't show me what's correct here.
        }
        else {
        sparkMax.set(IntakeSpeeds.BACKWARDS.getSpeed()); //also sudo code
        time.reset;
        }
    }

    @Override
    public void periodic() {
        
    }

}