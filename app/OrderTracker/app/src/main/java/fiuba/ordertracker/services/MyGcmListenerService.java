package fiuba.ordertracker.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {
    public MyGcmListenerService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        
        // Check if the user is logged in, in which case, the notification is shown.
        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);

        if(idVendedor != 0){

            String message = data.getString("message");
            Log.d("SERVER NOTIFICATION", "From: " + from);
            Log.d("SERVER NOTIFICATION", "Message: " + message);

            // Show notification
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
