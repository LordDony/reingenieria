package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class Gb extends C1120hc {
    /* access modifiers changed from: private */
    public static final AtomicLong c = new AtomicLong(Long.MIN_VALUE);
    /* access modifiers changed from: private */
    public Jb d;
    /* access modifiers changed from: private */
    public Jb e;
    private final PriorityBlockingQueue<Kb<?>> f = new PriorityBlockingQueue<>();
    private final BlockingQueue<Kb<?>> g = new LinkedBlockingQueue();
    private final UncaughtExceptionHandler h = new Ib(this, "Thread death: Uncaught exception on worker thread");
    private final UncaughtExceptionHandler i = new Ib(this, "Thread death: Uncaught exception on network thread");
    /* access modifiers changed from: private */
    public final Object j = new Object();
    /* access modifiers changed from: private */
    public final Semaphore k = new Semaphore(2);
    /* access modifiers changed from: private */
    public volatile boolean l;

    Gb(Mb mb) {
        super(mb);
    }

    public final <V> Future<V> a(Callable<V> callable) throws IllegalStateException {
        o();
        Preconditions.checkNotNull(callable);
        Kb kb = new Kb(this, callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.d) {
            if (!this.f.isEmpty()) {
                e().v().a("Callable skipped the worker queue.");
            }
            kb.run();
        } else {
            a(kb);
        }
        return kb;
    }

    public final <V> Future<V> b(Callable<V> callable) throws IllegalStateException {
        o();
        Preconditions.checkNotNull(callable);
        Kb kb = new Kb(this, callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.d) {
            kb.run();
        } else {
            a(kb);
        }
        return kb;
    }

    public final /* bridge */ /* synthetic */ Clock c() {
        return super.c();
    }

    public final /* bridge */ /* synthetic */ Gb d() {
        return super.d();
    }

    public final /* bridge */ /* synthetic */ C1124ib e() {
        return super.e();
    }

    public final /* bridge */ /* synthetic */ ce f() {
        return super.f();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void h() {
        if (Thread.currentThread() != this.e) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    public final void i() {
        if (Thread.currentThread() != this.d) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final /* bridge */ /* synthetic */ C1097d j() {
        return super.j();
    }

    public final /* bridge */ /* synthetic */ C1114gb k() {
        return super.k();
    }

    public final /* bridge */ /* synthetic */ Vd l() {
        return super.l();
    }

    /* access modifiers changed from: protected */
    public final boolean q() {
        return false;
    }

    public final boolean s() {
        return Thread.currentThread() == this.d;
    }

    public final void b(Runnable runnable) throws IllegalStateException {
        o();
        Preconditions.checkNotNull(runnable);
        Kb kb = new Kb(this, runnable, false, "Task exception on network thread");
        synchronized (this.j) {
            this.g.add(kb);
            if (this.e == null) {
                this.e = new Jb(this, "Measurement Network", this.g);
                this.e.setUncaughtExceptionHandler(this.i);
                this.e.start();
            } else {
                this.e.a();
            }
        }
    }

    public final void a(Runnable runnable) throws IllegalStateException {
        o();
        Preconditions.checkNotNull(runnable);
        a(new Kb<>(this, runnable, false, "Task exception on worker thread"));
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:16|17|(1:19)(1:20)|21|22|23) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        r2 = e().v();
        r3 = "Timed out waiting for ";
        r4 = java.lang.String.valueOf(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0026, code lost:
        if (r4.length() == 0) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        r3 = r3.concat(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        r3 = new java.lang.String(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        r2.a(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0036, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r2 = e().v();
        r3 = "Interrupted waiting for ";
        r4 = java.lang.String.valueOf(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0049, code lost:
        if (r4.length() != 0) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004b, code lost:
        r3 = r3.concat(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0050, code lost:
        r3 = new java.lang.String(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0056, code lost:
        r2.a(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        r1 = r1.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r1 != null) goto L_0x0036;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0037 */
    public final <T> T a(AtomicReference<T> atomicReference, long j2, String str, Runnable runnable) {
        synchronized (atomicReference) {
            d().a(runnable);
            atomicReference.wait(15000);
        }
    }

    public final /* bridge */ /* synthetic */ C1168rb b() {
        return super.b();
    }

    private final void a(Kb<?> kb) {
        synchronized (this.j) {
            this.f.add(kb);
            if (this.d == null) {
                this.d = new Jb(this, "Measurement Worker", this.f);
                this.d.setUncaughtExceptionHandler(this.h);
                this.d.start();
            } else {
                this.d.a();
            }
        }
    }

    public final /* bridge */ /* synthetic */ be a() {
        return super.a();
    }
}
