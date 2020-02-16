package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kHardwarePorts;
import frc.robot.Constants.kIntake.IntakeSpeeds;

public class Intake extends SubsystemBase {
//this is written using a Spark because I'm not positive how Spark Maxes work yet. I'll change it all later.
    private Spark spark = new Spark(kHardwarePorts.kIntakeServoID);

    public Intake() {

    }

    public void moveIntake(IntakeSpeeds speed) {
        spark.set(speed.getSpeed());
    }

}