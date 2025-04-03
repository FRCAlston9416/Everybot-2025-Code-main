// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriverConstants;
import frc.robot.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand extends Command {
  // Existing fields
  private final DoubleSupplier m_xSpeed;
  private final DoubleSupplier m_zRotation;
  private final DriveSubsystem m_drive;
  private final BooleanSupplier m_squared;

  // Add deadband threshold constant reference
  private static final double DEADBAND = DriverConstants.DEADBAND_THRESHOLD;

  public DriveCommand(DriveSubsystem driveSubsystem, 
      DoubleSupplier xSpeed, DoubleSupplier zRotation, BooleanSupplier squareInputs) {
    // Existing constructor logic
    m_xSpeed = xSpeed;
    m_zRotation = zRotation;
    m_drive = driveSubsystem;
    m_squared = squareInputs;
    addRequirements(m_drive);
  }

  // Add deadband adjustment method
  private double applyDeadband(double input) {
    if (Math.abs(input) <= DEADBAND) {
      return 0.0;
    }
    // Scale input to maintain full range after deadband
    return Math.copySign(
      (Math.abs(input) - DEADBAND) / (1.0 - DEADBAND), 
      input
    );
  }

  @Override
  public void execute() {
    // Apply deadband to inputs before sending to drive
    double processedX = applyDeadband(m_xSpeed.getAsDouble());
    double processedZ = applyDeadband(m_zRotation.getAsDouble());
    
    m_drive.driveArcade(processedX, processedZ, m_squared.getAsBoolean());
  }

  // Rest of class remains unchanged
  @Override public void initialize() {}
  @Override public void end(boolean isInterrupted) {}
  @Override public boolean isFinished() { return false; }
}