package com.google.firebase.perf.metrics;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class d implements Creator<Trace> {
    d() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new Trace(parcel, true, null);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Trace[i];
    }
}
