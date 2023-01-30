// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  XboxController controller;

  CANSparkMax drivingMotor;
  CANSparkMax turningMotor;

  RelativeEncoder drivingEncoder;
  AbsoluteEncoder turningEncoder;

  SparkMaxPIDController drivingPID;
  SparkMaxPIDController turningPID;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    controller = new XboxController(0);

    drivingMotor = new CANSparkMax(1, MotorType.kBrushless);
    turningMotor = new CANSparkMax(2, MotorType.kBrushless);

    drivingEncoder = drivingMotor.getEncoder();
    turningEncoder = turningMotor.getAbsoluteEncoder(Type.kDutyCycle);
    drivingPID = drivingMotor.getPIDController();
    turningPID = turningMotor.getPIDController();
    drivingPID.setFeedbackDevice(drivingEncoder);
    turningPID.setFeedbackDevice(turningEncoder);

    drivingEncoder.setPositionConversionFactor(0.0762 * Math.PI / 4.714);
    drivingEncoder.setVelocityConversionFactor((0.0762 * Math.PI / 4.714) / 60.0);
    turningEncoder.setPositionConversionFactor(2 * Math.PI);
    turningEncoder.setVelocityConversionFactor((2 * Math.PI) / 60.0);

    turningPID.setPositionPIDWrappingEnabled(true);
    turningPID.setPositionPIDWrappingMinInput(0);
    turningPID.setPositionPIDWrappingMaxInput(2 * Math.PI); 

    drivingPID.setP(0.04);
    drivingPID.setI(0);
    drivingPID.setD(0);
    drivingPID.setFF(1/4.8016);
    drivingPID.setOutputRange(-1, 1);

    turningPID.setP(0);
    turningPID.setI(0);
    turningPID.setD(0);
    turningPID.setFF(0);
    turningPID.setOutputRange(-1, 1);

    drivingMotor.setIdleMode(IdleMode.kBrake);
    turningMotor.setIdleMode(IdleMode.kBrake);

    drivingMotor.burnFlash();
    turningMotor.burnFlash();
    
    drivingEncoder.setPosition(0);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //reset all of our variables
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //drivingPID.setReference(0.2, CANSparkMax.ControlType.kVelocity);
    //turningPID.setReference(0.2, CANSparkMax.ControlType.kPosition);
    if(controller.getRawButtonReleased(1)){
      turningPID.setReference(Math.PI, CANSparkMax.ControlType.kPosition);
    } else if(controller.getRawButtonReleased(4)){
      turningPID.setReference(0, CANSparkMax.ControlType.kPosition);
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
