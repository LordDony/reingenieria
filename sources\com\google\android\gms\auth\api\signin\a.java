package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public interface a {
    PendingResult<Status> a(GoogleApiClient googleApiClient);
}
