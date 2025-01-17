package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.internal.o;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: FloatingActionButtonImpl */
class e {
    static final TimeInterpolator a = Ez.c;
    static final int[] b = {16842919, 16842910};
    static final int[] c = {16843623, 16842908, 16842910};
    static final int[] d = {16842908, 16842910};
    static final int[] e = {16843623, 16842910};
    static final int[] f = {16842910};
    static final int[] g = new int[0];
    private ArrayList<AnimatorListener> A;
    final VisibilityAwareImageButton B;
    final _z C;
    private final Rect D = new Rect();
    private final RectF E = new RectF();
    private final RectF F = new RectF();
    private final Matrix G = new Matrix();
    private OnPreDrawListener H;
    int h = 0;
    Animator i;
    Lz j;
    Lz k;
    private Lz l;
    private Lz m;
    private final o n;
    Zz o;
    private float p;
    Drawable q;
    Drawable r;
    com.google.android.material.internal.c s;
    Drawable t;
    float u;
    float v;
    float w;
    int x;
    float y = 1.0f;
    private ArrayList<AnimatorListener> z;

    /* compiled from: FloatingActionButtonImpl */
    private class a extends f {
        a() {
            super(e.this, null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            return 0.0f;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private class b extends f {
        b() {
            super(e.this, null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            e eVar = e.this;
            return eVar.u + eVar.v;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private class c extends f {
        c() {
            super(e.this, null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            e eVar = e.this;
            return eVar.u + eVar.w;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    interface d {
        void a();

        void b();
    }

    /* renamed from: com.google.android.material.floatingactionbutton.e$e reason: collision with other inner class name */
    /* compiled from: FloatingActionButtonImpl */
    private class C0046e extends f {
        C0046e() {
            super(e.this, null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            return e.this.u;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private abstract class f extends AnimatorListenerAdapter implements AnimatorUpdateListener {
        private boolean a;
        private float b;
        private float c;

        private f() {
        }

        /* access modifiers changed from: protected */
        public abstract float a();

        public void onAnimationEnd(Animator animator) {
            e.this.o.b(this.c);
            this.a = false;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.a) {
                this.b = e.this.o.b();
                this.c = a();
                this.a = true;
            }
            Zz zz = e.this.o;
            float f = this.b;
            zz.b(f + ((this.c - f) * valueAnimator.getAnimatedFraction()));
        }

        /* synthetic */ f(e eVar, b bVar) {
            this();
        }
    }

    e(VisibilityAwareImageButton visibilityAwareImageButton, _z _zVar) {
        this.B = visibilityAwareImageButton;
        this.C = _zVar;
        this.n = new o();
        this.n.a(b, a((f) new c()));
        this.n.a(c, a((f) new b()));
        this.n.a(d, a((f) new b()));
        this.n.a(e, a((f) new b()));
        this.n.a(f, a((f) new C0046e()));
        this.n.a(g, a((f) new a()));
        this.p = this.B.getRotation();
    }

    private void t() {
        if (this.H == null) {
            this.H = new d(this);
        }
    }

    private Lz u() {
        if (this.m == null) {
            this.m = Lz.a(this.B.getContext(), C1741tz.design_fab_hide_motion_spec);
        }
        return this.m;
    }

    private Lz v() {
        if (this.l == null) {
            this.l = Lz.a(this.B.getContext(), C1741tz.design_fab_show_motion_spec);
        }
        return this.l;
    }

    private boolean w() {
        return C1778vc.B(this.B) && !this.B.isInEditMode();
    }

    private void x() {
        if (VERSION.SDK_INT == 19) {
            if (this.p % 90.0f != 0.0f) {
                if (this.B.getLayerType() != 1) {
                    this.B.setLayerType(1, null);
                }
            } else if (this.B.getLayerType() != 0) {
                this.B.setLayerType(0, null);
            }
        }
        Zz zz = this.o;
        if (zz != null) {
            zz.a(-this.p);
        }
        com.google.android.material.internal.c cVar = this.s;
        if (cVar != null) {
            cVar.b(-this.p);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList, Mode mode, ColorStateList colorStateList2, int i2) {
        Drawable[] drawableArr;
        this.q = androidx.core.graphics.drawable.a.i(a());
        androidx.core.graphics.drawable.a.a(this.q, colorStateList);
        if (mode != null) {
            androidx.core.graphics.drawable.a.a(this.q, mode);
        }
        this.r = androidx.core.graphics.drawable.a.i(a());
        androidx.core.graphics.drawable.a.a(this.r, Yz.a(colorStateList2));
        if (i2 > 0) {
            this.s = a(i2, colorStateList);
            drawableArr = new Drawable[]{this.s, this.q, this.r};
        } else {
            this.s = null;
            drawableArr = new Drawable[]{this.q, this.r};
        }
        this.t = new LayerDrawable(drawableArr);
        Context context = this.B.getContext();
        Drawable drawable = this.t;
        float b2 = this.C.b();
        float f2 = this.u;
        Zz zz = new Zz(context, drawable, b2, f2, f2 + this.w);
        this.o = zz;
        this.o.a(false);
        this.C.a(this.o);
    }

    /* access modifiers changed from: 0000 */
    public void b(ColorStateList colorStateList) {
        Drawable drawable = this.r;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, Yz.a(colorStateList));
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Rect rect) {
    }

    /* access modifiers changed from: 0000 */
    public float c() {
        return this.u;
    }

    /* access modifiers changed from: 0000 */
    public final void d(float f2) {
        if (this.w != f2) {
            this.w = f2;
            a(this.u, this.v, this.w);
        }
    }

    /* access modifiers changed from: 0000 */
    public float e() {
        return this.v;
    }

    /* access modifiers changed from: 0000 */
    public float f() {
        return this.w;
    }

    /* access modifiers changed from: 0000 */
    public final Lz g() {
        return this.j;
    }

    /* access modifiers changed from: 0000 */
    public boolean h() {
        boolean z2 = false;
        if (this.B.getVisibility() == 0) {
            if (this.h == 1) {
                z2 = true;
            }
            return z2;
        }
        if (this.h != 2) {
            z2 = true;
        }
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean i() {
        boolean z2 = false;
        if (this.B.getVisibility() != 0) {
            if (this.h == 2) {
                z2 = true;
            }
            return z2;
        }
        if (this.h != 1) {
            z2 = true;
        }
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        this.n.a();
    }

    /* access modifiers changed from: 0000 */
    public com.google.android.material.internal.c k() {
        return new com.google.android.material.internal.c();
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable l() {
        return new GradientDrawable();
    }

    /* access modifiers changed from: 0000 */
    public void m() {
        if (q()) {
            t();
            this.B.getViewTreeObserver().addOnPreDrawListener(this.H);
        }
    }

    /* access modifiers changed from: 0000 */
    public void n() {
    }

    /* access modifiers changed from: 0000 */
    public void o() {
        if (this.H != null) {
            this.B.getViewTreeObserver().removeOnPreDrawListener(this.H);
            this.H = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void p() {
        float rotation = this.B.getRotation();
        if (this.p != rotation) {
            this.p = rotation;
            x();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean q() {
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void r() {
        c(this.y);
    }

    /* access modifiers changed from: 0000 */
    public final void s() {
        Rect rect = this.D;
        a(rect);
        b(rect);
        this.C.a(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* access modifiers changed from: 0000 */
    public final void c(float f2) {
        this.y = f2;
        Matrix matrix = this.G;
        a(f2, matrix);
        this.B.setImageMatrix(matrix);
    }

    /* access modifiers changed from: 0000 */
    public final void b(float f2) {
        if (this.v != f2) {
            this.v = f2;
            a(this.u, this.v, this.w);
        }
    }

    /* access modifiers changed from: 0000 */
    public final Lz d() {
        return this.k;
    }

    /* access modifiers changed from: 0000 */
    public void d(AnimatorListener animatorListener) {
        ArrayList<AnimatorListener> arrayList = this.z;
        if (arrayList != null) {
            arrayList.remove(animatorListener);
        }
    }

    public void c(AnimatorListener animatorListener) {
        ArrayList<AnimatorListener> arrayList = this.A;
        if (arrayList != null) {
            arrayList.remove(animatorListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(Lz lz) {
        this.j = lz;
    }

    /* access modifiers changed from: 0000 */
    public void b(AnimatorListener animatorListener) {
        if (this.z == null) {
            this.z = new ArrayList<>();
        }
        this.z.add(animatorListener);
    }

    /* access modifiers changed from: 0000 */
    public void b(d dVar, boolean z2) {
        if (!i()) {
            Animator animator = this.i;
            if (animator != null) {
                animator.cancel();
            }
            if (w()) {
                if (this.B.getVisibility() != 0) {
                    this.B.setAlpha(0.0f);
                    this.B.setScaleY(0.0f);
                    this.B.setScaleX(0.0f);
                    c(0.0f);
                }
                Lz lz = this.j;
                if (lz == null) {
                    lz = v();
                }
                AnimatorSet a2 = a(lz, 1.0f, 1.0f, 1.0f);
                a2.addListener(new c(this, z2, dVar));
                ArrayList<AnimatorListener> arrayList = this.z;
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        a2.addListener((AnimatorListener) it.next());
                    }
                }
                a2.start();
            } else {
                this.B.a(0, z2);
                this.B.setAlpha(1.0f);
                this.B.setScaleY(1.0f);
                this.B.setScaleX(1.0f);
                c(1.0f);
                if (dVar != null) {
                    dVar.a();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList) {
        Drawable drawable = this.q;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, colorStateList);
        }
        com.google.android.material.internal.c cVar = this.s;
        if (cVar != null) {
            cVar.a(colorStateList);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Mode mode) {
        Drawable drawable = this.q;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, mode);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(float f2) {
        if (this.u != f2) {
            this.u = f2;
            a(this.u, this.v, this.w);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2) {
        if (this.x != i2) {
            this.x = i2;
            r();
        }
    }

    private void a(float f2, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.B.getDrawable();
        if (drawable != null && this.x != 0) {
            RectF rectF = this.E;
            RectF rectF2 = this.F;
            rectF.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
            int i2 = this.x;
            rectF2.set(0.0f, 0.0f, (float) i2, (float) i2);
            matrix.setRectToRect(rectF, rectF2, ScaleToFit.CENTER);
            int i3 = this.x;
            matrix.postScale(f2, f2, ((float) i3) / 2.0f, ((float) i3) / 2.0f);
        }
    }

    /* access modifiers changed from: 0000 */
    public final Drawable b() {
        return this.t;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Lz lz) {
        this.k = lz;
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2, float f3, float f4) {
        Zz zz = this.o;
        if (zz != null) {
            zz.a(f2, this.w + f2);
            s();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int[] iArr) {
        this.n.a(iArr);
    }

    public void a(AnimatorListener animatorListener) {
        if (this.A == null) {
            this.A = new ArrayList<>();
        }
        this.A.add(animatorListener);
    }

    /* access modifiers changed from: 0000 */
    public void a(d dVar, boolean z2) {
        if (!h()) {
            Animator animator = this.i;
            if (animator != null) {
                animator.cancel();
            }
            if (w()) {
                Lz lz = this.k;
                if (lz == null) {
                    lz = u();
                }
                AnimatorSet a2 = a(lz, 0.0f, 0.0f, 0.0f);
                a2.addListener(new b(this, z2, dVar));
                ArrayList<AnimatorListener> arrayList = this.A;
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        a2.addListener((AnimatorListener) it.next());
                    }
                }
                a2.start();
            } else {
                this.B.a(z2 ? 8 : 4, z2);
                if (dVar != null) {
                    dVar.b();
                }
            }
        }
    }

    private AnimatorSet a(Lz lz, float f2, float f3, float f4) {
        ArrayList arrayList = new ArrayList();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.B, View.ALPHA, new float[]{f2});
        lz.a("opacity").a((Animator) ofFloat);
        arrayList.add(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.B, View.SCALE_X, new float[]{f3});
        String str = "scale";
        lz.a(str).a((Animator) ofFloat2);
        arrayList.add(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.B, View.SCALE_Y, new float[]{f3});
        lz.a(str).a((Animator) ofFloat3);
        arrayList.add(ofFloat3);
        a(f4, this.G);
        ObjectAnimator ofObject = ObjectAnimator.ofObject(this.B, new Jz(), new Kz(), new Matrix[]{new Matrix(this.G)});
        lz.a("iconScale").a((Animator) ofObject);
        arrayList.add(ofObject);
        AnimatorSet animatorSet = new AnimatorSet();
        Fz.a(animatorSet, arrayList);
        return animatorSet;
    }

    /* access modifiers changed from: 0000 */
    public void a(Rect rect) {
        this.o.getPadding(rect);
    }

    /* access modifiers changed from: 0000 */
    public com.google.android.material.internal.c a(int i2, ColorStateList colorStateList) {
        Context context = this.B.getContext();
        com.google.android.material.internal.c k2 = k();
        k2.a(androidx.core.content.a.a(context, C1801vz.design_fab_stroke_top_outer_color), androidx.core.content.a.a(context, C1801vz.design_fab_stroke_top_inner_color), androidx.core.content.a.a(context, C1801vz.design_fab_stroke_end_inner_color), androidx.core.content.a.a(context, C1801vz.design_fab_stroke_end_outer_color));
        k2.a((float) i2);
        k2.a(colorStateList);
        return k2;
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable a() {
        GradientDrawable l2 = l();
        l2.setShape(1);
        l2.setColor(-1);
        return l2;
    }

    private ValueAnimator a(f fVar) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(a);
        valueAnimator.setDuration(100);
        valueAnimator.addListener(fVar);
        valueAnimator.addUpdateListener(fVar);
        valueAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        return valueAnimator;
    }
}
