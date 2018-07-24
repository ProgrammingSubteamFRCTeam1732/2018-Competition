package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipClampOut extends InstantCommand {

	public ManipClampOut() {
		super();
		requires(Robot.manip);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.manip.clampOut();
	}

}