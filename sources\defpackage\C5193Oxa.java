package defpackage;

import android.database.Cursor;

/* renamed from: Oxa reason: default package and case insensitive filesystem */
/* compiled from: StationCollectionsModel */
class C5193Oxa implements C6772kMa<C3508cda> {
    final /* synthetic */ d a;

    C5193Oxa(d dVar) {
        this.a = dVar;
    }

    public C3508cda a(Cursor cursor) {
        return (C3508cda) this.a.b.decode(cursor.getString(0));
    }
}
