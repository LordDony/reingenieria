package com.comscore.android.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.comscore.Analytics;

@SuppressLint({"NewApi"})
@TargetApi(14)
public class BackgroundManager implements ActivityLifecycleCallbacks {
    private Application a;

    public BackgroundManager(Application application) {
        this.a = application;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
        Analytics.notifyExitForeground();
    }

    public void onActivityResumed(Activity activity) {
        Analytics.notifyEnterForeground();
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void register() {
        this.a.registerActivityLifecycleCallbacks(this);
    }

    public void unregister() {
        this.a.unregisterActivityLifecycleCallbacks(this);
    }
}
