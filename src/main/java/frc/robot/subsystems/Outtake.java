package frc.robot.subsystems;

import javax.xml.transform.SourceLocator;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.kOuttake.OuttakeSpeeds;

public class Outtake extends SubsystemBase {

    private final VictorSPX outtakeMotor;

    public Outtake() {
        outtakeMotor = new VictorSPX(Constants.kHardwarePorts.kOuttakeServoID);
        outtakeMotor.configFactoryDefault();
        outtakeMotor.setInverted(true);
    }

    private void setOuttakeSpeed(final double speed) {
        outtakeMotor.set(VictorSPXControlMode.PercentOutput, speed);

    }

    public void setOuttakeSpeed(final OuttakeSpeeds position) {
        System.out.println("Set outtake position: " + position);
        setOuttakeSpeed(position.getSpeed());
    }

    @Override
    public void periodic() {

    }
}