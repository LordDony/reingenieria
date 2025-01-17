package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/* compiled from: CircularProgressDrawable */
public class d extends Drawable implements Animatable {
    private static final Interpolator a = new LinearInterpolator();
    private static final Interpolator b = new C0488bd();
    private static final int[] c = {-16777216};
    private final a d = new a();
    private float e;
    private Resources f;
    private Animator g;
    float h;
    boolean i;

    /* compiled from: CircularProgressDrawable */
    private static class a {
        final RectF a = new RectF();
        final Paint b = new Paint();
        final Paint c = new Paint();
        final Paint d = new Paint();
        float e = 0.0f;
        float f = 0.0f;
        float g = 0.0f;
        float h = 5.0f;
        int[] i;
        int j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float p = 1.0f;
        float q;
        int r;
        int s;
        int t = 255;
        int u;

        a() {
            this.b.setStrokeCap(Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Style.STROKE);
            this.c.setStyle(Style.FILL);
            this.c.setAntiAlias(true);
            this.d.setColor(0);
        }

        /* access modifiers changed from: 0000 */
        public void a(float f2, float f3) {
            this.r = (int) f2;
            this.s = (int) f3;
        }

        /* access modifiers changed from: 0000 */
        public void b(int i2) {
            this.u = i2;
        }

        /* access modifiers changed from: 0000 */
        public void c(int i2) {
            this.j = i2;
            this.u = this.i[this.j];
        }

        /* access modifiers changed from: 0000 */
        public int d() {
            return (this.j + 1) % this.i.length;
        }

        /* access modifiers changed from: 0000 */
        public void e(float f2) {
            this.e = f2;
        }

        /* access modifiers changed from: 0000 */
        public void f(float f2) {
            this.h = f2;
            this.b.setStrokeWidth(f2);
        }

        /* access modifiers changed from: 0000 */
        public float g() {
            return this.l;
        }

        /* access modifiers changed from: 0000 */
        public float h() {
            return this.m;
        }

        /* access modifiers changed from: 0000 */
        public float i() {
            return this.k;
        }

        /* access modifiers changed from: 0000 */
        public void j() {
            c(d());
        }

        /* access modifiers changed from: 0000 */
        public void k() {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            e(0.0f);
            c(0.0f);
            d(0.0f);
        }

        /* access modifiers changed from: 0000 */
        public void l() {
            this.k = this.e;
            this.l = this.f;
            this.m = this.g;
        }

        /* access modifiers changed from: 0000 */
        public float b() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void d(float f2) {
            this.g = f2;
        }

        /* access modifiers changed from: 0000 */
        public float e() {
            return this.e;
        }

        /* access modifiers changed from: 0000 */
        public void a(Canvas canvas, Rect rect) {
            RectF rectF = this.a;
            float f2 = this.q;
            float f3 = (this.h / 2.0f) + f2;
            if (f2 <= 0.0f) {
                f3 = (((float) Math.min(rect.width(), rect.height())) / 2.0f) - Math.max((((float) this.r) * this.p) / 2.0f, this.h / 2.0f);
            }
            rectF.set(((float) rect.centerX()) - f3, ((float) rect.centerY()) - f3, ((float) rect.centerX()) + f3, ((float) rect.centerY()) + f3);
            float f4 = this.e;
            float f5 = this.g;
            float f6 = (f4 + f5) * 360.0f;
            float f7 = ((this.f + f5) * 360.0f) - f6;
            this.b.setColor(this.u);
            this.b.setAlpha(this.t);
            float f8 = this.h / 2.0f;
            rectF.inset(f8, f8);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.d);
            float f9 = -f8;
            rectF.inset(f9, f9);
            canvas.drawArc(rectF, f6, f7, false, this.b);
            a(canvas, f6, f7, rectF);
        }

        /* access modifiers changed from: 0000 */
        public void b(float f2) {
            this.q = f2;
        }

        /* access modifiers changed from: 0000 */
        public int c() {
            return this.i[d()];
        }

        /* access modifiers changed from: 0000 */
        public int f() {
            return this.i[this.j];
        }

        /* access modifiers changed from: 0000 */
        public void c(float f2) {
            this.f = f2;
        }

