package androidx.appcompat.widget;

import android.view.View;
import android.view.View.OnLayoutChangeListener;

/* compiled from: SearchView */
class Y implements OnLayoutChangeListener {
    final /* synthetic */ SearchView a;

    Y(SearchView searchView) {
        this.a = searchView;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.a.b();
    }
}
