package org.jmhsrobotics.warcore.util;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.jmhsrobotics.warcore.util.NetworkUtil.MACAddress;

/**
 * The `RobotIdentifier` class provides a mechanism for identifying a roboRIO
 * (Robot Controller) by its MAC Address. It allows you to associate a MAC
 * Address with a specific value (of type V) and provides a default value if a
 * match is not found.
 *
 * @param <V>
 *            The type of value associated with the MAC Address.
 */
public class RobotIdentifier<V> {

	private Map<MACAddress, V> robotMap;
	private V defaultVal = null;

	/**
	 * Constructs a new RobotIdentifier instance. Initializes an internal mapping of
	 * MAC Addresses to values.
	 */
	public RobotIdentifier() {
		robotMap = new HashMap<MACAddress, V>();
	}

	/**
	 * Associates a specific MAC Address with a value. If no default is set, it is
	 * assumed the first addition is the default value.
	 *
	 * @param mac
	 *            The MAC Address of the roboRIO.
	 * @param value
	 *            The value associated with the MAC Address.
	 */
	public void addRobot(MACAddress mac, V value) {
		if (defaultVal == null) {
			this.defaultVal = value;
		}
		this.robotMap.put(mac, value);
	}

	/**
	 * Sets the default value to be returned when no matching MAC Address is found
	 * during identification.
	 *
	 * @param value
	 *            The default value.
	 */
	public void addDefaultRobot(V value) {
		this.defaultVal = value;

	}

	/**
	 * Identifies a roboRIO by its MAC Address.
	 *
	 * @return The value associated with the identified MAC Address, or the default
	 *         value if not found.
	 */
	public V identify() {

		try {
			MACAddress mac = NetworkUtil.getMacAddress();
			return this.robotMap.getOrDefault(mac, defaultVal);
		} catch (SocketException e) {
			System.out.println("Warning: Unable to obtain Mac address. Using Default");
			return this.defaultVal;
		}

	}

}
