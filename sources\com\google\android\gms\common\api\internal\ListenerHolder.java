package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public final class ListenerHolder<L> {
    private final zaa zajj;
    private volatile L zajk;
    private final ListenerKey<L> zajl;

    @KeepForSdk
    public static final class ListenerKey<L> {
        private final L zajk;
        private final String zajn;

        @KeepForSdk
        ListenerKey(L l, String str) {
            this.zajk = l;
            this.zajn = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListenerKey)) {
                return false;
            }
            ListenerKey listenerKey = (ListenerKey) obj;
            return this.zajk == listenerKey.zajk && this.zajn.equals(listenerKey.zajn);
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zajk) * 31) + this.zajn.hashCode();
        }
    }

    @KeepForSdk
    public interface Notifier<L> {
        @KeepForSdk
        void notifyListener(L l);

        @KeepForSdk
        void onNotifyListenerFailed();
    }

    private final class zaa extends C1764us {
        public zaa(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            boolean z = true;
            if (message.what != 1) {
                z = false;
            }
            Preconditions.checkArgument(z);
            ListenerHolder.this.notifyListenerInternal((Notifier) message.obj);
        }
    }

    @KeepForSdk
    ListenerHolder(Looper looper, L l, String str) {
        this.zajj = new zaa(looper);
        Preconditions.checkNotNull(l, "Listener must not be null");
        this.zajk = l;
        Preconditions.checkNotEmpty(str);
        this.zajl = new ListenerKey<>(l, str);
    }

    @KeepForSdk
    public final void clear() {
        this.zajk = null;
    }

    @KeepForSdk
    public final ListenerKey<L> getListenerKey() {
        return this.zajl;
    }

    @KeepForSdk
    public final boolean hasListener() {
        return this.zajk != null;
    }

    @KeepForSdk
    public final void notifyListener(Notifier<? super L> notifier) {
        Preconditions.checkNotNull(notifier, "Notifier must not be null");
        this.zajj.sendMessage(this.zajj.obtainMessage(1, notifier));
    }

    /* access modifiers changed from: 0000 */
    @KeepForSdk
    public final void notifyListenerInternal(Notifier<? super L> notifier) {
        L l = this.zajk;
        if (l == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(l);
        } catch (RuntimeException e) {
            notifier.onNotifyListenerFailed();
            throw e;
        }
    }
}
