package com.google.android.gms.internal.clearcut;

/* renamed from: com.google.android.gms.internal.clearcut.u reason: case insensitive filesystem */
final class C0795u {
    private static final Class<?> a = a("libcore.io.Memory");
    private static final boolean b = (a("org.robolectric.Robolectric") != null);

    private static <T> Class<T> a(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean a() {
        return a != null && !b;
    }

    static Class<?> b() {
        return a;
    }
}
