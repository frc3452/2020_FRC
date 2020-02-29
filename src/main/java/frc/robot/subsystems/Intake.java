package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {

    private final VictorSPX intakeMotor;

    public Intake() {
        intakeMotor = new VictorSPX(kHardwarePorts.kIntakeMotorID);
        intakeMotor.configFactoryDefault();
    }

    public void moveIntake(IntakeSpeeds speed) {
        intakeMotor.set(VictorSPXControlMode.PercentOutput, speed.getSpeed());
    }

    public double getIntakeSpeed() {
        return intakeMotor.getMotorOutputPercent();
    }

    @Override
    public void periodic() {
    }

}