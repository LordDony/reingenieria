package com.comscore.android.task;

class b extends Thread {
    private boolean a = false;
    private Object b;
    private TaskExecutor c;
    private TaskExceptionHandler d;

    b(TaskExecutor taskExecutor, TaskExceptionHandler taskExceptionHandler) {
        this.d = taskExceptionHandler;
        this.c = taskExecutor;
        this.b = new Object();
    }

    private void a(long j) {
        synchronized (this.b) {
            try {
                this.b.wait(j);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.a = true;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        synchronized (this.b) {
            this.b.notify();
        }
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        long a2 = this.c.a();
        if (a2 > 0) {
            a(a2);
        }
    }

    public void run() {
        while (!a()) {
            a b2 = this.c.b();
            if (b2 != null) {
                b2.run();
                if (b2.j() != null) {
                    TaskExceptionHandler taskExceptionHandler = this.d;
                    if (taskExceptionHandler != null) {
                        taskExceptionHandler.exception(b2.j(), this.c, b2.i());
                    }
                }
                this.c.a(b2);
                if (b2.g()) {
                    this.c.execute(b2.i(), b2.h(), b2.h());
                }
            } else {
                d();
            }
        }
    }
}
