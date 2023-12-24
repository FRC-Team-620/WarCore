package org.jmhsrobotics.warcore.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RobotMathTest {

	@Test
	void testRelativeAngle() {
		double result = RobotMath.relativeAngle(30.0, 60.0);
		assertEquals(30.0, result, 1e-5);

		result = RobotMath.relativeAngle(180.0, -180.0);
		assertEquals(0.0, result, 1e-5);

		result = RobotMath.relativeAngle(-90.0, 90.0);
		assertEquals(180.0, result, 1e-5);
	}

	@Test
	void testShiftAngle() {
		double result = RobotMath.shiftAngle(30.0, 60.0);
		assertEquals(90, result, 1e-5);

		result = RobotMath.shiftAngle(180.0, -180.0);
		assertEquals(0.0, result, 1e-5);

		result = RobotMath.shiftAngle(-90.0, 90.0);
		assertEquals(0.0, result, 1e-5);
	}

	@Test
	void testApproximatelyZero() {
		boolean result = RobotMath.approximatelyZero(0.0);
		assertTrue(result);

		result = RobotMath.approximatelyZero(0.001);
		assertTrue(result);

		result = RobotMath.approximatelyZero(0.01);
		assertFalse(result);
	}

	@Test
	void testConstrain180() {
		double result = RobotMath.constrain180(225.0);
		assertEquals(-135.0, result, 1e-5);

		result = RobotMath.constrain180(-270.0);
		assertEquals(90.0, result, 1e-5);

		result = RobotMath.constrain180(180.0);
		assertEquals(180.0, result, 1e-5);
	}

	@Test
	void testSumForEach() {
		Integer[] values = {1, 2, 3, 4, 5};
		double sum = RobotMath.sumForEach(values, Integer::doubleValue);
		assertEquals(15.0, sum, 1e-5);
	}
}
