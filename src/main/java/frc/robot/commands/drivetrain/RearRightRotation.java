package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import net.thefletcher.revrobotics.enums.MotorType;
import net.thefletcher.revrobotics.enums.IdleMode;
import frc.robot.Constants.CANDevices;
import net.thefletcher.revrobotics.CANSparkMax;
import net.thefletcher.revrobotics.CANEncoder;
import net.thefletcher.revrobotics.CANPIDController;

public class RearRightRotation extends CommandBase{

    private static final double rotationkP = 1;
    private static final double rotationkD = 0.5;

    private CANSparkMax motor;
    private CANPIDController controller;
    private CANEncoder encoder;

    public RearRightRotation(){
        motor = new CANSparkMax(CANDevices.rearRightRotationMotorId, MotorType.kBrushless);
        motor.setIdleMode(IdleMode.kCoast);
        encoder = motor.getEncoder();
        controller = motor.getPIDController();
        controller.setP(rotationkP);
        controller.setD(rotationkD);
    }
}