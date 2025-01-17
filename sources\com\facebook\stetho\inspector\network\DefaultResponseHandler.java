package com.facebook.stetho.inspector.network;

import java.io.IOException;

public class DefaultResponseHandler implements ResponseHandler {
    private int mBytesRead = 0;
    private int mDecodedBytesRead = -1;
    private final NetworkEventReporter mEventReporter;
    private final String mRequestId;

    public DefaultResponseHandler(NetworkEventReporter networkEventReporter, String str) {
        this.mEventReporter = networkEventReporter;
        this.mRequestId = str;
    }

    private void reportDataReceived() {
        NetworkEventReporter networkEventReporter = this.mEventReporter;
        String str = this.mRequestId;
        int i = this.mBytesRead;
        int i2 = this.mDecodedBytesRead;
        if (i2 < 0) {
            i2 = i;
        }
        networkEventReporter.dataReceived(str, i, i2);
    }

    public void onEOF() {
        reportDataReceived();
        this.mEventReporter.responseReadFinished(this.mRequestId);
    }

    public void onError(IOException iOException) {
        reportDataReceived();
        this.mEventReporter.responseReadFailed(this.mRequestId, iOException.toString());
    }

    public void onRead(int i) {
        this.mBytesRead += i;
    }

    public void onReadDecoded(int i) {
        if (this.mDecodedBytesRead == -1) {
            this.mDecodedBytesRead = 0;
        }
        this.mDecodedBytesRead += i;
    }
}
