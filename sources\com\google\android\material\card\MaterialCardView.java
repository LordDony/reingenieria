package com.google.android.material.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import com.google.android.material.internal.r;

public class MaterialCardView extends CardView {
    private final a j;

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1771uz.materialCardViewStyle);
    }

    public int getStrokeColor() {
        return this.j.a();
    }

    public int getStrokeWidth() {
        return this.j.b();
    }

    public void setRadius(float f) {
        super.setRadius(f);
        this.j.c();
    }

    public void setStrokeColor(int i) {
        this.j.a(i);
    }

    public void setStrokeWidth(int i) {
        this.j.b(i);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray a = r.a(context, attributeSet, Dz.MaterialCardView, i, Cz.Widget_MaterialComponents_CardView, new int[0]);
        this.j = new a(this);
        this.j.a(a);
        a.recycle();
    }
}
