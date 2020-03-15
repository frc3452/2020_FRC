/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//I need to assign the motor IDs later!

package frc.robot.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.drive.TestDriveTrain;

import java.util.function.Supplier;

public class DriveTrain extends SubsystemBase {
    DifferentialDrive differentialDrive;

    /**
     * Creates a new DriveTrain.
     */

    private WPI_TalonSRX leftFront;
    private WPI_TalonSRX leftMiddle;
    private WPI_TalonSRX leftBack;
    private WPI_TalonSRX rightFront;
    private WPI_TalonSRX rightMiddle;
    private WPI_TalonSRX rightBack;

    private boolean fastMode = true;

    private String constructionErrors = "";
    private boolean leftEncoderEverBad = false;
    private boolean rightEncoderEverBad = false;
    private final boolean skipConstruction;

    public DriveTrain(boolean skipConstruction) {
        this.skipConstruction = skipConstruction;

        if (skipConstruction) {
            constructionErrors += "[Fake drivetrain]";
            constructionErrors += "[Not real]";
            constructionErrors += "[An illusion]";
            return;
        }

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

        //Set sensor phase
        leftMiddle.setSensorPhase(true);
        rightMiddle.setSensorPhase(true);

        leftMiddle.config_kP(0, Constants.kDrive.kP);
        leftMiddle.config_kD(0, Constants.kDrive.kD);

        rightMiddle.config_kP(0, Constants.kDrive.kP);
        rightMiddle.config_kD(0, Constants.kDrive.kD);

        leftFront.setSafetyEnabled(false);
        leftMiddle.setSafetyEnabled(false);
        leftBack.setSafetyEnabled(false);
        rightFront.setSafetyEnabled(false);
        rightBack.setSafetyEnabled(false);
        rightMiddle.setSafetyEnabled(false);

        rightFront.setInverted(true);
        rightMiddle.setInverted(true);
        rightBack.setInverted(true);


        var result = getLeftMaster().configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        if (result != ErrorCode.OK) {
            constructionErrors += "[Could not setup left encoder]";
        }

        result = getLeftMaster().configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.Disabled);
        if (result != ErrorCode.OK) {
            constructionErrors += "[Could not disable left limit switch]";
        }

        //The dang Limit switch!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        result = getRightMaster().configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);


        if (result != ErrorCode.OK) {
            constructionErrors += "[Could not setup right encoder]";
        }

        if (!leftEncoderValid()) {
            constructionErrors += "[Left encoder not found]";
        }

        if (!rightEncoderValid()) {
            constructionErrors += "[Right encoder not found]";
        }

