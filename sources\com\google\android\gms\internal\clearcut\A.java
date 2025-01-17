package com.google.android.gms.internal.clearcut;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;

public abstract class A implements Serializable, Iterable<Byte> {
    public static final A a = new H(C0761ia.c);
    private static final E b = (C0795u.a() ? new I(null) : new C(null));
    private int c = 0;

    A() {
    }

    public static A a(String str) {
        return new H(str.getBytes(C0761ia.a));
    }

    public static A a(byte[] bArr, int i, int i2) {
        return new H(b.a(bArr, i, i2));
    }

    static int b(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder(37);
            sb3.append("End index: ");
            sb3.append(i2);
            sb3.append(" >= ");
            sb3.append(i3);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    static F m(int i) {
        return new F(i, null);
    }

    /* access modifiers changed from: protected */
    public abstract int a(int i, int i2, int i3);

    public abstract A a(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract String a(Charset charset);

    /* access modifiers changed from: 0000 */
    public abstract void a(C0810z zVar) throws IOException;

    public abstract byte c(int i);

    public abstract boolean equals(Object obj);

    public abstract boolean f();

    /* access modifiers changed from: protected */
    public final int g() {
        return this.c;
    }

    public final String h() {
        return size() == 0 ? "" : a(C0761ia.a);
    }

    public final int hashCode() {
        int i = this.c;
        if (i == 0) {
            int size = size();
            i = a(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.c = i;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return new B(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }
}
