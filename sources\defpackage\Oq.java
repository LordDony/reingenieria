package defpackage;

/* renamed from: Oq reason: default package */
/* compiled from: ParsableBitArray */
public final class Oq {
    public byte[] a;
    private int b;
    private int c;
    private int d;

    public Oq() {
        this.a = C0471ar.f;
    }

    private void g() {
        boolean z;
        int i = this.b;
        if (i >= 0) {
            int i2 = this.d;
            if (i < i2 || (i == i2 && this.c == 0)) {
                z = true;
                C1852xq.b(z);
            }
        }
        z = false;
        C1852xq.b(z);
    }

    public void a(byte[] bArr) {
        a(bArr, bArr.length);
    }

    public void b(int i) {
        this.b = i / 8;
        this.c = i - (this.b * 8);
        g();
    }

    public int c() {
        C1852xq.b(this.c == 0);
        return this.b;
    }

    public int d() {
        return (this.b * 8) + this.c;
    }

    public boolean e() {
        boolean z = (this.a[this.b] & (128 >> this.c)) != 0;
        f();
        return z;
    }

    public void f() {
        int i = this.c + 1;
        this.c = i;
        if (i == 8) {
            this.c = 0;
            this.b++;
        }
        g();
    }

    public void a(Pq pq) {
        a(pq.a, pq.d());
        b(pq.c() * 8);
    }

    public void d(int i) {
        C1852xq.b(this.c == 0);
        this.b += i;
        g();
    }

    public Oq(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public void c(int i) {
        int i2 = i / 8;
        this.b += i2;
        this.c += i - (i2 * 8);
        int i3 = this.c;
        if (i3 > 7) {
            this.b++;
            this.c = i3 - 8;
        }
        g();
    }

    public Oq(byte[] bArr, int i) {
        this.a = bArr;
        this.d = i;
    }

    public void a(byte[] bArr, int i) {
        this.a = bArr;
        this.b = 0;
        this.c = 0;
        this.d = i;
    }

    public void b() {
        if (this.c != 0) {
            this.c = 0;
            this.b++;
            g();
        }
    }

    public int a() {
        return ((this.d - this.b) * 8) - this.c;
    }

    public void b(byte[] bArr, int i, int i2) {
        C1852xq.b(this.c == 0);
        System.arraycopy(this.a, this.b, bArr, i, i2);
        this.b += i2;
        g();
    }

    public int a(int i) {
        int i2;
        if (i == 0) {
            return 0;
        }
        this.c += i;
        int i3 = 0;
        while (true) {
            i2 = this.c;
            if (i2 <= 8) {
                break;
            }
            this.c = i2 - 8;
            byte[] bArr = this.a;
            int i4 = this.b;
            this.b = i4 + 1;
            i3 |= (bArr[i4] & 255) << this.c;
        }
        byte[] bArr2 = this.a;
        int i5 = this.b;
        int i6 = (-1 >>> (32 - i)) & (i3 | ((bArr2[i5] & 255) >> (8 - i2)));
        if (i2 == 8) {
            this.c = 0;
            this.b = i5 + 1;
        }
        g();
        return i6;
    }

    public void a(byte[] bArr, int i, int i2) {
        int i3 = (i2 >> 3) + i;
        while (i < i3) {
            byte[] bArr2 = this.a;
            int i4 = this.b;
            this.b = i4 + 1;
            byte b2 = bArr2[i4];
            int i5 = this.c;
            bArr[i] = (byte) (b2 << i5);
            bArr[i] = (byte) (((255 & bArr2[this.b]) >> (8 - i5)) | bArr[i]);
            i++;
        }
        int i6 = i2 & 7;
        if (i6 != 0) {
            bArr[i3] = (byte) (bArr[i3] & (255 >> i6));
            int i7 = this.c;
            if (i7 + i6 > 8) {
                byte b3 = bArr[i3];
                byte[] bArr3 = this.a;
                int i8 = this.b;
                this.b = i8 + 1;
                bArr[i3] = (byte) (b3 | ((bArr3[i8] & 255) << i7));
                this.c = i7 - 8;
            }
            this.c += i6;
            byte[] bArr4 = this.a;
            int i9 = this.b;
            byte b4 = bArr4[i9] & 255;
            int i10 = this.c;
            bArr[i3] = (byte) (((byte) ((b4 >> (8 - i10)) << (8 - i6))) | bArr[i3]);
            if (i10 == 8) {
                this.c = 0;
                this.b = i9 + 1;
            }
            g();
        }
    }

    public void a(int i, int i2) {
        if (i2 < 32) {
            i &= (1 << i2) - 1;
        }
        int min = Math.min(8 - this.c, i2);
        int i3 = this.c;
        int i4 = (8 - i3) - min;
        byte b2 = (65280 >> i3) | ((1 << i4) - 1);
        byte[] bArr = this.a;
        int i5 = this.b;
        bArr[i5] = (byte) (b2 & bArr[i5]);
        int i6 = i2 - min;
        bArr[i5] = (byte) (((i >>> i6) << i4) | bArr[i5]);
        int i7 = i5 + 1;
        while (i6 > 8) {
            int i8 = i7 + 1;
            this.a[i7] = (byte) (i >>> (i6 - 8));
            i6 -= 8;
            i7 = i8;
        }
        int i9 = 8 - i6;
        byte[] bArr2 = this.a;
        bArr2[i7] = (byte) (bArr2[i7] & ((1 << i9) - 1));
        bArr2[i7] = (byte) (((i & ((1 << i6) - 1)) << i9) | bArr2[i7]);
        c(i2);
        g();
    }
}
