package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.HashMap;

@KeepForSdk
public class MapUtils {
    @KeepForSdk
    public static void writeStringMapToJson(StringBuilder sb, HashMap<String, String> hashMap) {
        sb.append("{");
        boolean z = true;
        for (String str : hashMap.keySet()) {
            if (!z) {
                sb.append(",");
            } else {
                z = false;
            }
            String str2 = (String) hashMap.get(str);
            String str3 = "\"";
            sb.append(str3);
            sb.append(str);
            sb.append("\":");
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
                sb.append(str2);
                sb.append(str3);
            }
        }
        sb.append("}");
    }
}
