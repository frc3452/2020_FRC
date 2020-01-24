/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//I need to assign the motor IDs later!

package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.WPI_MotorSafetyImplem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
    DifferentialDrive differentialDrive;

    /**
     * Creates a new DriveTrain.
     */
    private Spark leftMaster;
    private Spark leftFollower1;
    private Spark leftFollower2;
    private Spark rightMaster;
    private Spark rightFollower1;
    private Spark rightFollower2;    

    /*
    PWM 1 - 
    PWM 2 - 
    PWM 3 - 
    PWM 4 - 
    */

    public DriveTrain() {
        leftMaster = new Spark(1);
        leftFollower1 = new Spark(2);

        rightMaster = new Spark(3);
        rightFollower1 = new Spark(4);

        leftFollower1 = leftMaster;
        rightFollower1 = rightMaster;

        differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
    }

    public void arcadeDriveControl(double speed, double rotation, boolean squareInputs) {
        differentialDrive.arcadeDrive(speed, rotation, squareInputs);
    }
    
    public void tankDriveControl(double speed, double rotation) {
        differentialDrive.tankDrive(speed, rotation, false);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
