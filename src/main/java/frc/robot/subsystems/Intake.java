package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {
    //this is written using a Spark because I'm not positive how Spark Maxes work yet. I'll change it all later.
    private static CANSparkMax sparkMax = new CANSparkMax(kHardwarePorts.kIntakeMotorID, MotorType.kBrushless);
        

    public Intake() {

    }

    public void moveIntake(IntakeSpeeds speed) {
        sparkMax.set(speed.getSpeed());
    }

    @Override
    public void periodic() {
        
    }

}