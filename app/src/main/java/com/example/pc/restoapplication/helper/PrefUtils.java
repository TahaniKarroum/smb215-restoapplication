package com.example.pc.restoapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by macbook on 2/17/16.
 */
public class PrefUtils {
    private static final String PREF_username = "us3ername";
    private static final String PREF_ISLOGIN = "isl5fefff2wefeff5errrre33efkjjmmmmmmmmmtrttjhgggkrlsn";
    private static final String PREF_LOGINUSERID = "gfe3f2ewffefelprrjiggrrmmmmmmiikmlttttu5g";

    public static void setUsername(String value, final Context context) {
        SharedPreferences sp = preparePreferences(context);
        sp.edit().putString(PREF_username, value).commit();
    }

    public static String getUsername(final Context context) {
        SharedPreferences sp = preparePreferences(context);
        return sp.getString(PREF_username, "");
    }


    public static SharedPreferences preparePreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isLogin(final Context context) {
        SharedPreferences sp = preparePreferences(context);
        return sp.getBoolean(PREF_ISLOGIN, false);
    }

    public static void setLogin(final boolean active, final Context context) {
        SharedPreferences sp = preparePreferences(context);
        sp.edit().putBoolean(PREF_ISLOGIN, active).commit();
    }

    public static int getUserid(final Context context) {
        SharedPreferences sp = preparePreferences(context);
        return sp.getInt(PREF_LOGINUSERID, 0);
    }

    public static void setUserid(int userid, final Context context) {
        SharedPreferences sp = preparePreferences(context);
        sp.edit().putInt(PREF_LOGINUSERID, userid).commit();
    }
}
