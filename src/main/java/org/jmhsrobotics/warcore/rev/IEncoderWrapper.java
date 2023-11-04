package org.jmhsrobotics.warcore.rev;

public interface IEncoderWrapper {
	void setDistance(double distance);

	void setVelocity(double velocity);

	double getPosition();
}
