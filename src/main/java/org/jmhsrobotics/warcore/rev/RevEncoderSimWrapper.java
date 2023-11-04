package org.jmhsrobotics.warcore.rev;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;

/**
 * The `RevEncoderSimWrapper` class provides a wrapper for simulating encoder
 * data associated with a `CANSparkMax` motor controller using WPILib's
 * simulation framework.
 */
public final class RevEncoderSimWrapper extends BaseEncoderWrapper {

	/**
	 * Creates a new instance of `RevEncoderSimWrapper` for simulating encoder data
	 * of a specified `CANSparkMax` motor controller.
	 *
	 * @param motorController
	 *            The `CANSparkMax` motor controller for which to create the
	 *            simulation wrapper.
	 * @return A new `RevEncoderSimWrapper` instance for simulating encoder data.
	 */
	public static RevEncoderSimWrapper create(CANSparkMax motorController) {
		SimDeviceSim deviceSim = new SimDeviceSim("SPARK MAX [" + motorController.getDeviceId() + "]");
		SimDouble position = deviceSim.getDouble("Position");
		SimDouble velocity = deviceSim.getDouble("Velocity");

		return new RevEncoderSimWrapper(position, velocity);
	}

	private RevEncoderSimWrapper(SimDouble position, SimDouble velocity) {
		super(position::get, position::set, velocity::set);
	}
}
