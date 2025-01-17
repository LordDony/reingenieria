package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.api.Api.BaseClientBuilder;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.util.Arrays;

/* renamed from: com.google.android.gms.internal.measurement.mb reason: case insensitive filesystem */
final class C1001mb extends C0989kb {
    private final byte[] f;
    private final boolean g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;

    private C1001mb(byte[] bArr, int i2, int i3, boolean z) {
        super();
        this.m = BaseClientBuilder.API_PRIORITY_OTHER;
        this.f = bArr;
        this.h = i3 + i2;
        this.j = i2;
        this.k = this.j;
        this.g = z;
    }

    private final byte A() throws IOException {
        int i2 = this.j;
        if (i2 != this.h) {
            byte[] bArr = this.f;
            this.j = i2 + 1;
            return bArr[i2];
        }
        throw Rb.a();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x0068;
     */
    private final int v() throws IOException {
        byte b;
        int i2 = this.j;
        int i3 = this.h;
        if (i3 != i2) {
            byte[] bArr = this.f;
            int i4 = i2 + 1;
            byte b2 = bArr[i2];
            if (b2 >= 0) {
                this.j = i4;
                return b2;
            } else if (i3 - i4 >= 9) {
                int i5 = i4 + 1;
                byte b3 = b2 ^ (bArr[i4] << 7);
                if (b3 < 0) {
                    b = b3 ^ Byte.MIN_VALUE;
                } else {
                    int i6 = i5 + 1;
                    byte b4 = b3 ^ (bArr[i5] << Ascii.SO);
                    if (b4 >= 0) {
                        b = b4 ^ 16256;
                    } else {
                        i5 = i6 + 1;
                        byte b5 = b4 ^ (bArr[i6] << Ascii.NAK);
                        if (b5 < 0) {
                            b = b5 ^ -2080896;
                        } else {
                            i6 = i5 + 1;
                            byte b6 = bArr[i5];
                            b = (b5 ^ (b6 << Ascii.FS)) ^ 266354560;
                            if (b6 < 0) {
                                i5 = i6 + 1;
                                if (bArr[i6] < 0) {
                                    i6 = i5 + 1;
                                    if (bArr[i5] < 0) {
                                        i5 = i6 + 1;
                                        if (bArr[i6] < 0) {
                                            i6 = i5 + 1;
                                            if (bArr[i5] < 0) {
                                                i5 = i6 + 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    i5 = i6;
                }
                this.j = i5;
                return b;
            }
        }
        return (int) u();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b0, code lost:
        if (((long) r2[r0]) >= 0) goto L_0x00b4;
     */
    private final long w() throws IOException {
        long j2;
        int i2;
        long j3;
        long j4;
        byte b;
        int i3 = this.j;
        int i4 = this.h;
        if (i4 != i3) {
            byte[] bArr = this.f;
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            if (b2 >= 0) {
                this.j = i5;
                return (long) b2;
            } else if (i4 - i5 >= 9) {
                int i6 = i5 + 1;
                byte b3 = b2 ^ (bArr[i5] << 7);
                if (b3 < 0) {
                    b = b3 ^ Byte.MIN_VALUE;
                } else {
                    int i7 = i6 + 1;
                    byte b4 = b3 ^ (bArr[i6] << Ascii.SO);
                    if (b4 >= 0) {
                        i2 = i7;
                        j2 = (long) (b4 ^ 16256);
                    } else {
                        i6 = i7 + 1;
                        byte b5 = b4 ^ (bArr[i7] << Ascii.NAK);
                        if (b5 < 0) {
                            b = b5 ^ -2080896;
                        } else {
                            long j5 = (long) b5;
                            int i8 = i6 + 1;
                            long j6 = j5 ^ (((long) bArr[i6]) << 28);
                            if (j6 >= 0) {
                                j4 = 266354560;
                            } else {
                                int i9 = i8 + 1;
                                long j7 = j6 ^ (((long) bArr[i8]) << 35);
                                if (j7 < 0) {
                                    j3 = -34093383808L;
                                } else {
                                    i8 = i9 + 1;
                                    j6 = j7 ^ (((long) bArr[i9]) << 42);
                                    if (j6 >= 0) {
                                        j4 = 4363953127296L;
                                    } else {
                                        i9 = i8 + 1;
                                        j7 = j6 ^ (((long) bArr[i8]) << 49);
                                        if (j7 < 0) {
                                            j3 = -558586000294016L;
                                        } else {
                                            int i10 = i9 + 1;
                                            long j8 = (j7 ^ (((long) bArr[i9]) << 56)) ^ 71499008037633920L;
                                            if (j8 < 0) {
                                                i2 = i10 + 1;
                                            } else {
                                                i2 = i10;
                                            }
                                            j2 = j8;
                                        }
                                    }
                                }
                                j2 = j7 ^ j3;
                            }
                            j2 = j6 ^ j4;
                            i2 = i8;
                        }
                    }
                    this.j = i2;
                    return j2;
                }
                j2 = (long) b;
                this.j = i2;
                return j2;
            }
        }
        return u();
    }

    private final int x() throws IOException {
        int i2 = this.j;
        if (this.h - i2 >= 4) {
            byte[] bArr = this.f;
            this.j = i2 + 4;
            return ((bArr[i2 + 3] & 255) << Ascii.CAN) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << Ascii.DLE);
        }
        throw Rb.a();
    }

    private final long y() throws IOException {
        int i2 = this.j;
        if (this.h - i2 >= 8) {
            byte[] bArr = this.f;
            this.j = i2 + 8;
            return ((((long) bArr[i2 + 7]) & 255) << 56) | (((long) bArr[i2]) & 255) | ((((long) bArr[i2 + 1]) & 255) << 8) | ((((long) bArr[i2 + 2]) & 255) << 16) | ((((long) bArr[i2 + 3]) & 255) << 24) | ((((long) bArr[i2 + 4]) & 255) << 32) | ((((long) bArr[i2 + 5]) & 255) << 40) | ((((long) bArr[i2 + 6]) & 255) << 48);
        }
        throw Rb.a();
    }

    private final void z() {
        this.h += this.i;
        int i2 = this.h;
        int i3 = i2 - this.k;
        int i4 = this.m;
        if (i3 > i4) {
            this.i = i3 - i4;
            this.h = i2 - this.i;
            return;
        }
        this.i = 0;
    }

    public final void a(int i2) throws Rb {
        if (this.l != i2) {
            throw Rb.e();
        }
    }

    public final boolean b(int i2) throws IOException {
        int d;
        int i3 = i2 & 7;
        int i4 = 0;
        if (i3 == 0) {
            if (this.h - this.j >= 10) {
                while (i4 < 10) {
                    byte[] bArr = this.f;
                    int i5 = this.j;
                    this.j = i5 + 1;
                    if (bArr[i5] < 0) {
                        i4++;
                    }
                }
                throw Rb.c();
            }
            while (i4 < 10) {
                if (A() < 0) {
                    i4++;
                }
            }
            throw Rb.c();
            return true;
        } else if (i3 == 1) {
            f(8);
            return true;
        } else if (i3 == 2) {
            f(v());
            return true;
        } else if (i3 == 3) {
            do {
                d = d();
                if (d == 0) {
                    break;
                }
            } while (b(d));
            a(((i2 >>> 3) << 3) | 4);
            return true;
        } else if (i3 == 4) {
            return false;
        } else {
            if (i3 == 5) {
                f(4);
                return true;
            }
            throw Rb.f();
        }
    }

    public final String c() throws IOException {
        int v = v();
        if (v > 0) {
            int i2 = this.h;
            int i3 = this.j;
            if (v <= i2 - i3) {
                String str = new String(this.f, i3, v, Hb.a);
                this.j += v;
                return str;
            }
        }
        if (v == 0) {
            return "";
        }
        if (v < 0) {
            throw Rb.b();
        }
        throw Rb.a();
    }

    public final int d() throws IOException {
        if (s()) {
            this.l = 0;
            return 0;
        }
        this.l = v();
        int i2 = this.l;
        if ((i2 >>> 3) != 0) {
            return i2;
        }
        throw Rb.d();
    }

    public final long e() throws IOException {
        return w();
    }

    public final long f() throws IOException {
        return w();
    }

    public final int g() throws IOException {
        return v();
    }

    public final long h() throws IOException {
        return y();
    }

    public final int i() throws IOException {
        return x();
    }

    public final boolean j() throws IOException {
        return w() != 0;
    }

    public final String k() throws IOException {
        int v = v();
        if (v > 0) {
            int i2 = this.h;
            int i3 = this.j;
            if (v <= i2 - i3) {
                String b = C0958fd.b(this.f, i3, v);
                this.j += v;
                return b;
            }
        }
        if (v == 0) {
            return "";
        }
        if (v <= 0) {
            throw Rb.b();
        }
        throw Rb.a();
    }

    public final Ya l() throws IOException {
        byte[] bArr;
        int v = v();
        if (v > 0) {
            int i2 = this.h;
            int i3 = this.j;
            if (v <= i2 - i3) {
                Ya a = Ya.a(this.f, i3, v);
                this.j += v;
                return a;
            }
        }
        if (v == 0) {
            return Ya.a;
        }
        if (v > 0) {
            int i4 = this.h;
            int i5 = this.j;
            if (v <= i4 - i5) {
                this.j = v + i5;
                bArr = Arrays.copyOfRange(this.f, i5, this.j);
                return Ya.a(bArr);
            }
        }
        if (v > 0) {
            throw Rb.a();
        } else if (v == 0) {
            bArr = Hb.c;
            return Ya.a(bArr);
        } else {
            throw Rb.b();
        }
    }

    public final int m() throws IOException {
        return v();
    }

    public final int n() throws IOException {
        return v();
    }

    public final int o() throws IOException {
        return x();
    }

    public final long p() throws IOException {
        return y();
    }

    public final int q() throws IOException {
        return C0989kb.g(v());
    }

    public final long r() throws IOException {
        return C0989kb.a(w());
    }

    public final boolean s() throws IOException {
        return this.j == this.h;
    }

    public final int t() {
        return this.j - this.k;
    }

    /* access modifiers changed from: 0000 */
    public final long u() throws IOException {
        long j2 = 0;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            byte A = A();
            j2 |= ((long) (A & Ascii.DEL)) << i2;
            if ((A & 128) == 0) {
                return j2;
            }
        }
        throw Rb.c();
    }

    public final void e(int i2) {
        this.m = i2;
        z();
    }

    public final void f(int i2) throws IOException {
        if (i2 >= 0) {
            int i3 = this.h;
            int i4 = this.j;
            if (i2 <= i3 - i4) {
                this.j = i4 + i2;
                return;
            }
        }
        if (i2 < 0) {
            throw Rb.b();
        }
        throw Rb.a();
    }

    public final double a() throws IOException {
        return Double.longBitsToDouble(y());
    }

    public final <T extends C1026qc> T a(C1073yc<T> ycVar, C1042tb tbVar) throws IOException {
        int v = v();
        if (this.a < this.b) {
            int d = d(v);
            this.a++;
            T t = (C1026qc) ycVar.a(this, tbVar);
            a(0);
            this.a--;
            e(d);
            return t;
        }
        throw Rb.g();
    }

    public final int d(int i2) throws Rb {
        if (i2 >= 0) {
            int t = i2 + t();
            int i3 = this.m;
            if (t <= i3) {
                this.m = t;
                z();
                return i3;
            }
            throw Rb.a();
        }
        throw Rb.b();
    }

    public final float b() throws IOException {
        return Float.intBitsToFloat(x());
    }
}
