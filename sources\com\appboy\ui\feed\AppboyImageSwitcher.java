package com.appboy.ui.feed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageSwitcher;
import com.appboy.ui.R$styleable;

public class AppboyImageSwitcher extends ImageSwitcher {
    private static final String TAG = Hg.a(AppboyImageSwitcher.class);
    private Drawable mReadIcon;
    private Drawable mUnReadIcon;

    public AppboyImageSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        try {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.com_appboy_ui_feed_AppboyImageSwitcher);
            for (int i = 0; i < obtainStyledAttributes.getIndexCount(); i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R$styleable.com_appboy_ui_feed_AppboyImageSwitcher_appboyFeedCustomReadIcon) {
                    Drawable drawable = obtainStyledAttributes.getDrawable(index);
                    if (drawable != null) {
                        this.mReadIcon = drawable;
                    }
                } else if (obtainStyledAttributes.getIndex(i) == R$styleable.com_appboy_ui_feed_AppboyImageSwitcher_appboyFeedCustomUnReadIcon) {
                    Drawable drawable2 = obtainStyledAttributes.getDrawable(index);
                    if (drawable2 != null) {
                        this.mUnReadIcon = drawable2;
                    }
                }
            }
            obtainStyledAttributes.recycle();
        } catch (Exception e) {
            Hg.d(TAG, "Error while checking for custom drawable.", e);
        }
    }

    public Drawable getReadIcon() {
        return this.mReadIcon;
    }

    public Drawable getUnReadIcon() {
        return this.mUnReadIcon;
    }

    public void setReadIcon(Drawable drawable) {
        this.mReadIcon = drawable;
    }

    public void setUnReadIcon(Drawable drawable) {
        this.mUnReadIcon = drawable;
    }
}