        differentialDrive = new DifferentialDrive(new SpeedControllerGroup(leftFront, leftMiddle, leftBack), new SpeedControllerGroup(rightFront, rightMiddle, rightBack));
        differentialDrive.setRightSideInverted(false);
        enableMotorSaftey();
        enableFollower();
    }

    private WPI_TalonSRX getRightMaster() {
        return rightMiddle;
    }

    public void enableFollower() {
        leftFront.follow(leftMiddle);
        leftBack.follow(leftMiddle);

        rightFront.follow(rightMiddle);
        rightBack.follow(rightMiddle);
    }

    public Supplier<String> getErrors() {
        return () -> constructionErrors;
    }

    public void driveDistance(double distance_inches) {
        int leftTarget = inchesToTicks(getLeftInches() + distance_inches);
        int rightTarget = inchesToTicks(getRightInches() + distance_inches);

        System.out.println("Set");
        leftMiddle.set(ControlMode.Position, leftTarget);
        rightMiddle.set(ControlMode.Position, rightTarget);
    }

    public double getRightInches() {
        return ticksToInches(getRightMaster().getSelectedSensorPosition());
    }

    public double getLeftInches() {
        return ticksToInches(getLeftMaster().getSelectedSensorPosition());
    }

    public static double ticksToInches(int ticks) {
        final double rotation = ticks / 4096.0;
        double inches = rotation * 2 * Math.PI * Constants.kDrive.kWheelRadiusInches;
        return inches;
    }

    public static int inchesToTicks(double inches) {
        double rotations = inches / (2 * Math.PI * Constants.kDrive.kWheelRadiusInches);
        return (int) (rotations * 4096);
    }

    private double getRPMfromSensor(int sensor) {
        return (((double) sensor) * (60 * 10)) / (4096.0);
    }

    public double getLeftRPM() {
        if (skipConstruction) {
            return Math.random();
        }

        return getRPMfromSensor(leftMiddle.getSelectedSensorVelocity());
    }

    public double getRightRPM() {
        if (skipConstruction) {
            return Math.random();
        }
        return getRPMfromSensor(rightMiddle.getSelectedSensorVelocity());
    }

    public void enableMotorSaftey() {
        if (!skipConstruction) {
            setSafetyEnabled(true);
        }
    }

    private void setSafetyEnabled(boolean enabled) {
        differentialDrive.setSafetyEnabled(enabled);
        leftFront.setSafetyEnabled(enabled);
        leftMiddle.setSafetyEnabled(enabled);
        leftBack.setSafetyEnabled(enabled);
        rightFront.setSafetyEnabled(enabled);
        rightMiddle.setSafetyEnabled(enabled);
        rightBack.setSafetyEnabled(enabled);
    }

    public void disableMotorSaftey() {
        if (!skipConstruction) {
            setSafetyEnabled(false);
        }
    }

    public boolean leftEncoderValid() {
        if (skipConstruction) {
            leftEncoderEverBad = true;
            return false;
        }

        final boolean good = getLeftMaster().getSensorCollection().getPulseWidthRiseToFallUs() != 0;

        if (!good) {
            leftEncoderEverBad = true;
        }

        return good;
    }

    private WPI_TalonSRX getLeftMaster() {
        return leftMiddle;
    }

    public boolean rightEncoderValid() {
        if (skipConstruction) {
            rightEncoderEverBad = true;
            return false;
        }

        final boolean good = getRightMaster().getSensorCollection().getPulseWidthRiseToFallUs() != 0;
        if (!good) {
            rightEncoderEverBad = true;
        }
        return good;
    }

    public boolean hasLeftEncoderBeenGood() {
        return !leftEncoderEverBad;
    }

    public boolean hasRightEncoderBeenGood() {
        return !rightEncoderEverBad;
    }

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
            arcadeDriveControl(speed, getRotationScalar(rotation, 1.5), false);
        } else {
            arcadeDriveControl(speed * .55, getRotationScalar(rotation, 1.5) * .4, false);
        }
    }

    private double getRotationScalar(double rotation, double exponent) {
        return Math.abs(Math.pow(Math.abs(rotation), exponent)) * Math.signum(rotation);
    }

    public void arcadeDriveControl(double speed, double rotation, boolean squareInputs) {
        if (!skipConstruction) {
            differentialDrive.arcadeDrive(speed, rotation, squareInputs);
        }
    }

    public void tankDriveControl(final double speed, final double rotation) {
        if (!skipConstruction) {
            differentialDrive.tankDrive(speed, rotation, false);
        }
    }

    @Override
    public void periodic() {
//        DecimalFormat df = new DecimalFormat("#0.000");
//        System.out.println("out: " + df.format(rightMiddle.getMotorOutputPercent()) + " speed:" + df.format(rightMiddle.getSelectedSensorVelocity()));
        // This method will be called once per scheduler run
    }

    public boolean isFastMode() {
        return fastMode;
    }

    public void runMotor(TestDriveTrain.Motor motor, double speed) {
        runMotor(motor.getLeft(), motor.getNum(), speed);
    }

    public void runMotor(boolean left, int num, double speed) {
        WPI_TalonSRX t = getTalon(left, num);
        if (t != null) {
            t.set(speed);
        } else {
            System.out.println("Invalid set!");
        }
    }


    public double getCurrent(boolean left, int num) {
        var t = getTalon(left, num);
        if (t == null) {
            return Math.random();
        }

        return t.getStatorCurrent();
    }

    private WPI_TalonSRX getTalon(boolean left, int num) {
        WPI_TalonSRX t = null;
        if (left) {
            if (num == 1) {
                t = leftFront;
            } else if (num == 2) {
                t = leftMiddle;
            } else if (num == 3) {
                t = leftBack;
            }
        } else {
            if (num == 1) {
                t = rightFront;
            } else if (num == 2) {
                t = rightMiddle;
            } else if (num == 3) {
                t = rightBack;
            }
        }
        return t;
    }

}