package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import net.thefletcher.revrobotics.enums.MotorType;
import net.thefletcher.revrobotics.enums.IdleMode;
import frc.robot.Constants.CANDevices;
import net.thefletcher.revrobotics.CANSparkMax;
import net.thefletcher.revrobotics.CANEncoder;
import net.thefletcher.revrobotics.CANPIDController;

public class RearRightDrive extends CommandBase{

    private static final double drivekP = 0.01;

    private CANSparkMax motor;
    private CANPIDController controller;
    private CANEncoder encoder;

    public RearRightDrive(){
        motor = new CANSparkMax(CANDevices.rearRightDriveMotorId, MotorType.kBrushless);
        encoder = motor.getEncoder();
        motor.setIdleMode((IdleMode.kBrake));
        controller = motor.getPIDController();
        controller.setP(drivekP);
    }
}