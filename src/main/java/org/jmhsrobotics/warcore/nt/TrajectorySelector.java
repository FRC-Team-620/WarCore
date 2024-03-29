package org.jmhsrobotics.warcore.nt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.util.function.BooleanConsumer;
import edu.wpi.first.util.function.FloatConsumer;
import edu.wpi.first.util.function.FloatSupplier;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrajectorySelector extends SendableChooser<Trajectory> {
	private Field2d field;

	/**
	 * Class to automatically load and display Trajectory information. This class
	 * will automatically scan a selected dir (shallow scan) and attempt to load
	 * every file. The loaded trajectories will be displayed in a sendable chooser
	 * on smartdashboard. Field2ds can also be added so that displayed trajectories
	 * automatically update with changes.
	 *
	 * @param directory
	 *            Directory to load Trajectory json files from
	 */
	public TrajectorySelector(Path directory) {
		this(directory, false);

	}

	public TrajectorySelector(Path directory, boolean defaultFirst) {
		super();
		this.loadTrajectories(directory, defaultFirst);
		SmartDashboard.putData(this);// Not sure if I should automatically add this to the dashboard or not.
	}

	/**
	 * Loads trajectory files from a directory.
	 *
	 * @param directory
	 *            Directory to load Trajectory json files from
	 * @param defaultFirst
	 *            Wether or not to set the first found file as the default value.
	 *            See getSelection()
	 */
	public void loadTrajectories(Path directory, boolean defaultFirst) {
		var files = directory.toFile().listFiles();// May want to filter files that only end with wpilib.json
		for (File file : files) {
			if (!file.isFile()) {
				continue;
			}
			String name = file.getName();
			Trajectory traj;
			try {
				traj = TrajectoryUtil.fromPathweaverJson(file.toPath());
				if (defaultFirst) {
					this.setDefaultOption("* " + name, traj);
					defaultFirst = false;
				} else {
					this.addOption("* " + name, traj);// Add star to paths imported from disk scan
				}
			} catch (IOException e) {
				e.printStackTrace();
				DriverStation.reportError("Unable to open trajectory: " + file.getPath(), e.getStackTrace());
			}

		}

	}

	/**
	 * Called when a selection on smardashboard is made.
	 *
	 * @param data
	 *            string of selected option
	 */
	private void onSelection(String data) {
		if (this.field != null) {
			this.field.getObject("traj").setTrajectory(this.getSelected());
		}
	}

	/**
	 * Links a field2d to this selector. This causes the Field to automaticly update
	 * its displayed trajectory to match the current selection of this widget.
	 *
	 * @param field
	 *            field2d to link.
	 */
	public void linkField(Field2d field) { // TODO: rename
		this.field = field;
		onSelection("");
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		// Ignore this this is a supper janky way to detect when the selection is
		// changed. I should switch this to use Network tables EntryListener.
		// All this nonesne was to avoid needing to use refelction.
		Jank buildProxy = new Jank(builder, "selected", this::onSelection);
		super.initSendable(buildProxy);
	}

	private class Jank implements SendableBuilder {
		SendableBuilder builder;
		String interceptKey;
		Consumer<String> intercept, callback;

		public Jank(SendableBuilder builder, String interceptKey, Consumer<String> callback) {
			this.callback = callback;
			this.builder = builder;
			this.interceptKey = interceptKey;
		}

		public void proxy(String indata) {
			this.intercept.accept(indata);
			this.callback.accept(indata);

		}

		@Override
		public void addBooleanProperty(String key, BooleanSupplier getter, BooleanConsumer setter) {
			this.builder.addBooleanProperty(key, getter, setter);

		}

		@Override
		public void addBooleanArrayProperty(String key, Supplier<boolean[]> getter, Consumer<boolean[]> setter) {
			this.builder.addBooleanArrayProperty(key, getter, setter);

		}

		@Override
		public void addStringArrayProperty(String key, Supplier<String[]> getter, Consumer<String[]> setter) {
			this.builder.addStringArrayProperty(key, getter, setter);

		}

		@Override
		public void setSmartDashboardType(String type) {
			this.builder.setSmartDashboardType(type);
		}

		@Override
		public void update() {
			this.builder.update();
		}

		@Override
		public void addStringProperty(String key, Supplier<String> getter, Consumer<String> setter) {
			if (this.interceptKey.equals(key)) {
				this.intercept = setter;
				this.builder.addStringProperty(key, getter, this::proxy);
			} else {
				this.builder.addStringProperty(key, getter, setter);
			}

		}

		@Override
		public void setActuator(boolean value) {
			this.builder.setActuator(value);
		}

		@Override
		public void clearProperties() {
			this.clearProperties();
		}

		@Override
		public void addDoubleArrayProperty(String key, Supplier<double[]> getter, Consumer<double[]> setter) {
			this.builder.addDoubleArrayProperty(key, getter, setter);
		}

		@Override
		public void addDoubleProperty(String key, DoubleSupplier getter, DoubleConsumer setter) {
			this.builder.addDoubleProperty(key, getter, setter);
		}

		@Override
		public boolean isPublished() {
			return this.builder.isPublished();
		}

		@Override
		public void setSafeState(Runnable func) {
			this.builder.setSafeState(func);
		}

		@Override
		public void addIntegerProperty(String key, LongSupplier getter, LongConsumer setter) {
			this.builder.addIntegerProperty(key, getter, setter);

		}

		@Override
		public void addFloatProperty(String key, FloatSupplier getter, FloatConsumer setter) {
			this.builder.addFloatProperty(key, getter, setter);
		}

		@Override
		public void addIntegerArrayProperty(String key, Supplier<long[]> getter, Consumer<long[]> setter) {
			this.builder.addIntegerArrayProperty(key, getter, setter);
		}

		@Override
		public void addFloatArrayProperty(String key, Supplier<float[]> getter, Consumer<float[]> setter) {
			this.builder.addFloatArrayProperty(key, getter, setter);
		}

		@Override
		public void addRawProperty(String key, String typeString, Supplier<byte[]> getter, Consumer<byte[]> setter) {
			this.builder.addRawProperty(key, typeString, getter, setter);
		}

		@Override
		public void addCloseable(AutoCloseable closeable) {
			this.builder.addCloseable(closeable);
		}

		@Override
		public void close() throws Exception {
			this.builder.close();
		}

		@Override
		public void publishConstBoolean(String key, boolean value) {
			this.builder.publishConstBoolean(key, value);
		}

		@Override
		public void publishConstInteger(String key, long value) {
			this.builder.publishConstInteger(key, value);
		}

		@Override
		public void publishConstFloat(String key, float value) {
			this.builder.publishConstFloat(key, value);
		}

		@Override
		public void publishConstDouble(String key, double value) {
			this.builder.publishConstDouble(key, value);
		}

		@Override
		public void publishConstString(String key, String value) {
			this.builder.publishConstString(key, value);
		}

		@Override
		public void publishConstBooleanArray(String key, boolean[] value) {
			this.builder.publishConstBooleanArray(key, value);
		}

		@Override
		public void publishConstIntegerArray(String key, long[] value) {
			this.builder.publishConstIntegerArray(key, value);
		}

		@Override
		public void publishConstFloatArray(String key, float[] value) {
			this.builder.publishConstFloatArray(key, value);
		}

		@Override
		public void publishConstDoubleArray(String key, double[] value) {
			this.builder.publishConstDoubleArray(key, value);
		}

		@Override
		public void publishConstStringArray(String key, String[] value) {
			this.builder.publishConstStringArray(key, value);
		}

		@Override
		public void publishConstRaw(String key, String typeString, byte[] value) {
			this.builder.publishConstRaw(key, typeString, value);
		}

		@Override
		public BackendKind getBackendKind() {
			return this.builder.getBackendKind();
		}

	}
}
