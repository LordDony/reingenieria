package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;

public class Response<T extends Result> {
    private T zzap;

    public Response() {
    }

    /* access modifiers changed from: protected */
    public T getResult() {
        return this.zzap;
    }

    public void setResult(T t) {
        this.zzap = t;
    }

    protected Response(T t) {
        this.zzap = t;
    }
}
