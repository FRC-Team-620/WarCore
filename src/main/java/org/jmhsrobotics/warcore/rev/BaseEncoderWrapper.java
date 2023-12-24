package org.jmhsrobotics.warcore.rev;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class BaseEncoderWrapper implements IEncoderWrapper {
	private final DoubleSupplier mPositionGetter;
	private final DoubleConsumer mPositionSetter;
	private final DoubleConsumer mVelocitySetter;

	protected BaseEncoderWrapper(DoubleSupplier positionGetter, DoubleConsumer positionSetter,
			DoubleConsumer velocitySetter) {
		mPositionGetter = positionGetter;
		mPositionSetter = positionSetter;
		mVelocitySetter = velocitySetter;
	}

	/**
	 * Sets the distance associated with the encoder data source.
	 *
	 * @param distance
	 *            The distance value to set.
	 */
	@Override
	public void setDistance(double distance) {
		mPositionSetter.accept(distance);
	}

	/**
	 * Sets the velocity associated with the encoder data source.
	 *
	 * @param velocity
	 *            The velocity value to set.
	 */
	@Override
	public void setVelocity(double velocity) {
		mVelocitySetter.accept(velocity);
	}

	/**
	 * Gets the current position from the encoder data source.
	 *
	 * @return The current position.
	 */
	@Override
	public double getPosition() {
		return mPositionGetter.getAsDouble();
	}
}
