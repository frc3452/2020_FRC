package frc.robot.subsystems;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Outtake extends SubsystemBase {

    private Servo servo = new Servo(Constants.kHardwarePorts.kBridgeServoID);

    private void setServoAngle(double angle) {
        servo.setAngle(angle);
    }

    public void openDoor() {
        setServoAngle(Constants.kOuttake.kBridgeOpenAngle);
    }

    public void closeDoor() {
        setServoAngle(Constants.kOuttake.kBridgeCloseAngle);
    }

    @Override
    public void periodic() {
        
    }
    // Possibly add a spinny wheel later
}