package org.jmhsrobotics.warcore.rev;

public enum SparkParameter {
	kCanID(0, Type.uint32), //
	kInputMode(1, Type.uint32), kMotorType(2, Type.uint32), kCommAdvance(3, Type.float32), // undocumented
	kSensorType(4, Type.uint32), kCtrlType(5, Type.uint32), kIdleMode(6, Type.uint32), kInputDeadband(7,
			Type.float32), kFeedbackSensorPID0(8, Type.uint32), // Undocumented
	kFeedbackSensorPID1(9, Type.uint32), // Undocumented
	kPolePairs(10, Type.uint32), kCurrentChop(11, Type.float32), kCurrentChopCycles(12, Type.uint32), kP_0(13,
			Type.float32), kI_0(14, Type.float32), kD_0(15, Type.float32), kF_0(16, Type.float32), kIZone_0(17,
					Type.float32), kDFilter_0(18, Type.float32), kOutputMin_0(19, Type.float32), kOutputMax_0(20,
							Type.float32), kP_1(21, Type.float32), kI_1(22, Type.float32), kD_1(23,
									Type.float32), kF_1(24, Type.float32), kIZone_1(25, Type.float32), kDFilter_1(26,
											Type.float32), kOutputMin_1(27, Type.float32), kOutputMax_1(28,
													Type.float32), kP_2(29, Type.float32), kI_2(30, Type.float32), kD_2(
															31, Type.float32), kF_2(32, Type.float32), kIZone_2(33,
																	Type.float32), kDFilter_2(34,
																			Type.float32), kOutputMin_2(35,
																					Type.float32), kOutputMax_2(36,
																							Type.float32), kP_3(37,
																									Type.float32), kI_3(
																											38,
																											Type.float32), kD_3(
																													39,
																													Type.float32), kF_3(
																															40,
																															Type.float32), kIZone_3(
																																	41,
																																	Type.float32), kDFilter_3(
																																			42,
																																			Type.float32), kOutputMin_3(
																																					43,
																																					Type.float32), kOutputMax_3(
																																							44,
																																							Type.float32), kInverted(
																																									45,
																																									Type.bool), // Undocumented
	kOutputRatio(46, Type.float32), // undocumented
	kSerialNumberLow(47, Type.uint32), // Undocumented
	kSerialNumberMid(48, Type.uint32), // Undocumented
	kSerialNumberHigh(49, Type.uint32), // Undocumented
	kLimitSwitchFwdPolarity(50, Type.bool), kLimitSwitchRevPolarity(51, Type.bool), kHardLimitFwdEn(52,
			Type.bool), kHardLimitRevEn(53, Type.bool), kSoftLimitFwdEn(54, Type.bool), // undocumented
	kSoftLimitRevEn(55, Type.bool), // undocumented
	kRampRate(56, Type.float32), kFollowerID(57, Type.uint32), kFollowerConfig(58,
			Type.uint32), kSmartCurrentStallLimit(59, Type.uint32), kSmartCurrentFreeLimit(60,
					Type.uint32), kSmartCurrentConfig(61, Type.uint32), kSmartCurrentReserved(62, Type.uint32), // undocumented
	kMotorKv(63, Type.uint32), // undocumented
	kMotorR(64, Type.uint32), // undocumented
	kMotorL(65, Type.uint32), // undocumented
	kMotorRsvd1(66, Type.uint32), // undocumented
	kMotorRsvd2(67, Type.uint32), // undocumented
	kMotorRsvd3(68, Type.uint32), // undocumented
	kEncoderCountsPerRev(69, Type.uint32), kEncoderAverageDepth(70, Type.uint32), kEncoderSampleDelta(71,
			Type.uint32), kEncoderInverted(72, Type.bool), // undocumented
	kEncoderRsvd1(73, Type.uint32), // undocumented
	kClosedLoopVoltageMode(74, Type.uint32), // undocumented
	kCompensatedNominalVoltage(75, Type.float32), kSmartMotionMaxVelocity_0(76, Type.float32), kSmartMotionMaxAccel_0(
			77, Type.float32), kSmartMotionMinVelOutput_0(78, Type.float32), kSmartMotionAllowedClosedLoopError_0(79,
					Type.float32), kSmartMotionAccelStrategy_0(80, Type.float32), kSmartMotionMaxVelocity_1(81,
							Type.float32), kSmartMotionMaxAccel_1(82, Type.float32), kSmartMotionMinVelOutput_1(83,
									Type.float32), kSmartMotionAllowedClosedLoopError_1(84,
											Type.float32), kSmartMotionAccelStrategy_1(85,
													Type.float32), kSmartMotionMaxVelocity_2(86,
															Type.float32), kSmartMotionMaxAccel_2(87,
																	Type.float32), kSmartMotionMinVelOutput_2(88,
																			Type.float32), kSmartMotionAllowedClosedLoopError_2(
																					89,
																					Type.float32), kSmartMotionAccelStrategy_2(
																							90,
																							Type.float32), kSmartMotionMaxVelocity_3(
																									91,
																									Type.float32), kSmartMotionMaxAccel_3(
																											92,
																											Type.float32), kSmartMotionMinVelOutput_3(
																													93,
																													Type.float32), kSmartMotionAllowedClosedLoopError_3(
																															94,
																															Type.float32), kSmartMotionAccelStrategy_3(
																																	95,
																																	Type.float32), kIMaxAccum_0(
																																			96,
																																			Type.float32), kSlot3Placeholder1_0(
																																					97,
																																					Type.float32), kSlot3Placeholder2_0(
																																							98,
																																							Type.float32), kSlot3Placeholder3_0(
																																									99,
																																									Type.float32), kIMaxAccum_1(
																																											100,
																																											Type.float32), kSlot3Placeholder1_1(
																																													101,
																																													Type.float32), kSlot3Placeholder2_1(
																																															102,
																																															Type.float32), kSlot3Placeholder3_1(
																																																	103,
																																																	Type.float32), kIMaxAccum_2(
																																																			104,
																																																			Type.float32), kSlot3Placeholder1_2(
																																																					105,
																																																					Type.float32), kSlot3Placeholder2_2(
																																																							106,
																																																							Type.float32), kSlot3Placeholder3_2(
																																																									107,
																																																									Type.float32), kIMaxAccum_3(
																																																											108,
																																																											Type.float32), kSlot3Placeholder1_3(
																																																													109,
																																																													Type.float32), kSlot3Placeholder2_3(
																																																															110,
																																																															Type.float32), kSlot3Placeholder3_3(
																																																																	111,
																																																																	Type.float32), kPositionConversionFactor(
																																																																			112,
																																																																			Type.float32), kVelocityConversionFactor(
																																																																					113,
																																																																					Type.float32), kClosedLoopRampRate(
																																																																							114,
																																																																							Type.float32), kSoftLimitFwd(
																																																																									115,
																																																																									Type.float32), kSoftLimitRev(
																																																																											116,
																																																																											Type.float32), kSoftLimitRsvd0(
																																																																													117,
																																																																													Type.uint32), // undocumented
	kSoftLimitRsvd1(118, Type.uint32), // undocumented
	kAnalogPositionConversion(119, Type.float32), kAnalogVelocityConversion(120, Type.float32), kAnalogAverageDepth(121,
			Type.uint32), kAnalogSensorMode(122, Type.uint32), kAnalogInverted(123,
					Type.bool), kAnalogSampleDelta(124, Type.uint32), kAnalogRsvd0(125, Type.uint32), // undocumented
	kAnalogRsvd1(126, Type.uint32), // undocumented
	kDataPortConfig(127, Type.uint32), kAltEncoderCountsPerRev(128, Type.uint32), kAltEncoderAverageDepth(129,
			Type.uint32), kAltEncoderSampleDelta(130, Type.uint32), kAltEncoderInverted(131,
					Type.bool), kAltEncoderPositionFactor(132,
							Type.float32), kAltEncoderVelocityFactor(133, Type.float32),
	// Undocumented
	kAltEncoderRsvd0(134, Type.uint32), kAltEncoderRsvd1(135, Type.uint32), kHallSensorSampleRate(136,
			Type.float32), kHallSensorAverageDepth(137, Type.uint32), kNumParameters(138,
					Type.uint32), kDutyCyclePositionFactor(139, Type.float32), kDutyCycleVelocityFactor(140,
							Type.float32), kDutyCycleInverted(141, Type.bool), kDutyCycleSensorMode(142,
									Type.uint32), kDutyCycleAverageDepth(143, Type.uint32), kDutyCycleSampleDelta(144,
											Type.uint32), kDutyCycleZeroOffsetv1p6p2(145,
													Type.float32), kDutyCycleRsvd0(146, Type.uint32), kDutyCycleRsvd1(
															147, Type.uint32), kDutyCycleRsvd2(148,
																	Type.uint32), kPositionPIDWrapEnable(149,
																			Type.bool), kPositionPIDMinInput(150,
																					Type.float32), kPositionPIDMaxInput(
																							151,
																							Type.float32), kDutyCycleZeroCentered(
																									152,
																									Type.bool), kDutyCyclePrescaler(
																											153,
																											Type.uint32), kDutyCycleZeroOffset(
																													154,
																													Type.float32)

	;

	public final int id;
	public final Type dataType;

	private SparkParameter(int id, Type dataType) {
		this.id = id;
		this.dataType = dataType;
	}

	public enum Type {

		int32(), // type 0? int 32
		uint32(), // Type 1 uint32

		float32(), // 2 float32
		bool(); // Type 3; bool
	}
}
