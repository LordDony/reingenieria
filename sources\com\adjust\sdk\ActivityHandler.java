package com.adjust.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import com.adjust.sdk.scheduler.SingleThreadCachedScheduler;
import com.adjust.sdk.scheduler.ThreadExecutor;
import com.adjust.sdk.scheduler.TimerCycle;
import com.adjust.sdk.scheduler.TimerOnce;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ActivityHandler implements IActivityHandler {
    private static final String ACTIVITY_STATE_NAME = "Activity state";
    private static final String ATTRIBUTION_NAME = "Attribution";
    private static long BACKGROUND_TIMER_INTERVAL = 0;
    private static final String BACKGROUND_TIMER_NAME = "Background timer";
    private static final String DELAY_START_TIMER_NAME = "Delay Start timer";
    private static long FOREGROUND_TIMER_INTERVAL = 0;
    private static final String FOREGROUND_TIMER_NAME = "Foreground timer";
    private static long FOREGROUND_TIMER_START = 0;
    private static final String SESSION_CALLBACK_PARAMETERS_NAME = "Session Callback parameters";
    private static long SESSION_INTERVAL = 0;
    private static final String SESSION_PARAMETERS_NAME = "Session parameters";
    private static final String SESSION_PARTNER_PARAMETERS_NAME = "Session Partner parameters";
    private static long SUBSESSION_INTERVAL = 0;
    private static final String TIME_TRAVEL = "Time travel!";
    private ActivityState activityState;
    /* access modifiers changed from: private */
    public AdjustConfig adjustConfig;
    /* access modifiers changed from: private */
    public AdjustAttribution attribution;
    private IAttributionHandler attributionHandler;
    private TimerOnce backgroundTimer;
    private String basePath;
    private TimerOnce delayStartTimer;
    private DeviceInfo deviceInfo;
    private ThreadExecutor executor;
    private TimerCycle foregroundTimer;
    private String gdprPath;
    private InstallReferrer installReferrer;
    /* access modifiers changed from: private */
    public InternalState internalState;
    /* access modifiers changed from: private */
    public ILogger logger = AdjustFactory.getLogger();
    private IPackageHandler packageHandler;
    private ISdkClickHandler sdkClickHandler;
    private SessionParameters sessionParameters;

    public class InternalState {
        boolean background;
        boolean delayStart;
        boolean enabled;
        boolean firstLaunch;
        boolean firstSdkStart;
        boolean offline;
        boolean sessionResponseProcessed;
        boolean updatePackages;

        public InternalState() {
        }

        public boolean hasFirstSdkStartNotOcurred() {
            return !this.firstSdkStart;
        }

        public boolean hasFirstSdkStartOcurred() {
            return this.firstSdkStart;
        }

        public boolean hasSessionResponseNotBeenProcessed() {
            return !this.sessionResponseProcessed;
        }

        public boolean isDisabled() {
            return !this.enabled;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public boolean isFirstLaunch() {
            return this.firstLaunch;
        }

        public boolean isInBackground() {
            return this.background;
        }

        public boolean isInDelayedStart() {
            return this.delayStart;
        }

        public boolean isInForeground() {
            return !this.background;
        }

        public boolean isNotFirstLaunch() {
            return !this.firstLaunch;
        }

        public boolean isNotInDelayedStart() {
            return !this.delayStart;
        }

        public boolean isOffline() {
            return this.offline;
        }

        public boolean isOnline() {
            return !this.offline;
        }

        public boolean itHasToUpdatePackages() {
            return this.updatePackages;
        }
    }

    private ActivityHandler(AdjustConfig adjustConfig2) {
        init(adjustConfig2);
        this.logger.lockLogLevel();
        this.executor = new SingleThreadCachedScheduler("ActivityHandler");
        this.internalState = new InternalState();
        InternalState internalState2 = this.internalState;
        Boolean bool = adjustConfig2.startEnabled;
        internalState2.enabled = bool != null ? bool.booleanValue() : true;
        InternalState internalState3 = this.internalState;
        internalState3.offline = adjustConfig2.startOffline;
        internalState3.background = true;
        internalState3.delayStart = false;
        internalState3.updatePackages = false;
        internalState3.sessionResponseProcessed = false;
        internalState3.firstSdkStart = false;
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.initI();
            }
        });
    }

    /* access modifiers changed from: private */
    public void backgroundTimerFiredI() {
        if (toSendI()) {
            this.packageHandler.sendFirstPackage();
        }
    }

    private boolean checkActivityStateI(ActivityState activityState2) {
        if (!this.internalState.hasFirstSdkStartNotOcurred()) {
            return true;
        }
        this.logger.error("Sdk did not yet start", new Object[0]);
        return false;
    }

    private void checkAfterNewStartI() {
        checkAfterNewStartI(new SharedPreferencesManager(getContext()));
    }

    private void checkAttributionStateI() {
        if (checkActivityStateI(this.activityState)) {
            if (this.internalState.isFirstLaunch() && this.internalState.hasSessionResponseNotBeenProcessed()) {
                return;
            }
            if (this.attribution == null || this.activityState.askingAttribution) {
                this.attributionHandler.getAttribution();
            }
        }
    }

    private boolean checkEventI(AdjustEvent adjustEvent) {
        if (adjustEvent == null) {
            this.logger.error("Event missing", new Object[0]);
            return false;
        } else if (adjustEvent.isValid()) {
            return true;
        } else {
            this.logger.error("Event not initialized correctly", new Object[0]);
            return false;
        }
    }

    private void checkForInstallReferrerInfo(SdkClickResponseData sdkClickResponseData) {
        if (sdkClickResponseData.isInstallReferrer) {
            ActivityState activityState2 = this.activityState;
            activityState2.clickTime = sdkClickResponseData.clickTime;
            activityState2.installBegin = sdkClickResponseData.installBegin;
            activityState2.installReferrer = sdkClickResponseData.installReferrer;
            writeActivityStateI();
        }
    }

    private boolean checkOrderIdI(String str) {
        if (str != null && !str.isEmpty()) {
            if (this.activityState.findOrderId(str)) {
                this.logger.info("Skipping duplicated order ID '%s'", str);
                return false;
            }
            this.activityState.addOrderId(str);
            this.logger.verbose("Added order ID '%s'", str);
        }
        return true;
    }

    private Intent createDeeplinkIntentI(Uri uri) {
        Intent intent;
        AdjustConfig adjustConfig2 = this.adjustConfig;
        Class cls = adjustConfig2.deepLinkComponent;
        String str = "android.intent.action.VIEW";
        if (cls == null) {
            intent = new Intent(str, uri);
        } else {
            intent = new Intent(str, uri, adjustConfig2.context, cls);
        }
        intent.setFlags(268435456);
        intent.setPackage(this.adjustConfig.context.getPackageName());
        return intent;
    }

    /* access modifiers changed from: private */
    public void delayStartI() {
        if (!this.internalState.isNotInDelayedStart() && !isToUpdatePackagesI()) {
            Double d = this.adjustConfig.delayStart;
            double doubleValue = d != null ? d.doubleValue() : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            long maxDelayStart = AdjustFactory.getMaxDelayStart();
            long j = (long) (1000.0d * doubleValue);
            if (j > maxDelayStart) {
                double d2 = (double) (maxDelayStart / 1000);
                this.logger.warn("Delay start of %s seconds bigger than max allowed value of %s seconds", Util.SecondsDisplayFormat.format(doubleValue), Util.SecondsDisplayFormat.format(d2));
                doubleValue = d2;
            } else {
                maxDelayStart = j;
            }
            this.logger.info("Waiting %s seconds before starting first session", Util.SecondsDisplayFormat.format(doubleValue));
            this.delayStartTimer.startIn(maxDelayStart);
            this.internalState.updatePackages = true;
            ActivityState activityState2 = this.activityState;
            if (activityState2 != null) {
                activityState2.updatePackages = true;
                writeActivityStateI();
            }
        }
    }

    public static boolean deleteActivityState(Context context) {
        return context.deleteFile(Constants.ACTIVITY_STATE_FILENAME);
    }

    public static boolean deleteAttribution(Context context) {
        return context.deleteFile(Constants.ATTRIBUTION_FILENAME);
    }

    public static boolean deleteSessionCallbackParameters(Context context) {
        return context.deleteFile(Constants.SESSION_CALLBACK_PARAMETERS_FILENAME);
    }

    public static boolean deleteSessionPartnerParameters(Context context) {
        return context.deleteFile(Constants.SESSION_PARTNER_PARAMETERS_FILENAME);
    }

    static void deleteState(Context context) {
        deleteActivityState(context);
        deleteAttribution(context);
        deleteSessionCallbackParameters(context);
        deleteSessionPartnerParameters(context);
        new SharedPreferencesManager(context).clear();
    }

    /* access modifiers changed from: private */
    public void endI() {
        if (!toSendI()) {
            pauseSendingI();
        }
        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    /* access modifiers changed from: private */
    public void foregroundTimerFiredI() {
        if (!isEnabledI()) {
            stopForegroundTimerI();
            return;
        }
        if (toSendI()) {
            this.packageHandler.sendFirstPackage();
        }
        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    /* access modifiers changed from: private */
    public void gdprForgetMeI() {
        if (checkActivityStateI(this.activityState) && isEnabledI()) {
            ActivityState activityState2 = this.activityState;
            if (!activityState2.isGdprForgotten) {
                activityState2.isGdprForgotten = true;
                writeActivityStateI();
                PackageBuilder packageBuilder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, this.sessionParameters, System.currentTimeMillis());
                ActivityPackage buildGdprPackage = packageBuilder.buildGdprPackage();
                this.packageHandler.addPackage(buildGdprPackage);
                new SharedPreferencesManager(getContext()).removeGdprForgetMe();
                if (this.adjustConfig.eventBufferingEnabled) {
                    this.logger.info("Buffered event %s", buildGdprPackage.getSuffix());
                } else {
                    this.packageHandler.sendFirstPackage();
                }
            }
        }
    }

    public static ActivityHandler getInstance(AdjustConfig adjustConfig2) {
        if (adjustConfig2 == null) {
            AdjustFactory.getLogger().error("AdjustConfig missing", new Object[0]);
            return null;
        } else if (!adjustConfig2.isValid()) {
            AdjustFactory.getLogger().error("AdjustConfig not initialized correctly", new Object[0]);
            return null;
        } else {
            if (adjustConfig2.processName != null) {
                int myPid = Process.myPid();
                ActivityManager activityManager = (ActivityManager) adjustConfig2.context.getSystemService("activity");
                if (activityManager != null) {
                    Iterator it = activityManager.getRunningAppProcesses().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) it.next();
                        if (runningAppProcessInfo.pid == myPid) {
                            if (!runningAppProcessInfo.processName.equalsIgnoreCase(adjustConfig2.processName)) {
                                AdjustFactory.getLogger().info("Skipping initialization in background process (%s)", runningAppProcessInfo.processName);
                                return null;
                            }
                        }
                    }
                } else {
                    return null;
                }
            }
            return new ActivityHandler(adjustConfig2);
        }
    }

    /* access modifiers changed from: private */
    public void gotOptOutResponseI() {
        this.activityState.isGdprForgotten = true;
        writeActivityStateI();
        this.packageHandler.flush();
        setEnabledI(false);
    }

    private boolean hasChangedStateI(boolean z, boolean z2, String str, String str2) {
        if (z != z2) {
            return true;
        }
        if (z) {
            this.logger.debug(str, new Object[0]);
        } else {
            this.logger.debug(str2, new Object[0]);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void initI() {
        SESSION_INTERVAL = AdjustFactory.getSessionInterval();
        SUBSESSION_INTERVAL = AdjustFactory.getSubsessionInterval();
        FOREGROUND_TIMER_INTERVAL = AdjustFactory.getTimerInterval();
        FOREGROUND_TIMER_START = AdjustFactory.getTimerStart();
        BACKGROUND_TIMER_INTERVAL = AdjustFactory.getTimerInterval();
        readAttributionI(this.adjustConfig.context);
        readActivityStateI(this.adjustConfig.context);
        this.sessionParameters = new SessionParameters();
        readSessionCallbackParametersI(this.adjustConfig.context);
        readSessionPartnerParametersI(this.adjustConfig.context);
        AdjustConfig adjustConfig2 = this.adjustConfig;
        if (adjustConfig2.startEnabled != null) {
            if (adjustConfig2.preLaunchActionsArray == null) {
                adjustConfig2.preLaunchActionsArray = new ArrayList();
            }
            this.adjustConfig.preLaunchActionsArray.add(new IRunActivityHandler() {
                public void run(ActivityHandler activityHandler) {
                    activityHandler.setEnabledI(ActivityHandler.this.adjustConfig.startEnabled.booleanValue());
                }
            });
        }
        if (this.internalState.hasFirstSdkStartOcurred()) {
            InternalState internalState2 = this.internalState;
            ActivityState activityState2 = this.activityState;
            internalState2.enabled = activityState2.enabled;
            internalState2.updatePackages = activityState2.updatePackages;
            internalState2.firstLaunch = false;
        } else {
            this.internalState.firstLaunch = true;
        }
        readConfigFile(this.adjustConfig.context);
        AdjustConfig adjustConfig3 = this.adjustConfig;
        this.deviceInfo = new DeviceInfo(adjustConfig3.context, adjustConfig3.sdkPrefix);
        if (this.adjustConfig.eventBufferingEnabled) {
            this.logger.info("Event buffering is enabled", new Object[0]);
        }
        this.deviceInfo.reloadPlayIds(this.adjustConfig.context);
        if (this.deviceInfo.playAdId == null) {
            this.logger.warn("Unable to get Google Play Services Advertising ID at start time", new Object[0]);
            DeviceInfo deviceInfo2 = this.deviceInfo;
            if (deviceInfo2.macSha1 == null && deviceInfo2.macShortMd5 == null && deviceInfo2.androidId == null) {
                this.logger.error("Unable to get any device id's. Please check if Proguard is correctly set with Adjust SDK", new Object[0]);
            }
        } else {
            this.logger.info("Google Play Services Advertising ID read correctly at start time", new Object[0]);
        }
        String str = this.adjustConfig.defaultTracker;
        if (str != null) {
            this.logger.info("Default tracker: '%s'", str);
        }
        String str2 = this.adjustConfig.pushToken;
        if (str2 != null) {
            this.logger.info("Push token: '%s'", str2);
            if (this.internalState.hasFirstSdkStartOcurred()) {
                setPushToken(this.adjustConfig.pushToken, false);
            } else {
                new SharedPreferencesManager(getContext()).savePushToken(this.adjustConfig.pushToken);
            }
        } else if (this.internalState.hasFirstSdkStartOcurred()) {
            setPushToken(new SharedPreferencesManager(getContext()).getPushToken(), true);
        }
        if (this.internalState.hasFirstSdkStartOcurred() && new SharedPreferencesManager(getContext()).getGdprForgetMe()) {
            gdprForgetMe();
        }
        TimerCycle timerCycle = new TimerCycle(new Runnable() {
            public void run() {
                ActivityHandler.this.foregroundTimerFired();
            }
        }, FOREGROUND_TIMER_START, FOREGROUND_TIMER_INTERVAL, FOREGROUND_TIMER_NAME);
        this.foregroundTimer = timerCycle;
        if (this.adjustConfig.sendInBackground) {
            this.logger.info("Send in background configured", new Object[0]);
            this.backgroundTimer = new TimerOnce(new Runnable() {
                public void run() {
                    ActivityHandler.this.backgroundTimerFired();
                }
            }, BACKGROUND_TIMER_NAME);
        }
        if (this.internalState.hasFirstSdkStartNotOcurred()) {
            Double d = this.adjustConfig.delayStart;
            if (d != null && d.doubleValue() > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                this.logger.info("Delay start configured", new Object[0]);
                this.internalState.delayStart = true;
                this.delayStartTimer = new TimerOnce(new Runnable() {
                    public void run() {
                        ActivityHandler.this.sendFirstPackages();
                    }
                }, DELAY_START_TIMER_NAME);
            }
        }
        UtilNetworking.setUserAgent(this.adjustConfig.userAgent);
        AdjustConfig adjustConfig4 = this.adjustConfig;
        this.basePath = adjustConfig4.basePath;
        this.gdprPath = adjustConfig4.gdprPath;
        this.packageHandler = AdjustFactory.getPackageHandler(this, adjustConfig4.context, toSendI(false));
        this.attributionHandler = AdjustFactory.getAttributionHandler(this, toSendI(false));
        this.sdkClickHandler = AdjustFactory.getSdkClickHandler(this, toSendI(true));
        if (isToUpdatePackagesI()) {
            updatePackagesI();
        }
        this.installReferrer = new InstallReferrer(this.adjustConfig.context, new InstallReferrerReadListener() {
            public void onInstallReferrerRead(String str, long j, long j2) {
                ActivityHandler.this.sendInstallReferrer(str, j, j2);
            }
        });
        preLaunchActionsI(this.adjustConfig.preLaunchActionsArray);
        sendReftagReferrerI();
    }

    private boolean isEnabledI() {
        ActivityState activityState2 = this.activityState;
        if (activityState2 != null) {
            return activityState2.enabled;
        }
        return this.internalState.isEnabled();
    }

    private boolean isToUpdatePackagesI() {
        ActivityState activityState2 = this.activityState;
        if (activityState2 != null) {
            return activityState2.updatePackages;
        }
        return this.internalState.itHasToUpdatePackages();
    }

    private void launchAttributionListenerI(Handler handler) {
        if (this.adjustConfig.onAttributionChangedListener != null) {
            handler.post(new Runnable() {
                public void run() {
                    if (ActivityHandler.this.adjustConfig != null && ActivityHandler.this.adjustConfig.onAttributionChangedListener != null) {
                        ActivityHandler.this.adjustConfig.onAttributionChangedListener.onAttributionChanged(ActivityHandler.this.attribution);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void launchAttributionResponseTasksI(AttributionResponseData attributionResponseData) {
        updateAdidI(attributionResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttributionI(attributionResponseData.attribution)) {
            launchAttributionListenerI(handler);
        }
        prepareDeeplinkI(attributionResponseData.deeplink, handler);
    }

    /* access modifiers changed from: private */
    public void launchDeeplinkMain(Intent intent, Uri uri) {
        if (!(this.adjustConfig.context.getPackageManager().queryIntentActivities(intent, 0).size() > 0)) {
            this.logger.error("Unable to open deferred deep link (%s)", uri);
            return;
        }
        this.logger.info("Open deferred deep link (%s)", uri);
        this.adjustConfig.context.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void launchEventResponseTasksI(final EventResponseData eventResponseData) {
        updateAdidI(eventResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (!eventResponseData.success || this.adjustConfig.onEventTrackingSucceededListener == null) {
            if (!eventResponseData.success && this.adjustConfig.onEventTrackingFailedListener != null) {
                this.logger.debug("Launching failed event tracking listener", new Object[0]);
                handler.post(new Runnable() {
                    public void run() {
                        if (ActivityHandler.this.adjustConfig != null && ActivityHandler.this.adjustConfig.onEventTrackingFailedListener != null) {
                            ActivityHandler.this.adjustConfig.onEventTrackingFailedListener.onFinishedEventTrackingFailed(eventResponseData.getFailureResponseData());
                        }
                    }
                });
            }
            return;
        }
        this.logger.debug("Launching success event tracking listener", new Object[0]);
        handler.post(new Runnable() {
            public void run() {
                if (ActivityHandler.this.adjustConfig != null && ActivityHandler.this.adjustConfig.onEventTrackingSucceededListener != null) {
                    ActivityHandler.this.adjustConfig.onEventTrackingSucceededListener.onFinishedEventTrackingSucceeded(eventResponseData.getSuccessResponseData());
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void launchSdkClickResponseTasksI(SdkClickResponseData sdkClickResponseData) {
        updateAdidI(sdkClickResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttributionI(sdkClickResponseData.attribution)) {
            launchAttributionListenerI(handler);
        }
    }

    private void launchSessionResponseListenerI(final SessionResponseData sessionResponseData, Handler handler) {
        if (!sessionResponseData.success || this.adjustConfig.onSessionTrackingSucceededListener == null) {
            if (!sessionResponseData.success && this.adjustConfig.onSessionTrackingFailedListener != null) {
                this.logger.debug("Launching failed session tracking listener", new Object[0]);
                handler.post(new Runnable() {
                    public void run() {
                        if (ActivityHandler.this.adjustConfig != null && ActivityHandler.this.adjustConfig.onSessionTrackingFailedListener != null) {
                            ActivityHandler.this.adjustConfig.onSessionTrackingFailedListener.onFinishedSessionTrackingFailed(sessionResponseData.getFailureResponseData());
                        }
                    }
                });
            }
            return;
        }
        this.logger.debug("Launching success session tracking listener", new Object[0]);
        handler.post(new Runnable() {
            public void run() {
                if (ActivityHandler.this.adjustConfig != null && ActivityHandler.this.adjustConfig.onSessionTrackingSucceededListener != null) {
                    ActivityHandler.this.adjustConfig.onSessionTrackingSucceededListener.onFinishedSessionTrackingSucceeded(sessionResponseData.getSuccessResponseData());
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void launchSessionResponseTasksI(SessionResponseData sessionResponseData) {
        updateAdidI(sessionResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttributionI(sessionResponseData.attribution)) {
            launchAttributionListenerI(handler);
        }
        if (this.attribution == null && !this.activityState.askingAttribution) {
            this.attributionHandler.getAttribution();
        }
        if (sessionResponseData.success) {
            new SharedPreferencesManager(getContext()).setInstallTracked();
        }
        launchSessionResponseListenerI(sessionResponseData, handler);
        this.internalState.sessionResponseProcessed = true;
    }

    private void pauseSendingI() {
        this.attributionHandler.pauseSending();
        this.packageHandler.pauseSending();
        if (!toSendI(true)) {
            this.sdkClickHandler.pauseSending();
        } else {
            this.sdkClickHandler.resumeSending();
        }
    }

    private boolean pausedI() {
        return pausedI(false);
    }

    private void preLaunchActionsI(List<IRunActivityHandler> list) {
        if (list != null) {
            for (IRunActivityHandler run : list) {
                run.run(this);
            }
        }
    }

    private void prepareDeeplinkI(final Uri uri, Handler handler) {
        if (uri != null) {
            this.logger.info("Deferred deeplink received (%s)", uri);
            final Intent createDeeplinkIntentI = createDeeplinkIntentI(uri);
            handler.post(new Runnable() {
                public void run() {
                    if (ActivityHandler.this.adjustConfig != null) {
                        boolean z = true;
                        if (ActivityHandler.this.adjustConfig.onDeeplinkResponseListener != null) {
                            z = ActivityHandler.this.adjustConfig.onDeeplinkResponseListener.launchReceivedDeeplink(uri);
                        }
                        if (z) {
                            ActivityHandler.this.launchDeeplinkMain(createDeeplinkIntentI, uri);
                        }
                    }
                }
            });
        }
    }

    private void processCachedDeeplinkI() {
        if (checkActivityStateI(this.activityState)) {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
            String deeplinkUrl = sharedPreferencesManager.getDeeplinkUrl();
            long deeplinkClickTime = sharedPreferencesManager.getDeeplinkClickTime();
            if (deeplinkUrl != null && deeplinkClickTime != -1) {
                readOpenUrl(Uri.parse(deeplinkUrl), deeplinkClickTime);
                sharedPreferencesManager.removeDeeplink();
            }
        }
    }

    private void processSessionI() {
        if (!this.activityState.isGdprForgotten) {
            long currentTimeMillis = System.currentTimeMillis();
            ActivityState activityState2 = this.activityState;
            long j = currentTimeMillis - activityState2.lastActivity;
            if (j < 0) {
                this.logger.error(TIME_TRAVEL, new Object[0]);
                this.activityState.lastActivity = currentTimeMillis;
                writeActivityStateI();
            } else if (j > SESSION_INTERVAL) {
                trackNewSessionI(currentTimeMillis);
                checkAfterNewStartI();
            } else if (j > SUBSESSION_INTERVAL) {
                activityState2.subsessionCount++;
                activityState2.sessionLength += j;
                activityState2.lastActivity = currentTimeMillis;
                this.logger.verbose("Started subsession %d of session %d", Integer.valueOf(activityState2.subsessionCount), Integer.valueOf(this.activityState.sessionCount));
                writeActivityStateI();
                this.installReferrer.startConnection();
            } else {
                this.logger.verbose("Time span since last activity too short for a new subsession", new Object[0]);
            }
        }
    }

    private void readActivityStateI(Context context) {
        String str = ACTIVITY_STATE_NAME;
        try {
            this.activityState = (ActivityState) Util.readObject(context, Constants.ACTIVITY_STATE_FILENAME, str, ActivityState.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", str, e.getMessage());
            this.activityState = null;
        }
        if (this.activityState != null) {
            this.internalState.firstSdkStart = true;
        }
    }

    private void readAttributionI(Context context) {
        String str = ATTRIBUTION_NAME;
        try {
            this.attribution = (AdjustAttribution) Util.readObject(context, Constants.ATTRIBUTION_FILENAME, str, AdjustAttribution.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", str, e.getMessage());
            this.attribution = null;
        }
    }

    private void readConfigFile(Context context) {
        try {
            InputStream open = context.getAssets().open("adjust_config.properties");
            Properties properties = new Properties();
            properties.load(open);
            this.logger.verbose("adjust_config.properties file read and loaded", new Object[0]);
            String property = properties.getProperty("defaultTracker");
            if (property != null) {
                this.adjustConfig.defaultTracker = property;
            }
        } catch (Exception e) {
            this.logger.debug("%s file not found in this app", e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void readOpenUrlI(Uri uri, long j) {
        if (isEnabledI()) {
            if (Util.isUrlFilteredOut(uri)) {
                ILogger iLogger = this.logger;
                StringBuilder sb = new StringBuilder();
                sb.append("Deep link (");
                sb.append(uri.toString());
                sb.append(") processing skipped");
                iLogger.debug(sb.toString(), new Object[0]);
                return;
            }
            ActivityPackage buildDeeplinkSdkClickPackage = PackageFactory.buildDeeplinkSdkClickPackage(uri, j, this.activityState, this.adjustConfig, this.deviceInfo, this.sessionParameters);
            if (buildDeeplinkSdkClickPackage != null) {
                this.sdkClickHandler.sendSdkClick(buildDeeplinkSdkClickPackage);
            }
        }
    }

    private void readSessionCallbackParametersI(Context context) {
        String str = SESSION_CALLBACK_PARAMETERS_NAME;
        try {
            this.sessionParameters.callbackParameters = (Map) Util.readObject(context, Constants.SESSION_CALLBACK_PARAMETERS_FILENAME, str, Map.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", str, e.getMessage());
            this.sessionParameters.callbackParameters = null;
        }
    }

    private void readSessionPartnerParametersI(Context context) {
        String str = SESSION_PARTNER_PARAMETERS_NAME;
        try {
            this.sessionParameters.partnerParameters = (Map) Util.readObject(context, Constants.SESSION_PARTNER_PARAMETERS_FILENAME, str, Map.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", str, e.getMessage());
            this.sessionParameters.partnerParameters = null;
        }
    }

    private void resumeSendingI() {
        this.attributionHandler.resumeSending();
        this.packageHandler.resumeSending();
        this.sdkClickHandler.resumeSending();
    }

    /* access modifiers changed from: private */
    public void sendFirstPackagesI() {
        if (this.internalState.isNotInDelayedStart()) {
            this.logger.info("Start delay expired or never configured", new Object[0]);
            return;
        }
        updatePackagesI();
        this.internalState.delayStart = false;
        this.delayStartTimer.cancel();
        this.delayStartTimer = null;
        updateHandlersStatusAndSendI();
    }

    /* access modifiers changed from: private */
    public void sendInstallReferrerI(String str, long j, long j2) {
        if (isEnabledI() && str != null) {
            ActivityState activityState2 = this.activityState;
            if (j != activityState2.clickTime || j2 != activityState2.installBegin || !str.equals(activityState2.installReferrer)) {
                this.sdkClickHandler.sendSdkClick(PackageFactory.buildInstallReferrerSdkClickPackage(str, j, j2, this.activityState, this.adjustConfig, this.deviceInfo, this.sessionParameters));
            }
        }
    }

    /* access modifiers changed from: private */
    public void sendReftagReferrerI() {
        if (isEnabledI() && !this.internalState.hasFirstSdkStartNotOcurred()) {
            this.sdkClickHandler.sendReftagReferrers();
        }
    }

    /* access modifiers changed from: private */
    public void setAskingAttributionI(boolean z) {
        this.activityState.askingAttribution = z;
        writeActivityStateI();
    }

    /* access modifiers changed from: private */
    public void setEnabledI(boolean z) {
        if (hasChangedStateI(isEnabledI(), z, "Adjust already enabled", "Adjust already disabled")) {
            if (!z || !this.activityState.isGdprForgotten) {
                InternalState internalState2 = this.internalState;
                internalState2.enabled = z;
                if (internalState2.hasFirstSdkStartNotOcurred()) {
                    updateStatusI(!z, "Handlers will start as paused due to the SDK being disabled", "Handlers will still start as paused", "Handlers will start as active due to the SDK being enabled");
                    return;
                }
                this.activityState.enabled = z;
                writeActivityStateI();
                if (z) {
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                    if (sharedPreferencesManager.getGdprForgetMe()) {
                        gdprForgetMeI();
                    }
                    if (!sharedPreferencesManager.getInstallTracked()) {
                        trackNewSessionI(System.currentTimeMillis());
                    }
                    checkAfterNewStartI(sharedPreferencesManager);
                }
                updateStatusI(!z, "Pausing handlers due to SDK being disabled", "Handlers remain paused", "Resuming handlers due to SDK being enabled");
                return;
            }
            this.logger.error("Re-enabling SDK not possible for forgotten user", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void setOfflineModeI(boolean z) {
        if (hasChangedStateI(this.internalState.isOffline(), z, "Adjust already in offline mode", "Adjust already in online mode")) {
            InternalState internalState2 = this.internalState;
            internalState2.offline = z;
            if (internalState2.hasFirstSdkStartNotOcurred()) {
                updateStatusI(z, "Handlers will start paused due to SDK being offline", "Handlers will still start as paused", "Handlers will start as active due to SDK being online");
            } else {
                updateStatusI(z, "Pausing handlers to put SDK offline mode", "Handlers remain paused", "Resuming handlers to put SDK in online mode");
            }
        }
    }

    /* access modifiers changed from: private */
    public void setPushTokenI(String str) {
        if (checkActivityStateI(this.activityState) && isEnabledI()) {
            ActivityState activityState2 = this.activityState;
            if (!activityState2.isGdprForgotten && str != null && !str.equals(activityState2.pushToken)) {
                this.activityState.pushToken = str;
                writeActivityStateI();
                PackageBuilder packageBuilder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, this.sessionParameters, System.currentTimeMillis());
                ActivityPackage buildInfoPackage = packageBuilder.buildInfoPackage(Constants.PUSH);
                this.packageHandler.addPackage(buildInfoPackage);
                new SharedPreferencesManager(getContext()).removePushToken();
                if (this.adjustConfig.eventBufferingEnabled) {
                    this.logger.info("Buffered event %s", buildInfoPackage.getSuffix());
                } else {
                    this.packageHandler.sendFirstPackage();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void startBackgroundTimerI() {
        if (this.backgroundTimer != null && toSendI() && this.backgroundTimer.getFireIn() <= 0) {
            this.backgroundTimer.startIn(BACKGROUND_TIMER_INTERVAL);
        }
    }

    private void startFirstSessionI() {
        updateHandlersStatusAndSendI();
        this.activityState = new ActivityState();
        this.internalState.firstSdkStart = true;
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        this.activityState.pushToken = sharedPreferencesManager.getPushToken();
        if (this.internalState.isEnabled()) {
            if (!sharedPreferencesManager.getGdprForgetMe()) {
                this.activityState.sessionCount = 1;
                transferSessionPackageI(currentTimeMillis);
                checkAfterNewStartI(sharedPreferencesManager);
            } else {
                gdprForgetMeI();
            }
        }
        this.activityState.resetSessionAttributes(currentTimeMillis);
        this.activityState.enabled = this.internalState.isEnabled();
        this.activityState.updatePackages = this.internalState.itHasToUpdatePackages();
        writeActivityStateI();
        sharedPreferencesManager.removePushToken();
        sharedPreferencesManager.removeGdprForgetMe();
        processCachedDeeplinkI();
    }

    /* access modifiers changed from: private */
    public void startForegroundTimerI() {
        if (isEnabledI()) {
            this.foregroundTimer.start();
        }
    }

    /* access modifiers changed from: private */
    public void startI() {
        if (this.internalState.hasFirstSdkStartNotOcurred()) {
            startFirstSessionI();
        } else if (this.activityState.enabled) {
            updateHandlersStatusAndSendI();
            processSessionI();
            checkAttributionStateI();
            processCachedDeeplinkI();
        }
    }

    /* access modifiers changed from: private */
    public void stopBackgroundTimerI() {
        TimerOnce timerOnce = this.backgroundTimer;
        if (timerOnce != null) {
            timerOnce.cancel();
        }
    }

    /* access modifiers changed from: private */
    public void stopForegroundTimerI() {
        this.foregroundTimer.suspend();
    }

    private void teardownActivityStateS() {
        synchronized (ActivityState.class) {
            if (this.activityState != null) {
                this.activityState = null;
            }
        }
    }

    private void teardownAllSessionParametersS() {
        synchronized (SessionParameters.class) {
            if (this.sessionParameters != null) {
                this.sessionParameters = null;
            }
        }
    }

    private void teardownAttributionS() {
        synchronized (AdjustAttribution.class) {
            if (this.attribution != null) {
                this.attribution = null;
            }
        }
    }

    private boolean toSendI() {
        return toSendI(false);
    }

    /* access modifiers changed from: private */
    public void trackEventI(AdjustEvent adjustEvent) {
        if (checkActivityStateI(this.activityState) && isEnabledI() && checkEventI(adjustEvent) && checkOrderIdI(adjustEvent.orderId) && !this.activityState.isGdprForgotten) {
            long currentTimeMillis = System.currentTimeMillis();
            this.activityState.eventCount++;
            updateActivityStateI(currentTimeMillis);
            PackageBuilder packageBuilder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, this.sessionParameters, currentTimeMillis);
            ActivityPackage buildEventPackage = packageBuilder.buildEventPackage(adjustEvent, this.internalState.isInDelayedStart());
            this.packageHandler.addPackage(buildEventPackage);
            if (this.adjustConfig.eventBufferingEnabled) {
                this.logger.info("Buffered event %s", buildEventPackage.getSuffix());
            } else {
                this.packageHandler.sendFirstPackage();
            }
            if (this.adjustConfig.sendInBackground && this.internalState.isInBackground()) {
                startBackgroundTimerI();
            }
            writeActivityStateI();
        }
    }

    private void trackNewSessionI(long j) {
        ActivityState activityState2 = this.activityState;
        long j2 = j - activityState2.lastActivity;
        activityState2.sessionCount++;
        activityState2.lastInterval = j2;
        transferSessionPackageI(j);
        this.activityState.resetSessionAttributes(j);
        writeActivityStateI();
    }

    private void transferSessionPackageI(long j) {
        PackageBuilder packageBuilder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, this.sessionParameters, j);
        this.packageHandler.addPackage(packageBuilder.buildSessionPackage(this.internalState.isInDelayedStart()));
        this.packageHandler.sendFirstPackage();
    }

    private boolean updateActivityStateI(long j) {
        if (!checkActivityStateI(this.activityState)) {
            return false;
        }
        ActivityState activityState2 = this.activityState;
        long j2 = j - activityState2.lastActivity;
        if (j2 > SESSION_INTERVAL) {
            return false;
        }
        activityState2.lastActivity = j;
        if (j2 < 0) {
            this.logger.error(TIME_TRAVEL, new Object[0]);
        } else {
            activityState2.sessionLength += j2;
            activityState2.timeSpent += j2;
        }
        return true;
    }

    private void updateAdidI(String str) {
        if (str != null && !str.equals(this.activityState.adid)) {
            this.activityState.adid = str;
            writeActivityStateI();
        }
    }

    private void updateHandlersStatusAndSendI() {
        if (!toSendI()) {
            pauseSendingI();
            return;
        }
        resumeSendingI();
        if (!this.adjustConfig.eventBufferingEnabled || (this.internalState.isFirstLaunch() && this.internalState.hasSessionResponseNotBeenProcessed())) {
            this.packageHandler.sendFirstPackage();
        }
    }

    private void updatePackagesI() {
        this.packageHandler.updatePackages(this.sessionParameters);
        this.internalState.updatePackages = false;
        ActivityState activityState2 = this.activityState;
        if (activityState2 != null) {
            activityState2.updatePackages = false;
            writeActivityStateI();
        }
    }

    private void updateStatusI(boolean z, String str, String str2, String str3) {
        if (z) {
            this.logger.info(str, new Object[0]);
        } else if (!pausedI(false)) {
            this.logger.info(str3, new Object[0]);
        } else if (pausedI(true)) {
            this.logger.info(str2, new Object[0]);
        } else {
            ILogger iLogger = this.logger;
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(", except the Sdk Click Handler");
            iLogger.info(sb.toString(), new Object[0]);
        }
        updateHandlersStatusAndSendI();
    }

    private void writeActivityStateI() {
        synchronized (ActivityState.class) {
            if (this.activityState != null) {
                Util.writeObject(this.activityState, this.adjustConfig.context, Constants.ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME);
            }
        }
    }

    private void writeAttributionI() {
        synchronized (AdjustAttribution.class) {
            if (this.attribution != null) {
                Util.writeObject(this.attribution, this.adjustConfig.context, Constants.ATTRIBUTION_FILENAME, ATTRIBUTION_NAME);
            }
        }
    }

    private void writeSessionCallbackParametersI() {
        synchronized (SessionParameters.class) {
            if (this.sessionParameters != null) {
                Util.writeObject(this.sessionParameters.callbackParameters, this.adjustConfig.context, Constants.SESSION_CALLBACK_PARAMETERS_FILENAME, SESSION_CALLBACK_PARAMETERS_NAME);
            }
        }
    }

    private void writeSessionPartnerParametersI() {
        synchronized (SessionParameters.class) {
            if (this.sessionParameters != null) {
                Util.writeObject(this.sessionParameters.partnerParameters, this.adjustConfig.context, Constants.SESSION_PARTNER_PARAMETERS_FILENAME, SESSION_PARTNER_PARAMETERS_NAME);
            }
        }
    }

    public void addSessionCallbackParameter(final String str, final String str2) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.addSessionCallbackParameterI(str, str2);
            }
        });
    }

    public void addSessionCallbackParameterI(String str, String str2) {
        String str3 = "Session Callback";
        if (Util.isValidParameter(str, "key", str3) && Util.isValidParameter(str2, "value", str3)) {
            SessionParameters sessionParameters2 = this.sessionParameters;
            if (sessionParameters2.callbackParameters == null) {
                sessionParameters2.callbackParameters = new LinkedHashMap();
            }
            String str4 = (String) this.sessionParameters.callbackParameters.get(str);
            if (str2.equals(str4)) {
                this.logger.verbose("Key %s already present with the same value", str);
                return;
            }
            if (str4 != null) {
                this.logger.warn("Key %s will be overwritten", str);
            }
            this.sessionParameters.callbackParameters.put(str, str2);
            writeSessionCallbackParametersI();
        }
    }

    public void addSessionPartnerParameter(final String str, final String str2) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.addSessionPartnerParameterI(str, str2);
            }
        });
    }

    public void addSessionPartnerParameterI(String str, String str2) {
        String str3 = "Session Partner";
        if (Util.isValidParameter(str, "key", str3) && Util.isValidParameter(str2, "value", str3)) {
            SessionParameters sessionParameters2 = this.sessionParameters;
            if (sessionParameters2.partnerParameters == null) {
                sessionParameters2.partnerParameters = new LinkedHashMap();
            }
            String str4 = (String) this.sessionParameters.partnerParameters.get(str);
            if (str2.equals(str4)) {
                this.logger.verbose("Key %s already present with the same value", str);
                return;
            }
            if (str4 != null) {
                this.logger.warn("Key %s will be overwritten", str);
            }
            this.sessionParameters.partnerParameters.put(str, str2);
            writeSessionPartnerParametersI();
        }
    }

    public void backgroundTimerFired() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.backgroundTimerFiredI();
            }
        });
    }

    public void finishedTrackingActivity(ResponseData responseData) {
        if (responseData instanceof SessionResponseData) {
            this.attributionHandler.checkSessionResponse((SessionResponseData) responseData);
        } else if (responseData instanceof SdkClickResponseData) {
            SdkClickResponseData sdkClickResponseData = (SdkClickResponseData) responseData;
            checkForInstallReferrerInfo(sdkClickResponseData);
            this.attributionHandler.checkSdkClickResponse(sdkClickResponseData);
        } else {
            if (responseData instanceof EventResponseData) {
                launchEventResponseTasks((EventResponseData) responseData);
            }
        }
    }

    public void foregroundTimerFired() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.foregroundTimerFiredI();
            }
        });
    }

    public void gdprForgetMe() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.gdprForgetMeI();
            }
        });
    }

    public ActivityState getActivityState() {
        return this.activityState;
    }

    public String getAdid() {
        ActivityState activityState2 = this.activityState;
        if (activityState2 == null) {
            return null;
        }
        return activityState2.adid;
    }

    public AdjustConfig getAdjustConfig() {
        return this.adjustConfig;
    }

    public AdjustAttribution getAttribution() {
        return this.attribution;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public Context getContext() {
        return this.adjustConfig.context;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public String getGdprPath() {
        return this.gdprPath;
    }

    public InternalState getInternalState() {
        return this.internalState;
    }

    public SessionParameters getSessionParameters() {
        return this.sessionParameters;
    }

    public void gotOptOutResponse() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.gotOptOutResponseI();
            }
        });
    }

    public void init(AdjustConfig adjustConfig2) {
        this.adjustConfig = adjustConfig2;
    }

    public boolean isEnabled() {
        return isEnabledI();
    }

    public void launchAttributionResponseTasks(final AttributionResponseData attributionResponseData) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchAttributionResponseTasksI(attributionResponseData);
            }
        });
    }

    public void launchEventResponseTasks(final EventResponseData eventResponseData) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchEventResponseTasksI(eventResponseData);
            }
        });
    }

    public void launchSdkClickResponseTasks(final SdkClickResponseData sdkClickResponseData) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchSdkClickResponseTasksI(sdkClickResponseData);
            }
        });
    }

    public void launchSessionResponseTasks(final SessionResponseData sessionResponseData) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchSessionResponseTasksI(sessionResponseData);
            }
        });
    }

    public void onPause() {
        this.internalState.background = true;
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.stopForegroundTimerI();
                ActivityHandler.this.startBackgroundTimerI();
                ActivityHandler.this.logger.verbose("Subsession end", new Object[0]);
                ActivityHandler.this.endI();
            }
        });
    }

    public void onResume() {
        this.internalState.background = false;
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.delayStartI();
                ActivityHandler.this.stopBackgroundTimerI();
                ActivityHandler.this.startForegroundTimerI();
                ActivityHandler.this.logger.verbose("Subsession start", new Object[0]);
                ActivityHandler.this.startI();
            }
        });
    }

    public void readOpenUrl(final Uri uri, final long j) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.readOpenUrlI(uri, j);
            }
        });
    }

    public void removeSessionCallbackParameter(final String str) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.removeSessionCallbackParameterI(str);
            }
        });
    }

    public void removeSessionCallbackParameterI(String str) {
        if (Util.isValidParameter(str, "key", "Session Callback")) {
            Map<String, String> map = this.sessionParameters.callbackParameters;
            if (map == null) {
                this.logger.warn("Session Callback parameters are not set", new Object[0]);
            } else if (((String) map.remove(str)) == null) {
                this.logger.warn("Key %s does not exist", str);
            } else {
                this.logger.debug("Key %s will be removed", str);
                writeSessionCallbackParametersI();
            }
        }
    }

    public void removeSessionPartnerParameter(final String str) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.removeSessionPartnerParameterI(str);
            }
        });
    }

    public void removeSessionPartnerParameterI(String str) {
        if (Util.isValidParameter(str, "key", "Session Partner")) {
            Map<String, String> map = this.sessionParameters.partnerParameters;
            if (map == null) {
                this.logger.warn("Session Partner parameters are not set", new Object[0]);
            } else if (((String) map.remove(str)) == null) {
                this.logger.warn("Key %s does not exist", str);
            } else {
                this.logger.debug("Key %s will be removed", str);
                writeSessionPartnerParametersI();
            }
        }
    }

    public void resetSessionCallbackParameters() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.resetSessionCallbackParametersI();
            }
        });
    }

    public void resetSessionCallbackParametersI() {
        if (this.sessionParameters.callbackParameters == null) {
            this.logger.warn("Session Callback parameters are not set", new Object[0]);
        }
        this.sessionParameters.callbackParameters = null;
        writeSessionCallbackParametersI();
    }

    public void resetSessionPartnerParameters() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.resetSessionPartnerParametersI();
            }
        });
    }

    public void resetSessionPartnerParametersI() {
        if (this.sessionParameters.partnerParameters == null) {
            this.logger.warn("Session Partner parameters are not set", new Object[0]);
        }
        this.sessionParameters.partnerParameters = null;
        writeSessionPartnerParametersI();
    }

    public void sendFirstPackages() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.sendFirstPackagesI();
            }
        });
    }

    public void sendInstallReferrer(String str, long j, long j2) {
        ThreadExecutor threadExecutor = this.executor;
        final String str2 = str;
        final long j3 = j;
        final long j4 = j2;
        AnonymousClass10 r1 = new Runnable() {
            public void run() {
                ActivityHandler.this.sendInstallReferrerI(str2, j3, j4);
            }
        };
        threadExecutor.submit(r1);
    }

    public void sendReftagReferrer() {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.sendReftagReferrerI();
            }
        });
    }

    public void setAskingAttribution(final boolean z) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.setAskingAttributionI(z);
            }
        });
    }

    public void setEnabled(final boolean z) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.setEnabledI(z);
            }
        });
    }

    public void setOfflineMode(final boolean z) {
        this.executor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.setOfflineModeI(z);
            }
        });
    }

    public void setPushToken(final String str, final boolean z) {
        this.executor.submit(new Runnable() {
            public void run() {
                if (!z) {
                    new SharedPreferencesManager(ActivityHandler.this.getContext()).savePushToken(str);
                }
                if (!ActivityHandler.this.internalState.hasFirstSdkStartNotOcurred()) {
                    ActivityHandler.this.setPushTokenI(str);
                }
            }
        });
    }

    public void teardown() {
        TimerOnce timerOnce = this.backgroundTimer;
        if (timerOnce != null) {
            timerOnce.teardown();
        }
        TimerCycle timerCycle = this.foregroundTimer;
        if (timerCycle != null) {
            timerCycle.teardown();
        }
        TimerOnce timerOnce2 = this.delayStartTimer;
        if (timerOnce2 != null) {
            timerOnce2.teardown();
        }
        ThreadExecutor threadExecutor = this.executor;
        if (threadExecutor != null) {
            threadExecutor.teardown();
        }
        IPackageHandler iPackageHandler = this.packageHandler;
        if (iPackageHandler != null) {
            iPackageHandler.teardown();
        }
        IAttributionHandler iAttributionHandler = this.attributionHandler;
        if (iAttributionHandler != null) {
            iAttributionHandler.teardown();
        }
        ISdkClickHandler iSdkClickHandler = this.sdkClickHandler;
        if (iSdkClickHandler != null) {
            iSdkClickHandler.teardown();
        }
        SessionParameters sessionParameters2 = this.sessionParameters;
        if (sessionParameters2 != null) {
            Map<String, String> map = sessionParameters2.callbackParameters;
            if (map != null) {
                map.clear();
            }
            Map<String, String> map2 = this.sessionParameters.partnerParameters;
            if (map2 != null) {
                map2.clear();
            }
        }
        teardownActivityStateS();
        teardownAttributionS();
        teardownAllSessionParametersS();
        this.packageHandler = null;
        this.logger = null;
        this.foregroundTimer = null;
        this.executor = null;
        this.backgroundTimer = null;
        this.delayStartTimer = null;
        this.internalState = null;
        this.deviceInfo = null;
        this.adjustConfig = null;
        this.attributionHandler = null;
        this.sdkClickHandler = null;
        this.sessionParameters = null;
    }

    public void trackEvent(final AdjustEvent adjustEvent) {
        this.executor.submit(new Runnable() {
            public void run() {
                if (ActivityHandler.this.internalState.hasFirstSdkStartNotOcurred()) {
                    ActivityHandler.this.logger.warn("Event tracked before first activity resumed.\nIf it was triggered in the Application class, it might timestamp or even send an install long before the user opens the app.\nPlease check https://github.com/adjust/android_sdk#can-i-trigger-an-event-at-application-launch for more information.", new Object[0]);
                    ActivityHandler.this.startI();
                }
                ActivityHandler.this.trackEventI(adjustEvent);
            }
        });
    }

    public boolean updateAttributionI(AdjustAttribution adjustAttribution) {
        if (adjustAttribution == null || adjustAttribution.equals(this.attribution)) {
            return false;
        }
        this.attribution = adjustAttribution;
        writeAttributionI();
        return true;
    }

    private boolean pausedI(boolean z) {
        boolean z2 = false;
        if (z) {
            if (this.internalState.isOffline() || !isEnabledI()) {
                z2 = true;
            }
            return z2;
        }
        if (this.internalState.isOffline() || !isEnabledI() || this.internalState.isInDelayedStart()) {
            z2 = true;
        }
        return z2;
    }

    private boolean toSendI(boolean z) {
        if (pausedI(z)) {
            return false;
        }
        if (this.adjustConfig.sendInBackground) {
            return true;
        }
        return this.internalState.isInForeground();
    }

    private void checkAfterNewStartI(SharedPreferencesManager sharedPreferencesManager) {
        String pushToken = sharedPreferencesManager.getPushToken();
        if (pushToken != null && !pushToken.equals(this.activityState.pushToken)) {
            setPushToken(pushToken, true);
        }
        if (sharedPreferencesManager.getRawReferrerArray() != null) {
            sendReftagReferrer();
        }
        this.installReferrer.startConnection();
    }
}
