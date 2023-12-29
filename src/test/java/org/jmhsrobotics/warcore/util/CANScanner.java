package org.jmhsrobotics.warcore.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.wpi.first.hal.CANStreamMessage;
import edu.wpi.first.hal.can.CANJNI;

public class CANScanner {
    private final int messageBufferSize = 280; /// Calculated buffer for worst case 0byte msg at 50hz
    private int m_canStreamSession;
    private CANStreamMessage[] m_messageBuffer;
    public Set<Integer> deviceSet = new HashSet<Integer>();

    public CANScanner() {
        // Listen to Rev on device 1
        m_canStreamSession = CANJNI.openCANStreamSession(0, 0, messageBufferSize);
        // m_canStreamSession = CANJNI.openCANStreamSession(0x2050001, 0x1FFF003F, messageBufferSize);
        m_messageBuffer = new CANStreamMessage[messageBufferSize];
        for (int i = 0; i < messageBufferSize; i++) {
            m_messageBuffer[i] = new CANStreamMessage();
        }
        if (m_canStreamSession == 0) {
            // TODO: Should this throw? I believe WPILib will already send some kind of
            // message
            // throw new Exception("Failed to open CAN Stream");
            System.out.println("Error Failed to open Can Stream");
        }
    }

    public void poll() {
        //TODO: Wait for WPILIB CAN error handling to be fixed in new pull request
        int numToRead = CANJNI.readCANStreamSession(m_canStreamSession, m_messageBuffer, messageBufferSize);
        // System.out.println("MSG to read" + numToRead);
        // if (!m_enabled.get()) {
        // // return ErrorCode.OK;
        // }

        for (int i = 0; i < numToRead; i++) {
            var message = m_messageBuffer[i];
            if (message != null) {
                deviceSet.add(Integer.valueOf(message.messageID & 0x1FFF003F)); // Extract only device type, man and
                                                                                // device number
            }
        }

    }

    public int extractDeviceId(int canId) {
        return canId & 0x3f;
    }

    public int extractApiId(int canId) {
        return (canId & 0xffc0) >> 6;
    }

    public int extractManufacturerId(int canId) {
        return (canId & 0xFF0000) >> 16;
    }

    public int extractDeviceTypeId(int canId) {
        return (canId & 0x1F000000) >> 24;
    }

    public void close() {

    }

    public class CanApiTypes {
        private static final Map<Integer, DeviceType> deviceLookup = new HashMap<Integer, DeviceType>();
        private static final Map<Integer, Manufacturer> manufacturerLookup = new HashMap<Integer, Manufacturer>();

        public enum DeviceType {
            BROADCAST_MESSAGES(0),
            ROBOT_CONTROLLER(1),
            MOTOR_CONTROLLER(2),
            RELAY_CONTROLLER(3),
            GYRO_SENSOR(4),
            ACCELEROMETER(5),
            ULTRASONIC_SENSOR(6),
            GEAR_TOOTH_SENSOR(7),
            POWER_DISTRIBUTION_MODULE(8),
            PNEUMATICS_CONTROLLER(9),
            MISCELLANEOUS(10),
            IO_BREAKOUT(11),
            FIRMWARE_UPDATE(31);

            public final int id;

            DeviceType(int id) {
                this.id = id;
                CanApiTypes.deviceLookup.put(id, this);
            }

            public static boolean isReserved(int id) {
                return id >= 12 && id <= 30;
            }

            public static boolean isValid(int id) {
                return id > 0 && id < 32;
            }

            public static DeviceType fromInt(int id) {
                return CanApiTypes.deviceLookup.get(id);
            }
        }

        public enum Manufacturer {
            BROADCAST(0),
            NI(1),
            LUMINARY_MICRO(2),
            DEKA(3),
            CTR_ELECTRONICS(4),
            REV_ROBOTICS(5),
            GRAPPLE(6),
            MIND_SENSORS(7),
            TEAM_USE(8),
            KAUAI_LABS(9),
            COPPERFORGE(10),
            PLAYING_WITH_FUSION(11),
            STUDICA(12),
            THE_THRIFTY_BOT(13),
            REDUX_ROBOTICS(14);

            public final int id;

            Manufacturer(int id) {
                this.id = id;
                manufacturerLookup.put(id, this);
            }

            public static Manufacturer fromInt(int id) {
                return CanApiTypes.manufacturerLookup.get(id);
            }
        }
    }
}
