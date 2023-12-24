package org.jmhsrobotics.warcore.math;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A wrapper for ProfiledPIDController that allows for trapezoidal profile
 * constraints to be tuned in real-time via NetworkTables.
 *
 * <p>
 * The following values are published to NetworkTables for tuning and
 * monitoring:
 * <ul>
 * <li>"p": Proportional gain of the PID controller.</li>
 * <li>"i": Integral gain of the PID controller.</li>
 * <li>"d": Derivative gain of the PID controller.</li>
 * <li>"goal": The target position of the motion profile.</li>
 * <li>"maxAcc": Maximum acceleration constraint of the motion profile.</li>
 * <li>"maxVel": Maximum velocity constraint of the motion profile .</li>
 * <li>"[RO] setpoint pos": Read-only property for the current setpoint
 * position.</li>
 * <li>"[RO] setpoint vel": Read-only property for the current setpoint
 * velocity.</li>
 * <li>"[RO] in": Read-only property for the current input value.</li>
 * <li>"[RO] out": Read-only property for the current output value.</li>
 * </ul>
 */
public class TuneableProfiledPIDController extends ProfiledPIDController {
	private Constraints consts;
	private double in, out;

	/**
	 * Allocates a TuneableProfiledPIDController with the given constants for Kp,
	 * Ki, and Kd.
	 *
	 * @param Kp
	 *            The proportional coefficient.
	 * @param Ki
	 *            The integral coefficient.
	 * @param Kd
	 *            The derivative coefficient.
	 * @param constraints
	 *            Velocity and acceleration constraints for goal.
	 * @param period
	 *            The period between controller updates in seconds. The default is
	 *            0.02 seconds.
	 */
	public TuneableProfiledPIDController(double Kp, double Ki, double Kd, Constraints constraints, double period) {
		super(Kp, Ki, Kd, constraints, period);
		this.consts = constraints;
	}

	/**
	 * Allocates a TuneableProfiledPIDController with the given constants for Kp,
	 * Ki, and Kd.
	 *
	 * @param Kp
	 *            The proportional coefficient.
	 * @param Ki
	 *            The integral coefficient.
	 * @param Kd
	 *            The derivative coefficient.
	 * @param constraints
	 *            Velocity and acceleration constraints for goal.
	 */
	public TuneableProfiledPIDController(double Kp, double Ki, double Kd, Constraints constraints) {
		super(Kp, Ki, Kd, constraints);
		this.consts = constraints;
	}

	@Override
	public void setConstraints(Constraints constraints) {
		super.setConstraints(constraints);
		this.consts = constraints;
	}

	@Override
	public double calculate(double measurement) {
		this.in = measurement;
		double tmp = super.calculate(measurement);
		this.out = tmp;
		return tmp;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("ProfiledPIDController");
		builder.addDoubleProperty("p", this::getP, this::setP);
		builder.addDoubleProperty("i", this::getI, this::setI);
		builder.addDoubleProperty("d", this::getD, this::setD);
		builder.addDoubleProperty("goal", () -> getGoal().position, this::setGoal);
		builder.addDoubleProperty("maxAcc", () -> this.consts.maxAcceleration, (double val) -> {
			setConstraints(new Constraints(this.consts.maxVelocity, val));
		});
		builder.addDoubleProperty("maxVel", () -> this.consts.maxVelocity, (double val) -> {
			setConstraints(new Constraints(val, this.consts.maxAcceleration));
		});
		builder.addDoubleProperty("[RO] setpoint pos", () -> this.getSetpoint().position, null);
		builder.addDoubleProperty("[RO] setpoint vel", () -> this.getSetpoint().velocity, null);
		builder.addDoubleProperty("[RO] in", () -> this.in, null);
		builder.addDoubleProperty("[RO] out", () -> out, null);
	}
}
