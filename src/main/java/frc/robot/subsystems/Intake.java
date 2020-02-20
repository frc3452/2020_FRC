package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {

    private final Spark spark;

    public Intake() {
        spark = new Spark(kHardwarePorts.kIntakeMotorID);
    }

    public void moveIntake(IntakeSpeeds speed) {
        spark.set(speed.getSpeed());
    }

    public double getIntakeSpeed() {
        return spark.getSpeed();
    }

    @Override
    public void periodic() {
    }

}