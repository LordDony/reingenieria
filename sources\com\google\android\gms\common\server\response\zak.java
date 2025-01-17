package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ShowFirstParty
@Class(creator = "FieldMappingDictionaryCreator")
public final class zak extends AbstractSafeParcelable {
    public static final Creator<zak> CREATOR = new zan();
    @VersionField(id = 1)
    private final int zalf;
    private final HashMap<String, Map<String, Field<?, ?>>> zaqv;
    @SafeParcelable.Field(getter = "getSerializedDictionary", id = 2)
    private final ArrayList<zal> zaqw;
    @SafeParcelable.Field(getter = "getRootClassName", id = 3)
    private final String zaqx;

    @Constructor
    zak(@Param(id = 1) int i, @Param(id = 2) ArrayList<zal> arrayList, @Param(id = 3) String str) {
        this.zalf = i;
        this.zaqw = null;
        HashMap<String, Map<String, Field<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zal zal = (zal) arrayList.get(i2);
            String str2 = zal.className;
            HashMap hashMap2 = new HashMap();
            int size2 = zal.zaqy.size();
            for (int i3 = 0; i3 < size2; i3++) {
                zam zam = (zam) zal.zaqy.get(i3);
                hashMap2.put(zam.zaqz, zam.zara);
            }
            hashMap.put(str2, hashMap2);
        }
        this.zaqv = hashMap;
        Preconditions.checkNotNull(str);
        this.zaqx = str;
        zacr();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.zaqv.keySet()) {
            sb.append(str);
            sb.append(":\n");
            Map map = (Map) this.zaqv.get(str);
            for (String str2 : map.keySet()) {
                sb.append("  ");
                sb.append(str2);
                sb.append(": ");
                sb.append(map.get(str2));
            }
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zalf);
        ArrayList arrayList = new ArrayList();
        for (String str : this.zaqv.keySet()) {
            arrayList.add(new zal(str, (Map) this.zaqv.get(str)));
        }
        SafeParcelWriter.writeTypedList(parcel, 2, arrayList, false);
        SafeParcelWriter.writeString(parcel, 3, this.zaqx, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final void zaa(Class<? extends FastJsonResponse> cls, Map<String, Field<?, ?>> map) {
        this.zaqv.put(cls.getCanonicalName(), map);
    }

    public final void zacr() {
        for (String str : this.zaqv.keySet()) {
            Map map = (Map) this.zaqv.get(str);
            for (String str2 : map.keySet()) {
                ((Field) map.get(str2)).zaa(this);
            }
        }
    }

    public final void zacs() {
        for (String str : this.zaqv.keySet()) {
            Map map = (Map) this.zaqv.get(str);
            HashMap hashMap = new HashMap();
            for (String str2 : map.keySet()) {
                hashMap.put(str2, ((Field) map.get(str2)).zacl());
            }
            this.zaqv.put(str, hashMap);
        }
    }

    public final String zact() {
        return this.zaqx;
    }

    public final Map<String, Field<?, ?>> zai(String str) {
        return (Map) this.zaqv.get(str);
    }

    public final boolean zaa(Class<? extends FastJsonResponse> cls) {
        return this.zaqv.containsKey(cls.getCanonicalName());
    }

    public zak(Class<? extends FastJsonResponse> cls) {
        this.zalf = 1;
        this.zaqw = null;
        this.zaqv = new HashMap<>();
        this.zaqx = cls.getCanonicalName();
    }
}
