package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.Objects;

public final class zai<O extends ApiOptions> {
    private final Api<O> mApi;
    private final O zabh;
    private final boolean zacu = true;
    private final int zacv;

    private zai(Api<O> api, O o) {
        this.mApi = api;
        this.zabh = o;
        this.zacv = Objects.hashCode(this.mApi, this.zabh);
    }

    public static <O extends ApiOptions> zai<O> zaa(Api<O> api, O o) {
        return new zai<>(api, o);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zai)) {
            return false;
        }
        zai zai = (zai) obj;
        return !this.zacu && !zai.zacu && Objects.equal(this.mApi, zai.mApi) && Objects.equal(this.zabh, zai.zabh);
    }

    public final int hashCode() {
        return this.zacv;
    }

    public final String zan() {
        return this.mApi.getName();
    }

    public static <O extends ApiOptions> zai<O> zaa(Api<O> api) {
        return new zai<>(api);
    }

    private zai(Api<O> api) {
        this.mApi = api;
        this.zabh = null;
        this.zacv = System.identityHashCode(this);
    }
}
