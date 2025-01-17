package androidx.core.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel.Stub;

public abstract class NotificationCompatSideChannelService extends Service {

    private class a extends Stub {
        a() {
        }

        public void cancel(String str, int i, String str2) throws RemoteException {
            NotificationCompatSideChannelService.this.a(Binder.getCallingUid(), str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.a(str, i, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelAll(String str) {
            NotificationCompatSideChannelService.this.a(Binder.getCallingUid(), str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.a(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notify(String str, int i, String str2, Notification notification) throws RemoteException {
            NotificationCompatSideChannelService.this.a(Binder.getCallingUid(), str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.a(str, i, str2, notification);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, String str) {
        String[] packagesForUid = getPackageManager().getPackagesForUid(i);
        int length = packagesForUid.length;
        int i2 = 0;
        while (i2 < length) {
            if (!packagesForUid[i2].equals(str)) {
                i2++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("NotificationSideChannelService: Uid ");
        sb.append(i);
        sb.append(" is not authorized for package ");
        sb.append(str);
        throw new SecurityException(sb.toString());
    }

    public abstract void a(String str);

    public abstract void a(String str, int i, String str2);

    public abstract void a(String str, int i, String str2, Notification notification);

    public IBinder onBind(Intent intent) {
        if (!intent.getAction().equals("android.support.BIND_NOTIFICATION_SIDE_CHANNEL") || VERSION.SDK_INT > 19) {
            return null;
        }
        return new a();
    }
}
