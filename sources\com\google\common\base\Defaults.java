package com.google.common.base;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class Defaults {
    private static final Double DOUBLE_DEFAULT = Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    private static final Float FLOAT_DEFAULT = Float.valueOf(0.0f);

    private Defaults() {
    }

    public static <T> T defaultValue(Class<T> cls) {
        Preconditions.checkNotNull(cls);
        if (cls == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (cls == Character.TYPE) {
            return Character.valueOf(0);
        }
        if (cls == Byte.TYPE) {
            return Byte.valueOf(0);
        }
        if (cls == Short.TYPE) {
            return Short.valueOf(0);
        }
        if (cls == Integer.TYPE) {
            return Integer.valueOf(0);
        }
        if (cls == Long.TYPE) {
            return Long.valueOf(0);
        }
        if (cls == Float.TYPE) {
            return FLOAT_DEFAULT;
        }
        if (cls == Double.TYPE) {
            return DOUBLE_DEFAULT;
        }
        return null;
    }
}
