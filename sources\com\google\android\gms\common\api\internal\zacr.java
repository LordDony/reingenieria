package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import com.google.android.gms.common.api.zac;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

final class zacr implements DeathRecipient, zacs {
    private final WeakReference<BasePendingResult<?>> zalc;
    private final WeakReference<zac> zald;
    private final WeakReference<IBinder> zale;

    private zacr(BasePendingResult<?> basePendingResult, zac zac, IBinder iBinder) {
        this.zald = new WeakReference<>(zac);
        this.zalc = new WeakReference<>(basePendingResult);
        this.zale = new WeakReference<>(iBinder);
    }

    private final void zaby() {
        BasePendingResult basePendingResult = (BasePendingResult) this.zalc.get();
        zac zac = (zac) this.zald.get();
        if (!(zac == null || basePendingResult == null)) {
            zac.remove(basePendingResult.zam().intValue());
        }
        IBinder iBinder = (IBinder) this.zale.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
            }
        }
    }

    public final void binderDied() {
        zaby();
    }

    public final void zac(BasePendingResult<?> basePendingResult) {
        zaby();
    }

    /* synthetic */ zacr(BasePendingResult basePendingResult, zac zac, IBinder iBinder, zacq zacq) {
        this(basePendingResult, null, iBinder);
    }
}
