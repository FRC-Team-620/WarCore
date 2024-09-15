package org.jmhsrobotics.warcore.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Utility class for logging Git status information to either NetworkTables or a
 * DataLog. This class is designed to work with a pre-build step that generates
 * a BuildConstants Java class. It leverages the gversion plugin to generate
 * version information. The generated BuildConstants class should be passed to
 * the functions provided by this class. Ensure that the generated
 * BuildConstants class is ignored in the version control system (e.g., Git).
 * <p>
 * To configure Gradle for the gversion plugin, add the following configuration
 * in your build.gradle file:
 *
 * <pre>
 * {@code
 *plugins {
 *     // Add to your existing plugins
 *     id "com.peterabeles.gversion" version "1.10"
 * }
 * }
 * </pre>
 *
 * <pre>
 * {@code
 * gversion {
 *     srcDir       = "src/main/java/"
 *     classPackage = "org.jmhsrobotics.frc2023"
 *     className    = "BuildConstants"
 *     dateFormat   = "yyyy-MM-dd HH:mm:ss z"
 *     timeZone     = "America/New_York"
 *     indent       = "  "
 * }
 * }
 * </pre>
 *
 * Note that the class package may vary based on your project structure.
 * <p>
 * Example of how to utilize this class:
 *
 * <pre>
 * {
 * 	&#64;code
 * 	// Assuming you have a pre-build step that generates BuildConstants class
 * 	// Import necessary classes and set up DataLog
 * 	DataLog dataLog = DataLogManager.getLog();
 * 	// Log build constants to DataLog
 * 	BuildDataLogger.LogToWpiLib(dataLog, BuildConstants.class);
 *
 * }
 * </pre>
 *
 */
public class BuildDataLogger {
	/**
	 * Logs the Git status information to a DataLog.
	 *
	 * @param log
	 *            The DataLog to which the information will be logged.
	 * @param buildConstants
	 *            The BuildConstants class containing Git status information.
	 *            (Generated from gversion gradle plugin)
	 */
	public static void LogToWpiLib(DataLog log, Class<?> buildConstants) {
		try {
			String entry = "BuildInfo/";
			for (Field field : buildConstants.getFields()) {
				String name = field.getName();
				Type t = field.getType();
				if (t.equals(int.class) || t.equals(long.class) || t.equals(double.class)) {
					// System.out.println(name + " " + field.get(null));
					var tmp = new DoubleLogEntry(log, entry + name);
					tmp.append(field.getDouble(null));
					tmp.finish();
				} else {
					// System.out.println(name + " " + field.get(null));
					var tmp = new StringLogEntry(log, entry + name);

					if (field.get(null) != null) {
						tmp.append(field.get(null).toString());
					} else {
						tmp.append(null);
					}
					tmp.finish();

				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Logs the Git status information to NetworkTables (SmartDashboard).
	 *
	 * @param buildConstants
	 *            The BuildConstants class containing Git status information.
	 *            (Generated from gversion gradle plugin)
	 */
	public static void LogToNetworkTables(Class<?> buildConstants) {
		try {
			String entry = "BuildInfo/";
			for (Field field : buildConstants.getFields()) {
				String name = field.getName();
				Type t = field.getType();
				if (t.equals(int.class) || t.equals(long.class) || t.equals(double.class)) {
					// System.out.println(name + " " + field.get(null));
					SmartDashboard.putNumber(entry + name, field.getDouble(null));
				} else {
					// System.out.println(name + " " + field.get(null));
					if (field.get(null) != null) {
						SmartDashboard.putString(entry + name, field.get(null).toString());
					} else {
						SmartDashboard.putString(entry + name, null);
					}

				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
