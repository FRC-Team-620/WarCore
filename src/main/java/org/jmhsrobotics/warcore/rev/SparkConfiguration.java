package org.jmhsrobotics.warcore.rev;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.jni.CANSparkMaxJNI;

public class SparkConfiguration {
	public String pluginName;
	public String name;
	public List<Parameter> parameters;

	@JsonCreator
	public SparkConfiguration(@JsonProperty("pluginName") String pluginName, @JsonProperty("name") String name,
			@JsonProperty("parameters") List<Parameter> parameters) {
		this.pluginName = pluginName;
		this.name = name;
		this.parameters = parameters;
	}

	public static SparkConfiguration loadFromJson(File configFile) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		SparkConfiguration configuration = objectMapper.readValue(configFile, SparkConfiguration.class);

		return configuration;
	}

	public void apply(CANSparkMaxLowLevel spark) {
		var handle = getHandle(spark);
		for (var param : this.parameters) {
			SparkParameter sparkParam = param.id;
			if (sparkParam.dataType == SparkParameter.Type.bool) {
				CANSparkMaxJNI.c_SparkMax_SetParameterBool(handle, sparkParam.id, param.asBoolean());
			} else if (sparkParam.dataType == SparkParameter.Type.int32) {
				CANSparkMaxJNI.c_SparkMax_SetParameterInt32(handle, sparkParam.id, param.asInt32());
			} else if (sparkParam.dataType == SparkParameter.Type.uint32) {
				CANSparkMaxJNI.c_SparkMax_SetParameterUint32(handle, sparkParam.id, (int) param.asUint32());
			} else if (sparkParam.dataType == SparkParameter.Type.float32) {
				CANSparkMaxJNI.c_SparkMax_SetParameterFloat32(handle, sparkParam.id, param.asFloat32());
			}

		}

	}

	public static void getParams(CANSparkMaxLowLevel spark) { // TODO:
		var handle = getHandle(spark);
		for (var param : SparkParameter.values()) {
			Parameter newParam;
			if (param.dataType == SparkParameter.Type.bool) {
				newParam = new Parameter(param, CANSparkMaxJNI.c_SparkMax_GetParameterBool(handle, param.id) ? 0 : 1);
			} else if (param.dataType == SparkParameter.Type.int32) {
				newParam = new Parameter(param, CANSparkMaxJNI.c_SparkMax_GetParameterInt32(handle, param.id));
			} else if (param.dataType == SparkParameter.Type.uint32) {
				newParam = new Parameter(param, CANSparkMaxJNI.c_SparkMax_GetParameterUint32(handle, param.id));
			} else if (param.dataType == SparkParameter.Type.float32) {
				newParam = new Parameter(param, CANSparkMaxJNI.c_SparkMax_GetParameterFloat32(handle, param.id));
			}

		}
	}

	private static long getHandle(CANSparkMaxLowLevel spark) {
		try {
			var f = CANSparkMaxLowLevel.class.getField("sparkMaxHandle");
			f.setAccessible(true);
			long handle = f.getLong(spark);
			return handle;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) { // TODO:
																													// Hangle
																													// Exeptions?
		}
		return -1;
	}

}

class Parameter {
	public SparkParameter id;
	public Number value;

	@JsonCreator
	public Parameter(@JsonProperty("id") SparkParameter id, @JsonProperty("value") Number value) {
		this.id = id;
		this.value = value;
	}

	public long asUint32() {
		return value.longValue();
	}

	public int asInt32() {
		return value.intValue();
	}

	public float asFloat32() {
		return value.floatValue();
	}

	public boolean asBoolean() {
		return value.floatValue() != 0;
	}

}
