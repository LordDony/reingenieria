package defpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* renamed from: ayb reason: default package and case insensitive filesystem */
/* compiled from: CipherSuite */
public final class C5613ayb {
    public static final C5613ayb A = a("TLS_KRB5_WITH_RC4_128_MD5", 36);
    public static final C5613ayb Aa = a("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162);
    public static final C5613ayb B = a("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
    public static final C5613ayb Ba = a("TLS_ECDH_RSA_WITH_NULL_SHA", 49163);
    public static final C5613ayb C = a("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
    public static final C5613ayb Ca = a("TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164);
    public static final C5613ayb D = a("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
    public static final C5613ayb Da = a("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165);
    public static final C5613ayb E = a("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
    public static final C5613ayb Ea = a("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166);
    public static final C5613ayb F = a("TLS_RSA_WITH_AES_128_CBC_SHA", 47);
    public static final C5613ayb Fa = a("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167);
    public static final C5613ayb G = a("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
    public static final C5613ayb Ga = a("TLS_ECDHE_RSA_WITH_NULL_SHA", 49168);
    public static final C5613ayb H = a("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
    public static final C5613ayb Ha = a("TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169);
    public static final C5613ayb I = a("TLS_DH_anon_WITH_AES_128_CBC_SHA", 52);
    public static final C5613ayb Ia = a("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170);
    public static final C5613ayb J = a("TLS_RSA_WITH_AES_256_CBC_SHA", 53);
    public static final C5613ayb Ja = a("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171);
    public static final C5613ayb K = a("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
    public static final C5613ayb Ka = a("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172);
    public static final C5613ayb L = a("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
    public static final C5613ayb La = a("TLS_ECDH_anon_WITH_NULL_SHA", 49173);
    public static final C5613ayb M = a("TLS_DH_anon_WITH_AES_256_CBC_SHA", 58);
    public static final C5613ayb Ma = a("TLS_ECDH_anon_WITH_RC4_128_SHA", 49174);
    public static final C5613ayb N = a("TLS_RSA_WITH_NULL_SHA256", 59);
    public static final C5613ayb Na = a("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175);
    public static final C5613ayb O = a("TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
    public static final C5613ayb Oa = a("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176);
    public static final C5613ayb P = a("TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
    public static final C5613ayb Pa = a("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177);
    public static final C5613ayb Q = a("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
    public static final C5613ayb Qa = a("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187);
    public static final C5613ayb R = a("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
    public static final C5613ayb Ra = a("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188);
    public static final C5613ayb S = a("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
    public static final C5613ayb Sa = a("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189);
    public static final C5613ayb T = a("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
    public static final C5613ayb Ta = a("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190);
    public static final C5613ayb U = a("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103);
    public static final C5613ayb Ua = a("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191);
    public static final C5613ayb V = a("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106);
    public static final C5613ayb Va = a("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192);
    public static final C5613ayb W = a("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107);
    public static final C5613ayb Wa = a("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193);
    public static final C5613ayb X = a("TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108);
    public static final C5613ayb Xa = a("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194);
    public static final C5613ayb Y = a("TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109);
    public static final C5613ayb Ya = a("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195);
    public static final C5613ayb Z = a("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", 132);
    public static final C5613ayb Za = a("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196);
    public static final C5613ayb _a = a("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197);
    static final Comparator<String> a = Nxb.a;
    public static final C5613ayb aa = a("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", 135);
    public static final C5613ayb ab = a("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198);
    private static final Map<String, C5613ayb> b = new LinkedHashMap();
    public static final C5613ayb ba = a("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", 136);
    public static final C5613ayb bb = a("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199);
    public static final C5613ayb c = a("SSL_RSA_WITH_NULL_MD5", 1);
    public static final C5613ayb ca = a("TLS_PSK_WITH_RC4_128_SHA", 138);
    public static final C5613ayb cb = a("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200);
    public static final C5613ayb d = a("SSL_RSA_WITH_NULL_SHA", 2);
    public static final C5613ayb da = a("TLS_PSK_WITH_3DES_EDE_CBC_SHA", 139);
    public static final C5613ayb db = a("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201);
    public static final C5613ayb e = a("SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3);
    public static final C5613ayb ea = a("TLS_PSK_WITH_AES_128_CBC_SHA", 140);
    public static final C5613ayb eb = a("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202);
    public static final C5613ayb f = a("SSL_RSA_WITH_RC4_128_MD5", 4);
    public static final C5613ayb fa = a("TLS_PSK_WITH_AES_256_CBC_SHA", 141);
    public static final C5613ayb fb = a("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", 49205);
    public static final C5613ayb g = a("SSL_RSA_WITH_RC4_128_SHA", 5);
    public static final C5613ayb ga = a("TLS_RSA_WITH_SEED_CBC_SHA", 150);
    public static final C5613ayb gb = a("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", 49206);
    public static final C5613ayb h = a("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8);
    public static final C5613ayb ha = a("TLS_RSA_WITH_AES_128_GCM_SHA256", 156);
    public static final C5613ayb hb = a("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52392);
    public static final C5613ayb i = a("SSL_RSA_WITH_DES_CBC_SHA", 9);
    public static final C5613ayb ia = a("TLS_RSA_WITH_AES_256_GCM_SHA384", 157);
    public static final C5613ayb ib = a("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", 52393);
    public static final C5613ayb j = a("SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10);
    public static final C5613ayb ja = a("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", 158);
    public static final C5613ayb jb = a("TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52394);
    public static final C5613ayb k = a("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17);
    public static final C5613ayb ka = a("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", 159);
    public static final C5613ayb kb = a("TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256", 52396);
    public static final C5613ayb l = a("SSL_DHE_DSS_WITH_DES_CBC_SHA", 18);
    public static final C5613ayb la = a("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", 162);
    public static final C5613ayb lb = a("TLS_AES_128_GCM_SHA256", 4865);
    public static final C5613ayb m = a("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
    public static final C5613ayb ma = a("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", 163);
    public static final C5613ayb mb = a("TLS_AES_256_GCM_SHA384", 4866);
    public static final C5613ayb n = a("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20);
    public static final C5613ayb na = a("TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166);
    public static final C5613ayb nb = a("TLS_CHACHA20_POLY1305_SHA256", 4867);
    public static final C5613ayb o = a("SSL_DHE_RSA_WITH_DES_CBC_SHA", 21);
    public static final C5613ayb oa = a("TLS_DH_anon_WITH_AES_256_GCM_SHA384", 167);
    public static final C5613ayb ob = a("TLS_AES_128_CCM_SHA256", 4868);
    public static final C5613ayb p = a("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
    public static final C5613ayb pa = a("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255);
    public static final C5613ayb pb = a("TLS_AES_128_CCM_8_SHA256", 4869);
    public static final C5613ayb q = a("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23);
    public static final C5613ayb qa = a("TLS_FALLBACK_SCSV", 22016);
    public static final C5613ayb r = a("SSL_DH_anon_WITH_RC4_128_MD5", 24);
    public static final C5613ayb ra = a("TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153);
    public static final C5613ayb s = a("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25);
    public static final C5613ayb sa = a("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154);
    public static final C5613ayb t = a("SSL_DH_anon_WITH_DES_CBC_SHA", 26);
    public static final C5613ayb ta = a("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155);
    public static final C5613ayb u = a("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27);
    public static final C5613ayb ua = a("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156);
    public static final C5613ayb v = a("TLS_KRB5_WITH_DES_CBC_SHA", 30);
    public static final C5613ayb va = a("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157);
    public static final C5613ayb w = a("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
    public static final C5613ayb wa = a("TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158);
    public static final C5613ayb x = a("TLS_KRB5_WITH_RC4_128_SHA", 32);
    public static final C5613ayb xa = a("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159);
    public static final C5613ayb y = a("TLS_KRB5_WITH_DES_CBC_MD5", 34);
    public static final C5613ayb ya = a("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160);
    public static final C5613ayb z = a("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
    public static final C5613ayb za = a("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161);
    final String qb;

    private C5613ayb(String str) {
        if (str != null) {
            this.qb = str;
            return;
        }
        throw new NullPointerException();
    }

    static /* synthetic */ int a(String str, String str2) {
        int min = Math.min(str.length(), str2.length());
        int i2 = 4;
        while (true) {
            int i3 = -1;
            if (i2 < min) {
                char charAt = str.charAt(i2);
                char charAt2 = str2.charAt(i2);
                if (charAt != charAt2) {
                    if (charAt >= charAt2) {
                        i3 = 1;
                    }
                    return i3;
                }
                i2++;
            } else {
                int length = str.length();
                int length2 = str2.length();
                if (length == length2) {
                    return 0;
                }
                if (length >= length2) {
                    i3 = 1;
                }
                return i3;
            }
        }
    }

    private static String b(String str) {
        String str2 = "TLS_";
        String str3 = "SSL_";
        if (str.startsWith(str2)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str.substring(4));
            return sb.toString();
        }
        if (str.startsWith(str3)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(str.substring(4));
            str = sb2.toString();
        }
        return str;
    }

    public String toString() {
        return this.qb;
    }

    public static synchronized C5613ayb a(String str) {
        C5613ayb ayb;
        synchronized (C5613ayb.class) {
            ayb = (C5613ayb) b.get(str);
            if (ayb == null) {
                ayb = (C5613ayb) b.get(b(str));
                if (ayb == null) {
                    ayb = new C5613ayb(str);
                }
                b.put(str, ayb);
            }
        }
        return ayb;
    }

    static List<C5613ayb> a(String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String a2 : strArr) {
            arrayList.add(a(a2));
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static C5613ayb a(String str, int i2) {
        C5613ayb ayb = new C5613ayb(str);
        b.put(str, ayb);
        return ayb;
    }

    public String a() {
        return this.qb;
    }
}
