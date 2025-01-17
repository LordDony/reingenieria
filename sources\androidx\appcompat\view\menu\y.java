package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

/* compiled from: MenuWrapperICS */
class y extends C0328c<C1505mb> implements Menu {
    y(Context context, C1505mb mbVar) {
        super(context, mbVar);
    }

    public MenuItem add(CharSequence charSequence) {
        return a(((C1505mb) this.a).add(charSequence));
    }

    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr;
        MenuItem[] menuItemArr3 = menuItemArr2 != null ? new MenuItem[menuItemArr2.length] : null;
        int addIntentOptions = ((C1505mb) this.a).addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr3);
        if (menuItemArr3 != null) {
            int length = menuItemArr3.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr2[i5] = a(menuItemArr3[i5]);
            }
        }
        return addIntentOptions;
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return a(((C1505mb) this.a).addSubMenu(charSequence));
    }

    public void clear() {
        b();
        ((C1505mb) this.a).clear();
    }

    public void close() {
        ((C1505mb) this.a).close();
    }

    public MenuItem findItem(int i) {
        return a(((C1505mb) this.a).findItem(i));
    }

    public MenuItem getItem(int i) {
        return a(((C1505mb) this.a).getItem(i));
    }

    public boolean hasVisibleItems() {
        return ((C1505mb) this.a).hasVisibleItems();
    }

    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return ((C1505mb) this.a).isShortcutKey(i, keyEvent);
    }

    public boolean performIdentifierAction(int i, int i2) {
        return ((C1505mb) this.a).performIdentifierAction(i, i2);
    }

    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return ((C1505mb) this.a).performShortcut(i, keyEvent, i2);
    }

    public void removeGroup(int i) {
        a(i);
        ((C1505mb) this.a).removeGroup(i);
    }

    public void removeItem(int i) {
        b(i);
        ((C1505mb) this.a).removeItem(i);
    }

    public void setGroupCheckable(int i, boolean z, boolean z2) {
        ((C1505mb) this.a).setGroupCheckable(i, z, z2);
    }

    public void setGroupEnabled(int i, boolean z) {
        ((C1505mb) this.a).setGroupEnabled(i, z);
    }

    public void setGroupVisible(int i, boolean z) {
        ((C1505mb) this.a).setGroupVisible(i, z);
    }

    public void setQwertyMode(boolean z) {
        ((C1505mb) this.a).setQwertyMode(z);
    }

    public int size() {
        return ((C1505mb) this.a).size();
    }

    public MenuItem add(int i) {
        return a(((C1505mb) this.a).add(i));
    }

    public SubMenu addSubMenu(int i) {
        return a(((C1505mb) this.a).addSubMenu(i));
    }

    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(((C1505mb) this.a).add(i, i2, i3, charSequence));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return a(((C1505mb) this.a).addSubMenu(i, i2, i3, charSequence));
    }

    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(((C1505mb) this.a).add(i, i2, i3, i4));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return a(((C1505mb) this.a).addSubMenu(i, i2, i3, i4));
    }
}
