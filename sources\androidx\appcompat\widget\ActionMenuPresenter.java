package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.C0327b;
import androidx.appcompat.view.menu.D;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.view.menu.u;
import androidx.appcompat.view.menu.w;
import androidx.appcompat.view.menu.z;
import java.util.ArrayList;

class ActionMenuPresenter extends C0327b implements defpackage.C0278Xb.a {
    a A;
    c B;
    private b C;
    final f D = new f();
    int E;
    d k;
    private Drawable l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int p;
    private int q;
    private int r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private int w;
    private final SparseBooleanArray x = new SparseBooleanArray();
    private View y;
    e z;

    private static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C0341h();
        public int a;

        SavedState() {
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
        }
    }

    private class a extends u {
        public a(Context context, D d, View view) {
            super(context, d, view, false, C0294a.actionOverflowMenuStyle);
            if (!((p) d.getItem()).h()) {
                View view2 = ActionMenuPresenter.this.k;
                if (view2 == null) {
                    view2 = (View) ActionMenuPresenter.this.i;
                }
                a(view2);
            }
            a((androidx.appcompat.view.menu.v.a) ActionMenuPresenter.this.D);
        }

        /* access modifiers changed from: protected */
        public void d() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            actionMenuPresenter.A = null;
            actionMenuPresenter.E = 0;
            super.d();
        }
    }

    private class b extends androidx.appcompat.view.menu.ActionMenuItemView.b {
        b() {
        }

        public z a() {
            a aVar = ActionMenuPresenter.this.A;
            if (aVar != null) {
                return aVar.b();
            }
            return null;
        }
    }

    private class c implements Runnable {
        private e a;

        public c(e eVar) {
            this.a = eVar;
        }

        public void run() {
            if (ActionMenuPresenter.this.c != null) {
                ActionMenuPresenter.this.c.a();
            }
            View view = (View) ActionMenuPresenter.this.i;
            if (!(view == null || view.getWindowToken() == null || !this.a.f())) {
                ActionMenuPresenter.this.z = this.a;
            }
            ActionMenuPresenter.this.B = null;
        }
    }

    private class d extends AppCompatImageView implements androidx.appcompat.widget.ActionMenuView.a {
        private final float[] c = new float[2];

        public d(Context context) {
            super(context, null, C0294a.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            va.a(this, getContentDescription());
            setOnTouchListener(new C0340g(this, this, ActionMenuPresenter.this));
        }

        public boolean b() {
            return false;
        }

        public boolean c() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.j();
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int max = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                androidx.core.graphics.drawable.a.a(background, paddingLeft - max, paddingTop - max, paddingLeft + max, paddingTop + max);
            }
            return frame;
        }
    }

    private class e extends u {
        public e(Context context, l lVar, View view, boolean z) {
            super(context, lVar, view, z, C0294a.actionOverflowMenuStyle);
            a(8388613);
            a((androidx.appcompat.view.menu.v.a) ActionMenuPresenter.this.D);
        }

        /* access modifiers changed from: protected */
        public void d() {
            if (ActionMenuPresenter.this.c != null) {
                ActionMenuPresenter.this.c.close();
            }
            ActionMenuPresenter.this.z = null;
            super.d();
        }
    }

    private class f implements androidx.appcompat.view.menu.v.a {
        f() {
        }

        public boolean a(l lVar) {
            boolean z = false;
            if (lVar == null) {
                return false;
            }
            ActionMenuPresenter.this.E = ((D) lVar).getItem().getItemId();
            androidx.appcompat.view.menu.v.a c = ActionMenuPresenter.this.c();
            if (c != null) {
                z = c.a(lVar);
            }
            return z;
        }

        public void a(l lVar, boolean z) {
            if (lVar instanceof D) {
                lVar.m().a(false);
            }
            androidx.appcompat.view.menu.v.a c = ActionMenuPresenter.this.c();
            if (c != null) {
                c.a(lVar, z);
            }
        }
    }

    public ActionMenuPresenter(Context context) {
        super(context, C1314g.abc_action_menu_layout, C1314g.abc_action_menu_item_layout);
    }

    public boolean g() {
        a aVar = this.A;
        if (aVar == null) {
            return false;
        }
        aVar.a();
        return true;
    }

    public boolean h() {
        return this.B != null || i();
    }

    public boolean i() {
        e eVar = this.z;
        return eVar != null && eVar.c();
    }

    public boolean j() {
        if (this.n && !i()) {
            l lVar = this.c;
            if (lVar != null && this.i != null && this.B == null && !lVar.j().isEmpty()) {
                e eVar = new e(this.b, this.c, this.k, true);
                this.B = new c(eVar);
                ((View) this.i).post(this.B);
                super.a((D) null);
                return true;
            }
        }
        return false;
    }

    public void a(Context context, l lVar) {
        super.a(context, lVar);
        Resources resources = context.getResources();
        C1682s a2 = C1682s.a(context);
        if (!this.o) {
            this.n = a2.g();
        }
        if (!this.u) {
            this.p = a2.b();
        }
        if (!this.s) {
            this.r = a2.c();
        }
        int i = this.p;
        if (this.n) {
            if (this.k == null) {
                this.k = new d(this.a);
                if (this.m) {
                    this.k.setImageDrawable(this.l);
                    this.l = null;
                    this.m = false;
                }
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
                this.k.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.k.getMeasuredWidth();
        } else {
            this.k = null;
        }
        this.q = i;
        this.w = (int) (resources.getDisplayMetrics().density * 56.0f);
        this.y = null;
    }

    public w b(ViewGroup viewGroup) {
        w wVar = this.i;
        w b2 = super.b(viewGroup);
        if (wVar != b2) {
            ((ActionMenuView) b2).setPresenter(this);
        }
        return b2;
    }

    public void c(boolean z2) {
        this.v = z2;
    }

    public void d(boolean z2) {
        this.n = z2;
        this.o = true;
    }

    public Drawable e() {
        d dVar = this.k;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (this.m) {
            return this.l;
        }
        return null;
    }

    public boolean f() {
        c cVar = this.B;
        if (cVar != null) {
            w wVar = this.i;
            if (wVar != null) {
                ((View) wVar).removeCallbacks(cVar);
                this.B = null;
                return true;
            }
        }
        e eVar = this.z;
        if (eVar == null) {
            return false;
        }
        eVar.a();
        return true;
    }

    public boolean d() {
        return f() | g();
    }

    public boolean b() {
        int i;
        ArrayList arrayList;
        int i2;
        int i3;
        int i4;
        boolean z2;
        ActionMenuPresenter actionMenuPresenter = this;
        l lVar = actionMenuPresenter.c;
        int i5 = 0;
        if (lVar != null) {
            arrayList = lVar.n();
            i = arrayList.size();
        } else {
            arrayList = null;
            i = 0;
        }
        int i6 = actionMenuPresenter.r;
        int i7 = actionMenuPresenter.q;
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) actionMenuPresenter.i;
        int i8 = i6;
        boolean z3 = false;
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < i; i11++) {
            p pVar = (p) arrayList.get(i11);
            if (pVar.k()) {
                i9++;
            } else if (pVar.j()) {
                i10++;
            } else {
                z3 = true;
            }
            if (actionMenuPresenter.v && pVar.isActionViewExpanded()) {
                i8 = 0;
            }
        }
        if (actionMenuPresenter.n && (z3 || i10 + i9 > i8)) {
            i8--;
        }
        int i12 = i8 - i9;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.x;
        sparseBooleanArray.clear();
        if (actionMenuPresenter.t) {
            int i13 = actionMenuPresenter.w;
            i2 = i7 / i13;
            i3 = i13 + ((i7 % i13) / i2);
        } else {
            i3 = 0;
            i2 = 0;
        }
        int i14 = i7;
        int i15 = 0;
        int i16 = 0;
        while (i15 < i) {
            p pVar2 = (p) arrayList.get(i15);
            if (pVar2.k()) {
                View a2 = actionMenuPresenter.a(pVar2, actionMenuPresenter.y, viewGroup);
                if (actionMenuPresenter.y == null) {
                    actionMenuPresenter.y = a2;
                }
                if (actionMenuPresenter.t) {
                    i2 -= ActionMenuView.a(a2, i3, i2, makeMeasureSpec, i5);
                } else {
                    a2.measure(makeMeasureSpec, makeMeasureSpec);
                }
                int measuredWidth = a2.getMeasuredWidth();
                i14 -= measuredWidth;
                if (i16 != 0) {
                    measuredWidth = i16;
                }
                int groupId = pVar2.getGroupId();
                if (groupId != 0) {
                    z2 = true;
                    sparseBooleanArray.put(groupId, true);
                } else {
                    z2 = true;
                }
                pVar2.d(z2);
                i4 = i;
                i16 = measuredWidth;
            } else if (pVar2.j()) {
                int groupId2 = pVar2.getGroupId();
                boolean z4 = sparseBooleanArray.get(groupId2);
                boolean z5 = (i12 > 0 || z4) && i14 > 0 && (!actionMenuPresenter.t || i2 > 0);
                if (z5) {
                    boolean z6 = z5;
                    View a3 = actionMenuPresenter.a(pVar2, actionMenuPresenter.y, viewGroup);
                    i4 = i;
                    if (actionMenuPresenter.y == null) {
                        actionMenuPresenter.y = a3;
                    }
                    if (actionMenuPresenter.t) {
                        int a4 = ActionMenuView.a(a3, i3, i2, makeMeasureSpec, 0);
                        i2 -= a4;
                        if (a4 == 0) {
                            z6 = false;
                        }
                    } else {
                        a3.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                    int measuredWidth2 = a3.getMeasuredWidth();
                    i14 -= measuredWidth2;
                    if (i16 == 0) {
                        i16 = measuredWidth2;
                    }
                    z5 = z6 & (!actionMenuPresenter.t ? i14 + i16 > 0 : i14 >= 0);
                } else {
                    boolean z7 = z5;
                    i4 = i;
                }
                if (z5 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z4) {
                    sparseBooleanArray.put(groupId2, false);
                    int i17 = 0;
                    while (i17 < i15) {
                        p pVar3 = (p) arrayList.get(i17);
                        if (pVar3.getGroupId() == groupId2) {
                            if (pVar3.h()) {
                                i12++;
                            }
                            pVar3.d(false);
                        }
                        i17++;
                    }
                }
                if (z5) {
                    i12--;
                }
                pVar2.d(z5);
            } else {
                i4 = i;
                pVar2.d(false);
                i15++;
                i5 = 0;
                actionMenuPresenter = this;
                i = i4;
            }
            i15++;
            i5 = 0;
            actionMenuPresenter = this;
            i = i4;
        }
        return true;
    }

    public void a(Configuration configuration) {
        if (!this.s) {
            this.r = C1682s.a(this.b).c();
        }
        l lVar = this.c;
        if (lVar != null) {
            lVar.b(true);
        }
    }

    public void a(Drawable drawable) {
        d dVar = this.k;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
            return;
        }
        this.m = true;
        this.l = drawable;
    }

    public View a(p pVar, View view, ViewGroup viewGroup) {
        View actionView = pVar.getActionView();
        if (actionView == null || pVar.f()) {
            actionView = super.a(pVar, view, viewGroup);
        }
        actionView.setVisibility(pVar.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    public void a(p pVar, androidx.appcompat.view.menu.w.a aVar) {
        aVar.a(pVar, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) aVar;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.i);
        if (this.C == null) {
            this.C = new b();
        }
        actionMenuItemView.setPopupCallback(this.C);
    }

    public boolean a(int i, p pVar) {
        return pVar.h();
    }

    public void a(boolean z2) {
        super.a(z2);
        ((View) this.i).requestLayout();
        l lVar = this.c;
        boolean z3 = false;
        if (lVar != null) {
            ArrayList c2 = lVar.c();
            int size = c2.size();
            for (int i = 0; i < size; i++) {
                C0278Xb a2 = ((p) c2.get(i)).a();
                if (a2 != null) {
                    a2.a((defpackage.C0278Xb.a) this);
                }
            }
        }
        l lVar2 = this.c;
        ArrayList j = lVar2 != null ? lVar2.j() : null;
        if (this.n && j != null) {
            int size2 = j.size();
            if (size2 == 1) {
                z3 = !((p) j.get(0)).isActionViewExpanded();
            } else if (size2 > 0) {
                z3 = true;
            }
        }
        if (z3) {
            if (this.k == null) {
                this.k = new d(this.a);
            }
            ViewGroup viewGroup = (ViewGroup) this.k.getParent();
            if (viewGroup != this.i) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.k);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.i;
                actionMenuView.addView(this.k, actionMenuView.c());
            }
        } else {
            d dVar = this.k;
            if (dVar != null) {
                ViewParent parent = dVar.getParent();
                w wVar = this.i;
                if (parent == wVar) {
                    ((ViewGroup) wVar).removeView(this.k);
                }
            }
        }
        ((ActionMenuView) this.i).setOverflowReserved(this.n);
    }

    public void b(boolean z2) {
        if (z2) {
            super.a((D) null);
            return;
        }
        l lVar = this.c;
        if (lVar != null) {
            lVar.a(false);
        }
    }

    public boolean a(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.k) {
            return false;
        }
        return super.a(viewGroup, i);
    }

    public boolean a(D d2) {
        boolean z2 = false;
        if (!d2.hasVisibleItems()) {
            return false;
        }
        D d3 = d2;
        while (d3.t() != this.c) {
            d3 = (D) d3.t();
        }
        View a2 = a(d3.getItem());
        if (a2 == null) {
            return false;
        }
        this.E = d2.getItem().getItemId();
        int size = d2.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            MenuItem item = d2.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z2 = true;
                break;
            }
            i++;
        }
        this.A = new a(this.b, d2, a2);
        this.A.a(z2);
        this.A.e();
        super.a(d2);
        return true;
    }

    private View a(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.i;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof androidx.appcompat.view.menu.w.a) && ((androidx.appcompat.view.menu.w.a) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public void a(l lVar, boolean z2) {
        d();
        super.a(lVar, z2);
    }

    public Parcelable a() {
        SavedState savedState = new SavedState();
        savedState.a = this.E;
        return savedState;
    }

    public void a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            int i = ((SavedState) parcelable).a;
            if (i > 0) {
                MenuItem findItem = this.c.findItem(i);
                if (findItem != null) {
                    a((D) findItem.getSubMenu());
                }
            }
        }
    }

    public void a(ActionMenuView actionMenuView) {
        this.i = actionMenuView;
        actionMenuView.a(this.c);
    }
}
