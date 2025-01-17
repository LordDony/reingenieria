package androidx.work.impl.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: IdGenerator */
public class e {
    private final Context a;
    private SharedPreferences b;
    private boolean c;

    public e(Context context) {
        this.a = context;
    }

    private void b() {
        if (!this.c) {
            this.b = this.a.getSharedPreferences("androidx.work.util.id", 0);
            this.c = true;
        }
    }

    public int a(int i, int i2) {
        synchronized (e.class) {
            b();
            int a2 = a("next_job_scheduler_id");
            if (a2 >= i) {
                if (a2 <= i2) {
                    i = a2;
                }
            }
            a("next_job_scheduler_id", i + 1);
        }
        return i;
    }

    public int a() {
        int a2;
        synchronized (e.class) {
            b();
            a2 = a("next_alarm_manager_id");
        }
        return a2;
    }

    private int a(String str) {
        int i = 0;
        int i2 = this.b.getInt(str, 0);
        if (i2 != Integer.MAX_VALUE) {
            i = i2 + 1;
        }
        a(str, i);
        return i2;
    }

    private void a(String str, int i) {
        this.b.edit().putInt(str, i).apply();
    }
}
