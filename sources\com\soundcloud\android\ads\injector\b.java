package com.soundcloud.android.ads.injector;

import com.soundcloud.android.ads.Na;
import com.soundcloud.android.foundation.playqueue.F;
import com.soundcloud.android.foundation.playqueue.q;

/* compiled from: AdInjectionPreferencesFragment.kt */
final class b extends C7540vYa implements PXa<RVa> {
    final /* synthetic */ q a;
    final /* synthetic */ s b;

    b(q qVar, s sVar) {
        this.a = qVar;
        this.b = sVar;
        super(0);
    }

    public final void d() {
        Na Rb = this.b.Rb();
        q qVar = this.a;
        C7471uYa.a((Object) qVar, "this");
        if (qVar instanceof F) {
            Rb.a((F) qVar, this.b.Sb().e());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Input ");
        sb.append(qVar);
        sb.append(" not of type ");
        sb.append(F.class.getSimpleName());
        throw new IllegalArgumentException(sb.toString());
    }
}
