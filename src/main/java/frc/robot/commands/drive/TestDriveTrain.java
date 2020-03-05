package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

import java.util.function.Consumer;

public class TestDriveTrain extends SequentialCommandGroup {

    private final DriveTrain drive;

    public TestDriveTrain(DriveTrain drive, ShuffleboardTab motorTestingTab, TestDriveConfig config) {
        this.drive = drive;
        addRequirements(drive);

        addCommands(new PrintCommand("Disabling motor saftey..."));
        addCommands(new InstantCommand(drive::disableMotorSaftey));
        addCommands(new PrintCommand("Disabled motor saftey"));

//        Jog
        driveEachForSpeed(config.initialJogPower, config.initialJogTime);
        driveEachForSpeed(-config.initialJogPower, config.initialJogTime);

        wait(config.delayInBetween);

        //Run forward
        driveEachForSpeed(config.runPercent, config.runTime, config.delayInBetween, (m) -> {
            double rpm = (m.left ? drive.getLeftRPM() : drive.getRightRPM());
            double current = drive.getCurrent(m.left, m.num);
            motorTestingTab.addNumber(m.toString() + " F RPM", () -> rpm)
                    .withPosition(getX(m), 0);
            motorTestingTab.addNumber(m.toString() + " F AMP", () -> current)
                    .withPosition(getX(m), 1);
        });

        //Run backward
        driveEachForSpeed(-config.runPercent, config.runTime, config.delayInBetween, (m) -> {
            double rpm = (m.left ? drive.getLeftRPM() : drive.getRightRPM());
            double current = drive.getCurrent(m.left, m.num);
            motorTestingTab.addNumber(m.toString() + " R RPM", () -> rpm)
                    .withPosition(getX(m), 2);
            motorTestingTab.addNumber(m.toString() + " R AMP", () -> current)
                    .withPosition(getX(m), 3);
        });

        addCommands(new InstantCommand(drive::enableMotorSaftey));
    }


    private void wait(double time) {
        addCommands(new WaitCommand(time));
    }

    private int getX(Motor m) {
        int num = m.getNum() - 1;
        if (!m.left) {
            num += 3;
        }
        return num;
    }

    public void driveEachForSpeed(double speed, double time) {
        driveEachForSpeed(speed, time, 0, m -> {
        });
    }

    public void driveEachForSpeed(double speed, double time, double delayInBetween, Consumer<Motor> checkAtSpeed) {
        Consumer<Motor> consumer = (m -> {
            addCommands(new InstantCommand(() -> drive.runMotor(m, speed)));
            addCommands(new WaitCommand(time));
            addCommands(new InstantCommand(() -> checkAtSpeed.accept(m)));
            addCommands(new InstantCommand(() -> drive.runMotor(m, 0)));
            addCommands(new WaitCommand(delayInBetween));
        });

        forEach(consumer);
    }

    private void forEach(Consumer<Motor> motorConsumer) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                Motor m = new Motor(i == 0, j + 1);
                motorConsumer.accept(m);
            }
        }
    }

    public static class Motor {
        private boolean left;
        private int num;

        public Motor(boolean left, int num) {
            this.left = left;
            this.num = num;
        }

        public boolean getLeft() {
            return left;
        }

        public int getNum() {
            return num;
        }

        @Override
        public String toString() {
            String side = (left ? "Left" : "Right");

            return side + " " + num;
        }
    }

}
