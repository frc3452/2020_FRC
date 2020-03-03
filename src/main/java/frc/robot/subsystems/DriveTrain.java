/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//I need to assign the motor IDs later!

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
    DifferentialDrive differentialDrive;

    /**
     * Creates a new DriveTrain.
     */

    private WPI_TalonSRX leftfront;
    private WPI_TalonSRX leftMiddle;
    private WPI_TalonSRX leftBack;
    private WPI_TalonSRX rightFront;
    private WPI_TalonSRX rightMiddle;
    private WPI_TalonSRX rightBack;

    /*
     * PWM 1 - PWM 2 - PWM 3 - PWM 4 -
     */

    public DriveTrain() {
        leftfront = new WPI_TalonSRX(Constants.kHardwarePorts.kLeftFrontID);
        leftMiddle = new WPI_TalonSRX(Constants.kHardwarePorts.kLeftMiddleID);
        leftBack = new WPI_TalonSRX(Constants.kHardwarePorts.kLeftBackID);

        rightFront = new WPI_TalonSRX(Constants.kHardwarePorts.kRightFrontID);
        rightMiddle = new WPI_TalonSRX(Constants.kHardwarePorts.kRightMiddleID);
        rightBack = new WPI_TalonSRX(Constants.kHardwarePorts.kRightBackID);

        // for every talon
        leftfront.configFactoryDefault();
        leftMiddle.configFactoryDefault();
        leftBack.configFactoryDefault();
        rightFront.configFactoryDefault();
        rightMiddle.configFactoryDefault();
        rightBack.configFactoryDefault();

        // leftfront.setInverted(true);
        // leftMiddle.setInverted(true);
        // leftBack.setInverted(true);

        // rightFront.setInverted(true);
        // rightMiddle.setInverted(true);
        // rightBack.setInverted(true);

        SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftfront, leftMiddle, leftBack);
        SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightMiddle, rightBack);

        differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
    }

    private boolean fastMode = true;

    public void changeMode() {
        fastMode = !fastMode;
        if (fastMode) {
            System.out.println("Robot is in fast mode");
        } else {
            System.out.println("Robot is in slow mode");
        }
    }

    public void teleArcadeDriveControl(double speed, double rotation, boolean squareInputs) {
        if (fastMode) {
            arcadeDriveControl(speed, rotation, squareInputs);
        } else {
            arcadeDriveControl(speed * 0.2, rotation * 0.2, squareInputs);
        }
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

    public boolean isFastMode() {
        return fastMode;
    }
}