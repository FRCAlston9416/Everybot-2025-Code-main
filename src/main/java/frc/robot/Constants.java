package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * Constants related to the drive system of the robot.
   */
  public static final class DriveConstants {
    // Motor IDs
    public static final int LEFT_LEADER_ID = 1;
    public static final int LEFT_FOLLOWER_ID = 2;
    public static final int RIGHT_LEADER_ID = 3;
    public static final int RIGHT_FOLLOWER_ID = 4;

    // Motor settings
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
    public static final double DRIVE_MOTOR_VOLTAGE_COMP = 12;

    // Movement settings
    public static final double SLOW_MODE_MOVE = 0.2;
    public static final double SLOW_MODE_TURN = 0.2;
    
  }

  /**
   * Constants related to the roller mechanism of the robot.
   */
  public static final class RollerConstants {
    // Motor ID and settings
    public static final int ROLLER_MOTOR_ID = 5;
    public static final int ROLLER_MOTOR_CURRENT_LIMIT = 60;
    public static final double ROLLER_MOTOR_VOLTAGE_COMP = 10;

    // Roller speeds
    public static final double ROLLER_CORAL_OUT = -.4;
    public static final double ROLLER_ALGAE_IN = -0.8;
    public static final double ROLLER_ALGAE_OUT = .4;
    public static final double ROLLER_CORAL_STACK = -1;
  }

  /**
   * Constants related to the arm mechanism of the robot.
   */
  public static final class ArmConstants {
    // Motor ID and settings
    public static final int ARM_MOTOR_ID = 6;
    public static final int ARM_MOTOR_CURRENT_LIMIT = 60;
    public static final double ARM_MOTOR_VOLTAGE_COMP = 10;

    // Arm speeds
    public static final double ARM_SPEED_DOWN = -0.1;
    public static final double ARM_SPEED_UP = 0.1;

    // Arm hold positions
    public static final double ARM_HOLD_DOWN = 0;
    public static final double ARM_HOLD_UP = 0.05;
  }

  /**
   * Constants related to the climber mechanism of the robot.
   */
  public static final class ClimberConstants {
    // Motor ID and settings
    public static final int CLIMBER_MOTOR_ID = 7;
    public static final int CLIMBER_MOTOR_CURRENT_LIMIT = 60;
    public static final double CLIMBER_MOTOR_VOLTAGE_COMP = 12;

    // Climber speeds
    public static final double CLIMBER_SPEED_DOWN = -1.8;
    public static final double CLIMBER_SPEED_UP = 1.8;
  }

  /**
   * Constants related to the driver controller.
   */
  public static final class DriverConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final double DEADBAND_THRESHOLD = 0.1;
  }
}
