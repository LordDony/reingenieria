package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.C0941da;
import com.google.android.gms.internal.measurement.C1042tb;
import com.google.android.gms.internal.measurement.D;
import com.google.android.gms.internal.measurement.G;
import com.google.android.gms.internal.measurement.Gb;
import com.google.android.gms.internal.measurement.S;
import com.google.android.gms.internal.measurement.U;
import com.google.android.gms.internal.measurement.W;
import com.google.android.gms.internal.measurement.W.a;
import com.google.android.gms.internal.measurement.Y;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class he extends Ld {
    /* access modifiers changed from: private */
    public static final String[] d = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;", "current_session_count", "ALTER TABLE events ADD COLUMN current_session_count INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] e = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    /* access modifiers changed from: private */
    public static final String[] f = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;", "dynamite_version", "ALTER TABLE apps ADD COLUMN dynamite_version INTEGER;", "safelisted_events", "ALTER TABLE apps ADD COLUMN safelisted_events TEXT;"};
    /* access modifiers changed from: private */
    public static final String[] g = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] h = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] i;
    /* access modifiers changed from: private */
    public static final String[] j;
    /* access modifiers changed from: private */
    public static final String[] k = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final ie l = new ie(this, getContext(), "google_app_measurement.db");
    /* access modifiers changed from: private */
    public final Hd m = new Hd(c());

    static {
        String str = "session_scoped";
        i = new String[]{str, "ALTER TABLE event_filters ADD COLUMN session_scoped BOOLEAN;"};
        j = new String[]{str, "ALTER TABLE property_filters ADD COLUMN session_scoped BOOLEAN;"};
    }

    he(Kd kd) {
        super(kd);
    }

    private final boolean O() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    private final long a(String str, String[] strArr, long j2) {
        Cursor cursor = null;
        try {
            Cursor rawQuery = w().rawQuery(str, strArr);
            if (rawQuery.moveToFirst()) {
                long j3 = rawQuery.getLong(0);
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return j3;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return j2;
        } catch (SQLiteException e2) {
            e().s().a("Database error", str, e2);
            throw e2;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long b(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = w().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                long j2 = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j2;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e2) {
            e().s().a("Database error", str, e2);
            throw e2;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void A() {
        i();
        s();
        if (O()) {
            long a = b().i.a();
            long elapsedRealtime = c().elapsedRealtime();
            if (Math.abs(elapsedRealtime - a) > ((Long) C1127j.J.a(null)).longValue()) {
                b().i.a(elapsedRealtime);
                i();
                s();
                if (O()) {
                    int delete = w().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(c().currentTimeMillis()), String.valueOf(ce.s())});
                    if (delete > 0) {
                        e().A().a("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }

    public final long B() {
        return a("select max(bundle_end_timestamp) from queue", (String[]) null, 0);
    }

    public final long C() {
        return a("select max(timestamp) from raw_events", (String[]) null, 0);
    }

    public final boolean D() {
        return b("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean E() {
        return b("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    public final long F() {
        Cursor cursor = null;
        try {
            cursor = w().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            if (!cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return -1;
            }
            long j2 = cursor.getLong(0);
            if (cursor != null) {
                cursor.close();
            }
            return j2;
        } catch (SQLiteException e2) {
            e().s().a("Error querying raw events", e2);
            if (cursor != null) {
                cursor.close();
            }
            return -1;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void c(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        i();
        s();
        try {
            e().A().a("Deleted user attribute rows", Integer.valueOf(w().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e2) {
            e().s().a("Error deleting user attribute. appId", C1124ib.a(str), k().c(str2), e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a9  */
    public final Sd d(String str, String str2) {
        Cursor cursor;
        String str3 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        i();
        s();
        try {
            cursor = w().query("user_attributes", new String[]{"set_timestamp", "value", "origin"}, "app_id=? and name=?", new String[]{str, str3}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                long j2 = cursor.getLong(0);
                try {
                    String str4 = str;
                    Sd sd = new Sd(str4, cursor.getString(2), str2, j2, a(cursor, 1));
                    if (cursor.moveToNext()) {
                        e().s().a("Got multiple records for user property, expected one. appId", C1124ib.a(str));
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return sd;
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        e().s().a("Error querying user property. appId", C1124ib.a(str), k().c(str3), e);
                        if (cursor != null) {
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        if (cursor != null) {
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                e().s().a("Error querying user property. appId", C1124ib.a(str), k().c(str3), e);
                if (cursor != null) {
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (cursor != null) {
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Error querying user property. appId", C1124ib.a(str), k().c(str3), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x011e  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0125  */
    public final zzq e(String str, String str2) {
        Cursor cursor;
        String str3 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        i();
        s();
        try {
            cursor = w().query("conditional_properties", new String[]{"origin", "value", "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str3}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                String string = cursor.getString(0);
                try {
                    Object a = a(cursor, 1);
                    boolean z = cursor.getInt(2) != 0;
                    String string2 = cursor.getString(3);
                    long j2 = cursor.getLong(4);
                    zzai zzai = (zzai) m().a(cursor.getBlob(5), zzai.CREATOR);
                    long j3 = cursor.getLong(6);
                    zzai zzai2 = (zzai) m().a(cursor.getBlob(7), zzai.CREATOR);
                    long j4 = cursor.getLong(8);
                    long j5 = cursor.getLong(9);
                    zzai zzai3 = (zzai) m().a(cursor.getBlob(10), zzai.CREATOR);
                    zzjn zzjn = new zzjn(str2, j4, a, string);
                    zzq zzq = new zzq(str, string, zzjn, j3, z, string2, zzai, j2, zzai2, j5, zzai3);
                    if (cursor.moveToNext()) {
                        e().s().a("Got multiple records for conditional property, expected one", C1124ib.a(str), k().c(str3));
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return zzq;
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        e().s().a("Error querying conditional property", C1124ib.a(str), k().c(str3), e);
                        if (cursor != null) {
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                e().s().a("Error querying conditional property", C1124ib.a(str), k().c(str3), e);
                if (cursor != null) {
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (cursor != null) {
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Error querying conditional property", C1124ib.a(str), k().c(str3), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
    }

    public final int f(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        i();
        s();
        try {
            return w().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e2) {
            e().s().a("Error deleting conditional property", C1124ib.a(str), k().c(str2), e2);
            return 0;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ad  */
    public final Map<Integer, List<D>> g(String str, String str2) {
        Cursor cursor;
        s();
        i();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        T t = new T();
        try {
            cursor = w().query("event_filters", new String[]{"audience_id", "data"}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    Map<Integer, List<D>> emptyMap = Collections.emptyMap();
                    if (cursor != null) {
                        cursor.close();
                    }
                    return emptyMap;
                }
                do {
                    try {
                        D a = D.a(cursor.getBlob(1), C1042tb.c());
                        int i2 = cursor.getInt(0);
                        List list = (List) t.get(Integer.valueOf(i2));
                        if (list == null) {
                            list = new ArrayList();
                            t.put(Integer.valueOf(i2), list);
                        }
                        list.add(a);
                    } catch (IOException e2) {
                        e().s().a("Failed to merge filter. appId", C1124ib.a(str), e2);
                    }
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                return t;
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    e().s().a("Database error querying filters. appId", C1124ib.a(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Database error querying filters. appId", C1124ib.a(str), e);
            if (cursor != null) {
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ad  */
    public final Map<Integer, List<G>> h(String str, String str2) {
        Cursor cursor;
        s();
        i();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        T t = new T();
        try {
            cursor = w().query("property_filters", new String[]{"audience_id", "data"}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    Map<Integer, List<G>> emptyMap = Collections.emptyMap();
                    if (cursor != null) {
                        cursor.close();
                    }
                    return emptyMap;
                }
                do {
                    try {
                        G a = G.a(cursor.getBlob(1), C1042tb.c());
                        int i2 = cursor.getInt(0);
                        List list = (List) t.get(Integer.valueOf(i2));
                        if (list == null) {
                            list = new ArrayList();
                            t.put(Integer.valueOf(i2), list);
                        }
                        list.add(a);
                    } catch (IOException e2) {
                        e().s().a("Failed to merge filter", C1124ib.a(str), e2);
                    }
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                return t;
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    e().s().a("Database error querying filters. appId", C1124ib.a(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Database error querying filters. appId", C1124ib.a(str), e);
            if (cursor != null) {
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public final long i(String str, String str2) {
        long j2;
        String str3;
        String str4;
        String str5 = str;
        String str6 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        i();
        s();
        SQLiteDatabase w = w();
        w.beginTransaction();
        try {
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 32);
            sb.append("select ");
            sb.append(str6);
            sb.append(" from app2 where app_id=?");
            try {
                j2 = a(sb.toString(), new String[]{str5}, -1);
                str3 = "app2";
                str4 = "app_id";
                if (j2 == -1) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(str4, str5);
                    contentValues.put("first_open_count", Integer.valueOf(0));
                    contentValues.put("previous_install_count", Integer.valueOf(0));
                    if (w.insertWithOnConflict(str3, null, contentValues, 5) == -1) {
                        e().s().a("Failed to insert column (got -1). appId", C1124ib.a(str), str6);
                        w.endTransaction();
                        return -1;
                    }
                    j2 = 0;
                }
            } catch (SQLiteException e2) {
                e = e2;
                j2 = 0;
                try {
                    e().s().a("Error inserting column. appId", C1124ib.a(str), str6, e);
                    w.endTransaction();
                    return j2;
                } catch (Throwable th) {
                    th = th;
                    w.endTransaction();
                    throw th;
                }
            }
            try {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(str4, str5);
                contentValues2.put(str6, Long.valueOf(1 + j2));
                if (((long) w.update(str3, contentValues2, "app_id = ?", new String[]{str5})) == 0) {
                    e().s().a("Failed to update column (got 0). appId", C1124ib.a(str), str6);
                    w.endTransaction();
                    return -1;
                }
                w.setTransactionSuccessful();
                w.endTransaction();
                return j2;
            } catch (SQLiteException e3) {
                e = e3;
                e().s().a("Error inserting column. appId", C1124ib.a(str), str6, e);
                w.endTransaction();
                return j2;
            }
        } catch (SQLiteException e4) {
            e = e4;
            j2 = 0;
            e().s().a("Error inserting column. appId", C1124ib.a(str), str6, e);
            w.endTransaction();
            return j2;
        } catch (Throwable th2) {
            th = th2;
            w.endTransaction();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean t() {
        return false;
    }

    public final void u() {
        s();
        w().beginTransaction();
    }

    public final void v() {
        s();
        w().endTransaction();
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final SQLiteDatabase w() {
        i();
        try {
            return this.l.getWritableDatabase();
        } catch (SQLiteException e2) {
            e().v().a("Error opening database", e2);
            throw e2;
        }
    }

    public final void x() {
        s();
        w().setTransactionSuccessful();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0041  */
    public final String y() {
        Cursor cursor;
        try {
            cursor = w().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
            try {
                if (cursor.moveToFirst()) {
                    String string = cursor.getString(0);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return string;
                }
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    e().s().a("Database error getting next bundle app id", e);
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            e().s().a("Database error getting next bundle app id", e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final boolean z() {
        return b("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    public final void a(C1107f fVar) {
        Preconditions.checkNotNull(fVar);
        i();
        s();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", fVar.a);
        contentValues.put("name", fVar.b);
        contentValues.put("lifetime_count", Long.valueOf(fVar.c));
        contentValues.put("current_bundle_count", Long.valueOf(fVar.d));
        contentValues.put("last_fire_timestamp", Long.valueOf(fVar.f));
        contentValues.put("last_bundled_timestamp", Long.valueOf(fVar.g));
        contentValues.put("last_bundled_day", fVar.h);
        contentValues.put("last_sampled_complex_event_id", fVar.i);
        contentValues.put("last_sampling_rate", fVar.j);
        if (f().e(fVar.a, C1127j.Fa)) {
            contentValues.put("current_session_count", Long.valueOf(fVar.e));
        }
        Boolean bool = fVar.k;
        contentValues.put("last_exempt_from_sampling", (bool == null || !bool.booleanValue()) ? null : Long.valueOf(1));
        try {
            if (w().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                e().s().a("Failed to insert/update event aggregates (got -1). appId", C1124ib.a(fVar.a));
            }
        } catch (SQLiteException e2) {
            e().s().a("Error storing event aggregates. appId", C1124ib.a(fVar.a), e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x015b  */
    public final C1107f b(String str, String str2) {
        Cursor cursor;
        Boolean bool;
        String str3 = str;
        String str4 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        i();
        s();
        boolean e2 = f().e(str3, C1127j.Fa);
        ArrayList arrayList = new ArrayList(Arrays.asList(new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_bundled_day", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}));
        if (e2) {
            arrayList.add("current_session_count");
        }
        try {
            boolean z = false;
            Cursor query = w().query("events", (String[]) arrayList.toArray(new String[0]), "app_id=? and name=?", new String[]{str3, str4}, null, null, null);
            try {
                if (!query.moveToFirst()) {
                    if (query != null) {
                        query.close();
                    }
                    return null;
                }
                long j2 = query.getLong(0);
                long j3 = query.getLong(1);
                long j4 = query.getLong(2);
                long j5 = 0;
                long j6 = query.isNull(3) ? 0 : query.getLong(3);
                Long valueOf = query.isNull(4) ? null : Long.valueOf(query.getLong(4));
                Long valueOf2 = query.isNull(5) ? null : Long.valueOf(query.getLong(5));
                Long valueOf3 = query.isNull(6) ? null : Long.valueOf(query.getLong(6));
                if (!query.isNull(7)) {
                    if (query.getLong(7) == 1) {
                        z = true;
                    }
                    bool = Boolean.valueOf(z);
                } else {
                    bool = null;
                }
                if (e2 && !query.isNull(8)) {
                    j5 = query.getLong(8);
                }
                r1 = r1;
                cursor = query;
                try {
                    C1107f fVar = new C1107f(str, str2, j2, j3, j5, j4, j6, valueOf, valueOf2, valueOf3, bool);
                    if (cursor.moveToNext()) {
                        e().s().a("Got multiple records for event aggregates, expected one. appId", C1124ib.a(str));
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return fVar;
                } catch (SQLiteException e3) {
                    e = e3;
                    try {
                        e().s().a("Error querying events. appId", C1124ib.a(str), k().a(str2), e);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        if (cursor != null) {
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e4) {
                e = e4;
                cursor = query;
                e().s().a("Error querying events. appId", C1124ib.a(str), k().a(str2), e);
                if (cursor != null) {
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                if (cursor != null) {
                }
                throw th;
            }
        } catch (SQLiteException e5) {
            e = e5;
            cursor = null;
            e().s().a("Error querying events. appId", C1124ib.a(str), k().a(str2), e);
            if (cursor != null) {
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0093  */
    public final Map<Integer, Y> f(String str) {
        Cursor cursor;
        s();
        i();
        Preconditions.checkNotEmpty(str);
        try {
            cursor = w().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                T t = new T();
                do {
                    int i2 = cursor.getInt(0);
                    try {
                        t.put(Integer.valueOf(i2), Y.a(cursor.getBlob(1), C1042tb.c()));
                    } catch (IOException e2) {
                        e().s().a("Failed to merge filter results. appId, audienceId, error", C1124ib.a(str), Integer.valueOf(i2), e2);
                    }
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                return t;
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    e().s().a("Database error querying filter results. appId", C1124ib.a(str), e);
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Database error querying filter results. appId", C1124ib.a(str), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
    }

    public final long c(String str) {
        Preconditions.checkNotEmpty(str);
        i();
        s();
        try {
            return (long) w().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, f().b(str, C1127j.A))))});
        } catch (SQLiteException e2) {
            e().s().a("Error deleting over the limit events. appId", C1124ib.a(str), e2);
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0073  */
    public final byte[] d(String str) {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        i();
        s();
        try {
            cursor = w().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                byte[] blob = cursor.getBlob(0);
                if (cursor.moveToNext()) {
                    e().s().a("Got multiple records for app config, expected one. appId", C1124ib.a(str));
                }
                if (cursor != null) {
                    cursor.close();
                }
                return blob;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    e().s().a("Error querying remote config. appId", C1124ib.a(str), e);
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            e().s().a("Error querying remote config. appId", C1124ib.a(str), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
    }

    public final long g(String str) {
        Preconditions.checkNotEmpty(str);
        return a("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0086  */
    public final Map<Integer, List<Integer>> e(String str) {
        Cursor cursor;
        s();
        i();
        Preconditions.checkNotEmpty(str);
        T t = new T();
        try {
            cursor = w().rawQuery("select audience_id, filter_id from event_filters where app_id = ? and session_scoped = 1 UNION select audience_id, filter_id from property_filters where app_id = ? and session_scoped = 1;", new String[]{str, str});
            try {
                if (!cursor.moveToFirst()) {
                    Map<Integer, List<Integer>> emptyMap = Collections.emptyMap();
                    if (cursor != null) {
                        cursor.close();
                    }
                    return emptyMap;
                }
                do {
                    int i2 = cursor.getInt(0);
                    List list = (List) t.get(Integer.valueOf(i2));
                    if (list == null) {
                        list = new ArrayList();
                        t.put(Integer.valueOf(i2), list);
                    }
                    list.add(Integer.valueOf(cursor.getInt(1)));
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                return t;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    e().s().a("Database error querying scoped filters. appId", C1124ib.a(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            e().s().a("Database error querying scoped filters. appId", C1124ib.a(str), e);
            if (cursor != null) {
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
    }

    public final boolean a(Sd sd) {
        Preconditions.checkNotNull(sd);
        i();
        s();
        if (d(sd.a, sd.c) == null) {
            if (Vd.a(sd.c)) {
                if (b("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{sd.a}) >= 25) {
                    return false;
                }
            } else {
                String str = "select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'";
                if (f().e(sd.a, C1127j.pa)) {
                    if (!"_npa".equals(sd.c)) {
                        if (b(str, new String[]{sd.a, sd.b}) >= 25) {
                            return false;
                        }
                    }
                } else {
                    if (b(str, new String[]{sd.a, sd.b}) >= 25) {
                        return false;
                    }
                }
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", sd.a);
        contentValues.put("origin", sd.b);
        contentValues.put("name", sd.c);
        contentValues.put("set_timestamp", Long.valueOf(sd.d));
        a(contentValues, "value", sd.e);
        try {
            if (w().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                e().s().a("Failed to insert/update user property (got -1). appId", C1124ib.a(sd.a));
            }
        } catch (SQLiteException e2) {
            e().s().a("Error storing user property. appId", C1124ib.a(sd.a), e2);
        }
        return true;
    }

    public final List<zzq> b(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        i();
        s();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return a(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0119 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x011d A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0153 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0155 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0164 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0179 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0195 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0196 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01a5 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01c0 A[Catch:{ SQLiteException -> 0x01d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01fd  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0204  */
    public final Db b(String str) {
        Cursor cursor;
        boolean z;
        boolean z2;
        String str2 = str;
        Preconditions.checkNotEmpty(str);
        i();
        s();
        try {
            boolean z3 = true;
            cursor = w().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled", "ssaid_reporting_enabled", "admob_app_id", "dynamite_version", "safelisted_events"}, "app_id=?", new String[]{str2}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                try {
                    Db db = new Db(this.b.q(), str2);
                    db.a(cursor.getString(0));
                    db.b(cursor.getString(1));
                    db.d(cursor.getString(2));
                    db.g(cursor.getLong(3));
                    db.a(cursor.getLong(4));
                    db.b(cursor.getLong(5));
                    db.f(cursor.getString(6));
                    db.g(cursor.getString(7));
                    db.d(cursor.getLong(8));
                    db.e(cursor.getLong(9));
                    if (!cursor.isNull(10)) {
                        if (cursor.getInt(10) == 0) {
                            z = false;
                            db.a(z);
                            db.j(cursor.getLong(11));
                            db.k(cursor.getLong(12));
                            db.l(cursor.getLong(13));
                            db.m(cursor.getLong(14));
                            db.h(cursor.getLong(15));
                            db.i(cursor.getLong(16));
                            db.c(!cursor.isNull(17) ? -2147483648L : (long) cursor.getInt(17));
                            db.e(cursor.getString(18));
                            db.o(cursor.getLong(19));
                            db.n(cursor.getLong(20));
                            db.h(cursor.getString(21));
                            long j2 = 0;
                            db.p(!cursor.isNull(22) ? 0 : cursor.getLong(22));
                            if (!cursor.isNull(23)) {
                                if (cursor.getInt(23) == 0) {
                                    z2 = false;
                                    db.b(z2);
                                    if (!cursor.isNull(24)) {
                                        if (cursor.getInt(24) == 0) {
                                            z3 = false;
                                        }
                                    }
                                    db.c(z3);
                                    db.c(cursor.getString(25));
                                    if (!cursor.isNull(26)) {
                                        j2 = cursor.getLong(26);
                                    }
                                    db.f(j2);
                                    if (!cursor.isNull(27)) {
                                        db.a(Arrays.asList(cursor.getString(27).split(",", -1)));
                                    }
                                    db.e();
                                    if (cursor.moveToNext()) {
                                        e().s().a("Got multiple records for app, expected one. appId", C1124ib.a(str));
                                    }
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    return db;
                                }
                            }
                            z2 = true;
                            db.b(z2);
                            if (!cursor.isNull(24)) {
                            }
                            db.c(z3);
                            db.c(cursor.getString(25));
                            if (!cursor.isNull(26)) {
                            }
                            db.f(j2);
                            if (!cursor.isNull(27)) {
                            }
                            db.e();
                            if (cursor.moveToNext()) {
                            }
                            if (cursor != null) {
                            }
                            return db;
                        }
                    }
                    z = true;
                    db.a(z);
                    db.j(cursor.getLong(11));
                    db.k(cursor.getLong(12));
                    db.l(cursor.getLong(13));
                    db.m(cursor.getLong(14));
                    db.h(cursor.getLong(15));
                    db.i(cursor.getLong(16));
                    db.c(!cursor.isNull(17) ? -2147483648L : (long) cursor.getInt(17));
                    db.e(cursor.getString(18));
                    db.o(cursor.getLong(19));
                    db.n(cursor.getLong(20));
                    db.h(cursor.getString(21));
                    long j22 = 0;
                    db.p(!cursor.isNull(22) ? 0 : cursor.getLong(22));
                    if (!cursor.isNull(23)) {
                    }
                    z2 = true;
                    db.b(z2);
                    if (!cursor.isNull(24)) {
                    }
                    db.c(z3);
                    db.c(cursor.getString(25));
                    if (!cursor.isNull(26)) {
                    }
                    db.f(j22);
                    if (!cursor.isNull(27)) {
                    }
                    db.e();
                    if (cursor.moveToNext()) {
                    }
                    if (cursor != null) {
                    }
                    return db;
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        e().s().a("Error querying app. appId", C1124ib.a(str), e);
                        if (cursor != null) {
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                e().s().a("Error querying app. appId", C1124ib.a(str), e);
                if (cursor != null) {
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (cursor != null) {
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Error querying app. appId", C1124ib.a(str), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a1  */
    public final List<Sd> a(String str) {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        i();
        s();
        ArrayList arrayList = new ArrayList();
        try {
            cursor = w().query("user_attributes", new String[]{"name", "origin", "set_timestamp", "value"}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                }
                do {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    if (string2 == null) {
                        string2 = "";
                    }
                    String str2 = string2;
                    long j2 = cursor.getLong(2);
                    Object a = a(cursor, 3);
                    if (a == null) {
                        e().s().a("Read invalid user property value, ignoring it. appId", C1124ib.a(str));
                    } else {
                        Sd sd = new Sd(str, str2, string, j2, a);
                        arrayList.add(sd);
                    }
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    e().s().a("Error querying user properties. appId", C1124ib.a(str), e);
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            e().s().a("Error querying user properties. appId", C1124ib.a(str), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00f8, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f9, code lost:
        r12 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0100, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0101, code lost:
        r12 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0104, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0105, code lost:
        r12 = r21;
        r11 = r22;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0100 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x000f] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0127  */
    public final List<Sd> a(String str, String str2, String str3) {
        Cursor cursor;
        String str4;
        Preconditions.checkNotEmpty(str);
        i();
        s();
        ArrayList arrayList = new ArrayList();
        Cursor cursor2 = null;
        try {
            ArrayList arrayList2 = new ArrayList(3);
            arrayList2.add(str);
            StringBuilder sb = new StringBuilder("app_id=?");
            if (!TextUtils.isEmpty(str2)) {
                str4 = str2;
                arrayList2.add(str4);
                sb.append(" and origin=?");
            } else {
                str4 = str2;
            }
            if (!TextUtils.isEmpty(str3)) {
                arrayList2.add(String.valueOf(str3).concat("*"));
                sb.append(" and name glob ?");
            }
            cursor = w().query("user_attributes", new String[]{"name", "set_timestamp", "value", "origin"}, sb.toString(), (String[]) arrayList2.toArray(new String[arrayList2.size()]), null, null, "rowid", "1001");
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                }
                while (true) {
                    if (arrayList.size() >= 1000) {
                        e().s().a("Read more than the max allowed user properties, ignoring excess", Integer.valueOf(1000));
                        break;
                    }
                    String string = cursor.getString(0);
                    long j2 = cursor.getLong(1);
                    try {
                        Object a = a(cursor, 2);
                        String string2 = cursor.getString(3);
                        if (a == null) {
                            try {
                                e().s().a("(2)Read invalid user property value, ignoring it", C1124ib.a(str), string2, str3);
                            } catch (SQLiteException e2) {
                                e = e2;
                                str4 = string2;
                                try {
                                    e().s().a("(2)Error querying user properties", C1124ib.a(str), str4, e);
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    return null;
                                } catch (Throwable th) {
                                    th = th;
                                    cursor2 = cursor;
                                    if (cursor2 != null) {
                                        cursor2.close();
                                    }
                                    throw th;
                                }
                            }
                        } else {
                            String str5 = str3;
                            Sd sd = new Sd(str, string2, string, j2, a);
                            arrayList.add(sd);
                        }
                        if (!cursor.moveToNext()) {
                            break;
                        }
                        str4 = string2;
                    } catch (SQLiteException e3) {
                        e = e3;
                        e().s().a("(2)Error querying user properties", C1124ib.a(str), str4, e);
                        if (cursor != null) {
                        }
                        return null;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (SQLiteException e4) {
                e = e4;
                e().s().a("(2)Error querying user properties", C1124ib.a(str), str4, e);
                if (cursor != null) {
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursor;
                if (cursor2 != null) {
                }
                throw th;
            }
        } catch (SQLiteException e5) {
            e = e5;
            str4 = str2;
            cursor = null;
            e().s().a("(2)Error querying user properties", C1124ib.a(str), str4, e);
            if (cursor != null) {
            }
            return null;
        } catch (Throwable th3) {
        }
    }

    public final boolean a(zzq zzq) {
        Preconditions.checkNotNull(zzq);
        i();
        s();
        if (d(zzq.a, zzq.c.b) == null) {
            if (b("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzq.a}) >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzq.a);
        contentValues.put("origin", zzq.b);
        contentValues.put("name", zzq.c.b);
        a(contentValues, "value", zzq.c.i());
        contentValues.put("active", Boolean.valueOf(zzq.e));
        contentValues.put("trigger_event_name", zzq.f);
        contentValues.put("trigger_timeout", Long.valueOf(zzq.h));
        l();
        contentValues.put("timed_out_event", Vd.a((Parcelable) zzq.g));
        contentValues.put("creation_timestamp", Long.valueOf(zzq.d));
        l();
        contentValues.put("triggered_event", Vd.a((Parcelable) zzq.i));
        contentValues.put("triggered_timestamp", Long.valueOf(zzq.c.c));
        contentValues.put("time_to_live", Long.valueOf(zzq.j));
        l();
        contentValues.put("expired_event", Vd.a((Parcelable) zzq.k));
        try {
            if (w().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                e().s().a("Failed to insert/update conditional user property (got -1)", C1124ib.a(zzq.a));
            }
        } catch (SQLiteException e2) {
            e().s().a("Error storing conditional user property", C1124ib.a(zzq.a), e2);
        }
        return true;
    }

    public final List<zzq> a(String str, String[] strArr) {
        i();
        s();
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            cursor = w().query("conditional_properties", new String[]{"app_id", "origin", "name", "value", "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", "1001");
            if (!cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            }
            while (true) {
                if (arrayList.size() < 1000) {
                    boolean z = false;
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    String string3 = cursor.getString(2);
                    Object a = a(cursor, 3);
                    if (cursor.getInt(4) != 0) {
                        z = true;
                    }
                    String string4 = cursor.getString(5);
                    long j2 = cursor.getLong(6);
                    zzai zzai = (zzai) m().a(cursor.getBlob(7), zzai.CREATOR);
                    long j3 = cursor.getLong(8);
                    zzai zzai2 = (zzai) m().a(cursor.getBlob(9), zzai.CREATOR);
                    long j4 = cursor.getLong(10);
                    long j5 = cursor.getLong(11);
                    zzai zzai3 = (zzai) m().a(cursor.getBlob(12), zzai.CREATOR);
                    zzjn zzjn = new zzjn(string3, j4, a, string2);
                    boolean z2 = z;
                    zzq zzq = r3;
                    zzq zzq2 = new zzq(string, string2, zzjn, j3, z2, string4, zzai, j2, zzai2, j5, zzai3);
                    arrayList.add(zzq);
                    if (!cursor.moveToNext()) {
                        break;
                    }
                } else {
                    e().s().a("Read more than the max allowed conditional properties, ignoring extra", Integer.valueOf(1000));
                    break;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        } catch (SQLiteException e2) {
            e().s().a("Error querying conditional user property value", e2);
            List<zzq> emptyList = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void a(Db db) {
        String str = "apps";
        Preconditions.checkNotNull(db);
        i();
        s();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", db.f());
        contentValues.put("app_instance_id", db.a());
        contentValues.put("gmp_app_id", db.c());
        contentValues.put("resettable_device_id_hash", db.h());
        contentValues.put("last_bundle_index", Long.valueOf(db.q()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(db.i()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(db.j()));
        contentValues.put("app_version", db.k());
        contentValues.put("app_store", db.m());
        contentValues.put("gmp_version", Long.valueOf(db.n()));
        contentValues.put("dev_cert_hash", Long.valueOf(db.o()));
        contentValues.put("measurement_enabled", Boolean.valueOf(db.d()));
        contentValues.put("day", Long.valueOf(db.u()));
        contentValues.put("daily_public_events_count", Long.valueOf(db.v()));
        contentValues.put("daily_events_count", Long.valueOf(db.w()));
        contentValues.put("daily_conversions_count", Long.valueOf(db.x()));
        contentValues.put("config_fetched_time", Long.valueOf(db.r()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(db.s()));
        contentValues.put("app_version_int", Long.valueOf(db.l()));
        contentValues.put("firebase_instance_id", db.b());
        contentValues.put("daily_error_events_count", Long.valueOf(db.z()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(db.y()));
        contentValues.put("health_monitor_sample", db.A());
        contentValues.put("android_id", Long.valueOf(db.C()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(db.D()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(db.E()));
        contentValues.put("admob_app_id", db.g());
        contentValues.put("dynamite_version", Long.valueOf(db.p()));
        if (db.G() != null) {
            if (db.G().size() == 0) {
                e().v().a("Safelisted events should not be an empty list. appId", db.f());
            } else {
                contentValues.put("safelisted_events", TextUtils.join(",", db.G()));
            }
        }
        try {
            SQLiteDatabase w = w();
            if (((long) w.update(str, contentValues, "app_id = ?", new String[]{db.f()})) == 0 && w.insertWithOnConflict(str, null, contentValues, 5) == -1) {
                e().s().a("Failed to insert/update app (got -1). appId", C1124ib.a(db.f()));
            }
        } catch (SQLiteException e2) {
            e().s().a("Error storing app. appId", C1124ib.a(db.f()), e2);
        }
    }

    public final ge a(long j2, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Preconditions.checkNotEmpty(str);
        i();
        s();
        String[] strArr = {str};
        ge geVar = new ge();
        Cursor cursor = null;
        try {
            SQLiteDatabase w = w();
            SQLiteDatabase sQLiteDatabase = w;
            cursor = sQLiteDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
            if (!cursor.moveToFirst()) {
                e().v().a("Not updating daily counts, app is not known. appId", C1124ib.a(str));
                if (cursor != null) {
                    cursor.close();
                }
                return geVar;
            }
            if (cursor.getLong(0) == j2) {
                geVar.b = cursor.getLong(1);
                geVar.a = cursor.getLong(2);
                geVar.c = cursor.getLong(3);
                geVar.d = cursor.getLong(4);
                geVar.e = cursor.getLong(5);
            }
            if (z) {
                geVar.b++;
            }
            if (z2) {
                geVar.a++;
            }
            if (z3) {
                geVar.c++;
            }
            if (z4) {
                geVar.d++;
            }
            if (z5) {
                geVar.e++;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("day", Long.valueOf(j2));
            contentValues.put("daily_public_events_count", Long.valueOf(geVar.a));
            contentValues.put("daily_events_count", Long.valueOf(geVar.b));
            contentValues.put("daily_conversions_count", Long.valueOf(geVar.c));
            contentValues.put("daily_error_events_count", Long.valueOf(geVar.d));
            contentValues.put("daily_realtime_events_count", Long.valueOf(geVar.e));
            w.update("apps", contentValues, "app_id=?", strArr);
            if (cursor != null) {
                cursor.close();
            }
            return geVar;
        } catch (SQLiteException e2) {
            e().s().a("Error updating daily counts. appId", C1124ib.a(str), e2);
            if (cursor != null) {
                cursor.close();
            }
            return geVar;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final boolean a(W w, boolean z) {
        i();
        s();
        Preconditions.checkNotNull(w);
        Preconditions.checkNotEmpty(w.t());
        Preconditions.checkState(w.O());
        A();
        long currentTimeMillis = c().currentTimeMillis();
        if (w.I() < currentTimeMillis - ce.s() || w.I() > ce.s() + currentTimeMillis) {
            e().v().a("Storing bundle outside of the max uploading time span. appId, now, timestamp", C1124ib.a(w.t()), Long.valueOf(currentTimeMillis), Long.valueOf(w.I()));
        }
        try {
            byte[] c = m().c(w.g());
            e().A().a("Saving bundle, size", Integer.valueOf(c.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", w.t());
            contentValues.put("bundle_end_timestamp", Long.valueOf(w.I()));
            contentValues.put("data", c);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            if (w.ra()) {
                contentValues.put("retry_count", Integer.valueOf(w.sa()));
            }
            try {
                if (w().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                e().s().a("Failed to insert bundle (got -1). appId", C1124ib.a(w.t()));
                return false;
            } catch (SQLiteException e2) {
                e().s().a("Error storing bundle. appId", C1124ib.a(w.t()), e2);
                return false;
            }
        } catch (IOException e3) {
            e().s().a("Data loss. Failed to serialize bundle. appId", C1124ib.a(w.t()), e3);
            return false;
        }
    }

    public final List<Pair<W, Long>> a(String str, int i2, int i3) {
        i();
        s();
        Preconditions.checkArgument(i2 > 0);
        Preconditions.checkArgument(i3 > 0);
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            cursor = w().query("queue", new String[]{"rowid", "data", "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i2));
            if (!cursor.moveToFirst()) {
                List<Pair<W, Long>> emptyList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return emptyList;
            }
            ArrayList arrayList = new ArrayList();
            int i4 = 0;
            do {
                long j2 = cursor.getLong(0);
                try {
                    byte[] b = m().b(cursor.getBlob(1));
                    if (!arrayList.isEmpty() && b.length + i4 > i3) {
                        break;
                    }
                    try {
                        a va = W.va();
                        va.a(b, C1042tb.c());
                        a aVar = va;
                        if (!cursor.isNull(2)) {
                            aVar.h(cursor.getInt(2));
                        }
                        i4 += b.length;
                        arrayList.add(Pair.create((W) ((Gb) aVar.w()), Long.valueOf(j2)));
                    } catch (IOException e2) {
                        e().s().a("Failed to merge queued bundle. appId", C1124ib.a(str), e2);
                    }
                    if (!cursor.moveToNext()) {
                        break;
                    }
                } catch (IOException e3) {
                    e().s().a("Failed to unzip queued bundle. appId", C1124ib.a(str), e3);
                }
            } while (i4 <= i3);
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        } catch (SQLiteException e4) {
            e().s().a("Error querying bundles. appId", C1124ib.a(str), e4);
            List<Pair<W, Long>> emptyList2 = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList2;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final void a(List<Long> list) {
        i();
        s();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (O()) {
            String join = TextUtils.join(",", list);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 80);
            sb3.append("SELECT COUNT(1) FROM queue WHERE rowid IN ");
            sb3.append(sb2);
            sb3.append(" AND retry_count =  2147483647 LIMIT 1");
            if (b(sb3.toString(), (String[]) null) > 0) {
                e().v().a("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                SQLiteDatabase w = w();
                StringBuilder sb4 = new StringBuilder(String.valueOf(sb2).length() + 127);
                sb4.append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ");
                sb4.append(sb2);
                sb4.append(" AND (retry_count IS NULL OR retry_count < 2147483647)");
                w.execSQL(sb4.toString());
            } catch (SQLiteException e2) {
                e().s().a("Error incrementing retry count. error", e2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, C0941da[] daVarArr) {
        boolean z;
        String str2 = str;
        C0941da[] daVarArr2 = daVarArr;
        String str3 = "app_id=? and audience_id=?";
        String str4 = "event_filters";
        String str5 = "app_id=?";
        String str6 = "property_filters";
        s();
        i();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(daVarArr);
        SQLiteDatabase w = w();
        w.beginTransaction();
        try {
            s();
            i();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase w2 = w();
            w2.delete(str6, str5, new String[]{str2});
            w2.delete(str4, str5, new String[]{str2});
            for (C0941da daVar : daVarArr2) {
                s();
                i();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(daVar);
                Preconditions.checkNotNull(daVar.f);
                Preconditions.checkNotNull(daVar.e);
                if (daVar.d == null) {
                    e().v().a("Audience with no ID. appId", C1124ib.a(str));
                } else {
                    int intValue = daVar.d.intValue();
                    D[] dArr = daVar.f;
                    int length = dArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            G[] gArr = daVar.e;
                            int length2 = gArr.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length2) {
                                    D[] dArr2 = daVar.f;
                                    int length3 = dArr2.length;
                                    int i4 = 0;
                                    while (true) {
                                        if (i4 >= length3) {
                                            z = true;
                                            break;
                                        } else if (!a(str2, intValue, dArr2[i4])) {
                                            z = false;
                                            break;
                                        } else {
                                            i4++;
                                        }
                                    }
                                    if (z) {
                                        G[] gArr2 = daVar.e;
                                        int length4 = gArr2.length;
                                        int i5 = 0;
                                        while (true) {
                                            if (i5 >= length4) {
                                                break;
                                            } else if (!a(str2, intValue, gArr2[i5])) {
                                                z = false;
                                                break;
                                            } else {
                                                i5++;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        s();
                                        i();
                                        Preconditions.checkNotEmpty(str);
                                        SQLiteDatabase w3 = w();
                                        w3.delete(str6, str3, new String[]{str2, String.valueOf(intValue)});
                                        w3.delete(str4, str3, new String[]{str2, String.valueOf(intValue)});
                                    }
                                } else if (!gArr[i3].r()) {
                                    e().v().a("Property filter with no ID. Audience definition ignored. appId, audienceId", C1124ib.a(str), daVar.d);
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                        } else if (!dArr[i2].s()) {
                            e().v().a("Event filter with no ID. Audience definition ignored. appId, audienceId", C1124ib.a(str), daVar.d);
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            for (C0941da daVar2 : daVarArr2) {
                arrayList.add(daVar2.d);
            }
            a(str2, (List<Integer>) arrayList);
            w.setTransactionSuccessful();
        } finally {
            w.endTransaction();
        }
    }

    private final boolean a(String str, int i2, D d2) {
        s();
        i();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(d2);
        Integer num = null;
        if (TextUtils.isEmpty(d2.q())) {
            C1134kb v = e().v();
            Object a = C1124ib.a(str);
            Integer valueOf = Integer.valueOf(i2);
            if (d2.s()) {
                num = Integer.valueOf(d2.p());
            }
            v.a("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", a, valueOf, String.valueOf(num));
            return false;
        }
        byte[] g2 = d2.g();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("audience_id", Integer.valueOf(i2));
        contentValues.put("filter_id", d2.s() ? Integer.valueOf(d2.p()) : null);
        contentValues.put("event_name", d2.q());
        if (f().e(str, C1127j.Ea)) {
            contentValues.put("session_scoped", d2.A() ? Boolean.valueOf(d2.B()) : null);
        }
        contentValues.put("data", g2);
        try {
            if (w().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                e().s().a("Failed to insert event filter (got -1). appId", C1124ib.a(str));
            }
            return true;
        } catch (SQLiteException e2) {
            e().s().a("Error storing event filter. appId", C1124ib.a(str), e2);
            return false;
        }
    }

    private final boolean a(String str, int i2, G g2) {
        s();
        i();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(g2);
        Integer num = null;
        if (TextUtils.isEmpty(g2.q())) {
            C1134kb v = e().v();
            Object a = C1124ib.a(str);
            Integer valueOf = Integer.valueOf(i2);
            if (g2.r()) {
                num = Integer.valueOf(g2.p());
            }
            v.a("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", a, valueOf, String.valueOf(num));
            return false;
        }
        byte[] g3 = g2.g();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("audience_id", Integer.valueOf(i2));
        contentValues.put("filter_id", g2.r() ? Integer.valueOf(g2.p()) : null);
        contentValues.put("property_name", g2.q());
        if (f().e(str, C1127j.Ea)) {
            contentValues.put("session_scoped", g2.u() ? Boolean.valueOf(g2.x()) : null);
        }
        contentValues.put("data", g3);
        try {
            if (w().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                return true;
            }
            e().s().a("Failed to insert property filter (got -1). appId", C1124ib.a(str));
            return false;
        } catch (SQLiteException e2) {
            e().s().a("Error storing property filter. appId", C1124ib.a(str), e2);
            return false;
        }
    }

    private final boolean a(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        s();
        i();
        SQLiteDatabase w = w();
        try {
            long b = b("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int max = Math.max(0, Math.min(2000, f().b(str, C1127j.Q)));
            if (b <= ((long) max)) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                Integer num = (Integer) list.get(i2);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String join = TextUtils.join(",", arrayList);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 140);
            sb3.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            sb3.append(sb2);
            sb3.append(" order by rowid desc limit -1 offset ?)");
            return w.delete("audience_filter_values", sb3.toString(), new String[]{str, Integer.toString(max)}) > 0;
        } catch (SQLiteException e2) {
            e().s().a("Database error querying filters. appId", C1124ib.a(str), e2);
            return false;
        }
    }

    private static void a(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    @VisibleForTesting
    private final Object a(Cursor cursor, int i2) {
        int type = cursor.getType(i2);
        if (type == 0) {
            e().s().a("Loaded invalid null value from database");
            return null;
        } else if (type == 1) {
            return Long.valueOf(cursor.getLong(i2));
        } else {
            if (type == 2) {
                return Double.valueOf(cursor.getDouble(i2));
            }
            if (type == 3) {
                return cursor.getString(i2);
            }
            if (type != 4) {
                e().s().a("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
            }
            e().s().a("Loaded invalid blob type value, ignoring it");
            return null;
        }
    }

    public final long a(W w) throws IOException {
        i();
        s();
        Preconditions.checkNotNull(w);
        Preconditions.checkNotEmpty(w.t());
        byte[] g2 = w.g();
        long a = m().a(g2);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", w.t());
        contentValues.put("metadata_fingerprint", Long.valueOf(a));
        contentValues.put("metadata", g2);
        try {
            w().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
            return a;
        } catch (SQLiteException e2) {
            e().s().a("Error storing raw event metadata. appId", C1124ib.a(w.t()), e2);
            throw e2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005b  */
    public final String a(long j2) {
        Cursor cursor;
        i();
        s();
        try {
            cursor = w().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j2)});
            try {
                if (!cursor.moveToFirst()) {
                    e().A().a("No expired configs for apps with pending events");
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                String string = cursor.getString(0);
                if (cursor != null) {
                    cursor.close();
                }
                return string;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    e().s().a("Error selecting expired configs", e);
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            e().s().a("Error selecting expired configs", e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008a  */
    public final Pair<S, Long> a(String str, Long l2) {
        Cursor cursor;
        i();
        s();
        try {
            cursor = w().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l2)});
            try {
                if (!cursor.moveToFirst()) {
                    e().A().a("Main event not found");
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                byte[] blob = cursor.getBlob(0);
                try {
                    Pair<S, Long> create = Pair.create(S.a(blob, C1042tb.c()), Long.valueOf(cursor.getLong(1)));
                    if (cursor != null) {
                        cursor.close();
                    }
                    return create;
                } catch (IOException e2) {
                    e().s().a("Failed to merge main event. appId, eventId", C1124ib.a(str), l2, e2);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    e().s().a("Error selecting main event", e);
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            e().s().a("Error selecting main event", e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final boolean a(String str, Long l2, long j2, S s) {
        i();
        s();
        Preconditions.checkNotNull(s);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l2);
        byte[] g2 = s.g();
        e().A().a("Saving complex main event, appId, data size", k().a(str), Integer.valueOf(g2.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("event_id", l2);
        contentValues.put("children_to_process", Long.valueOf(j2));
        contentValues.put("main_event", g2);
        try {
            if (w().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                return true;
            }
            e().s().a("Failed to insert complex main event (got -1). appId", C1124ib.a(str));
            return false;
        } catch (SQLiteException e2) {
            e().s().a("Error storing complex main event. appId", C1124ib.a(str), e2);
            return false;
        }
    }

    public final boolean a(C1112g gVar, long j2, boolean z) {
        i();
        s();
        Preconditions.checkNotNull(gVar);
        Preconditions.checkNotEmpty(gVar.a);
        S.a A = S.A();
        A.b(gVar.e);
        Iterator it = gVar.f.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            U.a y = U.y();
            y.a(str);
            m().a(y, gVar.f.a(str));
            A.a(y);
        }
        byte[] g2 = ((S) A.w()).g();
        e().A().a("Saving event, name, data size", k().a(gVar.b), Integer.valueOf(g2.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", gVar.a);
        contentValues.put("name", gVar.b);
        contentValues.put("timestamp", Long.valueOf(gVar.d));
        contentValues.put("metadata_fingerprint", Long.valueOf(j2));
        contentValues.put("data", g2);
        contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
        try {
            if (w().insert("raw_events", null, contentValues) != -1) {
                return true;
            }
            e().s().a("Failed to insert raw event (got -1). appId", C1124ib.a(gVar.a));
            return false;
        } catch (SQLiteException e2) {
            e().s().a("Error storing raw event. appId", C1124ib.a(gVar.a), e2);
            return false;
        }
    }
}
