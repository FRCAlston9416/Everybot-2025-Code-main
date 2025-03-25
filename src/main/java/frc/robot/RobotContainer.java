// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriverConstants;
import frc.robot.autos.DriveForwardAuto;
import frc.robot.autos.SimpleCoralAuto;
import frc.robot.commands.AlgieInCommand;
import frc.robot.commands.AlgieOutCommand;
import frc.robot.commands.ArmDownCommand;
import frc.robot.commands.ArmUpCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.CoralOutCommand;
import frc.robot.commands.CoralStackCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RollerSubsystem;

/**
 * This class serves as the central hub for declaring the robot's structure, including subsystems,
 * commands, and trigger mappings. In the command-based paradigm, most robot logic is defined here
 * rather than in the Robot class's periodic methods.
 */
public class RobotContainer {

    // Subsystem declaration
    private final RollerSubsystem m_roller = new RollerSubsystem();
    private final ArmSubsystem m_arm = new ArmSubsystem();
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final ClimberSubsystem m_climber = new ClimberSubsystem();
    //endregion

    //Control Devices (ie. XboxControllers)
    private final CommandXboxController m_driverController =
            new CommandXboxController(DriverConstants.DRIVER_CONTROLLER_PORT);
    // please do not drop the controller anymore tysm

    // Autonomous Commands
    private final SimpleCoralAuto m_simpleCoralAuto = new SimpleCoralAuto(m_drive, m_roller, m_arm);
    private final DriveForwardAuto m_driveForwardAuto = new DriveForwardAuto(m_drive);
    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    /** Constructs the robot container, initializing subsystems, bindings, and autonomous options. */
    public RobotContainer() {
        configureBindings();
        setupAutonomousChooser();
        CameraServer.startAutomaticCapture();
    }

    //region Configuration Methods

    /**
     * Configures trigger-to-command mappings for the driver controller.
     */
    private void configureBindings() {
        // Default drive command with corrected Y-axis inversion
        m_drive.setDefaultCommand(new DriveCommand(
                m_drive,
                () -> -m_driverController.getLeftY(),  // Negated to make forward positive
                () -> -m_driverController.getRightX(), // Matches WPILib counter-clockwise convention
                () -> true                             // Enables input squaring for smoother control
        ));

        // Slow mode: reduces speed when left bumper is held
        m_driverController.leftBumper().whileTrue(new DriveCommand(
                m_drive,
                () -> -m_driverController.getLeftY() * DriveConstants.SLOW_MODE_MOVE,
                () -> -m_driverController.getRightX() * DriveConstants.SLOW_MODE_TURN,
                () -> true
        ));

        // Roller commands
        m_driverController.rightBumper().whileTrue(new AlgieInCommand(m_roller));
        m_driverController.rightTrigger(0.2).whileTrue(new AlgieOutCommand(m_roller));

        // Arm commands (passively held after execution)
        m_driverController.pov(90).whileTrue(new ArmUpCommand(m_arm));
        m_driverController.pov(270).whileTrue(new ArmDownCommand(m_arm));

        // Coral scoring commands
        m_driverController.x().whileTrue(new CoralOutCommand(m_roller));
        m_driverController.y().whileTrue(new CoralStackCommand(m_roller));

        // Climber commands (direction depends on winch winding)
        m_driverController.pov(0).whileTrue(new ClimberUpCommand(m_climber));
        m_driverController.pov(180).whileTrue(new ClimberDownCommand(m_climber));
    }

    /**
     * Sets up the autonomous command chooser for selection via SmartDashboard.
     */
    private void setupAutonomousChooser() {
        m_chooser.setDefaultOption("Coral Auto", m_simpleCoralAuto);
        m_chooser.addOption("Drive Forward Auto", m_driveForwardAuto);
        SmartDashboard.putData(m_chooser);
    }

    //endregion

    /**
     * Returns the selected autonomous command to be executed by the Robot class.
     *
     * @return The command chosen via the SmartDashboard chooser
     */
    public Command getAutonomousCommand() {
        return m_chooser.getSelected();
    }
}