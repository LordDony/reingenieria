package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.api.internal.RegistrationMethods.Builder;

final class zacb extends UnregisterListenerMethod<A, L> {
    private final /* synthetic */ Builder zakh;

    zacb(Builder builder, ListenerKey listenerKey) {
        this.zakh = builder;
        super(listenerKey);
    }

    /* access modifiers changed from: protected */
    public final void unregisterListener(A a, Xy<Boolean> xy) throws RemoteException {
        this.zakh.zakc.accept(a, xy);
    }
}
