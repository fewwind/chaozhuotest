package com.example.fewwind.chaozhuofirst;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
                .getLaunchIntentForPackage("com.example.fewwind.keymap");
            intent.putExtra("category","AppStore");
            intent.putExtra("pakName","com.tencent.android.qqdownloader");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "失败",
                Toast.LENGTH_SHORT).show();
        }
    }
}
