package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.VisibleForTesting;

@ShowFirstParty
@KeepForSdk
public abstract class FastSafeParcelableJsonResponse extends FastJsonResponse implements SafeParcelable {
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        FastJsonResponse fastJsonResponse = (FastJsonResponse) obj;
        for (Field field : getFieldMappings().values()) {
            if (isFieldSet(field)) {
                if (!fastJsonResponse.isFieldSet(field) || !getFieldValue(field).equals(fastJsonResponse.getFieldValue(field))) {
                    return false;
                }
            } else if (fastJsonResponse.isFieldSet(field)) {
                return false;
            }
        }
        return true;
    }

    @VisibleForTesting
    public Object getValueObject(String str) {
        return null;
    }

    public int hashCode() {
        int i = 0;
        for (Field field : getFieldMappings().values()) {
            if (isFieldSet(field)) {
                i = (i * 31) + getFieldValue(field).hashCode();
            }
        }
        return i;
    }

    @VisibleForTesting
    public boolean isPrimitiveFieldSet(String str) {
        return false;
    }

    @KeepForSdk
    public byte[] toByteArray() {
        Parcel obtain = Parcel.obtain();
        writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }
}
