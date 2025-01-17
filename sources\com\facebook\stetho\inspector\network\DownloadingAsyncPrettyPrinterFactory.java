package com.facebook.stetho.inspector.network;

import com.facebook.stetho.common.ExceptionUtil;
import com.facebook.stetho.common.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class DownloadingAsyncPrettyPrinterFactory implements AsyncPrettyPrinterFactory {

    protected class MatchResult {
        private final PrettyPrinterDisplayType mDisplayType;
        private final String mSchemaUri;

        public MatchResult(String str, PrettyPrinterDisplayType prettyPrinterDisplayType) {
            this.mSchemaUri = str;
            this.mDisplayType = prettyPrinterDisplayType;
        }

        public PrettyPrinterDisplayType getDisplayType() {
            return this.mDisplayType;
        }

        public String getSchemaUri() {
            return this.mSchemaUri;
        }
    }

    private static class Request implements Callable<String> {
        private URL url;

        public Request(URL url2) {
            this.url = url2;
        }

        public String call() throws IOException {
            HttpURLConnection httpURLConnection = (HttpURLConnection) this.url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    return Util.readAsUTF8(inputStream);
                } finally {
                    inputStream.close();
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Got status code: ");
                sb.append(responseCode);
                sb.append(" while downloading schema with url: ");
                sb.append(this.url.toString());
                throw new IOException(sb.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    public static void doErrorPrint(PrintWriter printWriter, InputStream inputStream, String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("\n");
        sb.append(Util.readAsUTF8(inputStream));
        printWriter.print(sb.toString());
    }

    private static AsyncPrettyPrinter getErrorAsyncPrettyPrinter(final String str, final String str2) {
        return new AsyncPrettyPrinter() {
            public PrettyPrinterDisplayType getPrettifiedType() {
                return PrettyPrinterDisplayType.TEXT;
            }

            public void printTo(PrintWriter printWriter, InputStream inputStream) throws IOException {
                StringBuilder sb = new StringBuilder();
                sb.append("[Failed to parse header: ");
                sb.append(str);
                sb.append(" : ");
                sb.append(str2);
                sb.append(" ]");
                DownloadingAsyncPrettyPrinterFactory.doErrorPrint(printWriter, inputStream, sb.toString());
            }
        };
    }

    private static URL parseURL(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public abstract void doPrint(PrintWriter printWriter, InputStream inputStream, String str) throws IOException;

    public AsyncPrettyPrinter getInstance(String str, String str2) {
        final MatchResult matchAndParseHeader = matchAndParseHeader(str, str2);
        if (matchAndParseHeader == null) {
            return null;
        }
        URL parseURL = parseURL(matchAndParseHeader.getSchemaUri());
        if (parseURL == null) {
            return getErrorAsyncPrettyPrinter(str, str2);
        }
        ExecutorService executorService = AsyncPrettyPrinterExecutorHolder.getExecutorService();
        if (executorService == null) {
            return null;
        }
        final Future submit = executorService.submit(new Request(parseURL));
        return new AsyncPrettyPrinter() {
            public PrettyPrinterDisplayType getPrettifiedType() {
                return matchAndParseHeader.getDisplayType();
            }

            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
                r1 = new java.lang.StringBuilder();
                r1.append("Encountered spurious interrupt while downloading schema for pretty printing: ");
                r1.append(r0.getMessage());
                com.facebook.stetho.inspector.network.DownloadingAsyncPrettyPrinterFactory.access$000(r4, r5, r1.toString());
             */
            /* JADX WARNING: Code restructure failed: missing block: B:4:0x000e, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Removed duplicated region for block: B:4:0x000e A[Catch:{ ExecutionException -> 0x0010, InterruptedException -> 0x000e, InterruptedException -> 0x000e, ExecutionException -> 0x0037 }, ExcHandler: InterruptedException (r0v3 'e' java.lang.InterruptedException A[CUSTOM_DECLARE, Catch:{  }]), Splitter:B:0:0x0000] */
            public void printTo(PrintWriter printWriter, InputStream inputStream) throws IOException {
                try {
                    DownloadingAsyncPrettyPrinterFactory.this.doPrint(printWriter, inputStream, (String) submit.get());
                } catch (ExecutionException e) {
                    if (IOException.class.isInstance(e.getCause())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Cannot successfully download schema: ");
                        sb.append(e.getMessage());
                        DownloadingAsyncPrettyPrinterFactory.doErrorPrint(printWriter, inputStream, sb.toString());
                        return;
                    }
                    throw e;
                } catch (InterruptedException e2) {
                } catch (ExecutionException e3) {
                    ExceptionUtil.propagate(e3.getCause());
                    throw null;
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public abstract MatchResult matchAndParseHeader(String str, String str2);
}
