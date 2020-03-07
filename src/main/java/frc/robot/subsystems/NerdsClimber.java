package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;

public class NerdsClimber extends SubsystemBase {
    private Spark releaseServo;
    public NerdsClimber() {
        releaseServo = new Spark(kHardwarePorts.kNerdsClimberServoID);
    }

    public void resetClimber() {
        releaseServo.set(0.0);
    }
    
    public void setCommand(double power){
        releaseServo.set(power);
    }

}