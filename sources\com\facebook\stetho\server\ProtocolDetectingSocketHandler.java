package com.facebook.stetho.server;

import android.content.Context;
import android.net.LocalSocket;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ProtocolDetectingSocketHandler extends SecureSocketHandler {
    private static final int SENSING_BUFFER_SIZE = 256;
    private final ArrayList<HandlerInfo> mHandlers = new ArrayList<>(2);

    public static class AlwaysMatchMatcher implements MagicMatcher {
        public boolean matches(InputStream inputStream) throws IOException {
            return true;
        }
    }

    public static class ExactMagicMatcher implements MagicMatcher {
        private final byte[] mMagic;

        public ExactMagicMatcher(byte[] bArr) {
            this.mMagic = bArr;
        }

        public boolean matches(InputStream inputStream) throws IOException {
            byte[] bArr = new byte[this.mMagic.length];
            return inputStream.read(bArr) == bArr.length && Arrays.equals(bArr, this.mMagic);
        }
    }

    private static class HandlerInfo {
        public final SocketLikeHandler handler;
        public final MagicMatcher magicMatcher;

        private HandlerInfo(MagicMatcher magicMatcher2, SocketLikeHandler socketLikeHandler) {
            this.magicMatcher = magicMatcher2;
            this.handler = socketLikeHandler;
        }
    }

    public interface MagicMatcher {
        boolean matches(InputStream inputStream) throws IOException;
    }

    public ProtocolDetectingSocketHandler(Context context) {
        super(context);
    }

    public void addHandler(MagicMatcher magicMatcher, SocketLikeHandler socketLikeHandler) {
        this.mHandlers.add(new HandlerInfo(magicMatcher, socketLikeHandler));
    }

    /* access modifiers changed from: protected */
    public void onSecured(LocalSocket localSocket) throws IOException {
        LeakyBufferedInputStream leakyBufferedInputStream = new LeakyBufferedInputStream(localSocket.getInputStream(), SENSING_BUFFER_SIZE);
        if (!this.mHandlers.isEmpty()) {
            int size = this.mHandlers.size();
            for (int i = 0; i < size; i++) {
                HandlerInfo handlerInfo = (HandlerInfo) this.mHandlers.get(i);
                leakyBufferedInputStream.mark(SENSING_BUFFER_SIZE);
                boolean matches = handlerInfo.magicMatcher.matches(leakyBufferedInputStream);
                leakyBufferedInputStream.reset();
                if (matches) {
                    handlerInfo.handler.onAccepted(new SocketLike(localSocket, leakyBufferedInputStream));
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("No matching handler, firstByte=");
            sb.append(leakyBufferedInputStream.read());
            throw new IOException(sb.toString());
        }
        throw new IllegalStateException("No handlers added");
    }
}
