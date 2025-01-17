package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import java.lang.ref.SoftReference;

public class BufferRecyclers {
    private static final ThreadLocalBufferManager _bufferRecyclerTracker = ("true".equals(System.getProperty("com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers")) ? ThreadLocalBufferManager.instance() : null);
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _encoderRef = new ThreadLocal<>();
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef = new ThreadLocal<>();

    public static BufferRecycler getBufferRecycler() {
        BufferRecycler bufferRecycler;
        SoftReference softReference;
        SoftReference softReference2 = (SoftReference) _recyclerRef.get();
        if (softReference2 == null) {
            bufferRecycler = null;
        } else {
            bufferRecycler = (BufferRecycler) softReference2.get();
        }
        if (bufferRecycler == null) {
            bufferRecycler = new BufferRecycler();
            ThreadLocalBufferManager threadLocalBufferManager = _bufferRecyclerTracker;
            if (threadLocalBufferManager != null) {
                softReference = threadLocalBufferManager.wrapAndTrack(bufferRecycler);
            } else {
                softReference = new SoftReference(bufferRecycler);
            }
            _recyclerRef.set(softReference);
        }
        return bufferRecycler;
    }

    public static JsonStringEncoder getJsonStringEncoder() {
        JsonStringEncoder jsonStringEncoder;
        SoftReference softReference = (SoftReference) _encoderRef.get();
        if (softReference == null) {
            jsonStringEncoder = null;
        } else {
            jsonStringEncoder = (JsonStringEncoder) softReference.get();
        }
        if (jsonStringEncoder != null) {
            return jsonStringEncoder;
        }
        JsonStringEncoder jsonStringEncoder2 = new JsonStringEncoder();
        _encoderRef.set(new SoftReference(jsonStringEncoder2));
        return jsonStringEncoder2;
    }

    public static char[] quoteAsJsonText(String str) {
        return getJsonStringEncoder().quoteAsString(str);
    }
}
