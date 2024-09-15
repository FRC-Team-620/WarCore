package org.jmhsrobotics.warcore.nt;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The LimeLight class provides an interface for interacting with a LimeLight
 * vision camera through NetworkTables. It allows you to control LED modes,
 * access target data, and check for target presence.
 */
public class LimeLight {
	private static NetworkTable limelightTable;
	private static NetworkTableEntry txEntry; // Horizontal offset to target
	private static NetworkTableEntry tyEntry; // Vertical offset to target
	private static NetworkTableEntry tvEntry; // Whether a target is found
	private static NetworkTableEntry ledModeEntry; // The state of the front-facing lights
	private static NetworkTableEntry streamEntry; // The display mode of the camera

	/**
	 * Enum representing the available LED modes for the LimeLight camera.
	 */
	// TODO: Include Support for Flashing
	public enum LedMode {
		OFF(1),
		ON(3);

		private final int value;

		private LedMode(int value) {
			this.value = value;
		}

		/**
		 * Get the integer value associated with the LED mode.
		 *
		 * @return The integer value of the LED mode.
		 */
		public int get() {
			return this.value;
		}

		// A map of the enum's integer values to the enum itself
		private static Map<Integer, LedMode> reverseLookup = Arrays.stream(LedMode.values())
				.collect(Collectors.toMap(LedMode::get, Function.identity()));

		/**
		 * Convert an integer to a LedMode enum value.
		 *
		 * @param number
		 *            The integer value to convert.
		 * @return The corresponding LedMode enum value.
		 */
		public static LedMode fromInt(int number) {
			return reverseLookup.getOrDefault(number, OFF);
		}
	}

	/**
	 * Initialize the LimeLight instance by setting up the required NetworkTables
	 * entries.
	 */
	// TODO: Add support for selecting what limelight table. Maybe a way to search
	// for detected limelights? Move away from static approch?
	public static void init() {
		limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
		txEntry = limelightTable.getEntry("tx");
		tyEntry = limelightTable.getEntry("ty");
		tvEntry = limelightTable.getEntry("tv");
		ledModeEntry = limelightTable.getEntry("ledMode");
		streamEntry = limelightTable.getEntry("stream");
		// streamEntry.setDouble(2); // Puts the LimeLight in secondary
		// picture-in-picture mode
		setLedMode(LedMode.OFF); // Ensure that the LimeLight starts in off mode
	}

	/**
	 * Get the current LED mode of the LimeLight camera.
	 *
	 * @return The current LED mode.
	 */
	public static LedMode getLedMode() {
		return LedMode.fromInt(ledModeEntry.getNumber(0).intValue());
	}

	/**
	 * Set the LED mode of the LimeLight camera.
	 *
	 * @param ledMode
	 *            The LED mode to set.
	 */
	public static void setLedMode(LedMode ledMode) {
		ledModeEntry.setNumber(ledMode.get());
	}

	/**
	 * Get the horizontal offset to the target from the LimeLight camera.
	 *
	 * @return The horizontal offset in degrees.
	 */
	public static double getTX() {
		return txEntry.getDouble(0);
	}

	/**
	 * Get the vertical offset to the target from the LimeLight camera.
	 *
	 * @return The vertical offset in degrees.
	 */
	public static double getTY() {
		return tyEntry.getDouble(0);
	}

	/**
	 * Check if a target is present in the LimeLight camera's field of view.
	 *
	 * @return `true` if a target is present, `false` otherwise.
	 */
	public static boolean hasTarget() {
		return tvEntry.getDouble(0) == 1;
	}
}
