package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

/* compiled from: ViewGroupOverlayApi18 */
class la implements ma {
    private final ViewGroupOverlay a;

    la(ViewGroup viewGroup) {
        this.a = viewGroup.getOverlay();
    }

    public void a(Drawable drawable) {
        this.a.add(drawable);
    }

    public void b(Drawable drawable) {
        this.a.remove(drawable);
    }

    public void a(View view) {
        this.a.add(view);
    }

    public void b(View view) {
        this.a.remove(view);
    }
}
