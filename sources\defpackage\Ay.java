package defpackage;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: Ay reason: default package */
public class Ay implements IInterface {
    private final IBinder a;
    private final String b;

    protected Ay(IBinder iBinder, String str) {
        this.a = iBinder;
        this.b = str;
    }

    public IBinder asBinder() {
        return this.a;
    }
}
