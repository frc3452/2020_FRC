package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Outtake extends SubsystemBase {

    public Servo bridgeServo;

    public void setServoAngle(double angle) {
        bridgeServo.setAngle(angle);
    }

    @Override
    public void periodic() {
    }
    //Possibly add a spinny wheel later
}