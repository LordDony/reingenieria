package androidx.recyclerview.widget;

import android.view.animation.Interpolator;

/* compiled from: RecyclerView */
class Y implements Interpolator {
    Y() {
    }

    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }
}
