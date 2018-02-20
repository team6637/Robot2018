package org.usfirst.frc.team6637.robot.subsystems;

import org.usfirst.frc.team6637.robot.RobotMap;
import org.usfirst.frc.team6637.robot.commands.Drive_Arcade_Command;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Drive_Subsystem extends Subsystem {

		// motors for driving
		WPI_TalonSRX LFMotor = new WPI_TalonSRX(RobotMap.LFTalon);
		WPI_TalonSRX RFMotor = new WPI_TalonSRX(RobotMap.RFTalon);
	    
	    // slaves
		WPI_VictorSPX LRMotor = new WPI_VictorSPX(RobotMap.LRVictor);
		WPI_TalonSRX RRMotor = new WPI_TalonSRX(RobotMap.RRTalon);
		
	    DifferentialDrive drive = new DifferentialDrive(LFMotor, RFMotor);
	    
	    public PigeonIMU gyro;

	    public Drive_Subsystem() {
	    		
	    	// point slaves to masters
	    	LRMotor.follow(LFMotor);
	    	RRMotor.follow(RFMotor);
	    		
	    	// invert if necessary, otherwise delete
	    	LFMotor.setInverted(true);
	    	RFMotor.setInverted(true);
	    	LRMotor.setInverted(true);	    		
	    	RRMotor.setInverted(true);
	    	
	    	// initiate gyro
	    	gyro = new PigeonIMU(RRMotor);
	    	resetAngle();
	    	
	    	System.out.println("1");
	    	initEncoders();
	    	System.out.println("2");

	    }
		
		public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        setDefaultCommand(new Drive_Arcade_Command()); 
	    }
		
		public void autonDrive(double move, double turn, boolean isTurning) {
			drive.arcadeDrive(move, -turn, isTurning);
		}
		
		public void teleopDrive(double move, double turn) {

			if (Math.abs(move) < 0.10) {				
				move = 0;
			}
			if (Math.abs(turn) < 0.10) {
				turn = 0;
			}
			
			drive.arcadeDrive(move, -turn);
		}
		
		public void setPower(double leftPower, double rightPower) {
			drive.tankDrive(leftPower, rightPower);
		}
		
		public void stop() {
			drive.tankDrive(0.0, 0.0);
		}
		
	    public double getAngle() {
	    	double [] ypr = new double[3];
	    	gyro.getYawPitchRoll(ypr);
	    	double angle = ypr[0];
	    	return -angle;
	    }
	    
	    public void resetAngle() {
	    	gyro.setYaw(0.0, 0);
	    }
	    
	    // ENCODERS
	    
	    public void initEncoders() {
			LFMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			RFMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			
			LFMotor.setSensorPhase(true);
			RFMotor.setSensorPhase(true);
			
			resetEncoders();
		}
	    
	    public void resetEncoders() {
	    	System.out.println("reset");
	    	LFMotor.setSelectedSensorPosition(0, 0, 0);
	    	RFMotor.setSelectedSensorPosition(0, 0, 0);
	    	System.out.println(LFMotor.getSelectedSensorPosition(0));
	    }
	    
	    public int getLeftPosition() {
	    	int pos = LFMotor.getSensorCollection().getQuadraturePosition();
	    	return -pos;    
	    }
	    
	    public int getRightPosition() {
	    	int pos = RFMotor.getSensorCollection().getQuadraturePosition();
	    	return pos;    
	    }
	    
	    public double getAverageDistance() {
	    	return getLeftPositionInches() + getRightPositionInches() / 2;
	    }
	    
	    public double getLeftPositionInches() {
			double rotations = ((double) getLeftPosition()) / (4 * 360);
			
			return rotationsToInches(rotations);
		}
		
		public double getRightPositionInches() {
			double rotations = ((double) getRightPosition()) / (4 * 360);
			
			return rotationsToInches(rotations);
		}
		
		public double rotationsToInches(double rotations) {
			return rotations * (Math.PI * 6);
		}
		
		public double inchesToRotations(double inches) {
			return inches / (Math.PI * 6);
		}
	}