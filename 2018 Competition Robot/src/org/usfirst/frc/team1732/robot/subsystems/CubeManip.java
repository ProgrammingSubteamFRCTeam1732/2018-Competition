package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the intakes
 * 
 * Manages 2 TalonSPX (right, left)
 */
public class CubeManip extends Subsystem {

	public VictorSPX master;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public CubeManip(RobotConfig config) {
		master = MotorUtils.makeVictor(config.manipMaster, config.manipConfig);
		MotorUtils.makeVictor(config.manipFollower, config.manipConfig);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setIn() {
		master.set(ControlMode.PercentOutput, -0.5);
	}

	public void setHold() {
		// use current control to hold it (current is proportional to torque)
	}

	public void setOut() {
		master.set(ControlMode.PercentOutput, 0.5);
	}

	public void setStop() {
		master.set(ControlMode.PercentOutput, 0);
	}
}
