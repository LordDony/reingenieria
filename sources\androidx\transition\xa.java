package androidx.transition;

import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: ViewUtilsApi19 */
class xa extends Aa {
    private static Method b;
    private static boolean c;
    private static Method d;
    private static boolean e;

    xa() {
    }

    public void a(View view) {
    }

    public void a(View view, float f) {
        b();
        Method method = b;
        if (method != null) {
            try {
                method.invoke(view, new Object[]{Float.valueOf(f)});
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        } else {
            view.setAlpha(f);
        }
    }

    public float b(View view) {
        a();
        Method method = d;
        if (method != null) {
            try {
                return ((Float) method.invoke(view, new Object[0])).floatValue();
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
        return super.b(view);
    }

    public void c(View view) {
    }

    private void a() {
        if (!e) {
            try {
                d = View.class.getDeclaredMethod("getTransitionAlpha", new Class[0]);
                d.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi19", "Failed to retrieve getTransitionAlpha method", e2);
            }
            e = true;
        }
    }

    private void b() {
        if (!c) {
            try {
                b = View.class.getDeclaredMethod("setTransitionAlpha", new Class[]{Float.TYPE});
                b.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi19", "Failed to retrieve setTransitionAlpha method", e2);
            }
            c = true;
        }
    }
}
