package androidx.appcompat.widget;

import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/* compiled from: AppCompatHintHelper */
class r {
    static InputConnection a(InputConnection inputConnection, EditorInfo editorInfo, View view) {
        if (inputConnection != null && editorInfo.hintText == null) {
            ViewParent parent = view.getParent();
            while (true) {
                if (!(parent instanceof View)) {
                    break;
                } else if (parent instanceof Ca) {
                    editorInfo.hintText = ((Ca) parent).a();
                    break;
                } else {
                    parent = parent.getParent();
                }
            }
        }
        return inputConnection;
    }
}
