package org.jmhsrobotics.warcore.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BuildDataLoggerTest {
	// @Test
	// void testDataLog() { //TODO: Fix test
	// // String filename = "buildDataTest.wpilog";
	// // DataLog d = DataLogManager.getLog();
	// // d.setFilename(filename);
	// // BuildDataLogger.LogToWpiLib(d, BuildConstants.class);
	// // d.flush();
	// // d.close();

	// }
    //TODO: Fix need to start up a NT server for this to work
	// @Test
	// void testNTLog() {
	// 	BuildDataLogger.LogToNetworkTables(BuildConstants.class);
	// 	String TABLE_NAME = "BuildInfo";
	// 	assertEquals(BuildConstants.MAVEN_GROUP, SmartDashboard.getString(TABLE_NAME + "/MAVEN_GROUP", null));
	// 	assertEquals(BuildConstants.MAVEN_NAME, SmartDashboard.getString(TABLE_NAME + "/MAVEN_NAME", null));
	// 	assertEquals(BuildConstants.VERSION, SmartDashboard.getString(TABLE_NAME + "/VERSION", null));
	// 	assertEquals(BuildConstants.GIT_REVISION, SmartDashboard.getNumber(TABLE_NAME + "/GIT_REVISION", 0.0), 0.0);
	// 	assertEquals(BuildConstants.GIT_SHA, SmartDashboard.getString(TABLE_NAME + "/GIT_SHA", null));
	// 	assertEquals(BuildConstants.GIT_DATE, SmartDashboard.getString(TABLE_NAME + "/GIT_DATE", null));
	// 	assertEquals(BuildConstants.GIT_BRANCH, SmartDashboard.getString(TABLE_NAME + "/GIT_BRANCH", null));
	// 	assertEquals(BuildConstants.BUILD_DATE, SmartDashboard.getString(TABLE_NAME + "/BUILD_DATE", null));
	// 	assertEquals(BuildConstants.BUILD_UNIX_TIME, SmartDashboard.getNumber(TABLE_NAME + "/BUILD_UNIX_TIME", 0.0),
	// 			0.0);
	// 	assertEquals(BuildConstants.DIRTY, SmartDashboard.getNumber(TABLE_NAME + "/DIRTY", 0.0), 0.0);
	// }

	public final class BuildConstants {
		public static final String MAVEN_GROUP = "";
		public static final String MAVEN_NAME = "Warbots2023";
		public static final String VERSION = "unspecified";
		public static final int GIT_REVISION = 435;
		public static final String GIT_SHA = "69ed0b0a4c25786f423e6690970ecf77b8e0b67a";
		public static final String GIT_DATE = "2023-10-19 21:17:09 EDT";
		public static final String GIT_BRANCH = "fixedAutoSelector";
		public static final String BUILD_DATE = "2023-11-07 16:43:28 EST";
		public static final long BUILD_UNIX_TIME = 1699393408030L;
		public static final int DIRTY = 1;

		private BuildConstants() {
		}
	}

}
