package com.google.android.gms.common.api.internal;

import com.google.android.gms.signin.internal.c;
import com.google.android.gms.signin.internal.zaj;
import java.lang.ref.WeakReference;

final class zaar extends c {
    private final WeakReference<zaak> zagk;

    zaar(zaak zaak) {
        this.zagk = new WeakReference<>(zaak);
    }

    public final void zab(zaj zaj) {
        zaak zaak = (zaak) this.zagk.get();
        if (zaak != null) {
            zaak.zaft.zaa((zabf) new zaas(this, zaak, zaak, zaj));
        }
    }
}
