package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@KeepForSdk
@Class(creator = "FeatureCreator")
public class Feature extends AbstractSafeParcelable {
    public static final Creator<Feature> CREATOR = new zzb();
    @Field(getter = "getName", id = 1)
    private final String name;
    @Field(getter = "getOldVersion", id = 2)
    @Deprecated
    private final int zzk;
    @Field(defaultValue = "-1", getter = "getVersion", id = 3)
    private final long zzl;

    @KeepForSdk
    public Feature(String str, long j) {
        this.name = str;
        this.zzl = j;
        this.zzk = -1;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Feature) {
            Feature feature = (Feature) obj;
            if (((getName() != null && getName().equals(feature.getName())) || (getName() == null && feature.getName() == null)) && getVersion() == feature.getVersion()) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public String getName() {
        return this.name;
    }

    @KeepForSdk
    public long getVersion() {
        long j = this.zzl;
        return j == -1 ? (long) this.zzk : j;
    }

    public int hashCode() {
        return Objects.hashCode(getName(), Long.valueOf(getVersion()));
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", getName()).add("version", Long.valueOf(getVersion())).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeInt(parcel, 2, this.zzk);
        SafeParcelWriter.writeLong(parcel, 3, getVersion());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @Constructor
    public Feature(@Param(id = 1) String str, @Param(id = 2) int i, @Param(id = 3) long j) {
        this.name = str;
        this.zzk = i;
        this.zzl = j;
    }
}
