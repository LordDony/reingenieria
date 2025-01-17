package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Preconditions;

public final class zaq implements ConnectionCallbacks, OnConnectionFailedListener {
    public final Api<?> mApi;
    private final boolean zaec;
    private zar zaed;

    public zaq(Api<?> api, boolean z) {
        this.mApi = api;
        this.zaec = z;
    }

    private final void zav() {
        Preconditions.checkNotNull(this.zaed, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    public final void onConnected(Bundle bundle) {
        zav();
        this.zaed.onConnected(bundle);
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        zav();
        this.zaed.zaa(connectionResult, this.mApi, this.zaec);
    }

    public final void onConnectionSuspended(int i) {
        zav();
        this.zaed.onConnectionSuspended(i);
    }

    public final void zaa(zar zar) {
        this.zaed = zar;
    }
}
