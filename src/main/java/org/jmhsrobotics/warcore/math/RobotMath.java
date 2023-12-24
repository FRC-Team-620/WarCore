package org.jmhsrobotics.warcore.math;

import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import edu.wpi.first.math.MathUtil;

public class RobotMath {
	public static final double DEFAULT_THRESHOLD = 0.005;

	/**
	 * For systems where angles are given in the domain [-180, 180]: Returns the
	 * angle of some target angle relative to some pivot angle That is, it returns
	 * the angle of the target angle as though the pivot angle were at 0 degrees.
	 * This accounts for the restriction that angles must be in this specific
	 * domain.
	 *
	 * @param pivotAngle
	 *            The angle that the target angle is relative to
	 * @param targetAngle
	 *            The angle whose relative angle to the pivot angle will be returned
	 * @return The relative angle to the target angle (i.e. if the pivot angle were
	 *         at 0) in the domain [-180, 180]
	 */
	public static double relativeAngle(double pivotAngle, double targetAngle) {
		double diff = targetAngle - pivotAngle;
		if (Math.abs(diff) <= 180.0)
			return diff;
		return diff - Math.signum(diff) * 360.0;
	}

	/**
	 * For systems where angles are given in the domain [-180, 180]: Returns the
	 * resultant angle from adding some delta angle to a pivot angle, accounting for
	 * the restriction that angles must be in this specific domain.
	 *
	 * @param pivotAngle
	 *            The angle that the deltaAngle is being added to (shifted)
	 * @param deltaAngle
	 *            The angle being added to pivot angle (the shift)
	 * @return The resultant angle of the shift in the domain [-180, 180]
	 */
	public static double shiftAngle(double pivotAngle, double deltaAngle) {
		return RobotMath.relativeAngle(-pivotAngle, deltaAngle);
	}

	/**
	 * Checking whether a number appoximates zero based on the threshholds set in
	 * Constants.RobotMathConstants
	 *
	 * @param value
	 *            The value to be checked
	 * @return True if the value approximates zero, false otherwise
	 */
	public static boolean approximatelyZero(double value) {
		return RobotMath.approxZero(value, DEFAULT_THRESHOLD);
	}

	/**
	 * Checking whether a number appoximates zero based on the threshhold given.
	 *
	 * @param value
	 *            The value to be checked
	 * @param threshhold
	 *            Determines how close the value needs to be to zero to pass
	 * @return True if the value approximates zero, false otherwise
	 */
	public static boolean approxZero(double value, double threshhold) {
		return value > -threshhold && value < threshhold;
	}

	/**
	 * Takes any angle in degrees in the domain (-infinity, infinitiy) and returns
	 * its value relative to 0 in the domain [-180, 180].
	 *
	 * @param angle
	 *            The absolute angle in the domain (-infinity, infinity)
	 * @return The angle's value relative to 0 in the domain [-180, 180]
	 */
	public static double constrain180(double angle) {
		return MathUtil.inputModulus(angle, -180, 180);
	}

	/**
	 * Takes an array of objects of generic type T and a function which converts
	 * such objects into a double value, and returns the sum of doing this
	 * conversion for each element in the array.
	 *
	 * @param <T>
	 *            The type being iterated over
	 * @param values
	 *            The values being iterated over and being given a corresponding
	 *            double value
	 * @param doubleConversion
	 *            The function that converts an object of type T into a double
	 * @return The sum of the result of applying 'doubleConversion' to all T objects
	 *         in 'values'
	 */
	public static <T> double sumForEach(T[] values, ToDoubleFunction<T> doubleConversion) {
		double sum = 0.0;
		for (T t : values)
			sum += doubleConversion.applyAsDouble(t);
		return sum;
	}

	/**
	 * Takes an array of objects of generic type T and a function which converts
	 * such objects into an int value, and returns the sum of doing this conversion
	 * for each element in the array.
	 *
	 * @param <T>
	 *            The type being iterated over
	 * @param values
	 *            The values being iterated over and being given a corresponding int
	 *            value
	 * @param intConversion
	 *            The function that converts an object of type T into an int
	 * @return The sum of the result of applying 'intConversion' to all T objects in
	 *         'values'
	 */
	public static <T> int sumForEach(T[] values, ToIntFunction<T> intConversion) {
		int sum = 0;
		for (T t : values)
			sum += intConversion.applyAsInt(t);
		return sum;
	}
}
