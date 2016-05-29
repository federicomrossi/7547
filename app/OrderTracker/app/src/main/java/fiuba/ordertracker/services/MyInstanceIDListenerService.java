package fiuba.ordertracker.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    /**
     * Called if InstanceID token is updated.
     * May occur if the security of the previous token had been compromised
     * and is initiated by the InstanceID provider.
     */
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
