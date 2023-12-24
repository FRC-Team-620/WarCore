package org.jmhsrobotics.warcore.math;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;

/**
 * Utility class for working with Pose2d and related transformations.
 */
public class PoseUtill {

	/**
	 * Rotates a Pose2d around an origin point by a specified angle.
	 *
	 * @param origin
	 *            The origin point for the rotation.
	 * @param startingPos
	 *            The starting position of the Pose2d.
	 * @param rotation
	 *            The angle by which the Pose2d should be rotated.
	 * @return The Pose2d resulting from rotating the startingPose around the origin
	 *         by the specified angle.
	 *
	 * @see <a href=
	 *      "https://documentation.jmhsrobotics.org/en/programming/WpiLib-Geometry-CheatSheet"
	 *      target="_blank">WpiLib Geometry Cheat Sheet</a>
	 */
	public static Pose2d revolvePose2d(Pose2d origin, Pose2d startingPos, Rotation2d rotation) {
		Transform2d originDelta = new Transform2d(origin, startingPos); // Calculate difference between two points
		Translation2d rotatedTranslation = originDelta.getTranslation().rotateBy(rotation)
				.plus(origin.getTranslation());
		Pose2d out = new Pose2d(rotatedTranslation, rotation.plus(originDelta.getRotation())); // Final Rotated Point
		return out;
	}
}
