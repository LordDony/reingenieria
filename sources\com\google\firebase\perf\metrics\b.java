package com.google.firebase.perf.metrics;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class b implements Creator<Trace> {
    b() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new Trace(parcel, false, null);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Trace[i];
    }
}
