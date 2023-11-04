package org.jmhsrobotics.warcore.math;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Implements a PID control loop whose setpoint is constrained by a trapezoid
 * profile. Users should call reset() when they first start running the
 * controller to avoid unwanted behavior.
 */
public class TuneableProfiledPIDController extends ProfiledPIDController {
	private Constraints consts;
	private double in, out;

	public TuneableProfiledPIDController(double Kp, double Ki, double Kd, Constraints constraints, double period) {
		super(Kp, Ki, Kd, constraints, period);
		this.consts = constraints;
	}

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
