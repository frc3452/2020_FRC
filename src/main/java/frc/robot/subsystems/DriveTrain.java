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

    private final WPI_TalonSRX leftfront;
    private final WPI_TalonSRX leftMiddle;
    private final WPI_TalonSRX leftBack;
    private final WPI_TalonSRX rightFront;
    private final WPI_TalonSRX rightMiddle;
    private final WPI_TalonSRX rightBack;

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

        final SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftfront, leftMiddle, leftBack);
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

    public void teleArcadeDriveControl(final double speed, final double rotation, final boolean squareInputs) {
        if (fastMode) {
            arcadeDriveControl(speed, rotation, squareInputs);
        } else {
            arcadeDriveControl(speed * 0.55, rotation * 0.55, squareInputs);
        }
    }

    // public double getTurningValue(Joystick joystick) {
    // if(Math.abs(joystick.getValue() < 0.1) return 0;
    // else return joystick.getValue();
    // }
    private double deadbandreturn;

    public double deadband(final double JoystickValue, double DeadbandCutOff) {
        deadbandreturn = (JoystickValue - // initially in one of two ranges: [DeadbandCutOff,1] or -1,-DeadBandCutOff]
                (Math.abs(JoystickValue) / JoystickValue // 1 if JoystickValue > 0, -1 if JoystickValue < 0 (abs(x)/x);
                                                         // could use Math.signum(JoystickValue) instead
                        * DeadbandCutOff // multiply by the sign so that for >0, it comes out to - (DeadBandCutOff), and
                                         // for <0 it comes to - (-DeadBandCutOff)
                )) // now in either [0,1-DeadBandCutOff] or -1+DeadBandCutOff,0]
                / (1 - DeadbandCutOff); // scale to [0,1] or -1,0]
        if (JoystickValue < DeadbandCutOff && JoystickValue > (DeadbandCutOff * (-1))) {
            deadbandreturn = 0;
        } else {
            deadbandreturn = (JoystickValue - (Math.abs(JoystickValue) / JoystickValue * DeadbandCutOff))
                    / (1 - DeadbandCutOff);
        }

        return deadbandreturn;
    }

    public void arcadeDriveControl(final double speed, final double rotation, final boolean squareInputs) {
        differentialDrive.arcadeDrive(speed, rotation/* + deadband(getRawButton, .2)*/, squareInputs);
    }

    public void tankDriveControl(final double speed, final double rotation) {
        differentialDrive.tankDrive(speed, rotation, false);
    }

    @Override
    public void periodic() {

        // This method will be called once per scheduler run
    }
}

// public class hhh extends CommandBase {

// }