        /* access modifiers changed from: 0000 */
        public void a(Canvas canvas, float f2, float f3, RectF rectF) {
            if (this.n) {
                Path path = this.o;
                if (path == null) {
                    this.o = new Path();
                    this.o.setFillType(FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                float min = Math.min(rectF.width(), rectF.height()) / 2.0f;
                float f4 = (((float) this.r) * this.p) / 2.0f;
                this.o.moveTo(0.0f, 0.0f);
                this.o.lineTo(((float) this.r) * this.p, 0.0f);
                Path path2 = this.o;
                float f5 = (float) this.r;
                float f6 = this.p;
                path2.lineTo((f5 * f6) / 2.0f, ((float) this.s) * f6);
                this.o.offset((min + rectF.centerX()) - f4, rectF.centerY() + (this.h / 2.0f));
                this.o.close();
                this.c.setColor(this.u);
                this.c.setAlpha(this.t);
                canvas.save();
                canvas.rotate(f2 + f3, rectF.centerX(), rectF.centerY());
                canvas.drawPath(this.o, this.c);
                canvas.restore();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int[] iArr) {
            this.i = iArr;
            c(0);
        }

        /* access modifiers changed from: 0000 */
        public void a(ColorFilter colorFilter) {
            this.b.setColorFilter(colorFilter);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            this.t = i2;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return this.t;
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            if (this.n != z) {
                this.n = z;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(float f2) {
            if (f2 != this.p) {
                this.p = f2;
            }
        }
    }

    public d(Context context) {
        C0263Ub.a(context);
        this.f = context.getResources();
        this.d.a(c);
        c(2.5f);
        a();
    }

    private int a(float f2, int i2, int i3) {
        int i4 = (i2 >> 24) & 255;
        int i5 = (i2 >> 16) & 255;
        int i6 = (i2 >> 8) & 255;
        int i7 = i2 & 255;
        return ((i4 + ((int) (((float) (((i3 >> 24) & 255) - i4)) * f2))) << 24) | ((i5 + ((int) (((float) (((i3 >> 16) & 255) - i5)) * f2))) << 16) | ((i6 + ((int) (((float) (((i3 >> 8) & 255) - i6)) * f2))) << 8) | (i7 + ((int) (f2 * ((float) ((i3 & 255) - i7)))));
    }

    private void a(float f2, float f3, float f4, float f5) {
        a aVar = this.d;
        float f6 = this.f.getDisplayMetrics().density;
        aVar.f(f3 * f6);
        aVar.b(f2 * f6);
        aVar.c(0);
        aVar.a(f4 * f6, f5 * f6);
    }

    private void d(float f2) {
        this.e = f2;
    }

    public void b(float f2) {
        this.d.d(f2);
        invalidateSelf();
    }

    public void c(float f2) {
        this.d.f(f2);
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.e, bounds.exactCenterX(), bounds.exactCenterY());
        this.d.a(canvas, bounds);
        canvas.restore();
    }

    public int getAlpha() {
        return this.d.a();
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isRunning() {
        return this.g.isRunning();
    }

    public void setAlpha(int i2) {
        this.d.a(i2);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.d.a(colorFilter);
        invalidateSelf();
    }

    public void start() {
        this.g.cancel();
        this.d.l();
        if (this.d.b() != this.d.e()) {
            this.i = true;
            this.g.setDuration(666);
            this.g.start();
            return;
        }
        this.d.c(0);
        this.d.k();
        this.g.setDuration(1332);
        this.g.start();
    }

    public void stop() {
        this.g.cancel();
        d(0.0f);
        this.d.a(false);
        this.d.c(0);
        this.d.k();
        invalidateSelf();
    }

    private void b(float f2, a aVar) {
        a(f2, aVar);
        float floor = (float) (Math.floor((double) (aVar.h() / 0.8f)) + 1.0d);
        aVar.e(aVar.i() + (((aVar.g() - 0.01f) - aVar.i()) * f2));
        aVar.c(aVar.g());
        aVar.d(aVar.h() + ((floor - aVar.h()) * f2));
    }

    public void a(int i2) {
        if (i2 == 0) {
            a(11.0f, 3.0f, 12.0f, 6.0f);
        } else {
            a(7.5f, 2.5f, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    public void a(boolean z) {
        this.d.a(z);
        invalidateSelf();
    }

    public void a(float f2) {
        this.d.a(f2);
        invalidateSelf();
    }

    public void a(float f2, float f3) {
        this.d.e(f2);
        this.d.c(f3);
        invalidateSelf();
    }

    public void a(int... iArr) {
        this.d.a(iArr);
        this.d.c(0);
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2, a aVar) {
        if (f2 > 0.75f) {
            aVar.b(a((f2 - 0.75f) / 0.25f, aVar.f(), aVar.c()));
        } else {
            aVar.b(aVar.f());
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2, a aVar, boolean z) {
        float f3;
        float f4;
        if (this.i) {
            b(f2, aVar);
        } else if (f2 != 1.0f || z) {
            float h2 = aVar.h();
            if (f2 < 0.5f) {
                float f5 = f2 / 0.5f;
                float i2 = aVar.i();
                float f6 = i2;
                f3 = (b.getInterpolation(f5) * 0.79f) + 0.01f + i2;
                f4 = f6;
            } else {
                f3 = aVar.i() + 0.79f;
                f4 = f3 - (((1.0f - b.getInterpolation((f2 - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
            }
            float f7 = h2 + (0.20999998f * f2);
            float f8 = (f2 + this.h) * 216.0f;
            aVar.e(f4);
            aVar.c(f3);
            aVar.d(f7);
            d(f8);
        }
    }

    private void a() {
        a aVar = this.d;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.addUpdateListener(new b(this, aVar));
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(1);
        ofFloat.setInterpolator(a);
        ofFloat.addListener(new c(this, aVar));
        this.g = ofFloat;
    }
}
