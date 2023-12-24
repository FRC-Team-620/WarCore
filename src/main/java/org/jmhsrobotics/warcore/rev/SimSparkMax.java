package org.jmhsrobotics.warcore.rev;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * The `SimSparkMax` class extends `CANSparkMax` to provide enhanced simulation
 * support for Spark MAX motor controllers. It allows you to simulate the
 * behavior of Spark MAX motor controllers in software-based robot simulations.
 */
public class SimSparkMax extends CANSparkMax {
	private double lastSet = 0;

	/**
	 * Constructs a new `SimSparkMax` instance with the specified device ID and
	 * motor type.
	 *
	 * @param deviceId
	 *            The unique device ID of the Spark MAX.
	 * @param type
	 *            The type of motor (Brushed or Brushless) associated with the Spark
	 *            MAX.
	 */
	public SimSparkMax(int deviceId, MotorType type) {
		super(deviceId, type);
	}

	/**
	 * Common interface for getting the current set speed of a speed controller.
	 *
	 * @return The current set speed. Value is between -1.0 and 1.0.
	 */
	@Override
	public double get() {
		if (RobotBase.isSimulation()) {
			return this.lastSet;
		} else {
			return super.get();
		}

	}

	/**
	 * Common interface for setting the speed of a speed controller.
	 *
	 * @param speed
	 *            The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {
		if (RobotBase.isSimulation()) {
			this.lastSet = speed;
		} else {
			super.set(speed);
		}

	}

}
