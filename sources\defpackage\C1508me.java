package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

/* renamed from: me reason: default package and case insensitive filesystem */
/* compiled from: PathInterpolatorCompat */
public class C1508me implements Interpolator {
    private float[] a;
    private float[] b;

    public C1508me(Context context, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        this(context.getResources(), context.getTheme(), attributeSet, xmlPullParser);
    }

    private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
        String str = "pathData";
        if (C0286Za.a(xmlPullParser, str)) {
            String a2 = C0286Za.a(typedArray, xmlPullParser, str, 4);
            Path b2 = C0486bb.b(a2);
            if (b2 != null) {
                a(b2);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The path is null, which is created from ");
            sb.append(a2);
            throw new InflateException(sb.toString());
        }
        String str2 = "controlX1";
        if (C0286Za.a(xmlPullParser, str2)) {
            String str3 = "controlY1";
            if (C0286Za.a(xmlPullParser, str3)) {
                float a3 = C0286Za.a(typedArray, xmlPullParser, str2, 0, 0.0f);
                float a4 = C0286Za.a(typedArray, xmlPullParser, str3, 1, 0.0f);
                String str4 = "controlX2";
                boolean a5 = C0286Za.a(xmlPullParser, str4);
                String str5 = "controlY2";
                if (a5 != C0286Za.a(xmlPullParser, str5)) {
                    throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
                } else if (!a5) {
                    a(a3, a4);
                } else {
                    a(a3, a4, C0286Za.a(typedArray, xmlPullParser, str4, 2, 0.0f), C0286Za.a(typedArray, xmlPullParser, str5, 3, 0.0f));
                }
            } else {
                throw new InflateException("pathInterpolator requires the controlY1 attribute");
            }
        } else {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        }
    }

    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int i = 0;
        int length = this.a.length - 1;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.a[i2]) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float[] fArr = this.a;
        float f2 = fArr[length] - fArr[i];
        if (f2 == 0.0f) {
            return this.b[i];
        }
        float f3 = (f - fArr[i]) / f2;
        float[] fArr2 = this.b;
        float f4 = fArr2[i];
        return f4 + (f3 * (fArr2[length] - f4));
    }

    public C1508me(Resources resources, Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        TypedArray a2 = C0286Za.a(resources, theme, attributeSet, C1292fe.l);
        a(a2, xmlPullParser);
        a2.recycle();
    }

    private void a(float f, float f2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        a(path);
    }

    private void a(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        a(path);
    }

    private void a(Path path) {
        int i = 0;
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        int min = Math.min(3000, ((int) (length / 0.002f)) + 1);
        if (min > 0) {
            this.a = new float[min];
            this.b = new float[min];
            float[] fArr = new float[2];
            for (int i2 = 0; i2 < min; i2++) {
                pathMeasure.getPosTan((((float) i2) * length) / ((float) (min - 1)), fArr, null);
                this.a[i2] = fArr[0];
                this.b[i2] = fArr[1];
            }
            if (((double) Math.abs(this.a[0])) <= 1.0E-5d && ((double) Math.abs(this.b[0])) <= 1.0E-5d) {
                int i3 = min - 1;
                if (((double) Math.abs(this.a[i3] - 1.0f)) <= 1.0E-5d && ((double) Math.abs(this.b[i3] - 1.0f)) <= 1.0E-5d) {
                    int i4 = 0;
                    float f = 0.0f;
                    while (i < min) {
                        float[] fArr2 = this.a;
                        int i5 = i4 + 1;
                        float f2 = fArr2[i4];
                        if (f2 >= f) {
                            fArr2[i] = f2;
                            i++;
                            f = f2;
                            i4 = i5;
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("The Path cannot loop back on itself, x :");
                            sb.append(f2);
                            throw new IllegalArgumentException(sb.toString());
                        }
                    }
                    if (pathMeasure.nextContour()) {
                        throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
                    }
                    return;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("The Path must start at (0,0) and end at (1,1) start: ");
            sb2.append(this.a[0]);
            String str = ",";
            sb2.append(str);
            sb2.append(this.b[0]);
            sb2.append(" end:");
            int i6 = min - 1;
            sb2.append(this.a[i6]);
            sb2.append(str);
            sb2.append(this.b[i6]);
            throw new IllegalArgumentException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("The Path has a invalid length ");
        sb3.append(length);
        throw new IllegalArgumentException(sb3.toString());
    }
}
