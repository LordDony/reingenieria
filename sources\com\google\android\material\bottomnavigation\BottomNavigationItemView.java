package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.view.menu.w.a;
import androidx.appcompat.widget.va;
import androidx.core.widget.l;
import com.facebook.stetho.websocket.CloseCodes;

public class BottomNavigationItemView extends FrameLayout implements a {
    private static final int[] a = {16842912};
    private final int b;
    private float c;
    private float d;
    private float e;
    private int f;
    private boolean g;
    private ImageView h;
    private final TextView i;
    private final TextView j;
    private int k;
    private p l;
    private ColorStateList m;

    public BottomNavigationItemView(Context context) {
        this(context, null);
    }

    public void a(p pVar, int i2) {
        this.l = pVar;
        setCheckable(pVar.isCheckable());
        setChecked(pVar.isChecked());
        setEnabled(pVar.isEnabled());
        setIcon(pVar.getIcon());
        setTitle(pVar.getTitle());
        setId(pVar.getItemId());
        if (!TextUtils.isEmpty(pVar.getContentDescription())) {
            setContentDescription(pVar.getContentDescription());
        }
        va.a(this, pVar.getTooltipText());
        setVisibility(pVar.isVisible() ? 0 : 8);
    }

    public boolean a() {
        return false;
    }

    public p getItemData() {
        return this.l;
    }

    public int getItemPosition() {
        return this.k;
    }

    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        p pVar = this.l;
        if (pVar != null && pVar.isCheckable() && this.l.isChecked()) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, a);
        }
        return onCreateDrawableState;
    }

    public void setCheckable(boolean z) {
        refreshDrawableState();
    }

    public void setChecked(boolean z) {
        TextView textView = this.j;
        textView.setPivotX((float) (textView.getWidth() / 2));
        TextView textView2 = this.j;
        textView2.setPivotY((float) textView2.getBaseline());
        TextView textView3 = this.i;
        textView3.setPivotX((float) (textView3.getWidth() / 2));
        TextView textView4 = this.i;
        textView4.setPivotY((float) textView4.getBaseline());
        int i2 = this.f;
        if (i2 != -1) {
            if (i2 == 0) {
                if (z) {
                    a(this.h, this.b, 49);
                    a(this.j, 1.0f, 1.0f, 0);
                } else {
                    a(this.h, this.b, 17);
                    a(this.j, 0.5f, 0.5f, 4);
                }
                this.i.setVisibility(4);
            } else if (i2 != 1) {
                if (i2 == 2) {
                    a(this.h, this.b, 17);
                    this.j.setVisibility(8);
                    this.i.setVisibility(8);
                }
            } else if (z) {
                a(this.h, (int) (((float) this.b) + this.c), 49);
                a(this.j, 1.0f, 1.0f, 0);
                TextView textView5 = this.i;
                float f2 = this.d;
                a(textView5, f2, f2, 4);
            } else {
                a(this.h, this.b, 49);
                TextView textView6 = this.j;
                float f3 = this.e;
                a(textView6, f3, f3, 4);
                a(this.i, 1.0f, 1.0f, 0);
            }
        } else if (this.g) {
            if (z) {
                a(this.h, this.b, 49);
                a(this.j, 1.0f, 1.0f, 0);
            } else {
                a(this.h, this.b, 17);
                a(this.j, 0.5f, 0.5f, 4);
            }
            this.i.setVisibility(4);
        } else if (z) {
            a(this.h, (int) (((float) this.b) + this.c), 49);
            a(this.j, 1.0f, 1.0f, 0);
            TextView textView7 = this.i;
            float f4 = this.d;
            a(textView7, f4, f4, 4);
        } else {
            a(this.h, this.b, 49);
            TextView textView8 = this.j;
            float f5 = this.e;
            a(textView8, f5, f5, 4);
            a(this.i, 1.0f, 1.0f, 0);
        }
        refreshDrawableState();
        setSelected(z);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.i.setEnabled(z);
        this.j.setEnabled(z);
        this.h.setEnabled(z);
        if (z) {
            C1778vc.a((View) this, C1568oc.a(getContext(), CloseCodes.PROTOCOL_ERROR));
        } else {
            C1778vc.a((View) this, (C1568oc) null);
        }
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable();
            }
            drawable = androidx.core.graphics.drawable.a.i(drawable).mutate();
            androidx.core.graphics.drawable.a.a(drawable, this.m);
        }
        this.h.setImageDrawable(drawable);
    }

    public void setIconSize(int i2) {
        LayoutParams layoutParams = (LayoutParams) this.h.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i2;
        this.h.setLayoutParams(layoutParams);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.m = colorStateList;
        p pVar = this.l;
        if (pVar != null) {
            setIcon(pVar.getIcon());
        }
    }

    public void setItemBackground(int i2) {
        setItemBackground(i2 == 0 ? null : androidx.core.content.a.c(getContext(), i2));
    }

    public void setItemPosition(int i2) {
        this.k = i2;
    }

    public void setLabelVisibilityMode(int i2) {
        if (this.f != i2) {
            this.f = i2;
            if (this.l != null) {
                setChecked(this.l.isChecked());
            }
        }
    }

    public void setShifting(boolean z) {
        if (this.g != z) {
            this.g = z;
            if (this.l != null) {
                setChecked(this.l.isChecked());
            }
        }
    }

    public void setTextAppearanceActive(int i2) {
        l.d(this.j, i2);
        a(this.i.getTextSize(), this.j.getTextSize());
    }

    public void setTextAppearanceInactive(int i2) {
        l.d(this.i, i2);
        a(this.i.getTextSize(), this.j.getTextSize());
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.i.setTextColor(colorStateList);
            this.j.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.i.setText(charSequence);
        this.j.setText(charSequence);
        p pVar = this.l;
        if (pVar == null || TextUtils.isEmpty(pVar.getContentDescription())) {
            setContentDescription(charSequence);
        }
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.k = -1;
        Resources resources = getResources();
        LayoutInflater.from(context).inflate(Az.design_bottom_navigation_item, this, true);
        setBackgroundResource(C1861xz.design_bottom_navigation_item_background);
        this.b = resources.getDimensionPixelSize(C1831wz.design_bottom_navigation_margin);
        this.h = (ImageView) findViewById(C1891yz.icon);
        this.i = (TextView) findViewById(C1891yz.smallLabel);
        this.j = (TextView) findViewById(C1891yz.largeLabel);
        C1778vc.f(this.i, 2);
        C1778vc.f(this.j, 2);
        setFocusable(true);
        a(this.i.getTextSize(), this.j.getTextSize());
    }

    public void setItemBackground(Drawable drawable) {
        C1778vc.a((View) this, drawable);
    }

    private void a(View view, int i2, int i3) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i2;
        layoutParams.gravity = i3;
        view.setLayoutParams(layoutParams);
    }

    private void a(View view, float f2, float f3, int i2) {
        view.setScaleX(f2);
        view.setScaleY(f3);
        view.setVisibility(i2);
    }

    private void a(float f2, float f3) {
        this.c = f2 - f3;
        this.d = (f3 * 1.0f) / f2;
        this.e = (f2 * 1.0f) / f3;
    }
}
