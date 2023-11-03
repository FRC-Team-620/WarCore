package org.jmhsrobotics.warcore.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

public final class NetworkUtil {

	private static final String networkInterface = "eth0";

	public static MACAddress getMacAddress(String interfaceName) throws SocketException {
		MACAddress macAddress = new MACAddress(NetworkInterface.getByName(interfaceName).getHardwareAddress());
		return macAddress;
	}
	public static MACAddress getMacAddress() throws SocketException {
		return NetworkUtil.getMacAddress(networkInterface);
	}

	public static final class MACAddress {
		private final byte[] address;

		public MACAddress(byte[] address) {
			if (address == null) {
				throw new NullPointerException();
			}
			this.address = address;
		}
		public MACAddress(String address) {
			if (address == null) {
				throw new NullPointerException();
			}
			byte[] out = parseHex(address);
			if (out.length != 6) {
				throw new IllegalArgumentException("Invalid Mac address. Length must be 6 bytes");
			}
			this.address = out;
		}

		public byte[] getAddress() {
			return address;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof MACAddress)) {
				return false;
			}
			return Arrays.equals(address, ((MACAddress) other).getAddress());
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(address);
		}
		private String bytesToHex(byte[] bytes) {
			StringBuilder hexString = new StringBuilder(2 * bytes.length);
			for (byte b : bytes) {
				String hex = Integer.toHexString(0xFF & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString().toUpperCase();
		}
		private static byte[] parseHex(String hexString) {
			hexString = hexString.replaceAll(":", ""); // Remove ':' if present
			int length = hexString.length();

			// Check if the hex string has an odd number of characters
			if (length % 2 != 0) {
				throw new IllegalArgumentException("Hex string must have an even number of characters");
			}

			// Create a byte array to store the parsed bytes
			byte[] byteArray = new byte[length / 2];

			// Parse the hex string into bytes
			for (int i = 0; i < length; i += 2) {
				String byteString = hexString.substring(i, i + 2);
				byteArray[i / 2] = (byte) Integer.parseInt(byteString, 16);
			}

			return byteArray;
		}

		@Override
		public String toString() {
			return bytesToHex(this.address).replaceAll("(.)(?=(.{2})+$)", "$1:");
		}
	}

}
