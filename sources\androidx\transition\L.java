package androidx.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.util.Property;

/* compiled from: PropertyValuesHolderUtils */
class L {
    static PropertyValuesHolder a(Property<?, PointF> property, Path path) {
        if (VERSION.SDK_INT >= 21) {
            return PropertyValuesHolder.ofObject(property, null, path);
        }
        return PropertyValuesHolder.ofFloat(new K(property, path), new float[]{0.0f, 1.0f});
    }
}
