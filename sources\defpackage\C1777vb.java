package defpackage;

import android.os.Build.VERSION;
import android.os.Trace;

/* renamed from: vb reason: default package and case insensitive filesystem */
/* compiled from: TraceCompat */
public final class C1777vb {
    public static void a(String str) {
        if (VERSION.SDK_INT >= 18) {
            Trace.beginSection(str);
        }
    }

    public static void a() {
        if (VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }
}
