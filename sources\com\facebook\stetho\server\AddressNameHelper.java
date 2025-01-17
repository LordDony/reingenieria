package com.facebook.stetho.server;

import com.facebook.stetho.common.ProcessUtil;

public class AddressNameHelper {
    private static final String PREFIX = "stetho_";

    public static String createCustomAddress(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(ProcessUtil.getProcessName());
        sb.append(str);
        return sb.toString();
    }
}
