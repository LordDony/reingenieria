package androidx.room;

import android.content.Context;
import androidx.room.t.a;

/* compiled from: Room */
public class s {
    public static <T extends t> a<T> a(Context context, Class<T> cls, String str) {
        if (str != null && str.trim().length() != 0) {
            return new a<>(context, cls, str);
        }
        throw new IllegalArgumentException("Cannot build a database with null or empty name. If you are trying to create an in memory database, use Room.inMemoryDatabaseBuilder");
    }

    public static <T extends t> a<T> a(Context context, Class<T> cls) {
        return new a<>(context, cls, null);
    }

    static <T, C> T a(Class<C> cls, String str) {
        String str2;
        String name = cls.getPackage().getName();
        String canonicalName = cls.getCanonicalName();
        if (!name.isEmpty()) {
            canonicalName = canonicalName.substring(name.length() + 1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(canonicalName.replace('.', '_'));
        sb.append(str);
        String sb2 = sb.toString();
        try {
            if (name.isEmpty()) {
                str2 = sb2;
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(name);
                sb3.append(".");
                sb3.append(sb2);
                str2 = sb3.toString();
            }
            return Class.forName(str2).newInstance();
        } catch (ClassNotFoundException unused) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("cannot find implementation for ");
            sb4.append(cls.getCanonicalName());
            sb4.append(". ");
            sb4.append(sb2);
            sb4.append(" does not exist");
            throw new RuntimeException(sb4.toString());
        } catch (IllegalAccessException unused2) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Cannot access the constructor");
            sb5.append(cls.getCanonicalName());
            throw new RuntimeException(sb5.toString());
        } catch (InstantiationException unused3) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Failed to create an instance of ");
            sb6.append(cls.getCanonicalName());
            throw new RuntimeException(sb6.toString());
        }
    }
}
