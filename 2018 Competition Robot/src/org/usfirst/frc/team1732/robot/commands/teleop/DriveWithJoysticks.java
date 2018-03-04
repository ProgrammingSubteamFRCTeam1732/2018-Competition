package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.drivercontrol.DifferentialDrive;
import org.usfirst.frc.team1732.robot.util.NotifierCommand;

/**
 *
 */
public class DriveWithJoysticks extends NotifierCommand {

	private final DifferentialDrive drive;

	public DriveWithJoysticks(DifferentialDrive drive) {
		super(1);
		requires(Robot.drivetrain);
		this.drive = drive;
	}

	// Called just before this Command runs the first time
	@Override
	protected void init() {
		Robot.drivetrain.setCoast();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void exec() {
		// drive.tankDrive(Robot.joysticks.getLeft(), Robot.joysticks.getRight(),
		// false);
		drive.tankDrive(-Robot.joysticks.getRight(), -Robot.joysticks.getLeft(), false);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void whenEnded() {
	}
}
