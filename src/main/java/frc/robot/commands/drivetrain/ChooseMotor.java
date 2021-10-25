package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//Select Swerve Module(front right, front left, rear right, or rear left)
//Choose specific motor on Swerve Module (drive or rotation)
//Assign each motor to a button

public class ChooseMotor extends CommandBase {

    
    private final Command frontLeft =  new SwerveModule(frontRightDriveMotorId, frontRightRotationMotorId, );
    private final Command frontRight = new SwerveModule();
    private final Command backLeft = new SwerveModule();
    private final Command backRight = new SwerveModule();
    // Create instances of Swerve Module and pass in difference drive and rotation motor IDs. 

    public SendableChooser<Command> motorChooser = new SendableChooser<Command>();

    public ChooseMotor() {
        
        motorChooser.addOption("frontLeft", frontLeft);
        motorChooser.addOption("frontRight", frontRight);
        motorChooser.addOption("backLeft", backRight);
        motorChooser.addOption("backRight", backLeft);
        SmartDashboard.putData(motorChooser);


    }
    
}