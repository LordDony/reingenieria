package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;

/* renamed from: com.google.android.gms.internal.measurement.ud reason: case insensitive filesystem */
interface C1050ud {
    int a();

    @Deprecated
    void a(int i) throws IOException;

    void a(int i, double d) throws IOException;

    void a(int i, float f) throws IOException;

    void a(int i, int i2) throws IOException;

    void a(int i, long j) throws IOException;

    void a(int i, Ya ya) throws IOException;

    void a(int i, Object obj) throws IOException;

    void a(int i, Object obj, Ec ec) throws IOException;

    void a(int i, String str) throws IOException;

    void a(int i, List<Ya> list) throws IOException;

    void a(int i, List<?> list, Ec ec) throws IOException;

    void a(int i, List<Integer> list, boolean z) throws IOException;

    void a(int i, boolean z) throws IOException;

    @Deprecated
    void b(int i) throws IOException;

    void b(int i, int i2) throws IOException;

    void b(int i, long j) throws IOException;

    @Deprecated
    void b(int i, Object obj, Ec ec) throws IOException;

    void b(int i, List<String> list) throws IOException;

    @Deprecated
    void b(int i, List<?> list, Ec ec) throws IOException;

    void b(int i, List<Long> list, boolean z) throws IOException;

    void c(int i, int i2) throws IOException;

    void c(int i, long j) throws IOException;

    void c(int i, List<Integer> list, boolean z) throws IOException;

    void d(int i, int i2) throws IOException;

    void d(int i, long j) throws IOException;

    void d(int i, List<Long> list, boolean z) throws IOException;

    void e(int i, int i2) throws IOException;

    void e(int i, long j) throws IOException;

    void e(int i, List<Integer> list, boolean z) throws IOException;

    void f(int i, int i2) throws IOException;

    void f(int i, List<Boolean> list, boolean z) throws IOException;

    void g(int i, List<Integer> list, boolean z) throws IOException;

    void h(int i, List<Long> list, boolean z) throws IOException;

    void i(int i, List<Integer> list, boolean z) throws IOException;

    void j(int i, List<Integer> list, boolean z) throws IOException;

    void k(int i, List<Long> list, boolean z) throws IOException;

    void l(int i, List<Long> list, boolean z) throws IOException;

    void m(int i, List<Double> list, boolean z) throws IOException;

    void n(int i, List<Float> list, boolean z) throws IOException;
}
