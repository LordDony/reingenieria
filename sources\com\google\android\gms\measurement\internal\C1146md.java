package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.android.gms.measurement.internal.md reason: case insensitive filesystem */
final class C1146md implements Runnable {
    private final /* synthetic */ AtomicReference a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ zzn e;
    private final /* synthetic */ Yc f;

    C1146md(Yc yc, AtomicReference atomicReference, String str, String str2, String str3, zzn zzn) {
        this.f = yc;
        this.a = atomicReference;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = zzn;
    }

    public final void run() {
        synchronized (this.a) {
            try {
                _a d2 = this.f.d;
                if (d2 == null) {
                    this.f.e().s().a("Failed to get conditional properties", C1124ib.a(this.b), this.c, this.d);
                    this.a.set(Collections.emptyList());
                    this.a.notify();
                    return;
                }
                if (TextUtils.isEmpty(this.b)) {
                    this.a.set(d2.a(this.c, this.d, this.e));
                } else {
                    this.a.set(d2.a(this.b, this.c, this.d));
                }
                this.f.I();
                this.a.notify();
            } catch (RemoteException e2) {
                try {
                    this.f.e().s().a("Failed to get conditional properties", C1124ib.a(this.b), this.c, e2);
                    this.a.set(Collections.emptyList());
                    this.a.notify();
                } catch (Throwable th) {
                    this.a.notify();
                    throw th;
                }
            }
        }
    }
}
