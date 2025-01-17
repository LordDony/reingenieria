package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult.CallbackHandler;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;

@KeepForSdk
public class BaseImplementation {

    @KeepForSdk
    public static abstract class ApiMethodImpl<R extends Result, A extends AnyClient> extends BasePendingResult<R> implements ResultHolder<R> {
        @KeepForSdk
        private final Api<?> mApi;
        @KeepForSdk
        private final AnyClientKey<A> mClientKey;

        @KeepForSdk
        @Deprecated
        protected ApiMethodImpl(AnyClientKey<A> anyClientKey, GoogleApiClient googleApiClient) {
            Preconditions.checkNotNull(googleApiClient, "GoogleApiClient must not be null");
            super(googleApiClient);
            Preconditions.checkNotNull(anyClientKey);
            this.mClientKey = anyClientKey;
            this.mApi = null;
        }

        /* access modifiers changed from: protected */
        @KeepForSdk
        public abstract void doExecute(A a) throws RemoteException;

        @KeepForSdk
        public final Api<?> getApi() {
            return this.mApi;
        }

        @KeepForSdk
        public final AnyClientKey<A> getClientKey() {
            return this.mClientKey;
        }

        /* access modifiers changed from: protected */
        @KeepForSdk
        public void onSetFailedResult(R r) {
        }

        @KeepForSdk
        public final void run(A a) throws DeadObjectException {
            if (a instanceof SimpleClientAdapter) {
                a = ((SimpleClientAdapter) a).getClient();
            }
            try {
                doExecute(a);
            } catch (DeadObjectException e) {
                setFailedResult((RemoteException) e);
                throw e;
            } catch (RemoteException e2) {
                setFailedResult(e2);
            }
        }

        @KeepForSdk
        public final void setFailedResult(Status status) {
            Preconditions.checkArgument(!status.isSuccess(), "Failed result must not be success");
            Result createFailedResult = createFailedResult(status);
            setResult(createFailedResult);
            onSetFailedResult(createFailedResult);
        }

        @KeepForSdk
        public /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }

        @KeepForSdk
        protected ApiMethodImpl(Api<?> api, GoogleApiClient googleApiClient) {
            Preconditions.checkNotNull(googleApiClient, "GoogleApiClient must not be null");
            super(googleApiClient);
            Preconditions.checkNotNull(api, "Api must not be null");
            this.mClientKey = api.getClientKey();
            this.mApi = api;
        }

        @KeepForSdk
        private void setFailedResult(RemoteException remoteException) {
            setFailedResult(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        @KeepForSdk
        protected ApiMethodImpl(CallbackHandler<R> callbackHandler) {
            super(callbackHandler);
            this.mClientKey = null;
            this.mApi = null;
        }
    }

    @KeepForSdk
    public interface ResultHolder<R> {
        @KeepForSdk
        void setFailedResult(Status status);

        @KeepForSdk
        void setResult(R r);
    }
}
