package org.jmhsrobotics.warcore.util.tuning;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTunable {
    private static ArrayList<TuneableSender> sendables = new ArrayList<>();

    public static class TuneableSender implements Sendable {
        private ArrayList<TuneableField> val;
        private ArrayList<Sendable> sendables = new ArrayList<Sendable>();
        private final String path;

        public TuneableSender(String path, ArrayList<TuneableField> val) {
            this.path = path;
            this.val = val;
        }

        public void close() { // Note this fails to close everything if live window mode has been entered. Controlable gets modifed and stays
            // SendableRegistry.remove(sendables.get(0));
            for(Sendable item: sendables){
                SendableRegistry.remove(item);
                
            }
            SendableRegistry.remove(this);
            NetworkTableInstance.getDefault().getEntry("/SmartDashboard/" + this.path + "/.name").unpublish(); //TODO: Allow for configuration of any NT table
        }

        public void publish() {
            SmartDashboard.putData(path, this);
        }

        private void addtoBuilder(SendableBuilder builder, TuneableField tf) {
            try {
                var obj = tf.obj;
                var f = tf.f;
                var key = f.getName();
                var rawVal = f.get(obj);
                if (rawVal instanceof Sendable){
                    // SmartDashboard.putData(path + "/"  + key, (Sendable)rawVal); //TODO: This is bad because it is putting data to smart dashbaord before publishing. We should put inside of the initbuilder method
                    // sendables.add((Sendable)rawVal);

                }else if (f.getType() == boolean.class) {
                    builder.addBooleanProperty(key, () -> { // Getter
                        try {
                            return f.getBoolean(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }, (boolean value) -> { // Setter
                        try {
                            f.setBoolean(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == boolean[].class) {
                    builder.addBooleanArrayProperty(key, () -> { // Getter
                        try {
                            return (boolean[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (boolean[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == long.class) {
                    builder.addIntegerProperty(key, () -> { // Getter
                        try {

                            return f.getLong(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }, (long value) -> { // Setter
                        try {
                            f.setLong(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == long[].class) {
                    builder.addIntegerArrayProperty(key, () -> { // Getter
                        try {
                            return (long[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (long[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == float.class) {
                    builder.addFloatProperty(key, () -> { // Getter
                        try {
                            return f.getFloat(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return Float.NaN;
                        }
                    }, (float value) -> { // Setter
                        try {
                            f.setFloat(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == float[].class) {
                    builder.addFloatArrayProperty(key, () -> { // Getter
                        try {
                            return (float[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (float[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == double.class) {
                    builder.addDoubleProperty(key, () -> { // Getter
                        try {
                            return f.getDouble(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return Double.NaN;
                        }
                    }, (double value) -> { // Setter
                        try {
                            f.setDouble(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == double[].class) {
                    builder.addDoubleArrayProperty(key, () -> { // Getter
                        try {
                            return (double[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (double[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == String.class) {
                    builder.addStringProperty(key, () -> { // Getter
                        try {
                            return (String) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (String value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == String[].class) {
                    builder.addStringArrayProperty(key, () -> { // Getter
                        try {
                            return (String[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (String[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                } else if (f.getType() == int.class) {
                    builder.addIntegerProperty(key, () -> { // Getter
                        try {
                            return (long) f.getInt(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }, (long value) -> { // Setter
                        try {
                            f.setInt(obj, (int) value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                } else if (f.getType() == short.class) {
                    builder.addIntegerProperty(key, () -> { // Getter
                        try {
                            return (long) f.getShort(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }, (long value) -> { // Setter
                        try {
                            f.setShort(obj, (short) value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                } else if (f.getType() == byte.class) {
                    builder.addIntegerProperty(key, () -> { // Getter
                        try {
                            return (long) f.getByte(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }, (long value) -> { // Setter
                        try {
                            f.setByte(obj, (byte) value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                } else if (f.getType() == char.class) { // TODO Should I put these in as Strings?
                    builder.addIntegerProperty(key, () -> { // Getter
                        try {
                            return (long) f.getChar(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }, (long value) -> { // Setter
                        try {
                            f.setChar(obj, (char) value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                }

                else {
                    System.out.println("UNKNWON TYPE: " + f.getName() + " " + f.getType());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public SendableBuilder builder;

        private void addtoBuilder_old(SendableBuilder builder, TuneableField tf) {

            System.out.println("-------------------");
            try {
                for (var methods : builder.getClass().getMethods()) { // correctly builds all array properties. This
                                                                      // avoids repitition but is much harder to read
                                                                      // and is less clear if somthing breaks.
                    if (methods.getParameterCount() == 3 && methods.getParameterTypes()[0] == String.class
                            && methods.getParameterTypes()[1] == Supplier.class
                            && methods.getParameterTypes()[2] == Consumer.class) {
                        Type providerType = ((ParameterizedType) methods.getParameters()[1].getParameterizedType())
                                .getActualTypeArguments()[0];
                        Type consumerType = ((ParameterizedType) methods.getParameters()[1].getParameterizedType())
                                .getActualTypeArguments()[0];
                        System.out.println(methods.getName());
                        if (providerType != tf.f.getType() || consumerType != tf.f.getType()) {
                            continue;
                        }

                        Supplier<?> supplier = () -> {
                            try {
                                var tmp2 = tf.f.get(tf.obj);
                                return tf.f.getType().cast(tmp2);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                e.printStackTrace();
                                return null;
                            }
                        };
                        Consumer<?> consumer = (value) -> {
                            try {
                                tf.f.set(tf.obj, value);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        };
                        methods.invoke(builder, tf.f.getName(), supplier,
                                consumer);
                    } else if (methods.getParameterCount() == 2) {
                        // builder.addBooleanProperty(null, null, null);
                        // methods.getParameterTypes()[0].getClass().getMethods()[0]
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void initSendable(SendableBuilder builder) {
            this.builder = builder;
            for (var data : val) {
                addtoBuilder(builder, data);
            }

        }

    }

    public static void recurseAnotations(Object data) {
        ArrayList<TuneableField> foundFields = new ArrayList<TuneableField>();
        try {
            for (Field f : data.getClass().getDeclaredFields()) {

                // Check for Tunable Annotation in Field
                if (f.getAnnotation(Tunable.class) != null) {
                    f.setAccessible(true);
                    foundFields.add(new TuneableField(f, data));
                }

                // Check if Class of field is Tunable
                // Recurse if so.
                if (f.getType().getAnnotation(Tunable.class) != null) {
                    f.setAccessible(true);
                    AutoTunable.recurseAnotations(f.get(data));
                }
            }

            if (foundFields.size() != 0) {
                var path = data.getClass().getName().replace(".", "/");
                var sendable = new TuneableSender(path, foundFields); // This has a bug where if the sender is unable to
                                                                      // display a field it will not be counded leading
                                                                      // to blank sendables.
                sendable.publish();
                AutoTunable.sendables.add(sendable);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void enableTuning(RobotBase base) {
        if (AutoTunable.sendables.size() == 0) {
            AutoTunable.recurseAnotations(base);
        }

    }

    public static void disable() {
        for (TuneableSender item : AutoTunable.sendables) {
            item.close();
        }
        AutoTunable.sendables.clear();
    }

    private static class TuneableField {
        public final Field f;
        public final Object obj;

        public TuneableField(Field f, Object obj) {
            this.f = f;
            this.obj = obj;
        }
    }

}
