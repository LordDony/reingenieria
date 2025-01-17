package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;

public final class d extends a<Integer> {
    public static Integer a(SharedPreferences sharedPreferences, String str, Integer num) {
        try {
            return (Integer) Dy.a(new e(sharedPreferences, str, num));
        } catch (Exception e) {
            String str2 = "Flag value not available, returning default: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w("FlagDataUtils", valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return num;
        }
    }
}
