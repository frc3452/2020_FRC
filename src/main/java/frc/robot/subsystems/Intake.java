package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {
    private CANSparkMax sparkMax;
        

    public Intake() {
        sparkMax = new CANSparkMax(kHardwarePorts.kIntakeMotorID, MotorType.kBrushless);
    }

    public void moveIntake(IntakeSpeeds speed) {
        sparkMax.set(speed.getSpeed());
    }

    @Override
    public void periodic() {
        
    }

}