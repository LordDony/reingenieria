package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.ClientSettings.OptionalApiSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.internal.zaj;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zaak implements zabd {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final AbstractClientBuilder<? extends Oy, Ky> zace;
    /* access modifiers changed from: private */
    public final Lock zaeo;
    /* access modifiers changed from: private */
    public final ClientSettings zaet;
    private final Map<Api<?>, Boolean> zaew;
    /* access modifiers changed from: private */
    public final GoogleApiAvailabilityLight zaey;
    private ConnectionResult zafh;
    /* access modifiers changed from: private */
    public final zabe zaft;
    private int zafw;
    private int zafx = 0;
    private int zafy;
    private final Bundle zafz = new Bundle();
    private final Set<AnyClientKey> zaga = new HashSet();
    /* access modifiers changed from: private */
    public Oy zagb;
    private boolean zagc;
    /* access modifiers changed from: private */
    public boolean zagd;
    private boolean zage;
    /* access modifiers changed from: private */
    public IAccountAccessor zagf;
    private boolean zagg;
    private boolean zagh;
    private ArrayList<Future<?>> zagi = new ArrayList<>();

    public zaak(zabe zabe, ClientSettings clientSettings, Map<Api<?>, Boolean> map, GoogleApiAvailabilityLight googleApiAvailabilityLight, AbstractClientBuilder<? extends Oy, Ky> abstractClientBuilder, Lock lock, Context context) {
        this.zaft = zabe;
        this.zaet = clientSettings;
        this.zaew = map;
        this.zaey = googleApiAvailabilityLight;
        this.zace = abstractClientBuilder;
        this.zaeo = lock;
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    public final void zaa(zaj zaj) {
        if (zac(0)) {
            ConnectionResult connectionResult = zaj.getConnectionResult();
            if (connectionResult.isSuccess()) {
                ResolveAccountResponse i = zaj.i();
                ConnectionResult connectionResult2 = i.getConnectionResult();
                if (!connectionResult2.isSuccess()) {
                    String valueOf = String.valueOf(connectionResult2);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                    sb.append("Sign-in succeeded with resolve account failure: ");
                    sb.append(valueOf);
                    Log.wtf("GoogleApiClientConnecting", sb.toString(), new Exception());
                    zae(connectionResult2);
                    return;
                }
                this.zage = true;
                this.zagf = i.getAccountAccessor();
                this.zagg = i.getSaveDefaultAccount();
                this.zagh = i.isFromCrossClientAuth();
                zaap();
            } else if (zad(connectionResult)) {
                zaar();
                zaap();
            } else {
                zae(connectionResult);
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean zaao() {
        this.zafy--;
        int i = this.zafy;
        if (i > 0) {
            return false;
        }
        if (i < 0) {
            String str = "GoogleApiClientConnecting";
            Log.w(str, this.zaft.zaee.zaay());
            Log.wtf(str, "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zae(new ConnectionResult(8, null));
            return false;
        }
        ConnectionResult connectionResult = this.zafh;
        if (connectionResult == null) {
            return true;
        }
        this.zaft.zahs = this.zafw;
        zae(connectionResult);
        return false;
    }

    /* access modifiers changed from: private */
    public final void zaap() {
        if (this.zafy == 0) {
            if (!this.zagd || this.zage) {
                ArrayList arrayList = new ArrayList();
                this.zafx = 1;
                this.zafy = this.zaft.zagz.size();
                for (AnyClientKey anyClientKey : this.zaft.zagz.keySet()) {
                    if (!this.zaft.zahp.containsKey(anyClientKey)) {
                        arrayList.add((Client) this.zaft.zagz.get(anyClientKey));
                    } else if (zaao()) {
                        zaaq();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zagi.add(zabh.zabb().submit(new zaaq(this, arrayList)));
                }
            }
        }
    }

    private final void zaaq() {
        this.zaft.zaba();
        zabh.zabb().execute(new zaal(this));
        Oy oy = this.zagb;
        if (oy != null) {
            if (this.zagg) {
                oy.a(this.zagf, this.zagh);
            }
            zab(false);
        }
        for (AnyClientKey anyClientKey : this.zaft.zahp.keySet()) {
            ((Client) this.zaft.zagz.get(anyClientKey)).disconnect();
        }
        this.zaft.zaht.zab(this.zafz.isEmpty() ? null : this.zafz);
    }

    /* access modifiers changed from: private */
    public final void zaar() {
        this.zagd = false;
        this.zaft.zaee.zaha = Collections.emptySet();
        for (AnyClientKey anyClientKey : this.zaga) {
            if (!this.zaft.zahp.containsKey(anyClientKey)) {
                this.zaft.zahp.put(anyClientKey, new ConnectionResult(17, null));
            }
        }
    }

    private final void zaas() {
        ArrayList<Future<?>> arrayList = this.zagi;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zagi.clear();
    }

    /* access modifiers changed from: private */
    public final Set<Scope> zaat() {
        ClientSettings clientSettings = this.zaet;
        if (clientSettings == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map optionalApiSettings = this.zaet.getOptionalApiSettings();
        for (Api api : optionalApiSettings.keySet()) {
            if (!this.zaft.zahp.containsKey(api.getClientKey())) {
                hashSet.addAll(((OptionalApiSettings) optionalApiSettings.get(api)).mScopes);
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        if (r7 != false) goto L_0x0024;
     */
    public final void zab(ConnectionResult connectionResult, Api<?> api, boolean z) {
        boolean z2;
        int priority = api.zah().getPriority();
        boolean z3 = false;
        if (z) {
            if (!connectionResult.hasResolution() && this.zaey.getErrorResolutionIntent(connectionResult.getErrorCode()) == null) {
                z2 = false;
            } else {
                z2 = true;
            }
        }
        if (this.zafh == null || priority < this.zafw) {
            z3 = true;
        }
        if (z3) {
            this.zafh = connectionResult;
            this.zafw = priority;
        }
        this.zaft.zahp.put(api.getClientKey(), connectionResult);
    }

    /* access modifiers changed from: private */
    public final boolean zac(int i) {
        if (this.zafx == i) {
            return true;
        }
        String str = "GoogleApiClientConnecting";
        Log.w(str, this.zaft.zaee.zaay());
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
        sb.append("Unexpected callback in ");
        sb.append(valueOf);
        Log.w(str, sb.toString());
        int i2 = this.zafy;
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("mRemainingConnections=");
        sb2.append(i2);
        Log.w(str, sb2.toString());
        String zad = zad(this.zafx);
        String zad2 = zad(i);
        StringBuilder sb3 = new StringBuilder(String.valueOf(zad).length() + 70 + String.valueOf(zad2).length());
        sb3.append("GoogleApiClient connecting is in step ");
        sb3.append(zad);
        sb3.append(" but received callback for step ");
        sb3.append(zad2);
        Log.wtf(str, sb3.toString(), new Exception());
        zae(new ConnectionResult(8, null));
        return false;
    }

    private static String zad(int i) {
        return i != 0 ? i != 1 ? "UNKNOWN" : "STEP_GETTING_REMOTE_SERVICE" : "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
    }

    /* access modifiers changed from: private */
    public final boolean zad(ConnectionResult connectionResult) {
        return this.zagc && !connectionResult.hasResolution();
    }

    /* access modifiers changed from: private */
    public final void zae(ConnectionResult connectionResult) {
        zaas();
        zab(!connectionResult.hasResolution());
        this.zaft.zaf(connectionResult);
        this.zaft.zaht.zac(connectionResult);
    }

    public final void begin() {
        this.zaft.zahp.clear();
        this.zagd = false;
        this.zafh = null;
        this.zafx = 0;
        this.zagc = true;
        this.zage = false;
        this.zagg = false;
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (Api api : this.zaew.keySet()) {
            Client client = (Client) this.zaft.zagz.get(api.getClientKey());
            z |= api.zah().getPriority() == 1;
            boolean booleanValue = ((Boolean) this.zaew.get(api)).booleanValue();
            if (client.requiresSignIn()) {
                this.zagd = true;
                if (booleanValue) {
                    this.zaga.add(api.getClientKey());
                } else {
                    this.zagc = false;
                }
            }
            hashMap.put(client, new zaam(this, api, booleanValue));
        }
        if (z) {
            this.zagd = false;
        }
        if (this.zagd) {
            this.zaet.setClientSessionId(Integer.valueOf(System.identityHashCode(this.zaft.zaee)));
            zaat zaat = new zaat(this, null);
            AbstractClientBuilder<? extends Oy, Ky> abstractClientBuilder = this.zace;
            Context context = this.mContext;
            Looper looper = this.zaft.zaee.getLooper();
            ClientSettings clientSettings = this.zaet;
            this.zagb = (Oy) abstractClientBuilder.buildClient(context, looper, clientSettings, clientSettings.getSignInOptions(), zaat, zaat);
        }
        this.zafy = this.zaft.zagz.size();
        this.zagi.add(zabh.zabb().submit(new zaan(this, hashMap)));
    }

    public final void connect() {
    }

    public final boolean disconnect() {
        zaas();
        zab(true);
        this.zaft.zaf(null);
        return true;
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(T t) {
        this.zaft.zaee.zafc.add(t);
        return t;
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    public final void onConnected(Bundle bundle) {
        if (zac(1)) {
            if (bundle != null) {
                this.zafz.putAll(bundle);
            }
            if (zaao()) {
                zaaq();
            }
        }
    }

    public final void onConnectionSuspended(int i) {
        zae(new ConnectionResult(8, null));
    }

    private final void zab(boolean z) {
        Oy oy = this.zagb;
        if (oy != null) {
            if (oy.isConnected() && z) {
                this.zagb.a();
            }
            this.zagb.disconnect();
            if (this.zaet.isSignInClientDisconnectFixEnabled()) {
                this.zagb = null;
            }
            this.zagf = null;
        }
    }

    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zac(1)) {
            zab(connectionResult, api, z);
            if (zaao()) {
                zaaq();
            }
        }
    }
}
