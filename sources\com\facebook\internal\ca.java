package com.facebook.internal;

import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* compiled from: ProfileInformationCache */
class ca {
    private static final ConcurrentHashMap<String, JSONObject> a = new ConcurrentHashMap<>();

    public static JSONObject a(String str) {
        return (JSONObject) a.get(str);
    }

    public static void a(String str, JSONObject jSONObject) {
        a.put(str, jSONObject);
    }
}
