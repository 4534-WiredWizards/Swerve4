package frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.util.Units;

public class Constants {

    public static final class CANDevices {

        public static final int frontLeftRotationMotorId = 11; //12
        public static final int frontLeftDriveMotorId = 10; //13

        public static final int frontRightRotationMotorId = 21; //14
        public static final int frontRightDriveMotorId = 20; //15

        public static final int rearLeftRotationMotorId = 31; //10
        public static final int rearLeftDriveMotorId = 30; //11

        public static final int rearRightRotationMotorId = 41; //16
        public static final int rearRightDriveMotorId = 40; //17

        public static final int leftClimberMotorId = 20;
        public static final int rightClimberMotorId = 21;

        public static final int frontLeftRotationEncoderId = 39; //39
        public static final int frontRightRotationEncoderId = 49; //49
        public static final int rearLeftRotationEncoderId = 19; //19
        public static final int rearRightRotationEncoderId = 29; //22

        public static final double frontLeftAngleOffset = Units.degreesToRadians(307.2);  //26.60    //26.71
        public static final double frontRightAngleOffset = Units.degreesToRadians(202.1);  //111.18   //111.44
        public static final double rearLeftAngleOffset = Units.degreesToRadians(202.6);  //108.98    //109.60
        public static final double rearRightAngleOffset = Units.degreesToRadians(307.2);  //37.00     //37.00
    

        public static final int rightFlywheelMotorId = 12;
        public static final int leftFlywheelMotorId = 11;

        public static final int kickerMotorId = 10;
        public static final int intakeMotorId = 16; 
        public static final int conveyorMotorId = 9;

        public static final int imuId = 18;

    }

    public static final class DIOChannels {

        public static final int bottomBannerPort = 0;
        public static final int topBannerPort = 9;

    }

    public static final class InputDevices {

        public static final int leftJoystickPort = 0;
        public static final int rightJoystickPort = 1;

        public static final int gamepadPort = 2;

    }

    public static final class PneumaticChannels {

        public  static final int PCMId = 19;

        public static final int[] intakeSolenoidChannels = {0, 1};
        public static final int[] hoodSolenoidChannels = {2, 3};
        public static final int[] climberSolenoidChannels = {4, 5};

    }

    public static final class DriveConstants {

        public static final double trackWidth = Units.inchesToMeters(17.25);
        public static final double wheelBase = Units.inchesToMeters(28.5);

        public static final SwerveDriveKinematics kinematics = 
            new SwerveDriveKinematics(
                new Translation2d(trackWidth / 2.0, wheelBase / 2.0), //front left
                new Translation2d(trackWidth / 2.0, -wheelBase / 2.0), //front right
                new Translation2d(-trackWidth / 2.0, wheelBase / 2.0), //rear left
                new Translation2d(-trackWidth / 2.0, -wheelBase / 2.0) //rear right
            );

        public static final double driveWheelGearReduction = 8.14; //6.86 for MK3
        public static final double rotationWheelGearReduction = 12.8; //12.8 for MK3

        public static final double wheelDiameterMeters = 0.050686 * 2;

        public static final double rotationMotorMaxSpeedRadPerSec = .25; //1.0;
        public static final double rotationMotorMaxAccelRadPerSecSq = 0.5; //1.0

        public static final SimpleMotorFeedforward driveFF = new SimpleMotorFeedforward(0.254, 0.137);

        public static final double maxDriveSpeed = 4; //14.4;  4   6
        public static final double teleopTurnRateDegPerSec = 120; //360.0;  was at 90 but we lowered because it was very bumpy
                                                                 // 45 worked but was too slow :(    //Rate the robot will spin with full rotation command

    }

    public static final class SuperstructureConstants {

        public static final double baseShootingSpeed = 6000; //rptations per minute

        public static final double intakingPower = 0.4;
        public static final double jogFowardPower = 0.2;

        public static final double kickerPower = 1.0;
        public static final double conveyorPower = 0.35;

        public static final double flywheelGearRatio = 32.0 / 18.0;

        public static final double jogDelaySeconds = 0.1;

    }

    public static final class ClimbingConstants {

        public static final double climberMotorGearReduction = 1;

        public static final double winchDiameterInches = 1;
        public static final double climberMaxHeightInches = 40.5;

        public static final double desiredClimberSpeedInchesPerSecond = 1;

    }

    public static final class VisionConstants {

        public static final double limelightHeightInches = 26.5; // distance from limelight to ground
        public static final double limelightMountAngleRadians = Units.degreesToRadians(53);

    }

    public static final class AutoConstants {

        public static final double maxVelMetersPerSec = 4.5;
        public static final double maxAccelMetersPerSecondSq = 1.95;
        
    }

    public static final class FieldConstants {

        public static final double targetHeightInches = 89.5;

    }
    
}
