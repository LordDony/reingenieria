package com.adjust.sdk;

import android.net.Uri;
import com.adjust.sdk.scheduler.SingleThreadCachedScheduler;
import com.adjust.sdk.scheduler.ThreadScheduler;
import com.adjust.sdk.scheduler.TimerOnce;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

public class AttributionHandler implements IAttributionHandler {
    private static final String ATTRIBUTION_TIMER_NAME = "Attribution timer";
    /* access modifiers changed from: private */
    public WeakReference<IActivityHandler> activityHandlerWeakRef;
    private String basePath;
    private String clientSdk;
    /* access modifiers changed from: private */
    public String lastInitiatedBy;
    private ILogger logger = AdjustFactory.getLogger();
    private boolean paused;
    private ThreadScheduler scheduler = new SingleThreadCachedScheduler("AttributionHandler");
    private TimerOnce timer = new TimerOnce(new Runnable() {
        public void run() {
            AttributionHandler.this.sendAttributionRequest();
        }
    }, ATTRIBUTION_TIMER_NAME);

    public AttributionHandler(IActivityHandler iActivityHandler, boolean z) {
        this.basePath = iActivityHandler.getBasePath();
        this.clientSdk = iActivityHandler.getDeviceInfo().clientSdk;
        init(iActivityHandler, z);
    }

    private ActivityPackage buildAndGetAttributionPackage() {
        IActivityHandler iActivityHandler = (IActivityHandler) this.activityHandlerWeakRef.get();
        PackageBuilder packageBuilder = new PackageBuilder(iActivityHandler.getAdjustConfig(), iActivityHandler.getDeviceInfo(), iActivityHandler.getActivityState(), iActivityHandler.getSessionParameters(), System.currentTimeMillis());
        ActivityPackage buildAttributionPackage = packageBuilder.buildAttributionPackage(this.lastInitiatedBy);
        this.lastInitiatedBy = null;
        return buildAttributionPackage;
    }

    private void checkAttributionI(IActivityHandler iActivityHandler, ResponseData responseData) {
        JSONObject jSONObject = responseData.jsonResponse;
        if (jSONObject != null) {
            long optLong = jSONObject.optLong("ask_in", -1);
            if (optLong >= 0) {
                iActivityHandler.setAskingAttribution(true);
                this.lastInitiatedBy = "backend";
                getAttributionI(optLong);
                return;
            }
            iActivityHandler.setAskingAttribution(false);
            responseData.attribution = AdjustAttribution.fromJson(responseData.jsonResponse.optJSONObject("attribution"), responseData.adid, Util.getSdkPrefixPlatform(this.clientSdk));
        }
    }

    /* access modifiers changed from: private */
    public void checkAttributionResponseI(IActivityHandler iActivityHandler, AttributionResponseData attributionResponseData) {
        checkAttributionI(iActivityHandler, attributionResponseData);
        checkDeeplinkI(attributionResponseData);
        iActivityHandler.launchAttributionResponseTasks(attributionResponseData);
    }

