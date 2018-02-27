package org.usfirst.frc.team1732.robot.util;

import java.util.LinkedList;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	public Dashboard() {
		Notifier notifier = new Notifier(this::loop);
		int loopTimeMs = 40;
		notifier.startPeriodic(loopTimeMs / 1000.0);
	}

	private void loop() {
		entries.forEach(this::call);
	}

	private void call(Entry e) {
		e.putToDashboard();
	}

	private static LinkedList<Entry> entries;

	public void add(String name, Supplier<?> sup) {
		entries.add(new Entry(name, sup));
	}

	private class Entry {
		private final String name;
		private final Supplier<?> sup;

		public Entry(String name, Supplier<?> sup) {
			this.name = name;
			this.sup = sup;
		}

		public void putToDashboard() {
			Object o = sup.get();
			if (o instanceof Number) {
				SmartDashboard.putNumber(name, ((Number) o).doubleValue());
			} else if (o instanceof Boolean) {
				SmartDashboard.putBoolean(name, (Boolean) o);
			} else if (o instanceof String) {
				SmartDashboard.putString(name, (String) o);
			} else if (o instanceof Sendable) {
				SmartDashboard.putData((Sendable) o);
			}
		}
	}
}