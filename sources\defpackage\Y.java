package defpackage;

/* renamed from: Y reason: default package */
/* compiled from: LongSparseArray */
public class Y<E> implements Cloneable {
    private static final Object a = new Object();
    private boolean b;
    private long[] c;
    private Object[] d;
    private int e;

    public Y() {
        this(10);
    }

    private void k() {
        int i = this.e;
        long[] jArr = this.c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != a) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.b = false;
        this.e = i2;
    }

    public void a(long j) {
        int a2 = X.a(this.c, this.e, j);
        if (a2 >= 0) {
            Object[] objArr = this.d;
            Object obj = objArr[a2];
            Object obj2 = a;
            if (obj != obj2) {
                objArr[a2] = obj2;
                this.b = true;
            }
        }
    }

    public E b(long j) {
        return b(j, null);
    }

    public void c(long j, E e2) {
        int a2 = X.a(this.c, this.e, j);
        if (a2 >= 0) {
            this.d[a2] = e2;
        } else {
            int i = ~a2;
            if (i < this.e) {
                Object[] objArr = this.d;
                if (objArr[i] == a) {
                    this.c[i] = j;
                    objArr[i] = e2;
                    return;
                }
            }
            if (this.b && this.e >= this.c.length) {
                k();
                i = ~X.a(this.c, this.e, j);
            }
            int i2 = this.e;
            if (i2 >= this.c.length) {
                int c2 = X.c(i2 + 1);
                long[] jArr = new long[c2];
                Object[] objArr2 = new Object[c2];
                long[] jArr2 = this.c;
                System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
                Object[] objArr3 = this.d;
                System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
                this.c = jArr;
                this.d = objArr2;
            }
            int i3 = this.e;
            if (i3 - i != 0) {
                long[] jArr3 = this.c;
                int i4 = i + 1;
                System.arraycopy(jArr3, i, jArr3, i4, i3 - i);
                Object[] objArr4 = this.d;
                System.arraycopy(objArr4, i, objArr4, i4, this.e - i);
            }
            this.c[i] = j;
            this.d[i] = e2;
            this.e++;
        }
    }

    public void i() {
        int i = this.e;
        Object[] objArr = this.d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.e = 0;
        this.b = false;
    }

    public int j() {
        if (this.b) {
            k();
        }
        return this.e;
    }

    public String toString() {
        if (j() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.e * 28);
        sb.append('{');
        for (int i = 0; i < this.e; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a(i));
            sb.append('=');
            Object c2 = c(i);
            if (c2 != this) {
                sb.append(c2);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public Y(int i) {
        this.b = false;
        if (i == 0) {
            this.c = X.b;
            this.d = X.c;
        } else {
            int c2 = X.c(i);
            this.c = new long[c2];
            this.d = new Object[c2];
        }
        this.e = 0;
    }

    public E b(long j, E e2) {
        int a2 = X.a(this.c, this.e, j);
        if (a2 >= 0) {
            E[] eArr = this.d;
            if (eArr[a2] != a) {
                return eArr[a2];
            }
        }
        return e2;
    }

    public Y<E> clone() {
        try {
            Y<E> y = (Y) super.clone();
            y.c = (long[]) this.c.clone();
            y.d = (Object[]) this.d.clone();
            return y;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public long a(int i) {
        if (this.b) {
            k();
        }
        return this.c[i];
    }

    public void b(int i) {
        Object[] objArr = this.d;
        Object obj = objArr[i];
        Object obj2 = a;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.b = true;
        }
    }

    public void a(long j, E e2) {
        int i = this.e;
        if (i == 0 || j > this.c[i - 1]) {
            if (this.b && this.e >= this.c.length) {
                k();
            }
            int i2 = this.e;
            if (i2 >= this.c.length) {
                int c2 = X.c(i2 + 1);
                long[] jArr = new long[c2];
                Object[] objArr = new Object[c2];
                long[] jArr2 = this.c;
                System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
                Object[] objArr2 = this.d;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.c = jArr;
                this.d = objArr;
            }
            this.c[i2] = j;
            this.d[i2] = e2;
            this.e = i2 + 1;
            return;
        }
        c(j, e2);
    }

    public E c(int i) {
        if (this.b) {
            k();
        }
        return this.d[i];
    }

    public int c(long j) {
        if (this.b) {
            k();
        }
        return X.a(this.c, this.e, j);
    }
}
