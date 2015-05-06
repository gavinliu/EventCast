package cn.gavinliu.android.lib.eventcast.utils;

/**
 * Created by gavin on 15-5-5.
 */
public class Utils {

    public static String makeParameterTypesName(Class<?>[] parameterTypes) {
        if (parameterTypes == null || parameterTypes.length == 0) {
            return null;
        }
        String name = "";
        for (Class<?> type : parameterTypes) {
            type = convertType(type);
            name += type.getName() + ",";
        }
        return name;
    }

    public static String makeParameterTypesName(Object[] parameterTypes) {
        if (parameterTypes == null || parameterTypes.length == 0) {
            return null;
        }
        String name = "";
        for (Object type : parameterTypes) {
            name += type.getClass().getName() + ",";
        }
        return name;
    }

    public static boolean isSystemClass(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }

    public static Class<?> convertType(Class<?> eventType) {
        Class<?> returnClass = eventType;
        if (eventType.equals(boolean.class)) {
            returnClass = Boolean.class;
        } else if (eventType.equals(int.class)) {
            returnClass = Integer.class;
        } else if (eventType.equals(float.class)) {
            returnClass = Float.class;
        } else if (eventType.equals(double.class)) {
            returnClass = Double.class;
        }

        return returnClass;
    }

}
