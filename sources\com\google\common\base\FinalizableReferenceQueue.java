package com.google.common.base;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalizableReferenceQueue implements Closeable {
    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final Method startFinalizer = getStartFinalizer(loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader()));
    final PhantomReference<Object> frqRef = new PhantomReference<>(this, this.queue);
    final ReferenceQueue<Object> queue = new ReferenceQueue<>();
    final boolean threadStarted;

    static class DecoupledLoader implements FinalizerLoader {
        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.";

        DecoupledLoader() {
        }

        /* access modifiers changed from: 0000 */
        public URL getBaseUrl() throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(FinalizableReferenceQueue.FINALIZER_CLASS_NAME.replace('.', '/'));
            sb.append(".class");
            String sb2 = sb.toString();
            URL resource = DecoupledLoader.class.getClassLoader().getResource(sb2);
            if (resource != null) {
                String url = resource.toString();
                if (url.endsWith(sb2)) {
                    return new URL(resource, url.substring(0, url.length() - sb2.length()));
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Unsupported path style: ");
                sb3.append(url);
                throw new IOException(sb3.toString());
            }
            throw new FileNotFoundException(sb2);
        }

        public Class<?> loadFinalizer() {
            try {
                return newLoader(getBaseUrl()).loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (Exception e) {
                FinalizableReferenceQueue.logger.log(Level.WARNING, LOADING_ERROR, e);
                return null;
            }
        }

        /* access modifiers changed from: 0000 */
        public URLClassLoader newLoader(URL url) {
            return new URLClassLoader(new URL[]{url}, null);
        }
    }

    static class DirectLoader implements FinalizerLoader {
        DirectLoader() {
        }

        public Class<?> loadFinalizer() {
            try {
                return Class.forName(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
    }

    interface FinalizerLoader {
        Class<?> loadFinalizer();
    }

    static class SystemLoader implements FinalizerLoader {
        static boolean disabled;

        SystemLoader() {
        }

        public Class<?> loadFinalizer() {
            if (disabled) {
                return null;
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (systemClassLoader != null) {
                    try {
                        return systemClassLoader.loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
                    } catch (ClassNotFoundException unused) {
                    }
                }
                return null;
            } catch (SecurityException unused2) {
                FinalizableReferenceQueue.logger.info("Not allowed to access system class loader.");
                return null;
            }
        }
    }

    public FinalizableReferenceQueue() {
        boolean z = true;
        try {
            startFinalizer.invoke(null, new Object[]{FinalizableReference.class, this.queue, this.frqRef});
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (Throwable th) {
            logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", th);
            z = false;
        }
        this.threadStarted = z;
    }

    static Method getStartFinalizer(Class<?> cls) {
        try {
            return cls.getMethod("startFinalizer", new Class[]{Class.class, ReferenceQueue.class, PhantomReference.class});
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    private static Class<?> loadFinalizer(FinalizerLoader... finalizerLoaderArr) {
        for (FinalizerLoader loadFinalizer : finalizerLoaderArr) {
            Class<?> loadFinalizer2 = loadFinalizer.loadFinalizer();
            if (loadFinalizer2 != null) {
                return loadFinalizer2;
            }
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void cleanUp() {
        if (!this.threadStarted) {
            while (true) {
                Reference poll = this.queue.poll();
                if (poll != null) {
                    poll.clear();
                    try {
                        ((FinalizableReference) poll).finalizeReferent();
                    } catch (Throwable th) {
                        logger.log(Level.SEVERE, "Error cleaning up after reference.", th);
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void close() {
        this.frqRef.enqueue();
        cleanUp();
    }
}
