/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//I need to assign the motor IDs later!

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

    private final WPI_TalonSRX leftFront;
    private final WPI_TalonSRX leftMiddle;
    private final WPI_TalonSRX leftBack;
    private final WPI_TalonSRX rightFront;
    private final WPI_TalonSRX rightMiddle;
    private final WPI_TalonSRX rightBack;

    /*
     * PWM 1 - PWM 2 - PWM 3 - PWM 4 -
     */

    public DriveTrain() {
        leftFront = new WPI_TalonSRX(Constants.kHardwarePorts.kLeftFrontID);
        leftMiddle = new WPI_TalonSRX(Constants.kHardwarePorts.kLeftMiddleID);
        leftBack = new WPI_TalonSRX(Constants.kHardwarePorts.kLeftBackID);

        rightFront = new WPI_TalonSRX(Constants.kHardwarePorts.kRightFrontID);
        rightMiddle = new WPI_TalonSRX(Constants.kHardwarePorts.kRightMiddleID);
        rightBack = new WPI_TalonSRX(Constants.kHardwarePorts.kRightBackID);

        // for every talon
        leftFront.configFactoryDefault();
        leftMiddle.configFactoryDefault();
        leftBack.configFactoryDefault();
        rightFront.configFactoryDefault();
        rightMiddle.configFactoryDefault();
        rightBack.configFactoryDefault();

        leftFront.setNeutralMode(NeutralMode.Coast);
        leftMiddle.setNeutralMode(NeutralMode.Coast);
        leftBack.setNeutralMode(NeutralMode.Coast);
        rightFront.setNeutralMode(NeutralMode.Coast);
        rightMiddle.setNeutralMode(NeutralMode.Coast);
        rightBack.setNeutralMode(NeutralMode.Coast);

        leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 400000);
//these are test numbers    

        // leftfront.setInverted(true);
        // leftMiddle.setInverted(true);
        // leftBack.setInverted(true);

        // rightFront.setInverted(true);
        // rightMiddle.setInverted(true);
        // rightBack.setInverted(true);

        final SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFront, leftMiddle, leftBack);
        final SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightMiddle, rightBack);

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
            arcadeDriveControl(speed, Math.abs(Math.pow(Math.abs(rotation),1.5)) * Math.signum(rotation), false);
        } else {
            arcadeDriveControl(speed *.55, Math.abs(Math.pow(Math.abs(rotation),1.5)) * Math.signum(rotation) *.55, false);
        }
    }

    public void arcadeDriveControl(double speed, double rotation, boolean squareInputs) {
        differentialDrive.arcadeDrive(speed, rotation, squareInputs);
    }

    public void tankDriveControl(final double speed, final double rotation) {
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