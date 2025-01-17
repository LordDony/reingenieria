package com.comscore.android;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import java.util.List;

public class HostApplicationState {
    public static final int BACKGROUND = 1;
    public static final int FOREGROUND = 0;
    public static final int UNKNOWN = -1;

    public static int getState(Context context) {
        if (context != null && VERSION.SDK_INT > 20) {
            try {
                List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
                if (runningAppProcesses == null) {
                    return -1;
                }
                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo.importance == 100) {
                        for (String equals : runningAppProcessInfo.pkgList) {
                            if (equals.equals(context.getPackageName())) {
                                return 0;
                            }
                        }
                        continue;
                    }
                }
                return 1;
            } catch (Exception unused) {
            }
        }
        return -1;
    }
}
