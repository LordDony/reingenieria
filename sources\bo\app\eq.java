package bo.app;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class eq implements ei {
    private static final String b = Hg.a(eq.class);
    er a;

    public eq(JSONObject jSONObject) {
        JSONArray jSONArray = jSONObject.getJSONObject("data").getJSONArray("property_filters");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray2 = jSONArray.getJSONArray(i);
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                arrayList2.add(new el(jSONArray2.getJSONObject(i2)));
            }
            arrayList.add(new et(arrayList2));
        }
        this.a = new er(arrayList);
    }

    public boolean a(fb fbVar) {
        return this.a.a(fbVar);
    }

    /* renamed from: a */
    public JSONObject forJsonPut() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("property_filters", this.a.forJsonPut());
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e) {
            Hg.b(b, "Caught exception creating Json.", e);
        }
        return jSONObject;
    }
}
