package androidx.transition;

import android.graphics.PointF;
import android.util.Property;
import android.view.View;

/* renamed from: androidx.transition.f reason: case insensitive filesystem */
/* compiled from: ChangeBounds */
class C0447f extends Property<View, PointF> {
    C0447f(Class cls, String str) {
        super(cls, str);
    }

    /* renamed from: a */
    public PointF get(View view) {
        return null;
    }

    /* renamed from: a */
    public void set(View view, PointF pointF) {
        wa.a(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
    }
}
