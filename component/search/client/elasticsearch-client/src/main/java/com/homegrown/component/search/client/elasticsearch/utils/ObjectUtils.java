package com.homegrown.component.search.client.elasticsearch.utils;

import java.util.List;
import java.util.Map;

/**
 * @author youyu
 */
public class ObjectUtils {
    public static Boolean getBoolean(Map map, String key) {
        return getBoolean(map, key, null);
    }

    public static Long getLong(Map map, String key) {
        return getLong(map, key, null);
    }

    public static Integer getInteger(Map map, String key) {
        return getInteger(map, key, null);
    }

    public static Float getFloat(Map map, String key) {
        return getFloat(map, key, null);
    }

    public static Double getDouble(Map map, String key) {
        return getDouble(map, key, null);
    }

    public static String getString(Map map, String key) {
        return getString(map, key, null);
    }

    public static Object getObject(Map map, String key) {
        return getObject(map, key, null);
    }

    public static <T> List<T> getList(Map map, String key) {
        return getList(map, key, null);
    }

    public static <T> Map<String, T> getMap(Map map, String key) {
        return getMap(map, key, null);
    }

    public static Boolean getBoolean(Map map, String key, Boolean def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return "true".equals(obj.toString());
    }

    public static Long getLong(Map map, String key, Long def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        return Long.parseLong(obj.toString());
    }

    public static Integer getInteger(Map map, String key, Integer def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }
        return Integer.parseInt(obj.toString());
    }

    public static Float getFloat(Map map, String key, Float def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        if (obj instanceof Float) {
            return (Float) obj;
        }
        if (obj instanceof Double) {
            return ((Double) obj).floatValue();
        }
        return Float.parseFloat(obj.toString());
    }

    public static Double getDouble(Map map, String key, Double def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).doubleValue();
        }
        if (obj instanceof Long) {
            return ((Long) obj).doubleValue();
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Float) {
            return ((Float) obj).doubleValue();
        }
        return Double.parseDouble(obj.toString());
    }

    public static String getString(Map map, String key, String def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    public static Object getObject(Map map, String key, Object def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        return obj;
    }

    public static <T> List<T> getList(Map map, String key, List<T> def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        //noinspection unchecked
        return (List<T>) obj;
    }

    public static <T> Map<String, T> getMap(Map map, String key, Map<String, T> def) {
        if (null == map) {
            return def;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return def;
        }
        //noinspection unchecked
        return (Map<String, T>) obj;
    }

    private ObjectUtils() {

    }
}
