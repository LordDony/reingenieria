package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "WebImageCreator")
public final class WebImage extends AbstractSafeParcelable {
    public static final Creator<WebImage> CREATOR = new zae();
    @VersionField(id = 1)
    private final int zalf;
    @Field(getter = "getWidth", id = 3)
    private final int zane;
    @Field(getter = "getHeight", id = 4)
    private final int zanf;
    @Field(getter = "getUrl", id = 2)
    private final Uri zang;

    @Constructor
    WebImage(@Param(id = 1) int i, @Param(id = 2) Uri uri, @Param(id = 3) int i2, @Param(id = 4) int i3) {
        this.zalf = i;
        this.zang = uri;
        this.zane = i2;
        this.zanf = i3;
    }

    private static Uri zaa(JSONObject jSONObject) {
        String str = ImagesContract.URL;
        if (jSONObject.has(str)) {
            try {
                return Uri.parse(jSONObject.getString(str));
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof WebImage)) {
            WebImage webImage = (WebImage) obj;
            return Objects.equal(this.zang, webImage.zang) && this.zane == webImage.zane && this.zanf == webImage.zanf;
        }
    }

    public final int getHeight() {
        return this.zanf;
    }

    public final Uri getUrl() {
        return this.zang;
    }

    public final int getWidth() {
        return this.zane;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zang, Integer.valueOf(this.zane), Integer.valueOf(this.zanf));
    }

    @KeepForSdk
    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ImagesContract.URL, this.zang.toString());
            jSONObject.put("width", this.zane);
            jSONObject.put("height", this.zanf);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.zane), Integer.valueOf(this.zanf), this.zang.toString()});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zalf);
        SafeParcelWriter.writeParcelable(parcel, 2, getUrl(), i, false);
        SafeParcelWriter.writeInt(parcel, 3, getWidth());
        SafeParcelWriter.writeInt(parcel, 4, getHeight());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    @KeepForSdk
    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(zaa(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }
}
