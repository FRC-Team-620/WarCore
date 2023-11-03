package org.jmhsrobotics.warcore.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Identifies a roboRIO by its MAC Address
 */
public class DetectRobot {

    public enum RobotType {
		BABY_BOT, SUSAN, BOT_2020, UNKNOWN
	}
	private static final String networkInterface = "eth0";
	// Java bytes are signed so standard hex notation won't work
	private static final Map<MACAddress, RobotType> robotMACs = Map.of(
			new MACAddress(new byte[]{0, -128, 47, 39, 32, -120}), RobotType.SUSAN,
			new MACAddress(new byte[]{0, -128, 47, 40, -36, -62}), RobotType.BABY_BOT,
			new MACAddress(new byte[]{0, -128, 47, 25, 122, 6}), RobotType.BOT_2020);

	public static RobotType identifyRobot() {
		try {
			MACAddress macAddress = new MACAddress(NetworkInterface.getByName(networkInterface).getHardwareAddress());
			DataLogManager.log("Mac Address is: " + macAddress);
			RobotType robot = robotMACs.get(macAddress);
			if (robot == null) {
				DataLogManager.log("Could not identify MAC '" + Arrays.toString(macAddress.getAddress()));
				return RobotType.UNKNOWN;
			} else {
				System.out.println("Identified MAC address as " + robot);
			}

			switch (robot) {
				case SUSAN :
					SmartDashboard.putString("Detect/Robot", "Susan");
					break;
				case BABY_BOT :
					SmartDashboard.putString("Detect/Robot", "Baby Bot");
					break;
				case BOT_2020 :
					SmartDashboard.putString("Detect/Robot", "Bot 2020");
					break;
				default :
					SmartDashboard.putString("Detect/Robot", "Unknown");
			}

			return robot;
		} catch (SocketException | NullPointerException err) {
			// err.printStackTrace();
			DataLogManager.log("Failed to read MAC, using default robot instead.");
			// new Alert("Failed to read MAC, using default robot instead.",
			// AlertType.WARNING).set(true);
			return RobotType.UNKNOWN;
		}
	}

	// This class can be used in maps, unlike a raw byte[]
	/**
	 * A class that stores a MAC address that can be used in data structures. It
	 * implements equals and hashCode.
	 */
	private static final class MACAddress {
		private final byte[] address;

		public MACAddress(byte[] address) {
			if (address == null) {
				throw new NullPointerException();
			}
			this.address = address;
		}

		public byte[] getAddress() {
			return address;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof MACAddress)) {
				return false;
			}
			return Arrays.equals(address, ((MACAddress) other).address);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(address);
		}
	}
}
