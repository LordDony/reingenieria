package com.facebook.login;

import com.facebook.AccessToken;
import java.util.Set;

/* compiled from: LoginResult */
public class E {
    private final AccessToken a;
    private final Set<String> b;
    private final Set<String> c;

    public E(AccessToken accessToken, Set<String> set, Set<String> set2) {
        this.a = accessToken;
        this.b = set;
        this.c = set2;
    }

    public AccessToken a() {
        return this.a;
    }

    public Set<String> b() {
        return this.c;
    }

    public Set<String> c() {
        return this.b;
    }
}
