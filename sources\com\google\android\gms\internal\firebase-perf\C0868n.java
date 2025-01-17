package com.google.android.gms.internal.firebase-perf;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.google.android.gms.internal.firebase-perf.n reason: case insensitive filesystem */
final class C0868n {
    private final ConcurrentHashMap<C0864m, List<Throwable>> a = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> b = new ReferenceQueue<>();

    C0868n() {
    }

    public final List<Throwable> a(Throwable th, boolean z) {
        Reference poll = this.b.poll();
        while (poll != null) {
            this.a.remove(poll);
            poll = this.b.poll();
        }
        List<Throwable> list = (List) this.a.get(new C0864m(th, null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> list2 = (List) this.a.putIfAbsent(new C0864m(th, this.b), vector);
        return list2 == null ? vector : list2;
    }
}
