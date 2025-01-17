package com.appboy.ui.inappmessage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.appboy.ui.inappmessage.listeners.IInAppMessageWebViewClientListener;
import com.appboy.ui.support.UriUtils;
import java.util.Map;

public class InAppMessageWebViewClient extends WebViewClient {
    private static final String TAG = Hg.a(InAppMessageWebViewClient.class);
    private Context mContext;
    private final C0522cg mInAppMessage;
    private IInAppMessageWebViewClientListener mInAppMessageWebViewClientListener;

    public InAppMessageWebViewClient(Context context, C0522cg cgVar, IInAppMessageWebViewClientListener iInAppMessageWebViewClientListener) {
        this.mInAppMessageWebViewClientListener = iInAppMessageWebViewClientListener;
        this.mInAppMessage = cgVar;
        this.mContext = context;
    }

    private void appendBridgeJavascript(WebView webView) {
        String a = Fg.a(this.mContext.getAssets(), "appboy-html-in-app-message-javascript-component.js");
        if (a == null) {
            AppboyInAppMessageManager.getInstance().hideCurrentlyDisplayingInAppMessage(false);
            Hg.b(TAG, "Failed to get HTML in-app message javascript additions");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:");
        sb.append(a);
        webView.loadUrl(sb.toString());
    }

    static Bundle getBundleFromUrl(String str) {
        Bundle bundle = new Bundle();
        if (Ng.d(str)) {
            return bundle;
        }
        Map queryParameters = UriUtils.getQueryParameters(Uri.parse(str));
        for (String str2 : queryParameters.keySet()) {
            bundle.putString(str2, (String) queryParameters.get(str2));
        }
        return bundle;
    }

    public void onPageFinished(WebView webView, String str) {
        appendBridgeJavascript(webView);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (this.mInAppMessageWebViewClientListener == null) {
            Hg.c(TAG, "InAppMessageWebViewClient was given null IInAppMessageWebViewClientListener listener. Returning true.");
            return true;
        } else if (Ng.d(str)) {
            Hg.c(TAG, "InAppMessageWebViewClient.shouldOverrideUrlLoading was given null or blank url. Returning true.");
            return true;
        } else {
            Uri parse = Uri.parse(str);
            Bundle bundleFromUrl = getBundleFromUrl(str);
            if (parse.getScheme().equals("appboy")) {
                String authority = parse.getAuthority();
                if (authority.equals("close")) {
                    this.mInAppMessageWebViewClientListener.onCloseAction(this.mInAppMessage, str, bundleFromUrl);
                } else if (authority.equals("feed")) {
                    this.mInAppMessageWebViewClientListener.onNewsfeedAction(this.mInAppMessage, str, bundleFromUrl);
                } else if (authority.equals("customEvent")) {
                    this.mInAppMessageWebViewClientListener.onCustomEventAction(this.mInAppMessage, str, bundleFromUrl);
                }
                return true;
            }
            this.mInAppMessageWebViewClientListener.onOtherUrlAction(this.mInAppMessage, str, bundleFromUrl);
            return true;
        }
    }
}
