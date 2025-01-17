package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;

@KeepForSdk
public abstract class UnregisterListenerMethod<A extends AnyClient, L> {
    private final ListenerKey<L> zajl;

    @KeepForSdk
    protected UnregisterListenerMethod(ListenerKey<L> listenerKey) {
        this.zajl = listenerKey;
    }

    @KeepForSdk
    public ListenerKey<L> getListenerKey() {
        return this.zajl;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public abstract void unregisterListener(A a, Xy<Boolean> xy) throws RemoteException;
}
