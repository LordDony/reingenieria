package defpackage;

import android.os.IInterface;
import android.os.RemoteException;

/* renamed from: Gr reason: default package */
public interface Gr extends IInterface {
    boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException;

    int getIntFlagValue(String str, int i, int i2) throws RemoteException;

    long getLongFlagValue(String str, long j, int i) throws RemoteException;

    String getStringFlagValue(String str, String str2, int i) throws RemoteException;

    void init(Dr dr) throws RemoteException;
}
