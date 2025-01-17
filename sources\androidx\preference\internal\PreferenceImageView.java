package androidx.preference.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import androidx.preference.J;
import com.google.android.gms.common.api.Api.BaseClientBuilder;

public class PreferenceImageView extends ImageView {
    private int a;
    private int b;

    public PreferenceImageView(Context context) {
        this(context, null);
    }

    public int getMaxHeight() {
        return this.b;
    }

    public int getMaxWidth() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int size = MeasureSpec.getSize(i);
            int maxWidth = getMaxWidth();
            if (maxWidth != Integer.MAX_VALUE && (maxWidth < size || mode == 0)) {
                i = MeasureSpec.makeMeasureSpec(maxWidth, Integer.MIN_VALUE);
            }
        }
        int mode2 = MeasureSpec.getMode(i2);
        if (mode2 == Integer.MIN_VALUE || mode2 == 0) {
            int size2 = MeasureSpec.getSize(i2);
            int maxHeight = getMaxHeight();
            if (maxHeight != Integer.MAX_VALUE && (maxHeight < size2 || mode2 == 0)) {
                i2 = MeasureSpec.makeMeasureSpec(maxHeight, Integer.MIN_VALUE);
            }
        }
        super.onMeasure(i, i2);
    }

    public void setMaxHeight(int i) {
        this.b = i;
        super.setMaxHeight(i);
    }

    public void setMaxWidth(int i) {
        this.a = i;
        super.setMaxWidth(i);
    }

    public PreferenceImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreferenceImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = BaseClientBuilder.API_PRIORITY_OTHER;
        this.b = BaseClientBuilder.API_PRIORITY_OTHER;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, J.PreferenceImageView, i, 0);
        setMaxWidth(obtainStyledAttributes.getDimensionPixelSize(J.PreferenceImageView_maxWidth, BaseClientBuilder.API_PRIORITY_OTHER));
        setMaxHeight(obtainStyledAttributes.getDimensionPixelSize(J.PreferenceImageView_maxHeight, BaseClientBuilder.API_PRIORITY_OTHER));
        obtainStyledAttributes.recycle();
    }
}
