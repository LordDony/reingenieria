package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzl extends C1824ws implements IGmsCallbacks {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    public final void onPostInitComplete(int i, IBinder iBinder, Bundle bundle) throws RemoteException {
        Parcel zza = zza();
        zza.writeInt(i);
        zza.writeStrongBinder(iBinder);
        C1884ys.a(zza, (Parcelable) bundle);
        zzb(1, zza);
    }

    public final void zza(int i, Bundle bundle) throws RemoteException {
        Parcel zza = zza();
        zza.writeInt(i);
        C1884ys.a(zza, (Parcelable) bundle);
        zzb(2, zza);
    }

    public final void zza(int i, IBinder iBinder, zzb zzb) throws RemoteException {
        Parcel zza = zza();
        zza.writeInt(i);
        zza.writeStrongBinder(iBinder);
        C1884ys.a(zza, (Parcelable) zzb);
        zzb(3, zza);
    }
}
