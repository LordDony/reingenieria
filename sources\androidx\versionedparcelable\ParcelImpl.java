package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelImpl implements Parcelable {
    public static final Creator<ParcelImpl> CREATOR = new a();
    private final d a;

    protected ParcelImpl(Parcel parcel) {
        this.a = new c(parcel).h();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        new c(parcel).a(this.a);
    }
}
