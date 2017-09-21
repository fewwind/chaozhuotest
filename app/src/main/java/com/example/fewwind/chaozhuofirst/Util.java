package com.example.fewwind.chaozhuofirst;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fewwind on 17-3-8.
 */

public class Util {
    public static void launchApp(Context context, String packageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "失败",
                Toast.LENGTH_SHORT).show();
        }
    }

    public static void launchApp(Context context) {
        try {
            Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.chaozhuo.recommendedapp");
            //intent.putExtra("category","AppStore");
            intent.putExtra("pakName","com.tencent.android.qqdownloader");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "失败",
                Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFormatedDate(Context c, long time) {
        final Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder buffer = new StringBuilder();
        buffer.append(sdf.format(date));
        return buffer.toString();
    }

    private static final String TAG = "SystemPropertyUtils";

    public static String getSystemProperty(String key) {
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method m = clz.getMethod("get", new Class[]{String.class});
            String ret = (String)m.invoke((Object)null, new Object[]{key});
            return ret;
        } catch (Exception var4) {
            return "";
        }
    }

    public static String getSystemProperty(String key, String def) {
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method m = clz.getMethod("get", new Class[]{String.class, String.class});
            String ret = (String)m.invoke((Object)null, new Object[]{key, def});
            return ret;
        } catch (Exception var5) {
            return "";
        }
    }

    public static String getBuildInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT: " + Build.PRODUCT + "\n");
        sb.append("BOARD: " + Build.BOARD + "\n");
        sb.append("BRAND: " + Build.BRAND + "\n");
        sb.append("DEVICE: " + Build.DEVICE + "\n");
        sb.append("MODEL: " + Build.MODEL + "\n");
        sb.append("MANUFACTURER: " + Build.MANUFACTURER + "\n");
        return sb.toString();
    }
}
