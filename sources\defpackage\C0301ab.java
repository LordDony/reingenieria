package defpackage;

import android.graphics.Color;

/* renamed from: ab reason: default package and case insensitive filesystem */
/* compiled from: ColorUtils */
public final class C0301ab {
    private static final ThreadLocal<double[]> a = new ThreadLocal<>();

    private static float a(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    private static int a(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            return 0;
        }
        return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
    }

    public static int b(int i, int i2) {
        int alpha = Color.alpha(i2);
        int alpha2 = Color.alpha(i);
        int d = d(alpha2, alpha);
        return Color.argb(d, a(Color.red(i), alpha2, Color.red(i2), alpha, d), a(Color.green(i), alpha2, Color.green(i2), alpha, d), a(Color.blue(i), alpha2, Color.blue(i2), alpha, d));
    }

    public static int c(int i, int i2) {
        if (i2 >= 0 && i2 <= 255) {
            return (i & 16777215) | (i2 << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }

    private static int d(int i, int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255);
    }

    public static double a(int i) {
        double[] a2 = a();
        a(i, a2);
        return a2[1] / 100.0d;
    }

    public static double a(int i, int i2) {
        if (Color.alpha(i2) == 255) {
            if (Color.alpha(i) < 255) {
                i = b(i, i2);
            }
            double a2 = a(i) + 0.05d;
            double a3 = a(i2) + 0.05d;
            return Math.max(a2, a3) / Math.min(a2, a3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("background can not be translucent: #");
        sb.append(Integer.toHexString(i2));
        throw new IllegalArgumentException(sb.toString());
    }

    public static int a(int i, int i2, float f) {
        int i3 = 255;
        if (Color.alpha(i2) == 255) {
            double d = (double) f;
            if (a(c(i, 255), i2) < d) {
                return -1;
            }
            int i4 = 0;
            for (int i5 = 0; i5 <= 10 && i3 - i4 > 1; i5++) {
                int i6 = (i4 + i3) / 2;
                if (a(c(i, i6), i2) < d) {
                    i4 = i6;
                } else {
                    i3 = i6;
                }
            }
            return i3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("background can not be translucent: #");
        sb.append(Integer.toHexString(i2));
        throw new IllegalArgumentException(sb.toString());
    }

    public static void a(int i, int i2, int i3, float[] fArr) {
        float f;
        float f2;
        float f3 = ((float) i) / 255.0f;
        float f4 = ((float) i2) / 255.0f;
        float f5 = ((float) i3) / 255.0f;
        float max = Math.max(f3, Math.max(f4, f5));
        float min = Math.min(f3, Math.min(f4, f5));
        float f6 = max - min;
        float f7 = (max + min) / 2.0f;
        if (max == min) {
            f2 = 0.0f;
            f = 0.0f;
        } else {
            f2 = max == f3 ? ((f4 - f5) / f6) % 6.0f : max == f4 ? ((f5 - f3) / f6) + 2.0f : ((f3 - f4) / f6) + 4.0f;
            f = f6 / (1.0f - Math.abs((2.0f * f7) - 1.0f));
        }
        float f8 = (f2 * 60.0f) % 360.0f;
        if (f8 < 0.0f) {
            f8 += 360.0f;
        }
        fArr[0] = a(f8, 0.0f, 360.0f);
        fArr[1] = a(f, 0.0f, 1.0f);
        fArr[2] = a(f7, 0.0f, 1.0f);
    }

    public static void a(int i, float[] fArr) {
        a(Color.red(i), Color.green(i), Color.blue(i), fArr);
    }

    public static void a(int i, double[] dArr) {
        a(Color.red(i), Color.green(i), Color.blue(i), dArr);
    }

    public static void a(int i, int i2, int i3, double[] dArr) {
        double d;
        double d2;
        double d3;
        double[] dArr2 = dArr;
        if (dArr2.length == 3) {
            double d4 = ((double) i) / 255.0d;
            if (d4 < 0.04045d) {
                d = d4 / 12.92d;
            } else {
                d = Math.pow((d4 + 0.055d) / 1.055d, 2.4d);
            }
            double d5 = d;
            double d6 = ((double) i2) / 255.0d;
            if (d6 < 0.04045d) {
                d2 = d6 / 12.92d;
            } else {
                d2 = Math.pow((d6 + 0.055d) / 1.055d, 2.4d);
            }
            double d7 = d2;
            double d8 = ((double) i3) / 255.0d;
            if (d8 < 0.04045d) {
                d3 = d8 / 12.92d;
            } else {
                d3 = Math.pow((d8 + 0.055d) / 1.055d, 2.4d);
            }
            dArr2[0] = ((0.4124d * d5) + (0.3576d * d7) + (0.1805d * d3)) * 100.0d;
            dArr2[1] = ((0.2126d * d5) + (0.7152d * d7) + (0.0722d * d3)) * 100.0d;
            dArr2[2] = ((d5 * 0.0193d) + (d7 * 0.1192d) + (d3 * 0.9505d)) * 100.0d;
            return;
        }
        throw new IllegalArgumentException("outXyz must have a length of 3.");
    }

    private static double[] a() {
        double[] dArr = (double[]) a.get();
        if (dArr != null) {
            return dArr;
        }
        double[] dArr2 = new double[3];
        a.set(dArr2);
        return dArr2;
    }
}
