package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleFragment;

final class zae extends DialogRedirect {
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ Intent zaoh;
    private final /* synthetic */ LifecycleFragment zaoi;

    zae(Intent intent, LifecycleFragment lifecycleFragment, int i) {
        this.zaoh = intent;
        this.zaoi = lifecycleFragment;
        this.val$requestCode = i;
    }

    public final void redirect() {
        Intent intent = this.zaoh;
        if (intent != null) {
            this.zaoi.startActivityForResult(intent, this.val$requestCode);
        }
    }
}
