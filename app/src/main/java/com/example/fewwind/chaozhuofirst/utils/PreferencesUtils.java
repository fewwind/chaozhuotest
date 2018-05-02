
package com.example.fewwind.chaozhuofirst.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

    public static String PREFERENCE_NAME = "PhoenixCommon";


    private PreferencesUtils() {
        throw new AssertionError();
    }


    /**
     * put string preferences
     *
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putString(Context context, String key, String value) {

        final SharedPreferences settings =
                context.getSharedPreferences(PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public static boolean removeKeyOld(Context context, String key) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }


    public static String getString(Context context, String key) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return settings.getString(key, null);
    }


    /**
     * put int preferences
     *
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws
     * ClassCastException if there is a preference with this name that
     * is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }


    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }


    /**
     * put long preferences
     *
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }


    /**
     * get long preferences
     *
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }


    /**
     * put float preferences
     *
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }


    /**
     * get float preferences
     *
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }


    /**
     * put boolean preferences
     *
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws
     * ClassCastException if there is a preference with this name that
     * is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }


    /**
     * get boolean preferences
     *
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
