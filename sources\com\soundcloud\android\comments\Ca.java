package com.soundcloud.android.comments;

import com.soundcloud.android.foundation.events.C3700b;

/* compiled from: CommentsPresenter_Factory */
public final class Ca implements C4961HMa<Z> {
    private final C7054oVa<C5327TLa> a;
    private final C7054oVa<C3700b> b;
    private final C7054oVa<mb> c;
    private final C7054oVa<W> d;
    private final C7054oVa<Ea> e;
    private final C7054oVa<C5606ava> f;
    private final C7054oVa<HPa> g;
    private final C7054oVa<HPa> h;

    public Ca(C7054oVa<C5327TLa> ova, C7054oVa<C3700b> ova2, C7054oVa<mb> ova3, C7054oVa<W> ova4, C7054oVa<Ea> ova5, C7054oVa<C5606ava> ova6, C7054oVa<HPa> ova7, C7054oVa<HPa> ova8) {
        this.a = ova;
        this.b = ova2;
        this.c = ova3;
        this.d = ova4;
        this.e = ova5;
        this.f = ova6;
        this.g = ova7;
        this.h = ova8;
    }

    public static Ca a(C7054oVa<C5327TLa> ova, C7054oVa<C3700b> ova2, C7054oVa<mb> ova3, C7054oVa<W> ova4, C7054oVa<Ea> ova5, C7054oVa<C5606ava> ova6, C7054oVa<HPa> ova7, C7054oVa<HPa> ova8) {
        Ca ca = new Ca(ova, ova2, ova3, ova4, ova5, ova6, ova7, ova8);
        return ca;
    }

    public Z get() {
        Z z = new Z((C5327TLa) this.a.get(), (C3700b) this.b.get(), (mb) this.c.get(), (W) this.d.get(), (Ea) this.e.get(), (C5606ava) this.f.get(), (HPa) this.g.get(), (HPa) this.h.get());
        return z;
    }
}
