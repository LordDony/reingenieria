package com.google.android.gms.common.api.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@KeepForSdk
public final class BackgroundDetector implements ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final BackgroundDetector zzat = new BackgroundDetector();
    private final AtomicBoolean zzau = new AtomicBoolean();
    private final AtomicBoolean zzav = new AtomicBoolean();
    private final ArrayList<BackgroundStateChangeListener> zzaw = new ArrayList<>();
    private boolean zzax = false;

    @KeepForSdk
    public interface BackgroundStateChangeListener {
        @KeepForSdk
        void onBackgroundStateChanged(boolean z);
    }

    @KeepForSdk
    private BackgroundDetector() {
    }

    @KeepForSdk
    public static BackgroundDetector getInstance() {
        return zzat;
    }

    @KeepForSdk
    public static void initialize(Application application) {
        synchronized (zzat) {
            if (!zzat.zzax) {
                application.registerActivityLifecycleCallbacks(zzat);
                application.registerComponentCallbacks(zzat);
                zzat.zzax = true;
            }
        }
    }

    private final void onBackgroundStateChanged(boolean z) {
        synchronized (zzat) {
            ArrayList<BackgroundStateChangeListener> arrayList = this.zzaw;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((BackgroundStateChangeListener) obj).onBackgroundStateChanged(z);
            }
        }
    }

    @KeepForSdk
    public final void addListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        synchronized (zzat) {
            this.zzaw.add(backgroundStateChangeListener);
        }
    }

    @KeepForSdk
    public final boolean isInBackground() {
        return this.zzau.get();
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzau.compareAndSet(true, false);
        this.zzav.set(true);
        if (compareAndSet) {
            onBackgroundStateChanged(false);
        }
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzau.compareAndSet(true, false);
        this.zzav.set(true);
        if (compareAndSet) {
            onBackgroundStateChanged(false);
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzau.compareAndSet(false, true)) {
            this.zzav.set(true);
            onBackgroundStateChanged(true);
        }
    }

    @TargetApi(16)
    @KeepForSdk
    public final boolean readCurrentStateIfPossible(boolean z) {
        if (!this.zzav.get()) {
            if (!PlatformVersion.isAtLeastJellyBean()) {
                return z;
            }
            RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzav.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzau.set(true);
            }
        }
        return isInBackground();
    }
}
