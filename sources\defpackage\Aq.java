package defpackage;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: Aq reason: default package */
/* compiled from: ColorParser */
public final class Aq {
    private static final Pattern a = Pattern.compile("^rgb\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)$");
    private static final Pattern b = Pattern.compile("^rgba\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)$");
    private static final Pattern c = Pattern.compile("^rgba\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3}),(\\d*\\.?\\d*?)\\)$");
    private static final Map<String, Integer> d = new HashMap();

    static {
        d.put("aliceblue", Integer.valueOf(-984833));
        d.put("antiquewhite", Integer.valueOf(-332841));
        Map<String, Integer> map = d;
        Integer valueOf = Integer.valueOf(-16711681);
        map.put("aqua", valueOf);
        d.put("aquamarine", Integer.valueOf(-8388652));
        d.put("azure", Integer.valueOf(-983041));
        d.put("beige", Integer.valueOf(-657956));
        d.put("bisque", Integer.valueOf(-6972));
        d.put("black", Integer.valueOf(-16777216));
        d.put("blanchedalmond", Integer.valueOf(-5171));
        d.put("blue", Integer.valueOf(-16776961));
        d.put("blueviolet", Integer.valueOf(-7722014));
        d.put("brown", Integer.valueOf(-5952982));
        d.put("burlywood", Integer.valueOf(-2180985));
        d.put("cadetblue", Integer.valueOf(-10510688));
        d.put("chartreuse", Integer.valueOf(-8388864));
        d.put("chocolate", Integer.valueOf(-2987746));
        d.put("coral", Integer.valueOf(-32944));
        d.put("cornflowerblue", Integer.valueOf(-10185235));
        d.put("cornsilk", Integer.valueOf(-1828));
        d.put("crimson", Integer.valueOf(-2354116));
        d.put("cyan", valueOf);
        d.put("darkblue", Integer.valueOf(-16777077));
        d.put("darkcyan", Integer.valueOf(-16741493));
        d.put("darkgoldenrod", Integer.valueOf(-4684277));
        Map<String, Integer> map2 = d;
        Integer valueOf2 = Integer.valueOf(-5658199);
        map2.put("darkgray", valueOf2);
        d.put("darkgreen", Integer.valueOf(-16751616));
        d.put("darkgrey", valueOf2);
        d.put("darkkhaki", Integer.valueOf(-4343957));
        d.put("darkmagenta", Integer.valueOf(-7667573));
        d.put("darkolivegreen", Integer.valueOf(-11179217));
        d.put("darkorange", Integer.valueOf(-29696));
        d.put("darkorchid", Integer.valueOf(-6737204));
        d.put("darkred", Integer.valueOf(-7667712));
        d.put("darksalmon", Integer.valueOf(-1468806));
        d.put("darkseagreen", Integer.valueOf(-7357297));
        d.put("darkslateblue", Integer.valueOf(-12042869));
        Map<String, Integer> map3 = d;
        Integer valueOf3 = Integer.valueOf(-13676721);
        map3.put("darkslategray", valueOf3);
        d.put("darkslategrey", valueOf3);
        d.put("darkturquoise", Integer.valueOf(-16724271));
        d.put("darkviolet", Integer.valueOf(-7077677));
        d.put("deeppink", Integer.valueOf(-60269));
        d.put("deepskyblue", Integer.valueOf(-16728065));
        Map<String, Integer> map4 = d;
        Integer valueOf4 = Integer.valueOf(-9868951);
        map4.put("dimgray", valueOf4);
        d.put("dimgrey", valueOf4);
        d.put("dodgerblue", Integer.valueOf(-14774017));
        d.put("firebrick", Integer.valueOf(-5103070));
        d.put("floralwhite", Integer.valueOf(-1296));
        d.put("forestgreen", Integer.valueOf(-14513374));
        Map<String, Integer> map5 = d;
        Integer valueOf5 = Integer.valueOf(-65281);
        map5.put("fuchsia", valueOf5);
        d.put("gainsboro", Integer.valueOf(-2302756));
        d.put("ghostwhite", Integer.valueOf(-460545));
        d.put("gold", Integer.valueOf(-10496));
        d.put("goldenrod", Integer.valueOf(-2448096));
        Map<String, Integer> map6 = d;
        Integer valueOf6 = Integer.valueOf(-8355712);
        map6.put("gray", valueOf6);
        d.put("green", Integer.valueOf(-16744448));
        d.put("greenyellow", Integer.valueOf(-5374161));
        d.put("grey", valueOf6);
        d.put("honeydew", Integer.valueOf(-983056));
        d.put("hotpink", Integer.valueOf(-38476));
        d.put("indianred", Integer.valueOf(-3318692));
        d.put("indigo", Integer.valueOf(-11861886));
        d.put("ivory", Integer.valueOf(-16));
        d.put("khaki", Integer.valueOf(-989556));
        d.put("lavender", Integer.valueOf(-1644806));
        d.put("lavenderblush", Integer.valueOf(-3851));
        d.put("lawngreen", Integer.valueOf(-8586240));
        d.put("lemonchiffon", Integer.valueOf(-1331));
        d.put("lightblue", Integer.valueOf(-5383962));
        d.put("lightcoral", Integer.valueOf(-1015680));
        d.put("lightcyan", Integer.valueOf(-2031617));
        d.put("lightgoldenrodyellow", Integer.valueOf(-329006));
        Map<String, Integer> map7 = d;
        Integer valueOf7 = Integer.valueOf(-2894893);
        map7.put("lightgray", valueOf7);
        d.put("lightgreen", Integer.valueOf(-7278960));
        d.put("lightgrey", valueOf7);
        d.put("lightpink", Integer.valueOf(-18751));
        d.put("lightsalmon", Integer.valueOf(-24454));
        d.put("lightseagreen", Integer.valueOf(-14634326));
        d.put("lightskyblue", Integer.valueOf(-7876870));
        d.put("lightslategray", Integer.valueOf(-8943463));
        d.put("lightslategrey", Integer.valueOf(-8943463));
        d.put("lightsteelblue", Integer.valueOf(-5192482));
        d.put("lightyellow", Integer.valueOf(-32));
        d.put("lime", Integer.valueOf(-16711936));
        d.put("limegreen", Integer.valueOf(-13447886));
        d.put("linen", Integer.valueOf(-331546));
        d.put("magenta", valueOf5);
        d.put("maroon", Integer.valueOf(-8388608));
        d.put("mediumaquamarine", Integer.valueOf(-10039894));
        d.put("mediumblue", Integer.valueOf(-16777011));
        d.put("mediumorchid", Integer.valueOf(-4565549));
        d.put("mediumpurple", Integer.valueOf(-7114533));
        d.put("mediumseagreen", Integer.valueOf(-12799119));
        d.put("mediumslateblue", Integer.valueOf(-8689426));
        d.put("mediumspringgreen", Integer.valueOf(-16713062));
        d.put("mediumturquoise", Integer.valueOf(-12004916));
        d.put("mediumvioletred", Integer.valueOf(-3730043));
        d.put("midnightblue", Integer.valueOf(-15132304));
        d.put("mintcream", Integer.valueOf(-655366));
        d.put("mistyrose", Integer.valueOf(-6943));
        d.put("moccasin", Integer.valueOf(-6987));
        d.put("navajowhite", Integer.valueOf(-8531));
        d.put("navy", Integer.valueOf(-16777088));
        d.put("oldlace", Integer.valueOf(-133658));
        d.put("olive", Integer.valueOf(-8355840));
        d.put("olivedrab", Integer.valueOf(-9728477));
        d.put("orange", Integer.valueOf(-23296));
        d.put("orangered", Integer.valueOf(-47872));
        d.put("orchid", Integer.valueOf(-2461482));
        d.put("palegoldenrod", Integer.valueOf(-1120086));
        d.put("palegreen", Integer.valueOf(-6751336));
        d.put("paleturquoise", Integer.valueOf(-5247250));
        d.put("palevioletred", Integer.valueOf(-2396013));
        d.put("papayawhip", Integer.valueOf(-4139));
        d.put("peachpuff", Integer.valueOf(-9543));
        d.put("peru", Integer.valueOf(-3308225));
        d.put("pink", Integer.valueOf(-16181));
        d.put("plum", Integer.valueOf(-2252579));
        d.put("powderblue", Integer.valueOf(-5185306));
        d.put("purple", Integer.valueOf(-8388480));
        d.put("rebeccapurple", Integer.valueOf(-10079335));
        d.put("red", Integer.valueOf(-65536));
        d.put("rosybrown", Integer.valueOf(-4419697));
        d.put("royalblue", Integer.valueOf(-12490271));
        d.put("saddlebrown", Integer.valueOf(-7650029));
        d.put("salmon", Integer.valueOf(-360334));
        d.put("sandybrown", Integer.valueOf(-744352));
        d.put("seagreen", Integer.valueOf(-13726889));
        d.put("seashell", Integer.valueOf(-2578));
        d.put("sienna", Integer.valueOf(-6270419));
        d.put("silver", Integer.valueOf(-4144960));
        d.put("skyblue", Integer.valueOf(-7876885));
        d.put("slateblue", Integer.valueOf(-9807155));
        d.put("slategray", Integer.valueOf(-9404272));
        d.put("slategrey", Integer.valueOf(-9404272));
        d.put("snow", Integer.valueOf(-1286));
        d.put("springgreen", Integer.valueOf(-16711809));
        d.put("steelblue", Integer.valueOf(-12156236));
        d.put("tan", Integer.valueOf(-2968436));
        d.put("teal", Integer.valueOf(-16744320));
        d.put("thistle", Integer.valueOf(-2572328));
        d.put("tomato", Integer.valueOf(-40121));
        d.put("transparent", Integer.valueOf(0));
        d.put("turquoise", Integer.valueOf(-12525360));
        d.put("violet", Integer.valueOf(-1146130));
        d.put("wheat", Integer.valueOf(-663885));
        d.put("white", Integer.valueOf(-1));
        d.put("whitesmoke", Integer.valueOf(-657931));
        d.put("yellow", Integer.valueOf(-256));
        d.put("yellowgreen", Integer.valueOf(-6632142));
    }

