/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1732.robot;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.input.Input;
import org.usfirst.frc.team1732.robot.sensors.Sensors;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Climber;
import org.usfirst.frc.team1732.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;
import org.usfirst.frc.team1732.robot.subsystems.Manip;
import org.usfirst.frc.team1732.robot.util.BooleanTimer;
import org.usfirst.frc.team1732.robot.util.Dashboard;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	// RobotConfig
	public static RobotConfig robotConfig;
	public static Dashboard dash;

	// subsystems
	public static Drivetrain drivetrain;
	public static Arm arm;
	public static Manip manip;
	public static Elevator elevator;
	public static Climber climber;
	public static Sensors sensors;

	// input
	public static Input joysticks;

	// other
	public static final int PERIOD_MS = 20;
	public static final double PERIOD_S = PERIOD_MS / 1000.0;
	public static final int CONFIG_TIMEOUT = 10; // recommended timeout by CTRE
	private static BooleanTimer gameDataWaiter;

	private Command defaultAuto;
	private Supplier<Command> chosenAuto;
	
	private static double fps;
	public static double getFps() {
		return fps;
	}

	private double last;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		setPeriod(PERIOD_S); // periodic methods will loop every 10 ms (1/100 sec)
		robotConfig = RobotConfig.getConfig();
		dash = new Dashboard();

		drivetrain = new Drivetrain(robotConfig);
		manip = new Manip(robotConfig);
		arm = new Arm(robotConfig);
		elevator = new Elevator(robotConfig);
		climber = new Climber(robotConfig);
		sensors = new Sensors(robotConfig);

		joysticks = new Input(robotConfig);

		defaultAuto = new DriveDistance(140);
		gameDataWaiter = new BooleanTimer(10, DriverStationData::gotPlatePositions);
		// gameDataWaiter will either start the auto if game data is received before 10
		// seconds, or it will drive across the auto line after 10 seconds
		dash.add("Update Rate", this::getFps);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		protectRobot();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		protectRobot();
		gameDataWaiter.start();
		// in the below line we would get our chosen auto through whatever means
		// chosenAuto = () -> new DrivetrainCharacterizer(TestMode.QUASI_STATIC,
		// Direction.Forward);
		autoStarted = false;
	}

	private boolean autoStarted = false;

	@Override
	public void teleopInit() {
		protectRobot();
		// cancel auto command here
		drivetrain.setCoast();
	}

	@Override
	public void testInit() {
		protectRobot();
	}
	
	/**
	 * Safety functions to prevent auto and teleop commands from interfering with each other
	 */
	private void protectRobot() {
		Scheduler.getInstance().removeAll();
		fps = Timer.getFPGATimestamp() - last;
		last = Timer.getFPGATimestamp();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (!autoStarted) {
			autoStarted = gameDataWaiter.checkIfDone();
			if (autoStarted) {
				if (gameDataWaiter.isTimedOut()) {// start default auto
					System.out.println("ERROR: Game data not received. Starting default auto.");
					defaultAuto.start();
				} else {
					System.out.println("Game data received. Starting chosen auto.");
					chosenAuto.get().start();
				}
			} else {
				System.out.println("Game data not yet received");
			}
		}
		
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * This function is called periodically during the robot mode.
	 */
	@Override
	public void testPeriodic() {}
	@Override
	public void disabledPeriodic() {}
	@Override
	public void teleopPeriodic() {}
}
