package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.manip;

import org.usfirst.frc.team1732.robot.subsystems.Manip;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManipSetInUntilCube extends Command {

	public ManipSetInUntilCube() {
		requires(manip);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		manip.setIn();
		stopTimer.reset();
		stopTimer.stop();
		Debugger.logStart(this);
	}

	private Timer stopTimer = new Timer();
	private boolean gotAboveStopCurent = false;

	protected void execute() {
		if (manip.aboveStopCurrent() && !gotAboveStopCurent) {
			gotAboveStopCurent = true;
			stopTimer.start();
		} else if (gotAboveStopCurent && !manip.aboveStopCurrent()) {
			gotAboveStopCurent = false;
			stopTimer.reset();
			stopTimer.stop();
		}

	}

	protected boolean isFinished() {
		return manip.aboveStopCurrent() && stopTimer.get() > Manip.STOP_TIME;
	}

	protected void end() {
		manip.setStop();
		Debugger.logEnd(this);
	}
}