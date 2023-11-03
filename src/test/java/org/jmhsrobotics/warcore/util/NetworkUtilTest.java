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
		});
		assertThrows(NullPointerException.class, () -> {
			new MACAddress((String) null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MACAddress("NOTMAC");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MACAddress("03:80:2F:20:0C:88:11");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MACAddress("03:80:2F:20:0C:88:1");
		});
	}

	@Test
	void testBasicMacObtain() {
		try {
			assertNotNull(NetworkUtil.getMacAddress());
		} catch (SocketException e) {
		}
	}
}
