package org.jmhsrobotics.warcore.sim;

import org.jmhsrobotics.warcore.sim.TimeOfFlight.ToFProps;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimEnum;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;

public class SimTimeOfFlight {

	TimeOfFlight tof;
	SimDeviceSim sim;
	private SimBoolean rangeValid;
	private SimDouble range, rangeSigma, ambientLight, sampleTime;
	private SimEnum status, rangeMode;

	public SimTimeOfFlight(TimeOfFlight tof) {
		sim = new SimDeviceSim(ToFProps.kDeviceName, tof.canId);
		rangeValid = sim.getBoolean(ToFProps.kRangeValid);
		range = sim.getDouble(ToFProps.kRange);
		rangeSigma = sim.getDouble(ToFProps.kRangeSigma);
		ambientLight = sim.getDouble(ToFProps.kAmbientLight);
		sampleTime = sim.getDouble(ToFProps.kSampleTime);
		status = sim.getEnum(ToFProps.kStatus);
		rangeMode = sim.getEnum(ToFProps.kRangeMode);
	}

	public void setRange(double rangeVal) {
		this.range.set(rangeVal);
	}

	public void setRangeValid(boolean isValid) {
		this.rangeValid.set(isValid);
	}

	public void setRangeSigma(double sigma) {
		this.rangeSigma.set(sigma);
	}

	public void setAmientLightLevel(double level) {
		this.ambientLight.set(level);
	}

	public void setSampleTime(double period) {
		this.sampleTime.set(period);
	}
	// TODO: Set Status and Set rangemode
	// TODO: set ROI
}
