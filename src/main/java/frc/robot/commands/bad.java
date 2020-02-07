package frc.robot.commands;


import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.CommandBase;

//Outtake is a temporary name. It's for our mechanism to push out the balls.
public class Outtake extends CommandBase{
    private static Servo servo;
    
    private void setServoAngle(double angle, boolean invert) {
        setAngle(angle, invert);        
    };

    private void setAngle (double angle, boolean invert) {
        if(invert) {
            angle = -angle;
        }
        servo.setAngle(angle);
    };

}