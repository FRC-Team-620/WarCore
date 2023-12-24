package org.jmhsrobotics.warcore.nt;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The {@link NT4Util} helper class provides helper methods to put non-standard
 * datatypes into networktables via SmartDashboard.
 *
 */
public class NT4Util {

	/**
	 * Put a Pose3d array in the table.
	 *
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putPose3d(String key, Pose3d... value) {
		double[] data = new double[value.length * 7];
		for (int i = 0; i < value.length; i++) {
			data[i * 7] = value[i].getX();
			data[i * 7 + 1] = value[i].getY();
			data[i * 7 + 2] = value[i].getZ();
			data[i * 7 + 3] = value[i].getRotation().getQuaternion().getW();
			data[i * 7 + 4] = value[i].getRotation().getQuaternion().getX();
			data[i * 7 + 5] = value[i].getRotation().getQuaternion().getY();
			data[i * 7 + 6] = value[i].getRotation().getQuaternion().getZ();
		}
		return SmartDashboard.putNumberArray(key, data);
	}

	/**
	 * Put a Pose2d array in the table
	 *
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putPose2d(String key, Pose2d... value) {
		double[] data = new double[value.length * 3];
		int ndx = 0;
		for (int i = 0; i < value.length; i++) {
			Pose2d pos = value[i];
			data[ndx] = pos.getX();
			data[ndx + 1] = pos.getY();
			data[ndx + 2] = pos.getRotation().getDegrees();
			ndx += 3;
		}
		return SmartDashboard.putNumberArray(key, data);
	}

	/**
	 * Put a Trajectory into a table
	 *
	 * @param key
	 * @param value
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putTrajectory(String key, Trajectory value) {
		var states = value.getStates();
		// Pose2d[] pose2ds = new Pose2d[states.size()];
		double[] data = new double[states.size() * 3];
		int ndx = 0;
		for (int i = 0; i < states.size(); i++) {
			Pose2d pos = states.get(i).poseMeters;
			data[ndx] = pos.getX();
			data[ndx + 1] = pos.getY();
			data[ndx + 2] = pos.getRotation().getDegrees();
			ndx += 3;
		}

		return SmartDashboard.putNumberArray(key, data);
	}
}
