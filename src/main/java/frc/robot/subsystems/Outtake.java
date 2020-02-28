package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.kOuttake.OuttakePositions;

public class Outtake extends SubsystemBase {

    private Servo servo = new Servo(Constants.kHardwarePorts.kOuttakeServoID);

    private void setServoAngle(double angle) {
        servo.setAngle(angle);
    }

    public void setPosition(OuttakePositions position) {
        setServoAngle(position.getAngle());
    }

    @Override
    public void periodic() {

    }
    // Possibly add a spinny wheel later
}