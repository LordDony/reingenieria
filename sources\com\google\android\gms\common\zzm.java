package com.google.android.gms.common;

import android.util.Log;
import com.adjust.sdk.Constants;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import java.util.concurrent.Callable;

class zzm {
    private static final zzm zzac = new zzm(true, null, null);
    private final Throwable cause;
    final boolean zzad;
    private final String zzae;

    zzm(boolean z, String str, Throwable th) {
        this.zzad = z;
        this.zzae = str;
        this.cause = th;
    }

    static zzm zza(Callable<String> callable) {
        return new zzo(callable);
    }

    static zzm zzb(String str) {
        return new zzm(false, str, null);
    }

    static String zzc(String str, zze zze, boolean z, boolean z2) {
        return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", new Object[]{z2 ? "debug cert rejected" : "not whitelisted", str, Hex.bytesToStringLowercase(AndroidUtilsLight.zzj(Constants.SHA1).digest(zze.getBytes())), Boolean.valueOf(z), "12451009.false"});
    }

    static zzm zze() {
        return zzac;
    }

    /* access modifiers changed from: 0000 */
    public String getErrorMessage() {
        return this.zzae;
    }

    /* access modifiers changed from: 0000 */
    public final void zzf() {
        if (!this.zzad) {
            String str = "GoogleCertificatesRslt";
            if (Log.isLoggable(str, 3)) {
                if (this.cause != null) {
                    Log.d(str, getErrorMessage(), this.cause);
                    return;
                }
                Log.d(str, getErrorMessage());
            }
        }
    }

    static zzm zza(String str, Throwable th) {
        return new zzm(false, str, th);
    }
}
