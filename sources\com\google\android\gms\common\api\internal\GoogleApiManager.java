package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@KeepForSdk
public class GoogleApiManager implements Callback {
    /* access modifiers changed from: private */
    public static final Object lock = new Object();
    public static final Status zahx = new Status(4, "Sign-out occurred while this API call was in progress.");
    /* access modifiers changed from: private */
    public static final Status zahy = new Status(4, "The user must be signed in to make this API call.");
    private static GoogleApiManager zaic;
    /* access modifiers changed from: private */
    public final Handler handler;
    /* access modifiers changed from: private */
    public long zahz = 5000;
    /* access modifiers changed from: private */
    public long zaia = 120000;
    /* access modifiers changed from: private */
    public long zaib = 10000;
    /* access modifiers changed from: private */
    public final Context zaid;
    /* access modifiers changed from: private */
    public final GoogleApiAvailability zaie;
    /* access modifiers changed from: private */
    public final GoogleApiAvailabilityCache zaif;
    private final AtomicInteger zaig = new AtomicInteger(1);
    private final AtomicInteger zaih = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public final Map<zai<?>, zaa<?>> zaii = new ConcurrentHashMap(5, 0.75f, 1);
    /* access modifiers changed from: private */
    public zaae zaij = null;
    /* access modifiers changed from: private */
    public final Set<zai<?>> zaik = new V();
    private final Set<zai<?>> zail = new V();

    public class zaa<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zar {
        private final zai<O> zafq;
        private final Queue<zab> zain = new LinkedList();
        /* access modifiers changed from: private */
        public final Client zaio;
        private final AnyClient zaip;
        private final zaab zaiq;
        private final Set<zak> zair = new HashSet();
        private final Map<ListenerKey<?>, zabw> zais = new HashMap();
        private final int zait;
        private final zace zaiu;
        private boolean zaiv;
        private final List<zab> zaiw = new ArrayList();
        private ConnectionResult zaix = null;

        public zaa(GoogleApi<O> googleApi) {
            this.zaio = googleApi.zaa(GoogleApiManager.this.handler.getLooper(), this);
            Client client = this.zaio;
            if (client instanceof SimpleClientAdapter) {
                this.zaip = ((SimpleClientAdapter) client).getClient();
            } else {
                this.zaip = client;
            }
            this.zafq = googleApi.zak();
            this.zaiq = new zaab();
            this.zait = googleApi.getInstanceId();
            if (this.zaio.requiresSignIn()) {
                this.zaiu = googleApi.zaa(GoogleApiManager.this.zaid, GoogleApiManager.this.handler);
            } else {
                this.zaiu = null;
            }
        }

