package org.jmhsrobotics.warcore.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jmhsrobotics.warcore.util.NetworkUtil.MACAddress;
import org.junit.jupiter.api.Test;

public class NetworkUtilTest {
	@Test
	void testEquals() {

		MACAddress same1 = new MACAddress(new byte[]{3, -128, 47, 32, 12, -120});
		MACAddress same2 = new MACAddress(new byte[]{3, -128, 47, 32, 12, -120});
		MACAddress diff = new MACAddress(new byte[]{5, -122, 41, 35, 15, -110});
		assertTrue(same1.equals(same2));
		assertTrue(same2.equals(same1));
		assertFalse(diff.equals(same1));
		assertFalse(same1.equals(diff));
		assertFalse(diff.equals(same2));
		assertTrue(diff.equals(diff));
		assertFalse(same1.equals("anything"));
		same1.hashCode();
		new NetworkUtil();
	}
	@Test
	void testToString() {
		MACAddress same1 = new MACAddress(new byte[]{3, -128, 47, 32, 12, -120});
		assertEquals("03:80:2F:20:0C:88", same1.toString());
	}

	@Test
	void testStringParsing() {
		MACAddress same1 = new MACAddress(new byte[]{3, -128, 47, 32, 12, -120});
		assertEquals(same1, new MACAddress("03:80:2F:20:0C:88"));
		assertEquals(same1, new MACAddress("0380:2F20:0C88"));
		assertEquals(same1, new MACAddress("03802F200C88"));
		assertEquals(same1, new MACAddress("03802f200c88"));
	}

	@Test
	void testExceptions() {
		assertThrows(NullPointerException.class, () -> {
			new MACAddress((byte[]) null);
			// Code that is expected to throw an exception
		});
		assertThrows(NullPointerException.class, () -> {
			new MACAddress((String) null);
			// Code that is expected to throw an exception
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MACAddress("NOTMAC");
			// Code that is expected to throw an exception
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MACAddress("03:80:2F:20:0C:88:11");
			// Code that is expected to throw an exception
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MACAddress("03:80:2F:20:0C:88:1");
			// Code that is expected to throw an exception
		});
	}

	@Test
	void testBasicMacObtain() {
		try {
			// NetworkUtil.getMacAddress();
			assertNotNull(NetworkUtil.getMacAddress());
		} catch (SocketException e) {
		}
	}

	// @Test
	// void testShiftAngle() {
	// double result = RobotMath.shiftAngle(30.0, 60.0);
	// assertEquals(90, result, 1e-5);

	// result = RobotMath.shiftAngle(180.0, -180.0);
	// assertEquals(0.0, result, 1e-5);

	// result = RobotMath.shiftAngle(-90.0, 90.0);
	// assertEquals(0.0, result, 1e-5);
	// }

	// @Test
	// void testApproximatelyZero() {
	// boolean result = RobotMath.approximatelyZero(0.0);
	// assertTrue(result);

	// result = RobotMath.approximatelyZero(0.001);
	// assertTrue(result);

	// result = RobotMath.approximatelyZero(0.01);
	// assertFalse(result);
	// }

	// @Test
	// void testConstrain180() {
	// double result = RobotMath.constrain180(225.0);
	// assertEquals(-135.0, result, 1e-5);

	// result = RobotMath.constrain180(-270.0);
	// assertEquals(90.0, result, 1e-5);

	// result = RobotMath.constrain180(180.0);
	// assertEquals(180.0, result, 1e-5);
	// }

	// @Test
	// void testSumForEach() {
	// Integer[] values = {1, 2, 3, 4, 5};
	// double sum = RobotMath.sumForEach(values, Integer::doubleValue);
	// assertEquals(15.0, sum, 1e-5);
	// }
}
