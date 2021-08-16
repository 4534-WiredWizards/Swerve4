package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//Select Swerve Module(front right, front left, rear right, or rear left)
//Choose specific motor on Swerve Module (drive or rotation)
//Assign each motor to a button

public class ChooseMotor extends CommandBase {
    
    private final Command flDrive =  new FrontLeftDrive();
    private final Command flRotation = new FrontLeftRotation();
    private final Command frDrive = new FrontRightDrive();
    private final Command frRotation = new FrontRightRotation();
    private final Command rlDrive = new RearLeftDrive();
    private final Command rlRotation = new RearLeftRotation();
    private final Command rrDrive = new RearRightDrive();
    private final Command rrRotation = new RearRightRotation();
    public SendableChooser<Command> motorChooser = new SendableChooser<Command>();

    public ChooseMotor() {
        
        motorChooser.setDefaultOption("frontleftDrive", flDrive);
        motorChooser.addOption("frontleftRotation", flRotation);
        motorChooser.addOption("frontrightDrive", frDrive);
        motorChooser.addOption("frontrightRotation", frRotation);
        motorChooser.addOption("rearleftDrive", rlDrive);
        motorChooser.addOption("rearleftRotation", rlRotation);
        motorChooser.addOption("rearrightDrive", rrDrive);
        motorChooser.addOption("rearrightRotation", rrRotation);
        SmartDashboard.putData(motorChooser);


    }
    
}