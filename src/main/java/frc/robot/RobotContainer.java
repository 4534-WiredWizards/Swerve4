package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.InputDevices;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.drivetrain.QuickTurn;
import frc.robot.commands.drivetrain.AlignWithTargetVision;
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.OperatorControl;
import frc.robot.commands.superstructure.Indexing.Waiting;
import frc.robot.commands.superstructure.shooting.RampUpWithVision;
import frc.robot.subsystems.IndexingSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;


public class RobotContainer {

    /**
     * Establishes the controls and subsystems of the robot
     */

 //   private final Joystick leftJoystick = new Joystick(InputDevices.leftJoystickPort);
 //   private final Joystick rightJoystick = new Joystick(InputDevices.rightJoystickPort);

    private final XboxController GMDJoystick = new XboxController(InputDevices.leftJoystickPort);
    private final XboxController gamepad = new XboxController(InputDevices.gamepadPort);

    private DriveSubsystem drive = new DriveSubsystem();
    private IntakeSubsystem intake = new IntakeSubsystem();
    private IndexingSubsystem indexer = new IndexingSubsystem();
    private ShooterSubsystem shooter = new ShooterSubsystem();
    private Limelight limelight = new Limelight();
    
    public RobotContainer() {
        //callibrates joysticks
        drive.setDefaultCommand(
            new OperatorControl(
                drive, 
                () -> GMDJoystick.getY(GenericHID.Hand.kLeft), 
                () -> GMDJoystick.getX(GenericHID.Hand.kLeft), 
        //NOTE: Greg's Right X controller is on Axis 2
                () -> GMDJoystick.getRawAxis(2),
                true
            )
        );

        /*
        shooter.setDefaultCommand(
            new RunCommand(() -> shooter.runShooterPercent(gamepad.getRawAxis(3) / 5), shooter)
        );
        */

        indexer.setDefaultCommand(new Waiting(indexer));

        configureButtonBindings();

    }

    public void updateShuffleBoard() {

        SwerveModuleState[] tempStates; 

        SmartDashboard.putNumber("Left  Y Joy", GMDJoystick.getY(GenericHID.Hand.kLeft));
        SmartDashboard.putNumber("Left  X Joy", GMDJoystick.getX(GenericHID.Hand.kLeft));
        //NOTE: Greg's Right X controller is on Axis 2
        SmartDashboard.putNumber("Right X Joy", GMDJoystick.getRawAxis(2));

        tempStates = drive.getModuleStates();
        SmartDashboard.putNumber("CANcoder S7 FL", tempStates[2].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder S1 FR", tempStates[0].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder S6 RL", tempStates[3].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder S3 RR", tempStates[1].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder D8 FL", tempStates[2].speedMetersPerSecond);
        SmartDashboard.putNumber("CANcoder D2 FR", tempStates[0].speedMetersPerSecond);
        SmartDashboard.putNumber("CANcoder D5 RL", tempStates[3].speedMetersPerSecond);
        SmartDashboard.putNumber("CANcoder D4 RR", tempStates[1].speedMetersPerSecond);
    }

    public void configureButtonBindings() {

        // intake
        new JoystickButton(gamepad, Button.kB.value)
            .whenPressed(new InstantCommand(intake::runIntakeMotor))
            .whenReleased(new InstantCommand(intake::stopIntakeMotor));

        /*
        // ramp up shooter using vision
        new JoystickButton(gamepad, Button.kBumperRight.value)
            .whenPressed(new RampUpWithVision(shooter, limelight));
        */

        // shoot
        new JoystickButton(gamepad, Button.kY.value)
            .whileHeld(new InstantCommand(shooter::runKicker)
            .alongWith(new InstantCommand(indexer::runConveyor, indexer)))
            .whenReleased(new InstantCommand(shooter::stopKicker)
            .alongWith(new InstantCommand(indexer::stopConveyor, indexer)));

        // manual reverse
        new JoystickButton(gamepad, Button.kBack.value) 
            .whileHeld(
                new ParallelCommandGroup(
                    new InstantCommand(shooter::reverseKicker),
                    new InstantCommand(indexer::reverseConveyor, indexer),
                    new InstantCommand(intake::reverseIntakeMotor)
                )
            )
            .whenReleased(
                new ParallelCommandGroup(
                    new InstantCommand(shooter::stopKicker),
                    new InstantCommand(indexer::stopConveyor),
                    new InstantCommand(intake::stopIntakeMotor)
                )
            );

        // toggle intake
        new JoystickButton(gamepad, Button.kX.value)
            .whenPressed(new InstantCommand(intake::toggleIntake));

        // shoot close
        new POVButton(gamepad, 0)
            .whileHeld(
                new InstantCommand(
                    () -> shooter.runVelocityProfileController(Units.rotationsPerMinuteToRadiansPerSecond(4500))
                )
                .alongWith(new InstantCommand(shooter::retractHood))
            )
            .whenReleased(new InstantCommand(shooter::stopShooter));

        // shoot mid-close
        new POVButton(gamepad, 90)
            .whileHeld(
                new InstantCommand(
                    () -> shooter.runVelocityProfileController(Units.rotationsPerMinuteToRadiansPerSecond(3800))
                )
                .alongWith(new InstantCommand(shooter::retractHood))
            )
            .whenReleased(new InstantCommand(shooter::stopShooter));

        // shoot mid-far
        new POVButton(gamepad, 180)
            .whileHeld(
                new InstantCommand(
                    () -> shooter.runVelocityProfileController(Units.rotationsPerMinuteToRadiansPerSecond(6000))
                )
                .alongWith(new InstantCommand(shooter::extendHood))
            )
            .whenReleased(new InstantCommand(shooter::stopShooter));

        // shoot far
        new POVButton(gamepad, 270)
            .whileHeld(
                new InstantCommand(
                    () -> shooter.runVelocityProfileController(Units.rotationsPerMinuteToRadiansPerSecond(6300))
                )
                .alongWith(new InstantCommand(shooter::extendHood))
            )
            .whenReleased(new InstantCommand(shooter::stopShooter));

        // // align with vision
        // new JoystickButton(GMDJoystick, Joystick.ButtonType.kTop.value)
        //     .whileHeld(new AlignWithTargetVision(drive, limelight));

        // // align with 0 degrees
        // new JoystickButton(GMDJoystick, Joystick.ButtonType.kTrigger.value)
        //     .whileHeld(new QuickTurn(drive, Math.PI));

        // // align with 180 degrees
        // new JoystickButton(GMDJoystick, Joystick.ButtonType.kTrigger.value) 
        //     .whileHeld(new QuickTurn(drive, 0));

        // // reset imu 
        // new JoystickButton(GMDJoystick, 3)
        //     .whenPressed(new InstantCommand(drive::resetImu));

        // toggle hood
        /*new JoystickButton(rightJoystick, Joystick.ButtonType.kTop.value)
            .whenPressed(new InstantCommand(shooter::toggleHood));
        */

        new JoystickButton(gamepad, Button.kX.value)
            .whenPressed(new RampUpWithVision(shooter, limelight));

    }

    public Command getAutonomousCommand() {

        drive.resetPose(AutoTrajectories.testTrajectory.getInitialPose());
        return new FollowTrajectory(drive, AutoTrajectories.testTrajectory);

    }

}
