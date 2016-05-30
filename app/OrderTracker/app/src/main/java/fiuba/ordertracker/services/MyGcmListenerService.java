package fiuba.ordertracker.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

import fiuba.ordertracker.NotificationActivity;
import fiuba.ordertracker.R;

public class MyGcmListenerService extends GcmListenerService {

    public MyGcmListenerService() {
    }

    String TAG = getClass().getName();
    int countNotification = 0;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        // Check if the user is logged in, in which case, the notification is shown.
        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);

        if(idVendedor != 0){

            String message = data.getString("message");
            Log.d("SERVER NOTIFICATION", "From: " + from);
            Log.d("SERVER NOTIFICATION", "Message: " + message);

            // Show notification
            notifyUser(getApplicationContext(), message);
        }
    }

    public void notifyUser(Context context,String data){
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtra("data", data);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_shopping_cart_white_24dp);
        builder.setAutoCancel(true);
        builder.setContentTitle("Order Tracker");
        builder.setContentIntent(pendingIntent);
        builder.setContentText(data);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setVibrate(new long[] { 1000, 1000});
        notificationManager.notify(countNotification++, builder.build());
    }
}
