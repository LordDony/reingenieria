package androidx.appcompat.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.w.a;

/* compiled from: MenuItemImpl */
public final class p implements C1536nb {
    private View A;
    private C0278Xb B;
    private OnActionExpandListener C;
    private boolean D = false;
    private ContextMenuInfo E;
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private CharSequence e;
    private CharSequence f;
    private Intent g;
    private char h;
    private int i = 4096;
    private char j;
    private int k = 4096;
    private Drawable l;
    private int m = 0;
    l n;
    private D o;
    private Runnable p;
    private OnMenuItemClickListener q;
    private CharSequence r;
    private CharSequence s;
    private ColorStateList t = null;
    private Mode u = null;
    private boolean v = false;
    private boolean w = false;
    private boolean x = false;
    private int y = 16;
    private int z = 0;

    p(l lVar, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6) {
        this.n = lVar;
        this.a = i3;
        this.b = i2;
        this.c = i4;
        this.d = i5;
        this.e = charSequence;
        this.z = i6;
    }

    private static void a(StringBuilder sb, int i2, int i3, String str) {
        if ((i2 & i3) == i3) {
            sb.append(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z2) {
        int i2 = this.y;
        this.y = (z2 ? 2 : 0) | (i2 & -3);
        if (i2 != this.y) {
            this.n.b(false);
        }
    }

    public int c() {
        return this.d;
    }

    public boolean collapseActionView() {
        if ((this.z & 8) == 0) {
            return false;
        }
        if (this.A == null) {
            return true;
        }
        OnActionExpandListener onActionExpandListener = this.C;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse(this)) {
            return this.n.a(this);
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public char d() {
        return this.n.p() ? this.j : this.h;
    }

    /* access modifiers changed from: 0000 */
    public String e() {
        char d2 = d();
        if (d2 == 0) {
            return "";
        }
        Resources resources = this.n.e().getResources();
        StringBuilder sb = new StringBuilder();
        if (ViewConfiguration.get(this.n.e()).hasPermanentMenuKey()) {
            sb.append(resources.getString(C1345h.abc_prepend_shortcut_label));
        }
        int i2 = this.n.p() ? this.k : this.i;
        a(sb, i2, 65536, resources.getString(C1345h.abc_menu_meta_shortcut_label));
        a(sb, i2, 4096, resources.getString(C1345h.abc_menu_ctrl_shortcut_label));
        a(sb, i2, 2, resources.getString(C1345h.abc_menu_alt_shortcut_label));
        a(sb, i2, 1, resources.getString(C1345h.abc_menu_shift_shortcut_label));
        a(sb, i2, 4, resources.getString(C1345h.abc_menu_sym_shortcut_label));
        a(sb, i2, 8, resources.getString(C1345h.abc_menu_function_shortcut_label));
        if (d2 == 8) {
            sb.append(resources.getString(C1345h.abc_menu_delete_shortcut_label));
        } else if (d2 == 10) {
            sb.append(resources.getString(C1345h.abc_menu_enter_shortcut_label));
        } else if (d2 != ' ') {
            sb.append(d2);
        } else {
            sb.append(resources.getString(C1345h.abc_menu_space_shortcut_label));
        }
        return sb.toString();
    }

    public boolean expandActionView() {
        if (!f()) {
            return false;
        }
        OnActionExpandListener onActionExpandListener = this.C;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionExpand(this)) {
            return this.n.b(this);
        }
        return false;
    }

    public boolean f() {
        if ((this.z & 8) == 0) {
            return false;
        }
        if (this.A == null) {
            C0278Xb xb = this.B;
            if (xb != null) {
                this.A = xb.a((MenuItem) this);
            }
        }
        if (this.A != null) {
            return true;
        }
        return false;
    }

    public boolean g() {
        OnMenuItemClickListener onMenuItemClickListener = this.q;
        if (onMenuItemClickListener != null && onMenuItemClickListener.onMenuItemClick(this)) {
            return true;
        }
        l lVar = this.n;
        if (lVar.a(lVar, (MenuItem) this)) {
            return true;
        }
        Runnable runnable = this.p;
        if (runnable != null) {
            runnable.run();
            return true;
        }
        if (this.g != null) {
            try {
                this.n.e().startActivity(this.g);
                return true;
            } catch (ActivityNotFoundException e2) {
                Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e2);
            }
        }
        C0278Xb xb = this.B;
        if (xb == null || !xb.e()) {
            return false;
        }
        return true;
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    public View getActionView() {
        View view = this.A;
        if (view != null) {
            return view;
        }
        C0278Xb xb = this.B;
        if (xb == null) {
            return null;
        }
        this.A = xb.a((MenuItem) this);
        return this.A;
    }

    public int getAlphabeticModifiers() {
        return this.k;
    }

    public char getAlphabeticShortcut() {
        return this.j;
    }

    public CharSequence getContentDescription() {
        return this.r;
    }

    public int getGroupId() {
        return this.b;
    }

    public Drawable getIcon() {
        Drawable drawable = this.l;
        if (drawable != null) {
            return a(drawable);
        }
        if (this.m == 0) {
            return null;
        }
        Drawable b2 = C1437k.b(this.n.e(), this.m);
        this.m = 0;
        this.l = b2;
        return a(b2);
    }

    public ColorStateList getIconTintList() {
        return this.t;
    }

    public Mode getIconTintMode() {
        return this.u;
    }

    public Intent getIntent() {
        return this.g;
    }

    @CapturedViewProperty
    public int getItemId() {
        return this.a;
    }

    public ContextMenuInfo getMenuInfo() {
        return this.E;
    }

    public int getNumericModifiers() {
        return this.i;
    }

    public char getNumericShortcut() {
        return this.h;
    }

    public int getOrder() {
        return this.c;
    }

    public SubMenu getSubMenu() {
        return this.o;
    }

    @CapturedViewProperty
    public CharSequence getTitle() {
        return this.e;
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f;
        if (charSequence == null) {
            charSequence = this.e;
        }
        return (VERSION.SDK_INT >= 18 || charSequence == null || (charSequence instanceof String)) ? charSequence : charSequence.toString();
    }

    public CharSequence getTooltipText() {
        return this.s;
    }

    public boolean h() {
        return (this.y & 32) == 32;
    }

    public boolean hasSubMenu() {
        return this.o != null;
    }

    public boolean i() {
        return (this.y & 4) != 0;
    }

    public boolean isActionViewExpanded() {
        return this.D;
    }

    public boolean isCheckable() {
        return (this.y & 1) == 1;
    }

    public boolean isChecked() {
        return (this.y & 2) == 2;
    }

    public boolean isEnabled() {
        return (this.y & 16) != 0;
    }

    public boolean isVisible() {
        C0278Xb xb = this.B;
        boolean z2 = true;
        if (xb == null || !xb.f()) {
            if ((this.y & 8) != 0) {
                z2 = false;
            }
            return z2;
        }
        if ((this.y & 8) != 0 || !this.B.c()) {
            z2 = false;
        }
        return z2;
    }

    public boolean j() {
        return (this.z & 1) == 1;
    }

    public boolean k() {
        return (this.z & 2) == 2;
    }

    public boolean l() {
        return this.n.k();
    }

    /* access modifiers changed from: 0000 */
    public boolean m() {
        return this.n.q() && d() != 0;
    }

    public boolean n() {
        return (this.z & 4) == 4;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        if (this.j == c2) {
            return this;
        }
        this.j = Character.toLowerCase(c2);
        this.n.b(false);
        return this;
    }

    public MenuItem setCheckable(boolean z2) {
        int i2 = this.y;
        this.y = z2 | (i2 & true) ? 1 : 0;
        if (i2 != this.y) {
            this.n.b(false);
        }
        return this;
    }

    public MenuItem setChecked(boolean z2) {
        if ((this.y & 4) != 0) {
            this.n.a((MenuItem) this);
        } else {
            b(z2);
        }
        return this;
    }

    public MenuItem setEnabled(boolean z2) {
        if (z2) {
            this.y |= 16;
        } else {
            this.y &= -17;
        }
        this.n.b(false);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.m = 0;
        this.l = drawable;
        this.x = true;
        this.n.b(false);
        return this;
    }

    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.t = colorStateList;
        this.v = true;
        this.x = true;
        this.n.b(false);
        return this;
    }

    public MenuItem setIconTintMode(Mode mode) {
        this.u = mode;
        this.w = true;
        this.x = true;
        this.n.b(false);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.g = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c2) {
        if (this.h == c2) {
            return this;
        }
        this.h = c2;
        this.n.b(false);
        return this;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        this.C = onActionExpandListener;
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.q = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c2, char c3) {
        this.h = c2;
        this.j = Character.toLowerCase(c3);
        this.n.b(false);
        return this;
    }

    public void setShowAsAction(int i2) {
        int i3 = i2 & 3;
        if (i3 == 0 || i3 == 1 || i3 == 2) {
            this.z = i2;
            this.n.c(this);
            return;
        }
        throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.e = charSequence;
        this.n.b(false);
        D d2 = this.o;
        if (d2 != null) {
            d2.setHeaderTitle(charSequence);
        }
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f = charSequence;
        if (charSequence == null) {
            CharSequence charSequence2 = this.e;
        }
        this.n.b(false);
        return this;
    }

    public MenuItem setVisible(boolean z2) {
        if (e(z2)) {
            this.n.d(this);
        }
        return this;
    }

    public String toString() {
        CharSequence charSequence = this.e;
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    public void a(D d2) {
        this.o = d2;
        d2.setHeaderTitle(getTitle());
    }

    public void c(boolean z2) {
        this.y = (z2 ? 4 : 0) | (this.y & -5);
    }

    public void d(boolean z2) {
        if (z2) {
            this.y |= 32;
        } else {
            this.y &= -33;
        }
    }

    public C1536nb setContentDescription(CharSequence charSequence) {
        this.r = charSequence;
        this.n.b(false);
        return this;
    }

    public C1536nb setShowAsActionFlags(int i2) {
        setShowAsAction(i2);
        return this;
    }

    public C1536nb setTooltipText(CharSequence charSequence) {
        this.s = charSequence;
        this.n.b(false);
        return this;
    }

    public C1536nb setActionView(View view) {
        this.A = view;
        this.B = null;
        if (view != null && view.getId() == -1) {
            int i2 = this.a;
            if (i2 > 0) {
                view.setId(i2);
            }
        }
        this.n.c(this);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public CharSequence a(a aVar) {
        if (aVar == null || !aVar.a()) {
            return getTitle();
        }
        return getTitleCondensed();
    }

    public MenuItem setAlphabeticShortcut(char c2, int i2) {
        if (this.j == c2 && this.k == i2) {
            return this;
        }
        this.j = Character.toLowerCase(c2);
        this.k = KeyEvent.normalizeMetaState(i2);
        this.n.b(false);
        return this;
    }

    public MenuItem setNumericShortcut(char c2, int i2) {
        if (this.h == c2 && this.i == i2) {
            return this;
        }
        this.h = c2;
        this.i = KeyEvent.normalizeMetaState(i2);
        this.n.b(false);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        this.h = c2;
        this.i = KeyEvent.normalizeMetaState(i2);
        this.j = Character.toLowerCase(c3);
        this.k = KeyEvent.normalizeMetaState(i3);
        this.n.b(false);
        return this;
    }

    public void b() {
        this.n.c(this);
    }

    public MenuItem setIcon(int i2) {
        this.l = null;
        this.m = i2;
        this.x = true;
        this.n.b(false);
        return this;
    }

    public MenuItem setTitle(int i2) {
        setTitle((CharSequence) this.n.e().getString(i2));
        return this;
    }

    private Drawable a(Drawable drawable) {
        if (drawable != null && this.x && (this.v || this.w)) {
            drawable = androidx.core.graphics.drawable.a.i(drawable).mutate();
            if (this.v) {
                androidx.core.graphics.drawable.a.a(drawable, this.t);
            }
            if (this.w) {
                androidx.core.graphics.drawable.a.a(drawable, this.u);
            }
            this.x = false;
        }
        return drawable;
    }

    public C1536nb setActionView(int i2) {
        Context e2 = this.n.e();
        setActionView(LayoutInflater.from(e2).inflate(i2, new LinearLayout(e2), false));
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void a(ContextMenuInfo contextMenuInfo) {
        this.E = contextMenuInfo;
    }

    public C0278Xb a() {
        return this.B;
    }

    public C1536nb a(C0278Xb xb) {
        C0278Xb xb2 = this.B;
        if (xb2 != null) {
            xb2.h();
        }
        this.A = null;
        this.B = xb;
        this.n.b(true);
        C0278Xb xb3 = this.B;
        if (xb3 != null) {
            xb3.a((b) new o(this));
        }
        return this;
    }

    public void a(boolean z2) {
        this.D = z2;
        this.n.b(false);
    }

    /* access modifiers changed from: 0000 */
    public boolean e(boolean z2) {
        int i2 = this.y;
        this.y = (z2 ? 0 : 8) | (i2 & -9);
        if (i2 != this.y) {
            return true;
        }
        return false;
    }
}