    private void checkDeeplinkI(AttributionResponseData attributionResponseData) {
        JSONObject jSONObject = attributionResponseData.jsonResponse;
        if (jSONObject != null) {
            JSONObject optJSONObject = jSONObject.optJSONObject("attribution");
            if (optJSONObject != null) {
                String optString = optJSONObject.optString(Constants.DEEPLINK, null);
                if (optString != null) {
                    attributionResponseData.deeplink = Uri.parse(optString);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void checkSdkClickResponseI(IActivityHandler iActivityHandler, SdkClickResponseData sdkClickResponseData) {
        checkAttributionI(iActivityHandler, sdkClickResponseData);
        iActivityHandler.launchSdkClickResponseTasks(sdkClickResponseData);
    }

    /* access modifiers changed from: private */
    public void checkSessionResponseI(IActivityHandler iActivityHandler, SessionResponseData sessionResponseData) {
        checkAttributionI(iActivityHandler, sessionResponseData);
        iActivityHandler.launchSessionResponseTasks(sessionResponseData);
    }

    /* access modifiers changed from: private */
    public void getAttributionI(long j) {
        if (this.timer.getFireIn() <= j) {
            if (j != 0) {
                String format = Util.SecondsDisplayFormat.format(((double) j) / 1000.0d);
                this.logger.debug("Waiting to query attribution in %s seconds", format);
            }
            this.timer.startIn(j);
        }
    }

    /* access modifiers changed from: private */
    public void sendAttributionRequest() {
        this.scheduler.submit(new Runnable() {
            public void run() {
                AttributionHandler.this.sendAttributionRequestI();
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendAttributionRequestI() {
        if (!((IActivityHandler) this.activityHandlerWeakRef.get()).getActivityState().isGdprForgotten) {
            if (this.paused) {
                this.logger.debug("Attribution handler is paused", new Object[0]);
                return;
            }
            ActivityPackage buildAndGetAttributionPackage = buildAndGetAttributionPackage();
            this.logger.verbose("%s", buildAndGetAttributionPackage.getExtendedString());
            try {
                ResponseData createGETHttpsURLConnection = UtilNetworking.createGETHttpsURLConnection(buildAndGetAttributionPackage, this.basePath);
                if (createGETHttpsURLConnection instanceof AttributionResponseData) {
                    if (createGETHttpsURLConnection.trackingState == TrackingState.OPTED_OUT) {
                        ((IActivityHandler) this.activityHandlerWeakRef.get()).gotOptOutResponse();
                    } else {
                        checkAttributionResponse((AttributionResponseData) createGETHttpsURLConnection);
                    }
                }
            } catch (Exception e) {
                this.logger.error("Failed to get attribution (%s)", e.getMessage());
            }
        }
    }

    public void checkAttributionResponse(final AttributionResponseData attributionResponseData) {
        this.scheduler.submit(new Runnable() {
            public void run() {
                IActivityHandler iActivityHandler = (IActivityHandler) AttributionHandler.this.activityHandlerWeakRef.get();
                if (iActivityHandler != null) {
                    AttributionHandler.this.checkAttributionResponseI(iActivityHandler, attributionResponseData);
                }
            }
        });
    }

    public void checkSdkClickResponse(final SdkClickResponseData sdkClickResponseData) {
        this.scheduler.submit(new Runnable() {
            public void run() {
                IActivityHandler iActivityHandler = (IActivityHandler) AttributionHandler.this.activityHandlerWeakRef.get();
                if (iActivityHandler != null) {
                    AttributionHandler.this.checkSdkClickResponseI(iActivityHandler, sdkClickResponseData);
                }
            }
        });
    }

    public void checkSessionResponse(final SessionResponseData sessionResponseData) {
        this.scheduler.submit(new Runnable() {
            public void run() {
                IActivityHandler iActivityHandler = (IActivityHandler) AttributionHandler.this.activityHandlerWeakRef.get();
                if (iActivityHandler != null) {
                    AttributionHandler.this.checkSessionResponseI(iActivityHandler, sessionResponseData);
                }
            }
        });
    }

    public void getAttribution() {
        this.scheduler.submit(new Runnable() {
            public void run() {
                AttributionHandler.this.lastInitiatedBy = "sdk";
                AttributionHandler.this.getAttributionI(0);
            }
        });
    }

    public void init(IActivityHandler iActivityHandler, boolean z) {
        this.activityHandlerWeakRef = new WeakReference<>(iActivityHandler);
        this.paused = !z;
    }

    public void pauseSending() {
        this.paused = true;
    }

    public void resumeSending() {
        this.paused = false;
    }

    public void teardown() {
        this.logger.verbose("AttributionHandler teardown", new Object[0]);
        TimerOnce timerOnce = this.timer;
        if (timerOnce != null) {
            timerOnce.teardown();
        }
        ThreadScheduler threadScheduler = this.scheduler;
        if (threadScheduler != null) {
            threadScheduler.teardown();
        }
        WeakReference<IActivityHandler> weakReference = this.activityHandlerWeakRef;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.timer = null;
        this.logger = null;
        this.scheduler = null;
        this.activityHandlerWeakRef = null;
    }
}
