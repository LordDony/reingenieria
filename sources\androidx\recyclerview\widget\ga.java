package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView.i;
import androidx.recyclerview.widget.RecyclerView.s;

/* compiled from: ScrollbarHelper */
class ga {
    static int a(s sVar, T t, View view, View view2, i iVar, boolean z, boolean z2) {
        int i;
        if (iVar.e() == 0 || sVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        int min = Math.min(iVar.l(view), iVar.l(view2));
        int max = Math.max(iVar.l(view), iVar.l(view2));
        if (z2) {
            i = Math.max(0, (sVar.a() - max) - 1);
        } else {
            i = Math.max(0, min);
        }
        if (!z) {
            return i;
        }
        return Math.round((((float) i) * (((float) Math.abs(t.a(view2) - t.d(view))) / ((float) (Math.abs(iVar.l(view) - iVar.l(view2)) + 1)))) + ((float) (t.f() - t.d(view))));
    }

    static int b(s sVar, T t, View view, View view2, i iVar, boolean z) {
        if (iVar.e() == 0 || sVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return sVar.a();
        }
        return (int) ((((float) (t.a(view2) - t.d(view))) / ((float) (Math.abs(iVar.l(view) - iVar.l(view2)) + 1))) * ((float) sVar.a()));
    }

    static int a(s sVar, T t, View view, View view2, i iVar, boolean z) {
        if (iVar.e() == 0 || sVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(iVar.l(view) - iVar.l(view2)) + 1;
        }
        return Math.min(t.g(), t.a(view2) - t.d(view));
    }
}
