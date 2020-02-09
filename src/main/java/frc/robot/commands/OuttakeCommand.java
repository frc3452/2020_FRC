package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Outtake;

public class OuttakeCommand extends CommandBase {
    private final Outtake outtake;
    private boolean shouldOpen;

    public OuttakeCommand(Outtake outtake, boolean wantOpen) {
        this.outtake = outtake;
        shouldOpen = wantOpen;
        addRequirements(outtake);
    }

    @Override
    public void initialize() {
        System.out.println((shouldOpen ? "Opening" : "Closing") + " door");
    }

    @Override
    public void execute() {
        if (shouldOpen) {
            outtake.openDoor();
        } else {
            outtake.closeDoor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Door is " + (shouldOpen ? "open" : "closed"));
        } else {

        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}