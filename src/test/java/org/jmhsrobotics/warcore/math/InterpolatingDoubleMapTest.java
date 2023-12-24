package org.jmhsrobotics.warcore.math;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterpolatingDoubleMapTest {

	private InterpolatingDoubleMap map;

	@BeforeEach
	public void cleanUp() {
		map = new InterpolatingDoubleMap();
	}

	@Test
	public void checkEmpty() {
		assertTrue(map.isEmpty());
	}

	@Test
	public void checkNotEmpty() {
		map.put(0.2, 0.2);
		assertFalse(map.isEmpty());
	}

	@Test
	public void checkIsKeyInBoundsEmpty() {
		assertAll(() -> assertFalse(map.isKeyInBounds(0.5)), () -> assertFalse(map.isKeyInBounds(-10.2)),
				() -> assertFalse(map.isKeyInBounds(12.2)), () -> assertFalse(map.isKeyInBounds(null)));
	}

	@Test
	public void checkIsKeyInBoundsSingle() {
		map.put(2.0, 3.0);
		assertAll(() -> assertFalse(map.isKeyInBounds(0.5)), () -> assertFalse(map.isKeyInBounds(-10.2)),
				() -> assertFalse(map.isKeyInBounds(12.2)), () -> assertFalse(map.isKeyInBounds(null)));
	}

	@Test
	public void checkIsKeyInBoundsfull() {
		map.put(2.0, 3.0);
		map.put(-2.0, 2.0);
		map.put(40.2, -5.0);
		assertAll(() -> assertTrue(map.isKeyInBounds(0.5)), () -> assertFalse(map.isKeyInBounds(-10.2)),
				() -> assertTrue(map.isKeyInBounds(12.2)), () -> assertFalse(map.isKeyInBounds(null)),
				() -> assertFalse(map.isKeyInBounds(100.2)), () -> assertTrue(map.isKeyInBounds(-2.0)),
				() -> assertTrue(map.isKeyInBounds(40.2)));
	}

	@Test
	public void checkGetInterpolatedEmpty() {

		assertAll(() -> assertNull(map.getInterpolated(null)), () -> assertNull(map.getInterpolated(5.0)),
				() -> assertEquals(map.getInterpolated(1.0, 5.0), 5.0),
				() -> assertEquals(map.getInterpolated(1.0, null), null));
	}

	@Test
	public void checkGetInterpolatedData() {
		map.put(-20.0, 100.0);
		map.put(10.0, -100.0);
		map.put(0.0, 50.0);
		assertAll(() -> assertNull(map.getInterpolated(null)), () -> assertNull(map.getInterpolated(-21.0)),
				() -> assertNull(map.getInterpolated(11.0)), () -> assertEquals(map.getInterpolated(-20.0), 100.0),
				() -> assertEquals(map.getInterpolated(0.0), 50.0),
				() -> assertEquals(map.getInterpolated(10.0), -100.0),
				() -> assertEquals(map.getInterpolated(5.0), -25.0),
				() -> assertEquals(map.getInterpolated(-15.0), 87.5));
	}

}
