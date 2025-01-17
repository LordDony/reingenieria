package defpackage;

/* renamed from: JMa reason: default package and case insensitive filesystem */
/* compiled from: Preconditions */
public final class C5023JMa {
    public static <T> T a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static <T> T a(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static <T> T a(T t, String str, Object obj) {
        String str2;
        if (t != null) {
            return t;
        }
        String str3 = "%s";
        if (!str.contains(str3)) {
            throw new IllegalArgumentException("errorMessageTemplate has no format specifiers");
        } else if (str.indexOf(str3) == str.lastIndexOf(str3)) {
            if (obj instanceof Class) {
                str2 = ((Class) obj).getCanonicalName();
            } else {
                str2 = String.valueOf(obj);
            }
            throw new NullPointerException(str.replace(str3, str2));
        } else {
            throw new IllegalArgumentException("errorMessageTemplate has more than one format specifier");
        }
    }
}
