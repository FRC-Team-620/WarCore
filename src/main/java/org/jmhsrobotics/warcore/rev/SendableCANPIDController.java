package org.jmhsrobotics.warcore.rev;

import java.lang.reflect.Field;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkMaxConfigAccessor;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class SendableCANPIDController implements Sendable {
	private static int instances = 10;
	private SparkClosedLoopController pid;
	private double lastSetpoint = Double.NaN;
	private SparkBase.ControlType controlType;
	private int sparkHandle;
	private ClosedLoopSlot slot;
	private SparkMaxConfigAccessor pidconfig;
	private SparkBase base;

	/**
	 * A wraper class to allow Sparkmax pidloops to be tuned from within
	 * smartdashboard or glass. Add this display with SmartDashboard.putData(new
	 * SendableCANPIDController(args));
	 *
	 * @param pid
	 *            PID loop to use
	 * @param controlType
	 *            Control type to be used when setpoint is set.
	 * @param slot
	 *            PID slot
	 */
	public SendableCANPIDController(SparkClosedLoopController pid, SparkBase.ControlType controlType,
			ClosedLoopSlot slot) {
		this.pid = pid;
		this.controlType = controlType;
		this.slot = slot;
		SendableRegistry.addLW(this, "PIDController", instances++); // TODO: do a less janky solution to this.
		Field sparkfield;
		try {
			sparkfield = SparkClosedLoopController.class.getField("spark");
			sparkfield.setAccessible(true);
			var handleField = SparkBase.class.getField("sparkHandle");
			handleField.setAccessible(true);
			this.base = (SparkBase) sparkfield.get(pid);
			this.sparkHandle = (int) handleField.get(this.base);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to get reference to Spark Handle");
		}
		pidconfig = new SparkMaxConfigAccessor(this.sparkHandle);

	}

	public SendableCANPIDController(SparkClosedLoopController pid, SparkBase.ControlType controlType) {
		this(pid, controlType, ClosedLoopSlot.kSlot0);
	}

	/**
	 * Sets the Reference for the PID loop. This method should only be used by
	 * smartdashboards Sendable/Builder
	 *
	 * @param setpoint
	 *            Setpoint to hit
	 */
	public void setSetpoint(double setpoint) {
		this.setSetpoint(setpoint, this.controlType);

	}

	/**
	 * Sets the Reference for the PID loop including the control mode
	 *
	 * @param setpoint
	 *            Setpoint to hit
	 * @param controlType
	 *            Control mode to use
	 */
	public void setSetpoint(double setpoint, SparkBase.ControlType controlType) {
		this.lastSetpoint = setpoint;
		this.pid.setReference(setpoint, controlType);
	}

	/**
	 * A hacky getSetpoint method that just returns the last setpoint set via this
	 * class or smartdashboard. Does not Live track changes to the setpoint via the
	 * Smarkmax controler.
	 *
	 * @return returns the last set setpoint from this class.
	 */
	public double getSetpoint() {
		// System.err.println("Get setpoint is not supported, returning last set
		// setpoint");
		return this.lastSetpoint;
	}

	private double getP() {
		return pidconfig.closedLoop.getP(slot);
	}

	private double getI() {
		return pidconfig.closedLoop.getI(slot);
	}

	private double getD() {
		return pidconfig.closedLoop.getD(slot);
	}

	private void setP(double p) {
		var config = new SparkMaxConfig();
		config.closedLoop.p(p, slot);
		base.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
	}

	private void setI(double i) {
		var config = new SparkMaxConfig();
		config.closedLoop.i(i, slot);
		base.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
	}

	private void setD(double d) {
		var config = new SparkMaxConfig();
		config.closedLoop.d(d, slot);
		base.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
	}

	@Override
	public void initSendable(SendableBuilder builder) { // TODO: add IZone support
		builder.setSmartDashboardType("PIDController");
		builder.addDoubleProperty("p", this::getP, this::setP);
		builder.addDoubleProperty("i", this::getI, this::setI);
		builder.addDoubleProperty("d", this::getD, this::setD);
		builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);

	}

}
