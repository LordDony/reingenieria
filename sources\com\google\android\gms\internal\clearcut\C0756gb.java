package com.google.android.gms.internal.clearcut;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: com.google.android.gms.internal.clearcut.gb reason: case insensitive filesystem */
final class C0756gb implements Iterator<Entry<K, V>> {
    private int a;
    private Iterator<Entry<K, V>> b;
    private final /* synthetic */ C0750eb c;

    private C0756gb(C0750eb ebVar) {
        this.c = ebVar;
        this.a = this.c.b.size();
    }

    /* synthetic */ C0756gb(C0750eb ebVar, C0753fb fbVar) {
        this(ebVar);
    }

    private final Iterator<Entry<K, V>> a() {
        if (this.b == null) {
            this.b = this.c.f.entrySet().iterator();
        }
        return this.b;
    }

    public final boolean hasNext() {
        int i = this.a;
        return (i > 0 && i <= this.c.b.size()) || a().hasNext();
    }

    public final /* synthetic */ Object next() {
        Object obj;
        if (a().hasNext()) {
            obj = a().next();
        } else {
            List b2 = this.c.b;
            int i = this.a - 1;
            this.a = i;
            obj = b2.get(i);
        }
        return (Entry) obj;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
