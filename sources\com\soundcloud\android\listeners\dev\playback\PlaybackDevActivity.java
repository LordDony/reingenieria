package com.soundcloud.android.listeners.dev.playback;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.A;
import androidx.fragment.app.Fragment;

@EVa(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/soundcloud/android/listeners/dev/playback/PlaybackDevActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onNavigateUp", "", "devdrawer_release"}, mv = {1, 1, 15})
/* compiled from: PlaybackDevActivity.kt */
public final class PlaybackDevActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        A a = getSupportFragmentManager().a();
        a.a(16908290, (Fragment) new d());
        a.a();
    }

    public boolean onNavigateUp() {
        finish();
        return true;
    }
}
