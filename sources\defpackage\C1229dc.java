package defpackage;

import android.view.MotionEvent;

/* renamed from: dc reason: default package and case insensitive filesystem */
/* compiled from: MotionEventCompat */
public final class C1229dc {
    @Deprecated
    public static int a(MotionEvent motionEvent) {
        return motionEvent.getActionIndex();
    }

    @Deprecated
    public static int b(MotionEvent motionEvent, int i) {
        return motionEvent.getPointerId(i);
    }

    @Deprecated
    public static float c(MotionEvent motionEvent, int i) {
        return motionEvent.getX(i);
    }

    public static boolean d(MotionEvent motionEvent, int i) {
        return (motionEvent.getSource() & i) == i;
    }

    @Deprecated
    public static int a(MotionEvent motionEvent, int i) {
        return motionEvent.findPointerIndex(i);
    }
}
