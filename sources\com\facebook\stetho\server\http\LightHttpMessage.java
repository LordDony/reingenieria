package com.facebook.stetho.server.http;

import java.util.ArrayList;

public class LightHttpMessage {
    public final ArrayList<String> headerNames = new ArrayList<>();
    public final ArrayList<String> headerValues = new ArrayList<>();

    public void addHeader(String str, String str2) {
        this.headerNames.add(str);
        this.headerValues.add(str2);
    }

    public String getFirstHeaderValue(String str) {
        int size = this.headerNames.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(this.headerNames.get(i))) {
                return (String) this.headerValues.get(i);
            }
        }
        return null;
    }

    public void reset() {
        this.headerNames.clear();
        this.headerValues.clear();
    }
}
