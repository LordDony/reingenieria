package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.util.BiConsumer;

final /* synthetic */ class zaby implements RemoteCall {
    private final BiConsumer zakf;

    zaby(BiConsumer biConsumer) {
        this.zakf = biConsumer;
    }

    public final void accept(Object obj, Object obj2) {
        this.zakf.accept((AnyClient) obj, (Xy) obj2);
    }
}
