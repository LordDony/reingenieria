package defpackage;

import com.soundcloud.android.collections.data.C2852h;
import java.util.List;

/* renamed from: Pva reason: default package and case insensitive filesystem */
/* compiled from: LocalSearchSuggestionOperations.kt */
final class C5221Pva<T, R> implements C7118pQa<T, R> {
    final /* synthetic */ C6884lwa a;
    final /* synthetic */ String b;
    final /* synthetic */ _Xa c;
    final /* synthetic */ C6308dYa d;
    final /* synthetic */ a e;

    C5221Pva(C6884lwa lwa, String str, _Xa _xa, C6308dYa dya, a aVar) {
        this.a = lwa;
        this.b = str;
        this.c = _xa;
        this.d = dya;
        this.e = aVar;
    }

    /* renamed from: a */
    public final List<C4882Eva> apply(List<? extends HVa<C3509cea, ? extends C2852h>> list) {
        C7471uYa.b(list, "list");
        return this.a.b(list, this.b, this.c, this.d, this.e);
    }
}
