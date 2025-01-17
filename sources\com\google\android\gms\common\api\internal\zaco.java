package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

final class zaco extends C1764us {
    private final /* synthetic */ zacm zakw;

    public zaco(zacm zacm, Looper looper) {
        this.zakw = zacm;
        super(looper);
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            PendingResult pendingResult = (PendingResult) message.obj;
            synchronized (this.zakw.zado) {
                if (pendingResult == null) {
                    this.zakw.zakp.zad(new Status(13, "Transform returned null"));
                } else if (pendingResult instanceof zacd) {
                    this.zakw.zakp.zad(((zacd) pendingResult).getStatus());
                } else {
                    this.zakw.zakp.zaa(pendingResult);
                }
            }
        } else if (i != 1) {
            StringBuilder sb = new StringBuilder(70);
            sb.append("TransformationResultHandler received unknown message type: ");
            sb.append(i);
            Log.e("TransformedResultImpl", sb.toString());
        } else {
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String str = "Runtime exception on the transformation worker thread: ";
            String valueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            throw runtimeException;
        }
    }
}
