/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//I need to assign the motor IDs later!

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain. 
   */
  private WPI_TalonSRX leftMaster;
  private WPI_TalonSRX leftFollower1;
  private WPI_TalonSRX leftFollower2;

  private WPI_TalonSRX rightMaster;
  private WPI_TalonSRX rightFollower1;
  private WPI_TalonSRX rightFollower2;

  DifferentialDrive differentialDrive;
  
  public DriveTrain() {
    leftMaster = new WPI_TalonSRX(0);
    leftFollower1 = new WPI_TalonSRX(1);
    leftFollower2 = new WPI_TalonSRX(2);

    rightMaster = new WPI_TalonSRX(3);
    rightFollower1 = new WPI_TalonSRX(4);
    rightFollower2 = new WPI_TalonSRX(5);

    leftFollower1.follow(leftMaster);
    leftFollower2.follow(leftMaster);

    rightFollower1.follow(rightMaster);
    rightFollower2.follow(rightMaster);

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
