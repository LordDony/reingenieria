package defpackage;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* renamed from: ys reason: default package and case insensitive filesystem */
public class C1884ys {
    private static final ClassLoader a = C1884ys.class.getClassLoader();

    private C1884ys() {
    }

    public static boolean a(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void b(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 1);
    }

    public static void a(Parcel parcel, boolean z) {
        parcel.writeInt(z ? 1 : 0);
    }

    public static <T extends Parcelable> T a(Parcel parcel, Creator<T> creator) {
        if (parcel.readInt() == 0) {
            return null;
        }
        return (Parcelable) creator.createFromParcel(parcel);
    }

    public static void a(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 0);
    }

    public static void a(Parcel parcel, IInterface iInterface) {
        if (iInterface == null) {
            parcel.writeStrongBinder(null);
        } else {
            parcel.writeStrongBinder(iInterface.asBinder());
        }
    }
}
