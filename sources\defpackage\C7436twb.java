package defpackage;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

@EVa(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lkotlin/reflect/jvm/internal/structure/ReflectJavaType;", "Lkotlin/reflect/jvm/internal/impl/load/java/structure/JavaType;", "()V", "reflectType", "Ljava/lang/reflect/Type;", "getReflectType", "()Ljava/lang/reflect/Type;", "equals", "", "other", "", "hashCode", "", "toString", "", "Factory", "descriptors.runtime"}, mv = {1, 1, 15})
/* renamed from: twb reason: default package and case insensitive filesystem */
/* compiled from: ReflectJavaType.kt */
public abstract class C7436twb implements Zib {
    public static final a a = new a(null);

    /* renamed from: twb$a */
    /* compiled from: ReflectJavaType.kt */
    public static final class a {
        private a() {
        }

        public final C7436twb a(Type type) {
            C7471uYa.b(type, C1325gg.TYPE);
            boolean z = type instanceof Class;
            if (z) {
                Class cls = (Class) type;
                if (cls.isPrimitive()) {
                    return new C7367swb(cls);
                }
            }
            if ((type instanceof GenericArrayType) || (z && ((Class) type).isArray())) {
                return new Yvb(type);
            }
            if (type instanceof WildcardType) {
                return new C7643wwb((WildcardType) type);
            }
            return new C6681iwb(type);
        }

        public /* synthetic */ a(C7264rYa rya) {
            this();
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof C7436twb) && C7471uYa.a((Object) f(), (Object) ((C7436twb) obj).f());
    }

    /* access modifiers changed from: protected */
    public abstract Type f();

    public int hashCode() {
        return f().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": ");
        sb.append(f());
        return sb.toString();
    }
}
