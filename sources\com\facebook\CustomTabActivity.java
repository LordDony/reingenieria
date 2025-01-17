package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class CustomTabActivity extends Activity {
    public static final String a;
    public static final String b;
    private BroadcastReceiver c;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(CustomTabActivity.class.getSimpleName());
        sb.append(".action_customTabRedirect");
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(CustomTabActivity.class.getSimpleName());
        sb2.append(".action_destroy");
        b = sb2.toString();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 0) {
            Intent intent2 = new Intent(a);
            intent2.putExtra(CustomTabMainActivity.c, getIntent().getDataString());
            C1600pd.a((Context) this).a(intent2);
            this.c = new C0567l(this);
            C1600pd.a((Context) this).a(this.c, new IntentFilter(b));
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent(this, CustomTabMainActivity.class);
        intent.setAction(a);
        intent.putExtra(CustomTabMainActivity.c, getIntent().getDataString());
        intent.addFlags(603979776);
        startActivityForResult(intent, 2);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        C1600pd.a((Context) this).a(this.c);
        super.onDestroy();
    }
}
