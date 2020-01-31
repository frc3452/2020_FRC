package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExampleArmSubsystem;

import java.util.function.Supplier;

public class ExampleArmCommand extends CommandBase {

    private final ExampleArmSubsystem subsystem;
    private final Supplier<Double> percentageSupplier;

    public ExampleArmCommand(ExampleArmSubsystem subsystem, Supplier<Double> percentageSupplier) {
        this.subsystem = subsystem;
        this.percentageSupplier = percentageSupplier;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        subsystem.setPercentage(percentageSupplier.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
