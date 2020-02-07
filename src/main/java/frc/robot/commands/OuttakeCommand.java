package frc.robot.commands;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Outtake;

public class OuttakeCommand extends CommandBase {
    private final Outtake outtake;
    private Servo bridgeServo;
    private boolean setOuttake;

    public OuttakeCommand(Outtake outtake, Servo servo, boolean setOuttake) {
        bridgeServo = servo;
        this.outtake = outtake;
        this.setOuttake = setOuttake;
        addRequirements(outtake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (setOuttake) {
            outtake.setServoAngle(10.0);
        } else {
            outtake.setServoAngle(0.0);
        }
    }

}