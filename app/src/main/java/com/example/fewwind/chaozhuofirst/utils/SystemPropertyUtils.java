
package com.example.fewwind.chaozhuofirst.utils;

import android.util.Log;
import java.lang.reflect.Method;

public class SystemPropertyUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "SystemPropertyUtils";

    // 获取当前系统的渠道信息
    public static String getSystemProperty(String key) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method m = clz.getMethod("get", String.class);
            String ret = (String) m.invoke(null, key);
            if (DEBUG) {
                Log.d(TAG, "getting system property: " + key + ", value: " + ret);
            }
            return ret;
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "", e);
            }
        }
        return "";
    }

    public static String getSystemProperty(String key, String def) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method m = clz.getMethod("get", String.class, String.class);
            String ret = (String) m.invoke(null, key, def);
            if (DEBUG) {
                Log.d(TAG, "getting system property: " + key + ", value: " + ret);
            }
            return ret;
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "", e);
            }
        }
        return "";
    }
}
