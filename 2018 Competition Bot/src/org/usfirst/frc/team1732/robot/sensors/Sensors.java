package org.usfirst.frc.team1732.robot.sensors;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

public class Sensors {

	public final AHRS navX = new AHRS(SPI.Port.kMXP, (byte) 100);
	// if we use USB we can only get processed data, but that's fine
	public final AHRS nacXusb = new AHRS(SerialPort.Port.kUSB, SerialDataType.kProcessedData, (byte) 100);

}