    private static int a(int i, int i2, int i3, int i4) {
        return (i << 24) | (i2 << 16) | (i3 << 8) | i4;
    }

    public static int a(String str) {
        return a(str, true);
    }

    public static int b(String str) {
        return a(str, false);
    }

    private static int a(String str, boolean z) {
        int i;
        int i2;
        C1852xq.a(!TextUtils.isEmpty(str));
        String replace = str.replace(" ", "");
        if (replace.charAt(0) == '#') {
            int parseLong = (int) Long.parseLong(replace.substring(1), 16);
            if (replace.length() == 7) {
                i2 = -16777216 | parseLong;
            } else if (replace.length() == 9) {
                i2 = ((parseLong & 255) << 24) | (parseLong >>> 8);
            } else {
                throw new IllegalArgumentException();
            }
            return i2;
        }
        if (replace.startsWith("rgba")) {
            Matcher matcher = (z ? c : b).matcher(replace);
            if (matcher.matches()) {
                if (z) {
                    i = (int) (Float.parseFloat(matcher.group(4)) * 255.0f);
                } else {
                    i = Integer.parseInt(matcher.group(4), 10);
                }
                return a(i, Integer.parseInt(matcher.group(1), 10), Integer.parseInt(matcher.group(2), 10), Integer.parseInt(matcher.group(3), 10));
            }
        } else if (replace.startsWith("rgb")) {
            Matcher matcher2 = a.matcher(replace);
            if (matcher2.matches()) {
                return a(Integer.parseInt(matcher2.group(1), 10), Integer.parseInt(matcher2.group(2), 10), Integer.parseInt(matcher2.group(3), 10));
            }
        } else {
            Integer num = (Integer) d.get(C0471ar.g(replace));
            if (num != null) {
                return num.intValue();
            }
        }
        throw new IllegalArgumentException();
    }

    private static int a(int i, int i2, int i3) {
        return a(255, i, i2, i3);
    }
}