        private final boolean zab(zab zab) {
            if (!(zab instanceof zac)) {
                zac(zab);
                return true;
            }
            zac zac = (zac) zab;
            Feature zaa = zaa(zac.zab(this));
            if (zaa == null) {
                zac(zab);
                return true;
            }
            if (zac.zac(this)) {
                zab zab2 = new zab(this.zafq, zaa, null);
                int indexOf = this.zaiw.indexOf(zab2);
                if (indexOf >= 0) {
                    zab zab3 = (zab) this.zaiw.get(indexOf);
                    GoogleApiManager.this.handler.removeMessages(15, zab3);
                    GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 15, zab3), GoogleApiManager.this.zahz);
                } else {
                    this.zaiw.add(zab2);
                    GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 15, zab2), GoogleApiManager.this.zahz);
                    GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 16, zab2), GoogleApiManager.this.zaia);
                    ConnectionResult connectionResult = new ConnectionResult(2, null);
                    if (!zah(connectionResult)) {
                        GoogleApiManager.this.zac(connectionResult, this.zait);
                    }
                }
            } else {
                zac.zaa((RuntimeException) new UnsupportedApiCallException(zaa));
            }
            return false;
        }

        /* access modifiers changed from: private */
        public final void zabg() {
            zabl();
            zai(ConnectionResult.RESULT_SUCCESS);
            zabn();
            Iterator it = this.zais.values().iterator();
            while (it.hasNext()) {
                zabw zabw = (zabw) it.next();
                if (zaa(zabw.zajx.getRequiredFeatures()) != null) {
                    it.remove();
                } else {
                    try {
                        zabw.zajx.registerListener(this.zaip, new Xy());
                    } catch (DeadObjectException unused) {
                        onConnectionSuspended(1);
                        this.zaio.disconnect();
                    } catch (RemoteException unused2) {
                        it.remove();
                    }
                }
            }
            zabi();
            zabo();
        }

        /* access modifiers changed from: private */
        public final void zabh() {
            zabl();
            this.zaiv = true;
            this.zaiq.zaai();
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 9, this.zafq), GoogleApiManager.this.zahz);
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 11, this.zafq), GoogleApiManager.this.zaia);
            GoogleApiManager.this.zaif.flush();
        }

        private final void zabi() {
            ArrayList arrayList = new ArrayList(this.zain);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                zab zab = (zab) obj;
                if (!this.zaio.isConnected()) {
                    return;
                }
                if (zab(zab)) {
                    this.zain.remove(zab);
                }
            }
        }

        private final void zabn() {
            if (this.zaiv) {
                GoogleApiManager.this.handler.removeMessages(11, this.zafq);
                GoogleApiManager.this.handler.removeMessages(9, this.zafq);
                this.zaiv = false;
            }
        }

        private final void zabo() {
            GoogleApiManager.this.handler.removeMessages(12, this.zafq);
            GoogleApiManager.this.handler.sendMessageDelayed(GoogleApiManager.this.handler.obtainMessage(12, this.zafq), GoogleApiManager.this.zaib);
        }

        private final void zac(zab zab) {
            zab.zaa(this.zaiq, requiresSignIn());
            try {
                zab.zaa(this);
            } catch (DeadObjectException unused) {
                onConnectionSuspended(1);
                this.zaio.disconnect();
            }
        }

        private final boolean zah(ConnectionResult connectionResult) {
            synchronized (GoogleApiManager.lock) {
                if (GoogleApiManager.this.zaij == null || !GoogleApiManager.this.zaik.contains(this.zafq)) {
                    return false;
                }
                GoogleApiManager.this.zaij.zab(connectionResult, this.zait);
                return true;
            }
        }

        private final void zai(ConnectionResult connectionResult) {
            for (zak zak : this.zair) {
                String str = null;
                if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
                    str = this.zaio.getEndpointPackageName();
                }
                zak.zaa(this.zafq, connectionResult, str);
            }
            this.zair.clear();
        }

        public final void connect() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!this.zaio.isConnected() && !this.zaio.isConnecting()) {
                int clientAvailability = GoogleApiManager.this.zaif.getClientAvailability(GoogleApiManager.this.zaid, this.zaio);
                if (clientAvailability != 0) {
                    onConnectionFailed(new ConnectionResult(clientAvailability, null));
                    return;
                }
                zac zac = new zac(this.zaio, this.zafq);
                if (this.zaio.requiresSignIn()) {
                    this.zaiu.zaa((zach) zac);
                }
                this.zaio.connect(zac);
            }
        }

        public final int getInstanceId() {
            return this.zait;
        }

        /* access modifiers changed from: 0000 */
        public final boolean isConnected() {
            return this.zaio.isConnected();
        }

        public final void onConnected(Bundle bundle) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                zabg();
            } else {
                GoogleApiManager.this.handler.post(new zabj(this));
            }
        }

        public final void onConnectionFailed(ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            zace zace = this.zaiu;
            if (zace != null) {
                zace.zabs();
            }
            zabl();
            GoogleApiManager.this.zaif.flush();
            zai(connectionResult);
            if (connectionResult.getErrorCode() == 4) {
                zac(GoogleApiManager.zahy);
            } else if (this.zain.isEmpty()) {
                this.zaix = connectionResult;
            } else {
                if (!zah(connectionResult) && !GoogleApiManager.this.zac(connectionResult, this.zait)) {
                    if (connectionResult.getErrorCode() == 18) {
                        this.zaiv = true;
                    }
                    if (this.zaiv) {
                        GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 9, this.zafq), GoogleApiManager.this.zahz);
                        return;
                    }
                    String zan = this.zafq.zan();
                    StringBuilder sb = new StringBuilder(String.valueOf(zan).length() + 38);
                    sb.append("API: ");
                    sb.append(zan);
                    sb.append(" is not available on this device.");
                    zac(new Status(17, sb.toString()));
                }
            }
        }

        public final void onConnectionSuspended(int i) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                zabh();
            } else {
                GoogleApiManager.this.handler.post(new zabk(this));
            }
        }

        public final boolean requiresSignIn() {
            return this.zaio.requiresSignIn();
        }

        public final void resume() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zaiv) {
                connect();
            }
        }

        public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                onConnectionFailed(connectionResult);
            } else {
                GoogleApiManager.this.handler.post(new zabl(this, connectionResult));
            }
        }

        public final Client zaab() {
            return this.zaio;
        }

        public final void zaav() {
            Status status;
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zaiv) {
                zabn();
                if (GoogleApiManager.this.zaie.isGooglePlayServicesAvailable(GoogleApiManager.this.zaid) == 18) {
                    status = new Status(8, "Connection timed out while waiting for Google Play services update to complete.");
                } else {
                    status = new Status(8, "API failed to connect while resuming due to an unknown error.");
                }
                zac(status);
                this.zaio.disconnect();
            }
        }

        public final void zabj() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            zac(GoogleApiManager.zahx);
            this.zaiq.zaah();
            for (ListenerKey zah : (ListenerKey[]) this.zais.keySet().toArray(new ListenerKey[this.zais.size()])) {
                zaa((zab) new zah(zah, new Xy()));
            }
            zai(new ConnectionResult(4));
            if (this.zaio.isConnected()) {
                this.zaio.onUserSignOut(new zabm(this));
            }
        }

        public final Map<ListenerKey<?>, zabw> zabk() {
            return this.zais;
        }

        public final void zabl() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zaix = null;
        }

        public final ConnectionResult zabm() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            return this.zaix;
        }

        public final boolean zabp() {
            return zac(true);
        }

        /* access modifiers changed from: 0000 */
        public final Oy zabq() {
            zace zace = this.zaiu;
            if (zace == null) {
                return null;
            }
            return zace.zabq();
        }

        public final void zag(ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zaio.disconnect();
            onConnectionFailed(connectionResult);
        }

        public final void zaa(zab zab) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!this.zaio.isConnected()) {
                this.zain.add(zab);
                ConnectionResult connectionResult = this.zaix;
                if (connectionResult == null || !connectionResult.hasResolution()) {
                    connect();
                } else {
                    onConnectionFailed(this.zaix);
                }
            } else if (zab(zab)) {
                zabo();
            } else {
                this.zain.add(zab);
            }
        }

        public final void zac(Status status) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            for (zab zaa : this.zain) {
                zaa.zaa(status);
            }
            this.zain.clear();
        }

        /* access modifiers changed from: private */
        public final boolean zac(boolean z) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!this.zaio.isConnected() || this.zais.size() != 0) {
                return false;
            }
            if (this.zaiq.zaag()) {
                if (z) {
                    zabo();
                }
                return false;
            }
            this.zaio.disconnect();
            return true;
        }

        public final void zaa(zak zak) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zair.add(zak);
        }

        private final Feature zaa(Feature[] featureArr) {
            if (!(featureArr == null || featureArr.length == 0)) {
                Feature[] availableFeatures = this.zaio.getAvailableFeatures();
                if (availableFeatures == null) {
                    availableFeatures = new Feature[0];
                }
                T t = new T(availableFeatures.length);
                for (Feature feature : availableFeatures) {
                    t.put(feature.getName(), Long.valueOf(feature.getVersion()));
                }
                for (Feature feature2 : featureArr) {
                    if (!t.containsKey(feature2.getName()) || ((Long) t.get(feature2.getName())).longValue() < feature2.getVersion()) {
                        return feature2;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: private */
        public final void zaa(zab zab) {
            if (this.zaiw.contains(zab) && !this.zaiv) {
                if (!this.zaio.isConnected()) {
                    connect();
                    return;
                }
                zabi();
            }
        }

        /* access modifiers changed from: private */
        public final void zab(zab zab) {
            if (this.zaiw.remove(zab)) {
                GoogleApiManager.this.handler.removeMessages(15, zab);
                GoogleApiManager.this.handler.removeMessages(16, zab);
                Feature zad = zab.zajc;
                ArrayList arrayList = new ArrayList(this.zain.size());
                for (zab zab2 : this.zain) {
                    if (zab2 instanceof zac) {
                        Feature[] zab3 = ((zac) zab2).zab(this);
                        if (zab3 != null && ArrayUtils.contains((T[]) zab3, zad)) {
                            arrayList.add(zab2);
                        }
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zab zab4 = (zab) obj;
                    this.zain.remove(zab4);
                    zab4.zaa((RuntimeException) new UnsupportedApiCallException(zad));
                }
            }
        }
    }

    private static class zab {
        /* access modifiers changed from: private */
        public final zai<?> zajb;
        /* access modifiers changed from: private */
        public final Feature zajc;

        private zab(zai<?> zai, Feature feature) {
            this.zajb = zai;
            this.zajc = feature;
        }

        public final boolean equals(Object obj) {
            if (obj != null && (obj instanceof zab)) {
                zab zab = (zab) obj;
                if (Objects.equal(this.zajb, zab.zajb) && Objects.equal(this.zajc, zab.zajc)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hashCode(this.zajb, this.zajc);
        }

        public final String toString() {
            return Objects.toStringHelper(this).add("key", this.zajb).add("feature", this.zajc).toString();
        }

        /* synthetic */ zab(zai zai, Feature feature, zabi zabi) {
            this(zai, feature);
        }
    }

    private class zac implements zach, ConnectionProgressReportCallbacks {
        /* access modifiers changed from: private */
        public final zai<?> zafq;
        /* access modifiers changed from: private */
        public final Client zaio;
        private IAccountAccessor zajd = null;
        private Set<Scope> zaje = null;
        /* access modifiers changed from: private */
        public boolean zajf = false;

        public zac(Client client, zai<?> zai) {
            this.zaio = client;
            this.zafq = zai;
        }

        /* access modifiers changed from: private */
        public final void zabr() {
            if (this.zajf) {
                IAccountAccessor iAccountAccessor = this.zajd;
                if (iAccountAccessor != null) {
                    this.zaio.getRemoteService(iAccountAccessor, this.zaje);
                }
            }
        }

        public final void onReportServiceBinding(ConnectionResult connectionResult) {
            GoogleApiManager.this.handler.post(new zabo(this, connectionResult));
        }

        public final void zaa(IAccountAccessor iAccountAccessor, Set<Scope> set) {
            if (iAccountAccessor == null || set == null) {
                Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
                zag(new ConnectionResult(4));
                return;
            }
            this.zajd = iAccountAccessor;
            this.zaje = set;
            zabr();
        }

        public final void zag(ConnectionResult connectionResult) {
            ((zaa) GoogleApiManager.this.zaii.get(this.zafq)).zag(connectionResult);
        }
    }

    @KeepForSdk
    private GoogleApiManager(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zaid = context;
        this.handler = new C1764us(looper, this);
        this.zaie = googleApiAvailability;
        this.zaif = new GoogleApiAvailabilityCache(googleApiAvailability);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(6));
    }

    @KeepForSdk
    public static void reportSignOut() {
        synchronized (lock) {
            if (zaic != null) {
                GoogleApiManager googleApiManager = zaic;
                googleApiManager.zaih.incrementAndGet();
                googleApiManager.handler.sendMessageAtFrontOfQueue(googleApiManager.handler.obtainMessage(10));
            }
        }
    }

    public static GoogleApiManager zab(Context context) {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            if (zaic == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zaic = new GoogleApiManager(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            googleApiManager = zaic;
        }
        return googleApiManager;
    }

    public static GoogleApiManager zabc() {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            Preconditions.checkNotNull(zaic, "Must guarantee manager is non-null before using getInstance");
            googleApiManager = zaic;
        }
        return googleApiManager;
    }

    public boolean handleMessage(Message message) {
        zaa zaa2;
        int i = message.what;
        long j = 300000;
        String str = "GoogleApiManager";
        switch (i) {
            case 1:
                if (((Boolean) message.obj).booleanValue()) {
                    j = 10000;
                }
                this.zaib = j;
                this.handler.removeMessages(12);
                for (zai zai : this.zaii.keySet()) {
                    Handler handler2 = this.handler;
                    handler2.sendMessageDelayed(handler2.obtainMessage(12, zai), this.zaib);
                }
                break;
            case 2:
                zak zak = (zak) message.obj;
                Iterator it = zak.zap().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else {
                        zai zai2 = (zai) it.next();
                        zaa zaa3 = (zaa) this.zaii.get(zai2);
                        if (zaa3 == null) {
                            zak.zaa(zai2, new ConnectionResult(13), null);
                            break;
                        } else if (zaa3.isConnected()) {
                            zak.zaa(zai2, ConnectionResult.RESULT_SUCCESS, zaa3.zaab().getEndpointPackageName());
                        } else if (zaa3.zabm() != null) {
                            zak.zaa(zai2, zaa3.zabm(), null);
                        } else {
                            zaa3.zaa(zak);
                            zaa3.connect();
                        }
                    }
                }
            case 3:
                for (zaa zaa4 : this.zaii.values()) {
                    zaa4.zabl();
                    zaa4.connect();
                }
                break;
            case 4:
            case 8:
            case 13:
                zabv zabv = (zabv) message.obj;
                zaa zaa5 = (zaa) this.zaii.get(zabv.zajt.zak());
                if (zaa5 == null) {
                    zab(zabv.zajt);
                    zaa5 = (zaa) this.zaii.get(zabv.zajt.zak());
                }
                if (zaa5.requiresSignIn() && this.zaih.get() != zabv.zajs) {
                    zabv.zajr.zaa(zahx);
                    zaa5.zabj();
                    break;
                } else {
                    zaa5.zaa(zabv.zajr);
                    break;
                }
                break;
            case 5:
                int i2 = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                Iterator it2 = this.zaii.values().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        zaa2 = (zaa) it2.next();
                        if (zaa2.getInstanceId() == i2) {
                        }
                    } else {
                        zaa2 = null;
                    }
                }
                if (zaa2 == null) {
                    StringBuilder sb = new StringBuilder(76);
                    sb.append("Could not find API instance ");
                    sb.append(i2);
                    sb.append(" while trying to fail enqueued calls.");
                    Log.wtf(str, sb.toString(), new Exception());
                    break;
                } else {
                    String errorString = this.zaie.getErrorString(connectionResult.getErrorCode());
                    String errorMessage = connectionResult.getErrorMessage();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(errorString).length() + 69 + String.valueOf(errorMessage).length());
                    sb2.append("Error resolution was canceled by the user, original error message: ");
                    sb2.append(errorString);
                    sb2.append(": ");
                    sb2.append(errorMessage);
                    zaa2.zac(new Status(17, sb2.toString()));
                    break;
                }
            case 6:
                if (PlatformVersion.isAtLeastIceCreamSandwich() && (this.zaid.getApplicationContext() instanceof Application)) {
                    BackgroundDetector.initialize((Application) this.zaid.getApplicationContext());
                    BackgroundDetector.getInstance().addListener(new zabi(this));
                    if (!BackgroundDetector.getInstance().readCurrentStateIfPossible(true)) {
                        this.zaib = 300000;
                        break;
                    }
                }
                break;
            case 7:
                zab((GoogleApi) message.obj);
                break;
            case 9:
                if (this.zaii.containsKey(message.obj)) {
                    ((zaa) this.zaii.get(message.obj)).resume();
                    break;
                }
                break;
            case 10:
                for (zai remove : this.zail) {
                    ((zaa) this.zaii.remove(remove)).zabj();
                }
                this.zail.clear();
                break;
            case 11:
                if (this.zaii.containsKey(message.obj)) {
                    ((zaa) this.zaii.get(message.obj)).zaav();
                    break;
                }
                break;
            case 12:
                if (this.zaii.containsKey(message.obj)) {
                    ((zaa) this.zaii.get(message.obj)).zabp();
                    break;
                }
                break;
            case 14:
                zaaf zaaf = (zaaf) message.obj;
                zai zak2 = zaaf.zak();
                if (this.zaii.containsKey(zak2)) {
                    zaaf.zaal().a(Boolean.valueOf(((zaa) this.zaii.get(zak2)).zac(false)));
                    break;
                } else {
                    zaaf.zaal().a(Boolean.valueOf(false));
                    break;
                }
            case 15:
                zab zab2 = (zab) message.obj;
                if (this.zaii.containsKey(zab2.zajb)) {
                    ((zaa) this.zaii.get(zab2.zajb)).zaa(zab2);
                    break;
                }
                break;
            case 16:
                zab zab3 = (zab) message.obj;
                if (this.zaii.containsKey(zab3.zajb)) {
                    ((zaa) this.zaii.get(zab3.zajb)).zab(zab3);
                    break;
                }
                break;
            default:
                StringBuilder sb3 = new StringBuilder(31);
                sb3.append("Unknown message id: ");
                sb3.append(i);
                Log.w(str, sb3.toString());
                return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void maybeSignOut() {
        this.zaih.incrementAndGet();
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(10));
    }

    public final void zaa(GoogleApi<?> googleApi) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(7, googleApi));
    }

    public final int zabd() {
        return this.zaig.getAndIncrement();
    }

    public final Wy<Boolean> zac(GoogleApi<?> googleApi) {
        zaaf zaaf = new zaaf(googleApi.zak());
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(14, zaaf));
        return zaaf.zaal().a();
    }

    public final void zao() {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(3));
    }

    public final void zaa(zaae zaae) {
        synchronized (lock) {
            if (this.zaij != zaae) {
                this.zaij = zaae;
                this.zaik.clear();
            }
            this.zaik.addAll(zaae.zaaj());
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean zac(ConnectionResult connectionResult, int i) {
        return this.zaie.zaa(this.zaid, connectionResult, i);
    }

    public final Wy<Map<zai<?>, String>> zaa(Iterable<? extends GoogleApi<?>> iterable) {
        zak zak = new zak(iterable);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(2, zak));
        return zak.getTask();
    }

    private final void zab(GoogleApi<?> googleApi) {
        zai zak = googleApi.zak();
        zaa zaa2 = (zaa) this.zaii.get(zak);
        if (zaa2 == null) {
            zaa2 = new zaa(googleApi);
            this.zaii.put(zak, zaa2);
        }
        if (zaa2.requiresSignIn()) {
            this.zail.add(zak);
        }
        zaa2.connect();
    }

    public final <O extends ApiOptions> void zaa(GoogleApi<O> googleApi, int i, ApiMethodImpl<? extends Result, AnyClient> apiMethodImpl) {
        zae zae = new zae(i, apiMethodImpl);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(4, new zabv(zae, this.zaih.get(), googleApi)));
    }

    public final <O extends ApiOptions, ResultT> void zaa(GoogleApi<O> googleApi, int i, TaskApiCall<AnyClient, ResultT> taskApiCall, Xy<ResultT> xy, StatusExceptionMapper statusExceptionMapper) {
        zag zag = new zag(i, taskApiCall, xy, statusExceptionMapper);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(4, new zabv(zag, this.zaih.get(), googleApi)));
    }

    /* access modifiers changed from: 0000 */
    public final void zab(zaae zaae) {
        synchronized (lock) {
            if (this.zaij == zaae) {
                this.zaij = null;
                this.zaik.clear();
            }
        }
    }

    public final <O extends ApiOptions> Wy<Void> zaa(GoogleApi<O> googleApi, RegisterListenerMethod<AnyClient, ?> registerListenerMethod, UnregisterListenerMethod<AnyClient, ?> unregisterListenerMethod) {
        Xy xy = new Xy();
        zaf zaf = new zaf(new zabw(registerListenerMethod, unregisterListenerMethod), xy);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(8, new zabv(zaf, this.zaih.get(), googleApi)));
        return xy.a();
    }

    public final <O extends ApiOptions> Wy<Boolean> zaa(GoogleApi<O> googleApi, ListenerKey<?> listenerKey) {
        Xy xy = new Xy();
        zah zah = new zah(listenerKey, xy);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(13, new zabv(zah, this.zaih.get(), googleApi)));
        return xy.a();
    }

    /* access modifiers changed from: 0000 */
    public final PendingIntent zaa(zai<?> zai, int i) {
        zaa zaa2 = (zaa) this.zaii.get(zai);
        if (zaa2 == null) {
            return null;
        }
        Oy zabq = zaa2.zabq();
        if (zabq == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zaid, i, zabq.getSignInIntent(), 134217728);
    }

    public final void zaa(ConnectionResult connectionResult, int i) {
        if (!zac(connectionResult, i)) {
            Handler handler2 = this.handler;
            handler2.sendMessage(handler2.obtainMessage(5, i, 0, connectionResult));
        }
    }
}
