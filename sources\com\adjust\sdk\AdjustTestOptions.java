package com.adjust.sdk;

import android.content.Context;

public class AdjustTestOptions {
    public String basePath;
    public String baseUrl;
    public Context context;
    public String gdprPath;
    public String gdprUrl;
    public Boolean noBackoffWait;
    public Long sessionIntervalInMilliseconds;
    public Long subsessionIntervalInMilliseconds;
    public Boolean teardown;
    public Long timerIntervalInMilliseconds;
    public Long timerStartInMilliseconds;
    public Boolean tryInstallReferrer = Boolean.valueOf(false);
    public Boolean useTestConnectionOptions;
}
