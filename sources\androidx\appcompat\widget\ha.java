package androidx.appcompat.widget;

import android.util.Property;

/* compiled from: SwitchCompat */
class ha extends Property<SwitchCompat, Float> {
    ha(Class cls, String str) {
        super(cls, str);
    }

    /* renamed from: a */
    public Float get(SwitchCompat switchCompat) {
        return Float.valueOf(switchCompat.z);
    }

    /* renamed from: a */
    public void set(SwitchCompat switchCompat, Float f) {
        switchCompat.setThumbPosition(f.floatValue());
    }
}
