package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import java.util.List;

@Class(creator = "TokenDataCreator")
public class TokenData extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<TokenData> CREATOR = new h();
    @VersionField(id = 1)
    private final int a;
    @Field(getter = "getToken", id = 2)
    private final String b;
    @Field(getter = "getExpirationTimeSecs", id = 3)
    private final Long c;
    @Field(getter = "isCached", id = 4)
    private final boolean d;
    @Field(getter = "isSnowballed", id = 5)
    private final boolean e;
    @Field(getter = "getGrantedScopes", id = 6)
    private final List<String> f;
    @Field(getter = "getScopeData", id = 7)
    private final String g;

    @Constructor
    TokenData(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) Long l, @Param(id = 4) boolean z, @Param(id = 5) boolean z2, @Param(id = 6) List<String> list, @Param(id = 7) String str2) {
        this.a = i;
        Preconditions.checkNotEmpty(str);
        this.b = str;
        this.c = l;
        this.d = z;
        this.e = z2;
        this.f = list;
        this.g = str2;
    }

    public static TokenData a(Bundle bundle, String str) {
        bundle.setClassLoader(TokenData.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle(str);
        if (bundle2 == null) {
            return null;
        }
        bundle2.setClassLoader(TokenData.class.getClassLoader());
        return (TokenData) bundle2.getParcelable("TokenData");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TokenData)) {
            return false;
        }
        TokenData tokenData = (TokenData) obj;
        if (!TextUtils.equals(this.b, tokenData.b) || !Objects.equal(this.c, tokenData.c) || this.d != tokenData.d || this.e != tokenData.e || !Objects.equal(this.f, tokenData.f) || !Objects.equal(this.g, tokenData.g)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.b, this.c, Boolean.valueOf(this.d), Boolean.valueOf(this.e), this.f, this.g);
    }

    public final String i() {
        return this.b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.a);
        SafeParcelWriter.writeString(parcel, 2, this.b, false);
        SafeParcelWriter.writeLongObject(parcel, 3, this.c, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.d);
        SafeParcelWriter.writeBoolean(parcel, 5, this.e);
        SafeParcelWriter.writeStringList(parcel, 6, this.f, false);
        SafeParcelWriter.writeString(parcel, 7, this.g, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
