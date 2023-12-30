package org.jmhsrobotics.warcore.nt;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The {@link NT4Util} helper class provides helper methods to put non-standard
 * datatypes into networktables via SmartDashboard.
 *
 */
public class NT4Util {

	/**
	 * Put a Pose3d array into Network Tables
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
	 * Put a Pose2d array into Network Tables
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
	 * Put a Trajectory into Network Tables
	 *
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
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

	/**
	 * Get Pose2d array from Network Tables
	 *
	 * @param key
	 *            the key to be retrieved
	 * @return Pose2d array
	 */
	public static Pose2d[] getPose2ds(String key) {
		double[] data = SmartDashboard.getNumberArray(key, new double[0]);

		if (data.length % 3 != 0) {
			throw new IllegalArgumentException("Invalid Data format");
		}

		int numPoses = data.length / 3;
		Pose2d[] poses = new Pose2d[numPoses];

		int ndx = 0;
		for (int i = 0; i < numPoses; i++) {
			double x = data[ndx];
			double y = data[ndx + 1];
			double rotationDegrees = data[ndx + 2];
			poses[i] = new Pose2d(x, y, Rotation2d.fromDegrees(rotationDegrees));
			ndx += 3;
		}

		return poses;
	}

	/**
	 * Get Pose3d array from Network Tables
	 *
	 * @param key
	 *            the key to be retrieved
	 * @return Pose3d array
	 */
	public static Pose3d[] getPose3ds(String key) {
		double[] data = SmartDashboard.getNumberArray(key, new double[0]);

		if (data.length % 7 != 0) {
			throw new IllegalArgumentException("Invalid Data format");
		}

		int numPoses = data.length / 7;
		Pose3d[] poses = new Pose3d[numPoses];

		int ndx = 0;
		for (int i = 0; i < numPoses; i++) {
			double x = data[ndx];
			double y = data[ndx + 1];
			double z = data[ndx + 2];
			double qw = data[ndx + 3];
			double qx = data[ndx + 4];
			double qy = data[ndx + 5];
			double qz = data[ndx + 6];
			poses[i] = new Pose3d(x, y, z, new Rotation3d(new Quaternion(qw, qx, qy, qz)));
			ndx += 7;
		}

		return poses;
	}
}
